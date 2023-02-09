package com.syxu.yaofang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer genericJsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(genericJsonRedisSerializer);
        template.setHashKeySerializer(genericJsonRedisSerializer);
        template.setHashValueSerializer(genericJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
