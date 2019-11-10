package com;

import com.ab.cloud.coffeeClient.support.ClientBindingRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableBinding(ClientBindingRabbit.class)
@EnableScheduling // 定时任务
public class CoffeeClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeeClientApplication.class, args);
	}

}
