package com.yijiawenCoder.hbsmoj.model.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data

public class Person {

    private String name;

}
