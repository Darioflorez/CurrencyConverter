package com.example.dario.currencyconverter.Models;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Dario on 2015-11-18.
 */
public class CurrencyConverterModel {

    private TextView textView;
    private Spinner spinner_master;
    private Spinner spinner_slave;
    private String masterCurrency;
    private String slaveCurrency;
    private double masterValue;
    private double slaveValue;
    private EditText masterEditText;
    private EditText slaveEditText;
    private TextView resulTextView;
    private int clicks = 0;
    private String FILE_NAME = "currency";


    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public Spinner getSpinner_master() {
        return spinner_master;
    }

    public void setSpinner_master(Spinner spinner_master) {
        this.spinner_master = spinner_master;
    }

    public Spinner getSpinner_slave() {
        return spinner_slave;
    }

    public void setSpinner_slave(Spinner spinner_slave) {
        this.spinner_slave = spinner_slave;
    }

    public String getMasterCurrency() {
        return masterCurrency;
    }

    public void setMasterCurrency(String masterCurrency) {
        this.masterCurrency = masterCurrency;
    }

    public String getSlaveCurrency() {
        return slaveCurrency;
    }

    public void setSlaveCurrency(String slaveCurrency) {
        this.slaveCurrency = slaveCurrency;
    }

    public double getMasterValue() {
        return masterValue;
    }

    public void setMasterValue(double masterValue) {
        this.masterValue = masterValue;
    }

    public double getSlaveValue() {
        return slaveValue;
    }

    public void setSlaveValue(double slaveValue) {
        this.slaveValue = slaveValue;
    }

    public EditText getMasterEditText() {
        return masterEditText;
    }

    public void setMasterEditText(EditText masterEditText) {
        this.masterEditText = masterEditText;
    }

    public EditText getSlaveEditText() {
        return slaveEditText;
    }

    public void setSlaveEditText(EditText slaveEditText) {
        this.slaveEditText = slaveEditText;
    }

    public TextView getResulTextView() {
        return resulTextView;
    }

    public void setResulTextView(TextView resulTextView) {
        this.resulTextView = resulTextView;
    }

    public int getClicks() {
        return clicks;
    }

    public void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public String getFILE_NAME() {
        return FILE_NAME;
    }

    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }
}
