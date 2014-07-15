package io.github.appstash.shop.service.user

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext

/**
 * @author zutherb
 */
class UserServiceDriver implements CommandLineRunner {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationConfiguration.class, args)
    }

    @Override
    void run(String ... args) throws Exception {

    }
}
