package com.tb.common.demo;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class redisDemoCluster {
	
	
	public static void main(String[] args) {
		  JedisPoolConfig poolConfig = new JedisPoolConfig();	
		Set<HostAndPort> nodes=new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("169.254.218.128".toString(), 6379));
	    nodes.add(new HostAndPort("169.254.218.128".toString(), 6380));
	    nodes.add(new HostAndPort("169.254.218.128".toString(), 6381));
		JedisCluster jc=new JedisCluster(nodes, poolConfig);
		jc.set("123", "asd");
		String keys = jc.get("123");
		System.out.println(keys);
	}

}
