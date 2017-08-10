package com.tb.common.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tb.common.service.cache.intf.RedisFunction;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;

@Service("redisCluster")
public class RedisClusterService {
	
	@Autowired(required=false)//在spring容器当中找，找到注入，否者忽略
	private JedisCluster  JedisCluster ;
	
	 public  String set(final String key,final String value){  
	    	return this.redisExecute(new RedisFunction<String, JedisCluster>() {

				@Override
				public String redisCallBack(JedisCluster e) {
					
					return e.set(key, value);
				}
	    		   		
			});
	    }  
	
	 /**
     * redis 方法实现
     */
    private <T> T redisExecute(RedisFunction<T,JedisCluster> function){
    	JedisCluster JedisCluster = this.JedisCluster;
         try {  
        	// JedisCluster = JedisCluster.getClusterNodes().get("");  
         	 return	function.redisCallBack(JedisCluster);
         } finally {
        	 
         }  
    }
}
