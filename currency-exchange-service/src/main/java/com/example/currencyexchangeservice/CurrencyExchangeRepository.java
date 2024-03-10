package com.example.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Integer> {
    CurrencyExchange findByCurrencyFromAndCurrencyTo(String from, String to);
}
