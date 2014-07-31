package io.github.appstash.shop.dataloader

import org.springframework.context.ApplicationContext

import static org.springframework.boot.SpringApplication.run

/**
 * @author zutherb
 */
class Boot {

    public static void main(String[] args) {
        String[] sources = [
                "classpath:/io/github/appstash/shop/dataloader/spring-env-context.xml",
                "classpath:/io/github/appstash/shop/dataloader/spring-context.xml",
                "classpath:/io/github/appstash/dataloader/spring-context.xml"]
        ApplicationContext ctx = run(sources, args)
    }
}