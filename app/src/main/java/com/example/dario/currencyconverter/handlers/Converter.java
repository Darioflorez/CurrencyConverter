package com.example.dario.currencyconverter.handlers;

import com.example.dario.currencyconverter.models.CurrencyModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Dario on 2015-11-18.
 */
public class Converter {

    public static String convert(String masterText, String masterCurrency, String slaveCurrency, List<CurrencyModel> currencies){

        String finalResult = null;
        if(!(masterText.equals(""))){
            double masterCurrencyValue = 0, slaveCurrencyValue = 0;
            for (CurrencyModel currency: currencies) {
                if(currency.getCurrency().equalsIgnoreCase(masterCurrency)){
                    masterCurrencyValue = Double.parseDouble(currency.getRate());
                }
                if(currency.getCurrency().equalsIgnoreCase(slaveCurrency)){
                    slaveCurrencyValue = Double.parseDouble(currency.getRate());
                }
            }
            double masterValue =
                    Double.parseDouble(masterText);
            //formula
            double result =
                    (masterValue/masterCurrencyValue) * slaveCurrencyValue;
            //Round to four decimals
            DecimalFormat df = new DecimalFormat("#.####");
            df.setRoundingMode(RoundingMode.CEILING);
            finalResult = df.format(result);

        }
        return finalResult;
    }
}
