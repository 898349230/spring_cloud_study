package com.ab.cloud.coffeeClient.service.serviceImpl;

import com.ab.cloud.coffeeClient.service.CoffeeBackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoffeeFallBackServiceImpl implements CoffeeBackService {
    @Override
    public String coffeeBack(String id) {
        log.info("thread={}  CoffeeFallBackService coffeeBack id={}", Thread.currentThread(), id);
        return "CoffeeFallBackService "+id;
    }
}
