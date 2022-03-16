package com.example.demo.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.actuate.info.Info.Builder;

@Component
public class MyInfoContributor implements InfoContributor {

    @Override
    public void contribute(Builder builder) {
        Map<String, Object> tacoMap = new HashMap<>();
        tacoMap.put("count", 2);
        tacoMap.put("past", false);
        builder.withDetail("my-stats", tacoMap);
    }
}