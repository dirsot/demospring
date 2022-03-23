package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DemoApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("singletonDouble")
    @Scope("singleton")
    @Lazy
    @Primary
    public Double getDoubleSingleton() {
        return Math.random();
    }

    @Bean("prototypeDouble")
    @Scope("prototype")
    @DependsOn("singletonDouble")
    public Double getDoublePrototype() {
        return Math.random();
    }

    //	@Bean
//	public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//		return new Jackson2JsonMessageConverter();
//	}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
//        registry.addViewController("/logout");
    }

}
