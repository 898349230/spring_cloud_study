package com.ab.cloud.coffeeClient.support;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class CoffeeWaitingEvent extends ApplicationEvent {
    private Coffee coffee;
    public CoffeeWaitingEvent(Coffee coffee) {
        super(coffee);
        this.coffee = coffee;
    }
}
