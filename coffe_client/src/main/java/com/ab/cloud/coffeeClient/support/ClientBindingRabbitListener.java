package com.ab.cloud.coffeeClient.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * 监听 stream
 */
@Component
@Slf4j
public class ClientBindingRabbitListener {

    /**
     * 消费者监听器
     * @param message
     */
    @StreamListener(ClientBindingRabbit.FINISHED_ORDERS)
    public void listenFinishedOrders(String message){
        log.info("coffee order is finished {} ", message);
    }

}
