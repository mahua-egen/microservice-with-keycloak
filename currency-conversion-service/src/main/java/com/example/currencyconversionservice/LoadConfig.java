package com.example.currencyconversionservice;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"com.example.springsecuritycommon"})
@PropertySource("classpath:config.properties")
public class LoadConfig {

}
