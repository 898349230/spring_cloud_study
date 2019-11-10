package com.ab.cloud.coffeeClient.support;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@ConfigurationProperties("coffee")
@Component
@Data
@RefreshScope // 刷新配置 需要执行 http://localhost:8080//actuator/refresh 后才会刷新
public class CoffeeProperties {
    private String env;
    private Integer price;
    private Integer discount;

}
