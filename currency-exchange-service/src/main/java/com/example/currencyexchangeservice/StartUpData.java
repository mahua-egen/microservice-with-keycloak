package com.example.currencyexchangeservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpData implements CommandLineRunner {

    @Autowired
    private CurrencyExchangeRepository repository;

    @Override
    public void run(String... args) throws Exception {
        repository.findById(1).isPresent();
        if (!repository.findById(1).isPresent()) {
            repository.save(new CurrencyExchange(1, "USD", "BDT", 112.00));
        }
        if (!repository.findById(2).isPresent()) {
            repository.save(new CurrencyExchange(2, "USD", "INR", 98.00));
        }
        if (!repository.findById(3).isPresent()) {
            repository.save(new CurrencyExchange(3, "USD", "EUR", 65.6));
        }
    }
}
