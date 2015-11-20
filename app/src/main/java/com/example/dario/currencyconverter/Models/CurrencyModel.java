package com.example.dario.currencyconverter.models;

/**
 * Created by Dario on 2015-11-17.
 */
public class CurrencyModel {


    private String mRate;
    private String mCurrency;

    public CurrencyModel(String currency, String rate){
        mCurrency = currency;
        mRate = rate;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String getRate() {
        return mRate;
    }

    public void setRate(String mRate) {
        this.mRate = mRate;
    }

    @Override
    public String toString(){
        return mCurrency + "::" + mRate + "//";
    }
}
