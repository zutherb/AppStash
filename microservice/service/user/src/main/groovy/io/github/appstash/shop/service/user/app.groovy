package io.github.appstash.shop.service.user

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationConfiguration {

    def static main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationConfiguration.class, args)

        System.out.println("Let's inspect the beans provided by Spring Boot:")

//        String[] beanNames = ctx.getBeanDefinitionNames()
//        Arrays.sort(beanNames)
//        for (String beanName : beanNames) {
//            System.out.println(beanName)
//        }
    }

}