package com.example.dario.currencyconverter;

/**
 * Created by Dario on 2015-11-17.
 */
public class CurrencyModel {


    private double rate;
    private String currency;

    public CurrencyModel(){

    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
