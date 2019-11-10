package com.ab.cloud.coffeeClient.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "coffee-server", contextId = "coffee-client2")
public interface CoffeeResilience4jService {

    /**
     * 调用远程接口
     * @param id
     * @return
     */
    @GetMapping(value = "/getCoffee")
    String getCoffee(@RequestParam(value = "id") String id);

    @GetMapping(value = "/getAllMenu")
    List<String> getAllMenu();
}
