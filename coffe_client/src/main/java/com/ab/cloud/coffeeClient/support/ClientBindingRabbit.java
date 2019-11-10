package com.ab.cloud.coffeeClient.support;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface ClientBindingRabbit {

    String CREATE_ORDERS = "createOrders";
    String FINISHED_ORDERS = "finishedOrders";

    /**
     * 消费
     * @return
     */
    @Input(FINISHED_ORDERS)  // 注解内不写 默认与方法名相同
    SubscribableChannel finishedOrders();

    /**
     * 生产
     * @return
     */
    @Output
    MessageChannel createOrders();
}
