package com.somta.springboot;

import com.somta.springboot.pojo.User;
import com.somta.springboot.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description:
 * @Date: 2019/10/12
 * @Author: 明天的地平线
 * @Blog: https://somta.com.cn/index
 * @Version: 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test1(){
        redisUtil.set("author","明天的地平线");
        System.out.println(redisUtil.get("author"));
    }

    @Test
    public void test2(){
        User user = new User();
        user.setId("1");

        user.setName("明天的地平线");
        user.setAge(18);
        redisUtil.set("user",user);
        User u = (User) redisUtil.get("user");
        System.out.println(u.getName());
        System.out.println(redisUtil.get("user"));
    }

    @Test
    public void test3(){
        User user2 = new User();
        user2.setId("1");
        user2.setName("明天的地平线");
        user2.setAge(20);
        redisTemplate.opsForValue().set("user2",user2);
        System.out.println((redisTemplate.opsForValue().get("user2")));
    }
}
