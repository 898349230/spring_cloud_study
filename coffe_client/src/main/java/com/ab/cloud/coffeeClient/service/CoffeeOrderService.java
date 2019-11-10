package com.ab.cloud.coffeeClient.service;

public interface CoffeeOrderService {

    String orderCoffee(String id);

    String backCoffee(String id);

    String orderCoffeeMq(String id);
}
