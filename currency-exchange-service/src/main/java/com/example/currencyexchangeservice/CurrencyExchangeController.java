package com.example.currencyexchangeservice;

import com.example.springsecuritycommon.security.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rate")
public class CurrencyExchangeController {
  private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class);
  @Autowired
  private Environment environment;

  @Autowired
  private CurrentUser currentUser;

  @Autowired
  private CurrencyExchangeRepository repository;

  @GetMapping("/from/{from}/to/{to}")
  public ResponseEntity<CurrencyExchange> getCurrencyExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to) {
    logger.info("call currency exchange {} to {}", from, to);
    CurrencyExchange currencyExchange = repository.findByCurrencyFromAndCurrencyTo(from, to);
    logger.info(currentUser.getFullName());
    if (currencyExchange == null)
      throw new RuntimeException("data not found");
    currencyExchange.setEnv(environment.getProperty("local.server.port"));
    return ResponseEntity.ok(currencyExchange);
  }
}
