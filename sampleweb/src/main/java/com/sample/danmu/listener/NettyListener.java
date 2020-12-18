package com.sample.danmu.listener;

import com.sample.danmu.DanMuServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author lidengjin
 * @Date 2020/12/18 2:35 下午
 * @Version 1.0
 */
@Component
public class NettyListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			try {
				DanMuServer.getInstance().start(8081);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
