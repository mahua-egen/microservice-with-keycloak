package com.example.currencyconversionservice;

import com.example.springsecuritycommon.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/conversion")
public class CurrencyConversionController {
  private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);

  @Autowired
  public CurrencyExchangeProxy currencyExchangeProxy;

  @Autowired
  public CurrentUser currentUser;

  @GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion getCurrencyConversion(@PathVariable("from") String from, @PathVariable("to") String to,
                                                  @PathVariable("quantity") Integer quantity) {
    logger.info("call currency conversion {} to {} and {}", from, to, quantity);
    System.out.println("user name in controller: " + currentUser.getId());
    CurrencyConversion currencyConversion = currencyExchangeProxy.getCurrencyExchangeValue(from, to);
    currencyConversion.setQuantity(quantity);
    currencyConversion.setConversionValue(quantity * currencyConversion.getConversionMultiple());
    return currencyConversion;
  }

}
