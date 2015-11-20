package com.example.dario.currencyconverter.models;

import java.util.List;

/**
 * Created by Dario on 2015-11-20.
 */
public class FileModel {

    private String mDate;
    private List<CurrencyModel> mCurrencies;

    public List<CurrencyModel> getmCurrencies() {
        return mCurrencies;
    }

    public void setCurrencies(List<CurrencyModel> mCurrencies) {
        this.mCurrencies = mCurrencies;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }
}
