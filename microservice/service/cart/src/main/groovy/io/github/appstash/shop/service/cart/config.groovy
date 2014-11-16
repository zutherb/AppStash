package io.github.appstash.shop.service.cart

import io.github.appstash.shop.service.cart.domain.CartItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.context.embedded.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.web.filter.CommonsRequestLoggingFilter
import org.springframework.web.filter.ShallowEtagHeaderFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@EnableAutoConfiguration
@ComponentScan
class ApplicationConfiguration extends WebMvcConfigurationSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfiguration.class)

    @Autowired
    Environment environment;

    @Bean
    RedisConnectionFactory connectionFactory() {
        def factory = new JedisConnectionFactory();
        def hostName = environment.getProperty("CART_SERVICE_REDIS_URL", "localhost")
        LOGGER.info("Redis Hostname is $hostName")
        factory.setHostName(hostName)
        factory
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