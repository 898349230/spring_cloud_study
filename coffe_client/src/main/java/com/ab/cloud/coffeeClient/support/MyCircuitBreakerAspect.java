package com.ab.cloud.coffeeClient.support;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
@Slf4j
public class MyCircuitBreakerAspect {

    private static final Integer THRESHOLD = 3;
//    断路次数
    private Map<String, AtomicInteger> counter = new ConcurrentHashMap<>();
//    短路保护次数
    private Map<String, AtomicInteger> breakCounter = new ConcurrentHashMap<>();

    @Around("execution(* com.ab.cloud.coffeeClient2.service..*(..))")
    public Object doWithCircuitBreaker(ProceedingJoinPoint joinPoint) throws Throwable {

        String sinNature = joinPoint.getSignature().toLongString();
        log.info("Invoke {}", sinNature);
        Object proceed;
        try {
            if(counter.containsKey(sinNature)){
//          如果调用断路超过阈值并且断路保护不超过阈值 返回 null，
                if(counter.get(sinNature).get() > THRESHOLD && breakCounter.get(sinNature).get() < THRESHOLD){
                    log.warn("Circuit breaker return null, break {} times.",
//                            断路保护次数 + 1
                            breakCounter.get(sinNature).incrementAndGet());
                    return null;
                }
            }else {
//                初始化
                counter.put(sinNature, new AtomicInteger(0));
                breakCounter.put(sinNature, new AtomicInteger(0));
            }
            proceed = joinPoint.proceed();
//            执行成功则初始化
            counter.get(sinNature).set(0);
            breakCounter.get(sinNature).set(0);

        } catch (Throwable throwable) {
            log.warn("Circuit breaker counter: {}, Throwable {}",
//                    断路次数 + 1
                    counter.get(sinNature).incrementAndGet(), throwable.getMessage());
//            初始化断路保护次数
            breakCounter.get(sinNature).set(0);
            throw throwable;
        }
        return proceed;
    }

}
