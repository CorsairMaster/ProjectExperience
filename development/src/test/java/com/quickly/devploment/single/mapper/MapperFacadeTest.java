package com.quickly.devploment.single.mapper;

import com.quickly.devploment.single.mapper.vo.UserVo;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lidengjin
 * @Date 2020/8/31 4:43 下午
 * @Version 1.0
 */
public class MapperFacadeTest {

	public  static MapperFacade getObject() throws Exception {
		return new DefaultMapperFactory.Builder().build().getMapperFacade();
	}

	@Test
	public void testMapToClass() throws Exception {
		MapperFacade mapperFacade = getObject();
		Map<String,Object> map = new HashMap<>();
		map.put("username","lisan");
		map.put("password","lisan");
		map.put("age",123);
		map.put("amount",new BigDecimal("123"));
		map.put("names", Arrays.asList("1","3","213"));
		Map<String, Object> student1 = new HashMap<>();
		student1.put("name","111");
		student1.put("age",14);
		student1.put("debit",Arrays.asList("2","4","213"));
		Map<String, Object> student2 = new HashMap<>();
		student2.put("name","122");
		student2.put("age",14);
		student2.put("debit",Arrays.asList("2","4","213"));
		Map<String, Object> student3 = new HashMap<>();
		student3.put("name","133");
		student3.put("age",14);
		student3.put("debit",Arrays.asList("2","4","213"));
		map.put("students", Arrays.asList(student1,student2, student3));
		UserVo map1 = mapperFacade.map(map, UserVo.class);
		System.out.println(map1.toString());

	}
}
