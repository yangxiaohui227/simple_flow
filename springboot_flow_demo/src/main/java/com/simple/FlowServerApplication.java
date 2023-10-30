package com.simple;

import com.simple.flow.EnableSimpleFlow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@SpringBootApplication
@EnableSimpleFlow
public class FlowServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowServerApplication.class, args);
    }
    @Bean
    public ThreadPoolTaskExecutor shopTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(100);
        executor.setQueueCapacity(6000);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setRejectedExecutionHandler((Runnable r, ThreadPoolExecutor exe) -> {

        });
        return executor;
    }
}
