package com.quickly.devploment.mapdb;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;


/**
 * @Author lidengjin
 * @Date 2020/12/9 9:28 上午
 * @Version 1.0
 */
public class MapDbTest {
	@Test
	public void testMapDB() {
		DB db = DBMaker.fileDB("file3.db").fileMmapEnable().transactionEnable().closeOnJvmShutdown() //JVM关闭时关闭db
				.make();
		ConcurrentMap<String, Long> map = db.hashMap("mapsl3", Serializer.STRING, Serializer.LONG).createOrOpen();
		map.put("a", 1L);
		map.put("b", 2L);
		db.commit();
		System.out.println(map.get("a"));
		System.out.println(map.get("b"));
		map.put("c", 3L);
		System.out.println("rollback之前，c:" + map.get("c"));
		db.rollback();
		System.out.println("rollback之后，a:" + map.get("a"));
		System.out.println("rollback之后，c:" + map.get("c"));
	}

	@Test
	public void testSaveLocal(){
		String dir = "/Users/lidengjin/study/files/mapdb_delete";
		String hashMap = "hashMap";
		try {
			FileUtils.forceMkdir(new File(dir));
//			DB db = DBMaker.fileDB(new File(dir)).fileMmapEnable().transactionEnable().closeOnJvmShutdown().make();
			DB db = DBMaker.fileDB(new File(dir+"/index")).fileMmapEnable().transactionEnable().closeOnJvmShutdown().make();
			ConcurrentMap<String, String> map = db.hashMap(hashMap, Serializer.STRING, Serializer.STRING).createOrOpen();
			map.put("name", "zhangsan");
			db.commit();
			String name = map.get("name");
			System.out.println(name);
			db.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
