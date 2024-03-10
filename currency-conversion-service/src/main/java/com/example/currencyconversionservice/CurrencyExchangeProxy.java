package com.example.currencyconversionservice;

import com.example.springsecuritycommon.FeignProxyConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "currency-exchange", url = "localhost:8000")
//@FeignClient(name = "currency-exchange")  // load balancer
@FeignClient(name = "currency-exchange", configuration = FeignProxyConfig.class)  // load balancer
public interface CurrencyExchangeProxy {
  @GetMapping("/currency-exchange/exchange-rate/from/{from}/to/{to}")
  CurrencyConversion getCurrencyExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
}
