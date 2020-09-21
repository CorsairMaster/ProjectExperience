package com.quickly.devploment.forkjoin;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * @Author lidengjin
 * @Date 2020/9/21 6:06 下午
 * @Version 1.0
 */
@Slf4j
public final class ManagedForkJoinPool {

	/**
	 * 默认核心线程数
	 */
	private static final int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors() << 1;
	/**
	 * 线程工厂
	 */
	private static final ForkJoinPool.ForkJoinWorkerThreadFactory FACTORY = new ForkJoinPool.ForkJoinWorkerThreadFactory() {
		private final String namingPattern = "ForkJoinPool.external.worker-%d";
		private final AtomicLong threadCounter = new AtomicLong(0);

		@Override
		public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
			return buildThread(pool);
		}

		private ForkJoinWorkerThread buildThread(ForkJoinPool pool) {
			ForkJoinWorkerThread thread = new ForkJoinWorkerThread(pool) {};
			thread.setName(String.format(namingPattern, threadCounter.incrementAndGet()));
			thread.setDaemon(true);
			thread.setContextClassLoader(ForkJoinWorkerThread.class.getClassLoader());
			return thread;
		}
	};
	/**
	 * 线程池
	 */
	private static ForkJoinPool FORK_JOIN_POOL = buildForkJoinPool(DEFAULT_CORE_SIZE);

	static {
		// 关闭事件的挂钩
		Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdownForkJoinPool(FORK_JOIN_POOL)));
	}

	private static void shutdownForkJoinPool(ForkJoinPool pool) {
		log.info("ManagedForkJoinPool shutting down.");
		pool.shutdown();
		try {
			// 等待1分执行关闭
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				log.error("ManagedForkJoinPool shutdown immediately due to wait timeout.");
				pool.shutdownNow();
			}
		} catch (InterruptedException e) {
			log.error("ManagedForkJoinPool shutdown interrupted.");
			pool.shutdownNow();
		}
		log.info("ManagedForkJoinPool shutdown complete.");
	}

	@EnableScheduling
	@Component
	public static class InternalSpringValueProcessor {
		private static final Executor EXECUTOR = Executors.newFixedThreadPool(1);

		@Value("${fork.join.pool.parallelism:0}")
		public void setForkJoinPoolParallelism(int parallelism) {
			if (parallelism > 0 && FORK_JOIN_POOL.getParallelism() != parallelism) {
				ForkJoinPool oldPool = FORK_JOIN_POOL;
				FORK_JOIN_POOL = buildForkJoinPool(parallelism);
				EXECUTOR.execute(() -> shutdownForkJoinPool(oldPool));
				log.debug("替换ForkJoinPool线程池");
			}
		}

		@Scheduled(cron = "0/1 * * * * ?")
		public void scheduled() {
			log.info("ForkJoinPool-Scheduled parallelism: {}, active: {}, queue: {}", FORK_JOIN_POOL.getParallelism(), FORK_JOIN_POOL.getActiveThreadCount(), FORK_JOIN_POOL.getQueuedTaskCount());
		}
	}

	/**
	 * 构建线程池
	 */
	private static ForkJoinPool buildForkJoinPool(int parallelism) {
		// asyncMode: 工作线程内的任务队列模式, true:先进先出(类似消息队列), false:后进先出(类似递归式任务)
		return new ForkJoinPool(Math.min(parallelism, Byte.MAX_VALUE), FACTORY, null, false);
	}

	private static class TaskWrapper<V> extends RecursiveTask<List<V>> {
		private final List<Callable<V>> tasks;
		private final Map<String, String> context;

		private TaskWrapper(List<Callable<V>> tasks, Map<String, String> context) {
			if (tasks == null || tasks.isEmpty() || tasks.stream().anyMatch(Objects::isNull)) {
				throw new IllegalArgumentException("task must be not null");
			}
			this.tasks = tasks;
			this.context = context;
		}

		@Override
		protected List<V> compute() {
			if (context != null) {
				MDC.setContextMap(context);
			}
			try {
				if (tasks.size() == 1) {
					return Collections.singletonList(tasks.get(0).call());
				}
				// 拆解任务
				List<TaskWrapper<V>> taskWrapperList = tasks.stream()
						.map(task -> new TaskWrapper<>(Collections.singletonList(task), context)).collect(Collectors.toList());
				// 指定任务
				invokeAll(taskWrapperList);
				// 聚合任务结果
				return taskWrapperList.stream()
						.map(ForkJoinTask::join)
						.filter(CollectionUtils::isNotEmpty)
						.flatMap(Collection::stream)
						.collect(Collectors.toList());
			} catch (Throwable e) {
				if (e instanceof RuntimeException) {
					throw (RuntimeException) e;
				}
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

	/**
	 * 提交一个任务
	 */
	public static <T> Future<T> executeTask(Callable<T> task) {
		return FORK_JOIN_POOL.submit(task);
	}

	/**
	 * 提交一组任务
	 */
	public static <T> Future<List<T>> executeTask(Callable<T>... tasks) {
		return executeTask(Arrays.asList(tasks));
	}

	/**
	 * 提交一组任务
	 */
	public static <T> Future<List<T>> executeTask(List<Callable<T>> tasks) {
		try {
			Map<String, String> context = MDC.getCopyOfContextMap();
			return FORK_JOIN_POOL.submit(new TaskWrapper<>(tasks, context));
		} catch (RejectedExecutionException e) {
			log.error("Task executing was rejected.", e);
			throw new UnsupportedOperationException("Unable to submit the task, rejected.", e);
		} finally {
			log.info("ForkJoinPool", String.format("parallelism: %d, active: %d, queue: %d", FORK_JOIN_POOL.getParallelism(), FORK_JOIN_POOL.getActiveThreadCount(), FORK_JOIN_POOL.getQueuedTaskCount()));
		}
	}

	/**
	 * 执行一组任务并等待结果
	 */
	public static <T> List<T> executeAndWaitResult(List<Callable<T>> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return Collections.emptyList();
		}

		try {
			return executeTask(tasks).get();
		} catch (Throwable e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
