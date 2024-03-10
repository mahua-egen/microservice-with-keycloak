package com.example.currencyconversionservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyConversion {
    private Integer id;
    private String currencyFrom;
    private String currencyTo;
    private Double conversionMultiple;
    private Integer quantity;
    private Double conversionValue;
    private String env;
}
