package com.hainan.cs.dao;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class RedisGeneratorDao<k extends Serializable, v extends Serializable> {

	@Autowired
	protected RedisTemplate<k,v> redisTemplate;
	
	public void setRedisTemplate(RedisTemplate<k,v> redisTemplate){
		this.redisTemplate=redisTemplate;
	}
	protected RedisSerializer<String> getRedisSerializer(){
		return redisTemplate.getStringSerializer();
	}
}
