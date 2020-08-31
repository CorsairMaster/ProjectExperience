package com.quickly.devploment.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName HttpTest
 * @Description
 * @Author LiDengJin
 * @Date 2019/11/12 14:03
 * @Version V-1.0
 **/
@Slf4j
public class HttpTest {

	@Test
	public void testHttpHost() {
		HttpHost proxy = new HttpHost("127.0.0.1", 9876, "HTTP");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet request = new HttpGet("url");
		try {
			CloseableHttpResponse response = httpclient.execute(proxy, request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1(){
		Set<Integer> integers = new HashSet<>();
		integers.add(1);

		System.out.println(integers.contains(1));
	}

	@Test
	public void testIpAddress(){
		ArrayList inetAddressList = new ArrayList();

		try {
			Enumeration enumeration = NetworkInterface.getNetworkInterfaces();

			while(enumeration.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface)enumeration.nextElement();
				Enumeration addrs = networkInterface.getInetAddresses();

				while(addrs.hasMoreElements()) {
					inetAddressList.add(((InetAddress)addrs.nextElement()).getHostAddress());
				}
			}

			InetAddress addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
		} catch (Exception var4) {
			throw new RuntimeException("get local inet address fail", var4);
		}
		log.info("inetAddressList --> {}", inetAddressList);
	}

	@Test
	public void testHttpGet(){
//		HttpHost proxy = new HttpHost("127.0.0.1", 9876, "HTTP");
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet request = new HttpGet("https://www.soshuw.com/ZheTian/395054.html");
		try {
			CloseableHttpResponse response = httpclient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			String resultJson = EntityUtils.toString(responseEntity, "UTF-8");
			log.info("response {}", resultJson);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testHttpGetByJsoup(){
		String index = "5055";
		String url = "https://www.soshuw.com/ZheTian/39"+index+".html";
		Connection connect = Jsoup.connect(url);
		try {
			Document document = connect.get();
			Elements content = document.getElementsByClass("content");
			String text = content.toString().replaceAll("&nbsp;","").replaceAll("<br>", " ");
			String substring = text.substring(0, text.indexOf("<p"));
			//			String text = content.text();
//						log.info("document {}", document.toString());
//			log.info("content {}", content.toString());
//			log.info("text {}", text);
			log.info("substring {}", substring);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
