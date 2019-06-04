package top.luyuni.qaa;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class JedisTest {

    Jedis jedis = new Jedis("redis://localhost:6379/9");
    @Test
    public void stringTest(){
        jedis.set("name", "niyulu");
        jedis.set("name", "new niyulu");
        jedis.rename("name", "new name");
        String name1 = jedis.get("name");
        System.out.println(name1);
        String new_name = jedis.get("new name");
        System.out.println(new_name);
        jedis.setex("age", 10, "18");//自动过期
        String age = jedis.get("age");
        System.out.println(age);
        jedis.set("click", "1");//自增自减操作
        jedis.incr("click");
        jedis.incrBy("click", 5);
        jedis.decrBy("click", 2);
        jedis.decr("click");
        String click = jedis.get("click");
        System.out.println(click);
    }
    @Test
    public void HashTest(){
        jedis.hset("people", "name", "luyuni");
        String hget = jedis.hget("people", "name");
        System.out.println(hget);
    }
    @Test
    public void listTest(){
        jedis.del("listkey");
        for (int i = 0; i < 10; i ++){
            jedis.lpush("listkey", "value" + i);
        }
        jedis.rpush("listkey", "niyulu");
        List<String> listvalue = jedis.lrange("listkey", 5, 7);

        Long llen = jedis.llen("listkey");
        jedis.rpop("listkey");
        Long llen1 = jedis.llen("listkey");
        System.out.println(listvalue);
        System.out.println(llen);
        System.out.println(llen1);
    }
}
