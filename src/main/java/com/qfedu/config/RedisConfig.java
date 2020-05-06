package com.qfedu.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @auther Zhangbo
 * @date 2020/5/2 1:26
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        //创建redis模板对象
        RedisTemplate<Object,Object> template = new RedisTemplate<>();

        //使用fastJson实现对象的序列化
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        //设置redis模板对象
        //设置“值”的序列化方式
        template.setValueSerializer(serializer);
        //设置“hash”类型的序列化方式
        template.setHashKeySerializer(serializer);
        //设置“key”的序列化方式
        template.setKeySerializer(new StringRedisSerializer());
        //设置“hash的key”的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());

        //设置redis模板的工厂对象
        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();

        template.setConnectionFactory(redisConnectionFactory);

        return template;
    }

}
