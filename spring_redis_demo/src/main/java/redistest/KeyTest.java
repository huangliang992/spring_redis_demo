package redistest;


import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class KeyTest {
	public static void main(String[] args) {
	      //连接本地的 Redis 服务
	      Jedis jedis = new Jedis("localhost");
	      System.out.println("Connection to server sucessfully");
	      
	     // 获取数据并输出
	     Set<String> list =  jedis.keys("*");
	     Iterator<String> it=list.iterator();
	     while(it.hasNext()){
	    	Object o=it.next();
	    	 System.out.println("List of stored keys:: "+o);
	     }
	   }
}
