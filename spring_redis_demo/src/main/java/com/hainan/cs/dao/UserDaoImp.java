package com.hainan.cs.dao;

import java.util.UUID;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

import com.hainan.cs.bean.User;
@Repository(value="userDao")
public class UserDaoImp extends RedisGeneratorDao<String,User>{
	//添加Hash hsetNX
	public boolean add(final User user){
		System.out.println(redisTemplate);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer=getRedisSerializer();
				byte[] key=serializer.serialize(user.getId());
				byte[] name=serializer.serialize(user.getUsername());
				byte[] password=serializer.serialize(user.getPassword());
				Boolean r=connection.hSetNX(key, name,password);//hash类型的提交<key,hash的key,hash的value>
				return r;
			}
		});
		return result;
	}
	//添加Hash hSet
	public boolean add1(final User user){
		System.out.println(redisTemplate);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer=getRedisSerializer();
				byte[] key=serializer.serialize(user.getId());
				byte[] name=serializer.serialize(user.getUsername());
				byte[] password=serializer.serialize(user.getPassword());
				Boolean r=connection.hSet(key, name, password);//和hSetNX一样
				return r;
			}
		});
		return result;
	}
	//添加String
	public boolean add2(final User user){
		System.out.println(redisTemplate);
		boolean result=redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer=getRedisSerializer();
				byte[] key=serializer.serialize(user.getId());
				byte[] name=serializer.serialize(user.getUsername());
				Boolean r=connection.setNX(key, name);//添加String类型
				return r;
			}
		});
		return result;
	}
	//添加List
	public void add3(final String key,final String value){
		redisTemplate.execute(new RedisCallback<Boolean>(){
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer=getRedisSerializer();
				byte[] a=serializer.serialize(key);
				byte[] b=serializer.serialize(value);
				connection.lPush(a, b);
				return true;
			}
			});
	}
	//删除对象
	public void deleteKey(String key){
		redisTemplate.delete(key);
	}
	//删除hash中的一条记录
	public void deleteRecord(String key,String id){
		BoundHashOperations<String,Object,Object> bhops=redisTemplate.boundHashOps(key);
		bhops.delete(id);
	}
	//测试
	public static void main(String args[]){
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring/application-config.xml");
		UserDaoImp udi=context.getBean(UserDaoImp.class);
		User u=new User();
		String id=UUID.randomUUID().toString();//自动生成一个id
		u.setId("test1");
		u.setUsername(id);
		u.setPassword("123456");
		udi.add1(u);
		udi.deleteKey("1");
		udi.deleteRecord("test1", "zhangsan");
		udi.add3("listtest", "test");
	}
}
