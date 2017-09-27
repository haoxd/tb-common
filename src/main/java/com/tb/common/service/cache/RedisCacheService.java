package com.tb.common.service.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tb.common.service.cache.intf.RedisFunction;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * @author acer11
 *  作者：haoxd
* 创建时间：2017年5月6日 下午8:47:15  
* 项目名称：tb-manager-service  
* @author daniel  
* @version 1.0   
* @since JDK 1.6.0_21  
* 文件名称：RedisCacheService.java  
* 类说明：redis 缓存服务
 */
/*
 * Redis操作字符串工具类封装：http://fanshuyao.iteye.com/blog/2326221
Redis操作Hash工具类封装：http://fanshuyao.iteye.com/blog/2327134
Redis操作List工具类封装：http://fanshuyao.iteye.com/blog/2327137
Redis操作Set工具类封装：http://fanshuyao.iteye.com/blog/2327228
 * */
@Service("redis")
public class RedisCacheService {
	
	@Autowired(required=false)//在spring容器当中找，找到注入，否者忽略
	private ShardedJedisPool shardedJedisPool;
	
	private static final String OK="ok";
	
	
	
	 /** 
     * <p>向redis存入key和value,并释放连接资源</p> 
     * <p>如果key已经存在 则覆盖</p> 
     * @param key 
     * @param value 
     * @return 成功 返回OK 失败返回 0 
     */  
    public  String set(final String key,final String value){  
    	return this.redisExecute(new RedisFunction<String, ShardedJedis>() {

			@Override
			public String redisCallBack(ShardedJedis e) {
				
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
    	return this.redisExecute(new RedisFunction<String, ShardedJedis>() {

			@Override
			public String redisCallBack(ShardedJedis e) {
				
				return e.get(key);
			}
    		   		
		});
    }
    
    
    /** 
     * 删除指定的key,支持多个
     */  
    public  Long del(final String[] keys){  
    	return this.redisExecute(new RedisFunction<Long, ShardedJedis>() {

			@Override
			public Long redisCallBack(ShardedJedis e) {
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
    	return this.redisExecute(new RedisFunction<Long, ShardedJedis>() {

			@Override
			public Long redisCallBack(ShardedJedis e) {

				return	e.del(keys);
			}
    		   		
		});
    }
    
    
    /** 
     * 指定的key的生存时间(单位秒)
     */  
    public  Long expire(final String key ,final Integer seconds){  
    	return this.redisExecute(new RedisFunction<Long, ShardedJedis>() {

			@Override
			public Long redisCallBack(ShardedJedis e) {
				
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
    	return this.redisExecute(new RedisFunction<String, ShardedJedis>() {

			@Override
			public String redisCallBack(ShardedJedis e) {
				String str =e.set(key, value);
				e.expire(key, seconds);
				return str;
			}
    		   		
		});
    }  
    
    /**
     * 设置hash类型的数据
     * @param key ：建
     * @param field：字段
     * @param value：值
     * @return
     */
    public boolean hashSet(final String key,final String field , final String value){
    	return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {
				Long statusCode = e.hset(key, field, value);
				
				return statusCode>-1;
			}
    		
		});
    }
    
    /**
     * 批量设置Hash的属性 
     * @param key:key
     * @param fields:字段数组
     * @param values：属性数组
     * @return
     */
	public boolean hashMSet(final String key, final String[] fields, final String[] values) {
		if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(fields) || StringUtils.isAnyEmpty(values)) {
			return false;
		}
		return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {
				Map<String, String> hash = new HashMap<String, String>();
				for (int i = 0; i < fields.length; i++) {
					hash.put(fields[i], values[i]);
				}

				return StringUtils.equalsIgnoreCase(OK, e.hmset(key, hash));
			}

		});

	}
	
	/**
	 * 批量设置Hash的属性 
	 * @param  key:key
	 * @param 字段和属性对应的map
	 * @return
	 */
	public boolean hashMSet(final String key, final Map<String, String> map) {
		if (StringUtils.isBlank(key) || map.isEmpty()) {
			return false;
		}
		return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {

				return StringUtils.equalsIgnoreCase(OK, e.hmset(key, map));
			}

		});
	}
	
	
	/**
	 * 仅当field不存在时设置值，成功返回true 
	 * HSETNX：设置成功，返回 1 。如果给定域已经存在且没有操作被执行，返回 0 。
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public boolean hashSetNX(final String key,final String field ,final String value){
		if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(field) || StringUtils.isAnyEmpty(value)) {
			return false;
		}
		return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {

				return  e.hsetnx(key, field,value)==1;
			}

		});
	}
	
	 /** 
     * 获取属性的值 
     * @param key 
     * @param field 
     * @return 
     */ 
	public String hashGet(final String key, final String field) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return null;
		}
		return this.redisExecute(new RedisFunction<String, ShardedJedis>() {

			@Override
			public String redisCallBack(ShardedJedis e) {

				return e.hget(key, field);
			}

		});
	}
    
	  /** 
     * 批量hash获取属性的值 
     * @param key 
     * @param fields String... 
     * @return 
     */  
	public List<String> hashMGet(final String key, final String... fields) {
		if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(fields)) {
			return null;
		}
		return this.redisExecute(new RedisFunction<List<String>, ShardedJedis>() {

			@Override
			public List<String> redisCallBack(ShardedJedis e) {

				return e.hmget(key, fields);
			}

		});
	}
	
	 /** 
     * 删除hash的属性 
     * @param key 
     * @param fields String... 
     * @return 
     */  
	public boolean hashDel(final String key, final String... fields) {
		if (StringUtils.isBlank(key) || StringUtils.isAnyEmpty(fields)) {
			return false;
		}
		return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {
				return  e.hdel(key, fields)>0;  
				
			}

		});
	}
	
	  /** 
     * 查看哈希表 key 中，指定的字段是否存在。 
     * @param key 
     * @param field 
     * @return 
     */  
	public boolean hashExists(final String key, final String field) {
		if (StringUtils.isBlank(key) || StringUtils.isBlank(field)) {
			return false;
		}

		return this.redisExecute(new RedisFunction<Boolean, ShardedJedis>() {

			@Override
			public Boolean redisCallBack(ShardedJedis e) {
				return e.hexists(key, field);

			}

		});
	}  
	
	  /** 
     * 获取所有哈希表中的字段 
     * @param key 
     * @return Set<String> 
     */   
	public Set<String> hashMGetKeys(final String key, final String field) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		return this.redisExecute(new RedisFunction<Set<String>, ShardedJedis>() {

			@Override
			public Set<String> redisCallBack(ShardedJedis e) {
				  return e.hkeys(key);  

			}

		});
	}  
	
	/** 
     * 获取哈希表中所有值 
     * @param key 
     * @return List<String> 
     */  
	public  List<String> hashMGetValues(final String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		return this.redisExecute(new RedisFunction<List<String>, ShardedJedis>() {

			@Override
			public List<String> redisCallBack(ShardedJedis e) {
				  return e.hvals(key);  

			}

		});
	}  
    /**
     * redis 方法实现
     */
    private <T> T redisExecute(RedisFunction<T,ShardedJedis> function){
    	 ShardedJedis shardedJedis = null;
         try {  
         	shardedJedis = shardedJedisPool.getResource();  
         	return	function.redisCallBack(shardedJedis);
         } finally {  
         	 if (null != shardedJedis) {
                  // 关闭，检测连接是否有效，有效则放回到连接池中，无效则重置状态
                  shardedJedis.close();
              }
         }  
    }

}
