package com.example.pet_clinic_jpa.config;

import com.example.pet_clinic_jpa.domain.Pet;
import com.example.pet_clinic_jpa.domain.Visit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class RedisCacheConfig {

    public static final String OWNER_CACHE = "owner_cache";
    public static final String PET_CACHE = "pet_cache";
    public static final String VISIT_CACHE = "visit_cache";
    public static final Duration VISIT_CACHE_DURATION = Duration.ofMinutes(30);

    @Bean
    CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .withCacheConfiguration(OWNER_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(60)))
                .withCacheConfiguration(PET_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(30)))
                .withCacheConfiguration(VISIT_CACHE, RedisCacheConfiguration.defaultCacheConfig().entryTtl(VISIT_CACHE_DURATION))
                .build();
    }

    @Bean
    RedisTemplate<String, Pet> redisPetTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<String, Pet>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    RedisTemplate<String, List<Visit>> redisVisitTemplate(RedisConnectionFactory connectionFactory) {
        var template = new RedisTemplate<String, List<Visit>>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        return template;
    }
}
