package com.ab.cloud.coffeeClient.service.serviceImpl;

import com.ab.cloud.coffeeClient.service.CoffeeBackService;
import com.ab.cloud.coffeeClient.service.CoffeeOrderService;
import com.ab.cloud.coffeeClient.service.CoffeeService;
import com.ab.cloud.coffeeClient.support.ClientBindingRabbit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CoffeeOrderServiceImpl implements CoffeeOrderService {

    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeBackService coffeeBackService;

    @Autowired
    private ClientBindingRabbit clientBindingRabbit;

    @Override
    public String orderCoffee(String id) {
        log.info("CoffeeOrderServiceImpl orderCoffee id = {}",id);
        return coffeeService.getCoffee(id + " aaa");
    }

    @Override
    public String backCoffee(String id) {
        log.info("CoffeeOrderServiceImpl backCoffee id={}", id);
        return coffeeBackService.coffeeBack(id);
    }

    @Override
    public String orderCoffeeMq(String id) {
        boolean send = clientBindingRabbit.createOrders().send(MessageBuilder.withPayload("我要咖啡 " + id).build());
        log.info(" create order success {} ", send);
        return "create order success " + send;
    }


}
