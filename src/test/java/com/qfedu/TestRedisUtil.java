package com.qfedu;

import com.qfedu.pojo.User;
import com.qfedu.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther Zhangbo
 * @date 2020/5/6 15:57
 */
@SpringBootTest
public class TestRedisUtil {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void testString() throws InterruptedException {
        //测试string数据的设置与获取
        System.out.println(redisUtil.set("hello1", "word11"));
        System.out.println(redisUtil.get("hello1"));

        //测试string数据失效
        System.out.println(redisUtil.set("1", "1", 5));
        System.out.println(redisUtil.get("1"));
        Thread.sleep(5000);
        System.out.println(redisUtil.get("1"));
    }

    @Test
    public void testStringObject() throws InterruptedException {
        User user = new User();

        user.setUid(9527);
        user.setUsername("zhouxingxing");
        user.setPassword("999999");
        user.setAge(20);

        //测试string类型是否能存储对象
        System.out.println(redisUtil.set("zhouxing", user, 5));
        System.out.println(redisUtil.get("zhouxing"));
        Thread.sleep(5000);
        System.out.println(redisUtil.get("zhouxing"));
    }

    @Test
    public void testStringList(){
        List<User> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new User(i, "name" + i, "pass" + i, 18 + i));
        }

        //测试list的数据设置与获取情况
        System.out.println(redisUtil.set("list", list));
        System.out.println(redisUtil.get("list"));
    }

    @Test
    public void testHash(){
        Map<String, Object> users = new HashMap<>();

        users.put("wukong", "sunxingzhe");

        User tangtang = new User(1000, "tangsanzang", "888888", 20);

        users.put("tangtang", tangtang);
        users.put("bajie", "zhuwunong");

        //测试hash类型数据的设置与两种获取情况
        System.out.println(redisUtil.hmset("xiyouji", users));
        System.out.println(redisUtil.hmget("xiyouji"));
        System.out.println(redisUtil.hget("xiyouji", "tangtang"));
    }
}
