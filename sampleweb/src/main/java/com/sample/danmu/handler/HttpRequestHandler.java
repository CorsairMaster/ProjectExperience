package com.sample.danmu.handler;

/**
 * @Author lidengjin
 * @Date 2020/12/18 2:28 下午
 * @Version 1.0
 */

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

/**
 * 处理 Http 请求
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> { //1
	private final String wsUri;
	private static File INDEX = new File("websocket.html");
	//	private static final String INDEX;

	static {

		//		URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();
		try {
//			String path = ResourceUtils.getURL("classpath:").getPath();
//			//			String path = location.toURI() + "WebsocketDanMu.html";
//			//			String path = location.toURI() + "static/WebsocketDanMu.html";
//			System.out.println("origin file path ----" + path);
//			path = !path.contains("file:") ? path : path.substring(path.indexOf("/"));
//			path = path + "static/WebsocketDanMu.html";
//			System.out.println("change file --->" + path);

			// 注意打成 jar 包，之后不一定能访问到file 文件，此时需要使用inputstream 流式处理，然后生成新的文件就可以了。
			String path = "static/WebsocketDanMu.html";
			getFiles(path);
		} catch (Exception e) {
			throw new IllegalStateException("Unable to locate WebsocketChatClient.html", e);
		}

		// /Users/lidengjin/study/sourceofgithub/mygithub/ProjectExperience/sampleweb/target/sampleweb-1.0-SNAPSHOT.jar!/BOOT-INF/classes!/static/WebsocketDanMu.html
	}

	private static void getFiles(String path) throws IOException {

		ClassPathResource resource = new ClassPathResource(path);
		InputStream inputStream = resource.getInputStream();
		// commons-io
		FileUtils.copyInputStreamToFile(inputStream, INDEX);
	}

	public HttpRequestHandler(String wsUri) {
		this.wsUri = wsUri;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (wsUri.equalsIgnoreCase(request.getUri())) {
			ctx.fireChannelRead(request.retain());
		} else {
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(ctx);
			}

			RandomAccessFile file = new RandomAccessFile(INDEX, "r");

			HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

			boolean keepAlive = HttpHeaders.isKeepAlive(request);

			if (keepAlive) {
				response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
				response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
			}
			ctx.write(response);

			if (ctx.pipeline().get(SslHandler.class) == null) {
				ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
			} else {
				ctx.write(new ChunkedNioFile(file.getChannel()));
			}
			ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
			if (!keepAlive) {
				future.addListener(ChannelFutureListener.CLOSE);
			}

			file.close();
		}
	}

	private static void send100Continue(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		ctx.writeAndFlush(response);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("Client:" + incoming.remoteAddress() + "异常");
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}
}
