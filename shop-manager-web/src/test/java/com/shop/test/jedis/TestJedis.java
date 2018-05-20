package com.shop.test.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestJedis {

	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("47.95.1.113",6379,10000);
		
		jedis.set("hello", "This is my alibaba redis server!");
		
		String result = jedis.get("hello");
		System.out.println(result);
		jedis.close();
	}

	
}
