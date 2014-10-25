package io.github.appstash.shop.service.cart

import io.github.appstash.shop.service.cart.domain.CartItem
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@EnableAutoConfiguration
@ComponentScan
class ApplicationConfiguration extends WebMvcConfigurationSupport {

    @Bean
    RedisConnectionFactory connectionFactory() {
        new JedisConnectionFactory()
    }

    @Bean
    RedisTemplate<String, CartItem> redisTemplate() {
        def RedisTemplate<String, List<CartItem>> redisTemplate = new RedisTemplate()
        redisTemplate.setConnectionFactory(connectionFactory())
        redisTemplate.setKeySerializer(new StringRedisSerializer())
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<CartItem>(CartItem.class))
        redisTemplate
    }
}