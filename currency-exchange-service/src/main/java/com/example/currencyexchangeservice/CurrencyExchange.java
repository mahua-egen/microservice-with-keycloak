package com.example.currencyexchangeservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CurrencyExchange {
    @Id
    private Integer id;
    private String currencyFrom;
    private String currencyTo;
    private Double conversionMultiple;
    private String env;

    public CurrencyExchange(Integer id, String currencyFrom, String currencyTo, Double conversionMultiple) {
        this.id = id;
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.conversionMultiple = conversionMultiple;
    }
}
