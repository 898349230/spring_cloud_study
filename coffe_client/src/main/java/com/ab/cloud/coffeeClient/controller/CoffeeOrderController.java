package com.ab.cloud.coffeeClient.controller;

import com.ab.cloud.coffeeClient.service.CoffeeOrderService;
import com.ab.cloud.coffeeClient.support.ClientBindingRabbit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 测试 stream
 */
@Controller
@RequestMapping(value = "/coffeeOrder")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;
    /**
     * 生产 coffee 订单， 通过 stream 将信息发送个 rabbitMq
     * @param id
     * @return
     */
    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public String coffeeOrder(String id){
        log.info(" create order {} ", id);
        String s = coffeeOrderService.orderCoffeeMq(id);
        return s;
    }

}
