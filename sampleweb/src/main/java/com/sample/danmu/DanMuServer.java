package com.sample.danmu;

import com.sample.danmu.init.WebsocketDanmuServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Author lidengjin
 * @Date 2020/12/18 2:25 下午
 * @Version 1.0
 */
@Component
public class DanMuServer {
	private static class SingletionWSServer {
		static final DanMuServer instance = new DanMuServer();
	}

	public static DanMuServer getInstance() {
		return SingletionWSServer.instance;
	}

	private EventLoopGroup workerGroup;
	private EventLoopGroup bossGroup;
	private ServerBootstrap server;

	public DanMuServer() {
		workerGroup = new NioEventLoopGroup(10);
		bossGroup = new NioEventLoopGroup(1000);
		server = new ServerBootstrap();
		server.group(workerGroup, bossGroup).channel(NioServerSocketChannel.class)
				.childHandler(new WebsocketDanmuServerInitializer())
				.option(ChannelOption.SO_BACKLOG, 128)          // (5)
				.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
	}

	public void start(int port) throws Exception {

		System.out.println("SnakeGameServer 启动了" + port);
		// 绑定端口，开始接收进来的连接
		try {
			ChannelFuture f = server.bind(port).sync();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();

			System.out.println("SnakeGameServer 关闭了");
		}
		// 等待服务器  socket 关闭 。
		// 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。

	}
}
