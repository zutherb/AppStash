package io.github.appstash.shop.service.user

import io.github.appstash.shop.service.user.domain.User
import io.github.appstash.shop.service.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationConfiguration extends WebMvcConfigurationSupport implements CommandLineRunner {

    @Autowired
    def UserRepository repository;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationConfiguration.class, args)
    }

    void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter())
        addDefaultHttpMessageConverters(converters)
    }

    @Bean
    MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter()
        //do your customizations here...
        return converter
    }

    @Override
    void run(String... args) throws Exception {
        for (User user : repository.findAll()) {
            System.out.println(user.getUsername())
        }
    }
}