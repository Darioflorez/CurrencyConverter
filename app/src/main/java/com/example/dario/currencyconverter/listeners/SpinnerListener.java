package com.example.dario.currencyconverter.listeners;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

/**
 * Created by Dario on 2015-11-18.
 */
public class SpinnerListener implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private String currency;

    public SpinnerListener(Spinner spinner, String currency){
        this.spinner = spinner;
        this.currency = currency;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        currency = (String) spinner.getSelectedItem();
        Log.d("Selected curency: ", currency);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
