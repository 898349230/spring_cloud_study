package com.ab.cloud.coffeeClient.controller;

import com.ab.cloud.coffeeClient.service.CoffeeResilience4jService;
import com.google.common.base.Supplier;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerOpenException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * 使用 Resilience4j 限流
 */
@Controller
@Slf4j
public class CoffeeResilience4jController {

    @Autowired
    private CoffeeResilience4jService coffeeResilience4jService;
//    断路器
    private CircuitBreaker circuitBreaker;
//    并发
    private Bulkhead bulkheader;
//    限流
    private RateLimiter rateLimiter;
//    构造一个断路器 名称为 menu，
    public CoffeeResilience4jController(CircuitBreakerRegistry circuitBreakerRegistry,
                                        BulkheadRegistry bulkheadRegistry,
                                        RateLimiterRegistry rateLimiterRegistry){
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("menu");
        bulkheader = bulkheadRegistry.bulkhead("menu");
        rateLimiter = rateLimiterRegistry.rateLimiter("menu");
    }



    /**
     * 代码方式配置
     * @return
     */
    @RequestMapping(value = "/resil/menu")
    @ResponseBody
    public List<String> getMenu(){
        Try<List<String>> recover = Try.ofSupplier(
                Bulkhead.decorateSupplier(bulkheader,
                        CircuitBreaker.decorateSupplier(circuitBreaker, () -> coffeeResilience4jService.getAllMenu())))
//                产生 CircuitBreakerOpenException 异常 返回
                .recover(CircuitBreakerOpenException.class, Arrays.asList("咖啡1", "咖啡2"))
//                产生 BulkheadFullException 异常 返回
                .recover(BulkheadFullException.class, Arrays.asList("限流咖啡"));

        return recover.get();
    }

    /**
     * 使用注解形式
     * @param id
     * @return
     */
    @RequestMapping(value = "/resil/coffee")
    @ResponseBody
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "buyCoffee")
    @io.github.resilience4j.bulkhead.annotation.Bulkhead(name = "buyCoffee")
    public String buyCoffee(String id){
        log.info("buyCoffee  id={}", id);
        return coffeeResilience4jService.getCoffee(id);
    }

    /**
     * 代码形式
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    @ResponseBody
    public String getCoffee(@PathVariable String id){
        try {
            log.info("请求成功");
            return rateLimiter.executeSupplier(() -> "咖啡 coffee " + id);
        }catch (RequestNotPermitted ex){
            log.info("请求失败");
            return "请求频繁";
        }
    }

    /**
     * 注解形式限流
     * @param id
     * @return
     */
    @RequestMapping("/annotation/{id}")
    @ResponseBody
//    注解可以使用到 类上
    @io.github.resilience4j.ratelimiter.annotation.RateLimiter(name = "buyCoffee")
    public String getCoffeeAnnotation(@PathVariable String id){
        log.info("请求成功");
        Supplier<String> stringSupplier = () -> "注解 咖啡 coffee " + id;
        return stringSupplier.get();
    }

}
