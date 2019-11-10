package com.ab.cloud.coffeeClient.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "coffee-server", contextId = "coffee-client")
public interface CoffeeService {

    /**
     * 调用远程接口
     * @param id
     * @return
     */
    @GetMapping(value = "getCoffee")
    String getCoffee(@RequestParam(value = "id") String id);
}
