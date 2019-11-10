package com.ab.cloud.coffeeClient.controller;

import com.ab.cloud.coffeeClient.support.Coffee;
import com.ab.cloud.coffeeClient.support.CoffeeWaitingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@Slf4j
@RequestMapping("/coffeeEvent")
public class CoffeeEventController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @RequestMapping("/getCoffee")
    @ResponseBody
    public Coffee getCoffee(Coffee coffee){

        log.info("coffee id :{}", coffee.getId());
//        发送一个 CoffeeWaitingEvent
        applicationEventPublisher.publishEvent(new CoffeeWaitingEvent(coffee));

        return coffee;
    }
}
