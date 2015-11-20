package com.example.dario.currencyconverter.handlers;

import android.content.Context;
import android.util.Log;

import com.example.dario.currencyconverter.models.CurrencyModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 2015-11-19.
 */
public class FileHandler {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + FileHandler.class.getSimpleName();

    public static List<CurrencyModel> newCurrencyList(String fileName, Context context) {

        List<CurrencyModel> returnList = null;
        BufferedReader reader = null;
        try{
            InputStream is = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            Log.d(LOG_TAG, line);
            String[] currencies = line.split("//");
            returnList = new ArrayList<>();
            returnList.add(new CurrencyModel("EUR","1"));
            for(int i = 1; i<currencies.length; i++){
                String[] currentCurrency = currencies[i].split("::");
                returnList.add(new CurrencyModel(currentCurrency[0],currentCurrency[1]));
            }
            Log.d(LOG_TAG, "Operation success");
        }catch(Exception e){
            Log.d(LOG_TAG,"ERROR: " + e.getMessage());
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                    Log.d(LOG_TAG, "Reader closed!");
                } catch (IOException e) {
                    Log.d(LOG_TAG,"ERROR: " + e.getMessage());
                }
            }
        }
        return returnList;
    }
}
