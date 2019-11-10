package com.ab.cloud.coffeeClient.service;

import com.ab.cloud.coffeeClient.service.serviceImpl.CoffeeFallBackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coffee-server", // 远程服务名
        contextId = "coffee-back",
        path = "/coffeeBack", // 统一路径， 不要在类上使用 @RequestMapping 注解
        fallback = CoffeeFallBackServiceImpl.class) // 熔断后调用的
public interface CoffeeBackService {

    @RequestMapping(value = "/")
    String coffeeBack(@RequestParam(name = "id") String id);

}
