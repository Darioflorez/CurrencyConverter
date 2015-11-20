package com.example.dario.currencyconverter.helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Dario on 2015-11-19.
 */
public class Printer {

    public static void showToast(String msg, Context context){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
