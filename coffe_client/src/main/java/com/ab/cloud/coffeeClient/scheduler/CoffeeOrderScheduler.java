package com.ab.cloud.coffeeClient.scheduler;

import com.ab.cloud.coffeeClient.support.CoffeeWaitingEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SourceFilteringListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理定时任务类
 */
@Component
@Slf4j
public class CoffeeOrderScheduler {

    private Map<String, Object> map = new ConcurrentHashMap<>();

    /**
     * 接收 applicationEventPublisher.publishEvent(new CoffeeWaitingEvent(coffee)) 中的 CoffeeWaitingEvent
     * @param coffeeWaitingEvent
     */
    @EventListener
    public void acceptCoffeeEvent(CoffeeWaitingEvent coffeeWaitingEvent){
        log.info("acceptCoffeeEvent {}", coffeeWaitingEvent);
        map.put(coffeeWaitingEvent.getCoffee().getId(), coffeeWaitingEvent.getCoffee());
    }

    /**
     * 1000 ms 调用一次
     */
    @Scheduled(fixedRate = 1000)
    public void dealCoffeeWaitingEvent(){
        log.info("dealCoffeeWaitingEvent....");
        Set<String> strings = map.keySet();
        strings.forEach(key -> log.info("dealCoffeeWaitingEvent key :{}, {}", key, map.get(key)));
        map.clear();
    }

}
