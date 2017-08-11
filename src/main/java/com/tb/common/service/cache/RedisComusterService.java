package com.tb.common.service.cache;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tb.common.service.cache.intf.RedisFunction;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;  
  
/**
 * @author acer11
 *  作者：haoxud
* 创建时间：2017年8月11日 下午3:05:14  
* 项目名称：tb-common  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：RedisComusterService.java  
* 类说明：redis集群操作
 */
@Service("redisComuster")
public class RedisComusterService {  
  
    @Autowired(required=false) 
    private JedisCluster jedisCluster;  
  
  
    /**  
     * 保存指定key值的value  
     * @param key  
     * @param list  
     */  
    public  String set(final String key,final List<String> list){  
    	return this.redisExecute(new RedisFunction<String, JedisCluster>() {

			@Override
			public String redisCallBack(JedisCluster e) {
				
				return e.rpush(key, (String[]) list.toArray()).toString();  
			}
    		   		
		});
    } 
    /** 
     * <p>向redis存入key和value,并释放连接资源</p> 
     * <p>如果key已经存在 则覆盖</p> 
     * @param key 
     * @param value 
     * @return 成功 返回OK 失败返回 0 
     */  
    public  String set(final String key,final String value){  
    	return this.redisExecute(new RedisFunction<String, JedisCluster>() {

			@Override
			public String redisCallBack(JedisCluster e) {
				
				return e.set(key, value);
			}
    		   		
		});
    }  
    /** 
     * <p>通过key获取储存在redis中的value</p> 
     * <p>并释放连接</p> 
     * @param key 
     * @return 成功返回value 失败返回null 
     */  
    public  String get(final String key){  
    	return this.redisExecute(new RedisFunction<String, JedisCluster>() {

			@Override
			public String redisCallBack(JedisCluster e) {
				
				return e.get(key);
			}
    		   		
		});
    }
    
    /** 
     * 删除指定的key,支持多个
     */  
    public  Long del(final String[] keys){  
    	return this.redisExecute(new RedisFunction<Long, JedisCluster>() {

			@Override
			public Long redisCallBack(JedisCluster e) {
				if(keys.length==1){
				return	e.del(keys[1]);
				}
				long content = 0;
				for (int i = 0; i < keys.length; i++) {
					e.del(keys[i]);
					content++;
				}
				return content;
			}
    		   		
		});
    }
    /** 
     * 删除指定的key
     */  
    public  Long del(final String keys){  
    	return this.redisExecute(new RedisFunction<Long, JedisCluster>() {

			@Override
			public Long redisCallBack(JedisCluster e) {

				return	e.del(keys);
			}
    		   		
		});
    }
   
    /** 
     * 指定的key的生存时间(单位秒)
     */  
    public  Long expire(final String key ,final Integer seconds){  
    	return this.redisExecute(new RedisFunction<Long, JedisCluster>() {

			@Override
			public Long redisCallBack(JedisCluster e) {
				
				return e.expire(key, seconds);
			}
    		   		
		});
    }
    /**
     * 执行set操作，并且指定生存时间
     * @param key：键值
     * @param value：值
     * @param seconds：时间（秒）
     * @return
     */
    public  String set(final String key,final String value,final Integer seconds){  
    	return this.redisExecute(new RedisFunction<String, JedisCluster>() {

			@Override
			public String redisCallBack(JedisCluster e) {
				String str =e.set(key, value);
				e.exists(key);
				return str;
			}
    		   		
		});
    }  
    
    /**
     * redis 方法实现
     */
    private <T> T redisExecute(RedisFunction<T,JedisCluster> function){
     	return	function.redisCallBack(this.jedisCluster);
        
    }
}  

