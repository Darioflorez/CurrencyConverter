package com.example.dario.currencyconverter.helper;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.dario.currencyconverter.R;
import com.example.dario.currencyconverter.models.CurrencyModel;

import java.util.List;

/**
 * Created by Dario on 2015-11-18.
 */
public class SpinnerAdapterFactory {


    public static ArrayAdapter<String> newAdapter(Context context, List<CurrencyModel> currencies){

        List<CurrencyModel> mCurrencies = currencies;
        String [] adapterArray = createAdapterArray(mCurrencies);

        /*ArrayAdapter<CharSequence> mAdapter =
                ArrayAdapter.createFromResource(
                        context,
                        R.array.currencies_array,
                        android.R.layout.simple_spinner_item
                );

        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, adapterArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        return adapter;
    }

    private static String[] createAdapterArray(List<CurrencyModel> currencies){

        String[] result = new String[currencies.size()];
        int i = 0;
        for (CurrencyModel cM: currencies) {
            result[i] = cM.getCurrency();
            i++;
        }
        return result;
    }
}
