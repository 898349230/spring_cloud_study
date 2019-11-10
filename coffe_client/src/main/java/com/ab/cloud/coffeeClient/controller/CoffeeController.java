package com.ab.cloud.coffeeClient.controller;

import com.ab.cloud.coffeeClient.service.CoffeeOrderService;
import com.ab.cloud.coffeeClient.service.CoffeeService;
import com.ab.cloud.coffeeClient.support.CoffeeProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

//    测试 配置中心
    @Autowired
    private CoffeeProperties coffeeProperties;

    @GetMapping(value = "/coffee")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "fallbackCoffee") // 熔断后执行的方法
    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD")// SEMAPHORE
    public String getCoffee(String id){
        log.info("thread={} getCoffee {}...", Thread.currentThread(), id);
        return coffeeOrderService.orderCoffee(id);
    }

    /**
     * 熔断时调用的方法
     * @param id
     * @return
     */
    public String fallbackCoffee(String id){
        log.info("thread={} fallback id={}", Thread.currentThread(), id);
        return "fallback id=" + id;
    }

    /**
     * 指定熔断后的处理类 CoffeeFallBackService
     * @param id
     * @return
     */
    @GetMapping(value = "/coffee/backCoffee")
    @ResponseBody
    public String backCoffee(String id){
        log.info("thread={} backCoffee {}...", Thread.currentThread(),id);
        return coffeeOrderService.backCoffee(id);
    }

    @ResponseBody
    @GetMapping(value = "/coffee/props")
    public String printConfigServer(){
        log.info("coffeeProperties getPrice: " + coffeeProperties.getPrice());
        log.info("coffeeProperties getDiscount: " + coffeeProperties.getDiscount());
        log.info("coffeeProperties getEnv: " + coffeeProperties.getEnv());
        return coffeeProperties.toString();
    }

}
