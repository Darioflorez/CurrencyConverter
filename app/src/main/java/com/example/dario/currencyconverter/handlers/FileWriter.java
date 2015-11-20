package com.example.dario.currencyconverter.handlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dario.currencyconverter.MainActivity;
import com.example.dario.currencyconverter.models.CurrencyModel;
import com.example.dario.currencyconverter.models.FileModel;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Dario on 2015-11-20.
 */
public class FileWriter extends AsyncTask<String,Void,Boolean> {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + FileWriter.class.getSimpleName();
    private String mFileName;
    private MainActivity mContext;
    private FileModel mFileContent;

    public FileWriter(String fileName, FileModel fileContent, MainActivity context) {
        mFileName = fileName;
        mContext = context;
        mFileContent = fileContent;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        BufferedOutputStream bos = null;
        try{
            /* OutputStream to write to file */
            FileOutputStream fos = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
            bos = new BufferedOutputStream(fos);
            Log.d(LOG_TAG, "Buffer to write to file opened!");
            String date = mFileContent.getDate() + "//";
            bos.write(date.getBytes());
            for (CurrencyModel currency: mFileContent.getCurrencies()) {
                bos.write(currency.toString().getBytes());
                Log.d(LOG_TAG, currency.toString());
            }
            Log.d(LOG_TAG, "File write successfully!");
        }catch(Exception e){
            Log.d(LOG_TAG, e.getMessage());
            return false;
        }finally {
            if(bos!=null){
                try {
                    bos.close();
                    Log.d(LOG_TAG, "Operation Success!!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        /*Update the model in mainActivity*/
        mContext.bindListeners();
    }
}
