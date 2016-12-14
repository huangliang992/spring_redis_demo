package com.hainan.cs.dao;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.hainan.cs.bean.User;
@Repository(value="userDao")
public class UserDaoImp extends RedisGeneratorDao<String,User>{
	//添加对象
	public boolean add(final User user){
		System.out.println(redisTemplate);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer=getRedisSerializer();
				byte[] key=serializer.serialize(user.getId());
				byte[] name=serializer.serialize(user.getUsername());
				byte[] password=serializer.serialize(user.getPassword());
				return connection.setNX(key, name);
			}
		});
		return result;
	}
	//测试
	public static void main(String args[]){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/application-config.xml");
		UserDaoImp udi=context.getBean(UserDaoImp.class);
		User u=new User();
		u.setId("1");
		u.setUsername("zhangsan");
		udi.add(u);
	}
}
