package com.example.dario.currencyconverter.handlers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dario.currencyconverter.MainActivity;
import com.example.dario.currencyconverter.models.CurrencyModel;
import com.example.dario.currencyconverter.models.FileModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 2015-11-19.
 */
public class FileHandler extends AsyncTask<String,Void,FileModel> {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + FileHandler.class.getSimpleName();
    private static final String READ = "read";
    private static final String WRITE = "write";
    private static final String ILLEGAL_NUMBER_OF_PARAMS = "Illegal number of parameters";

    private String mFileName;
    private MainActivity mContext;

    public FileHandler(String filename, MainActivity context) {
        mFileName = filename;
        mContext = context;
    }

    private static boolean writeToFile(String fileName, Context context){
        return false;
    }

    @Override
    protected FileModel doInBackground(String... params) {
        if(params.length != 1){
            return null;
        }
        FileModel file = new FileModel();
        String action = params[0];
        switch (action){
            case READ:
                file = readFromFile(mFileName, mContext);
                break;
            case WRITE:
                writeToFile(mFileName,mContext);
                break;
            default:
                break;
        }
        return file;
    }

    @Override
    protected void onPostExecute(FileModel result) {
        mContext.setFile(result);
        mContext.bindListeners();
    }

    private FileModel readFromFile(String fileName, Context context){

        FileModel file = null;
        BufferedReader reader = null;
        try{
            /*Read data from the file*/
            InputStream is = context.openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(is));
            String line = reader.readLine();
            Log.d(LOG_TAG, line);

            String[] rowData = line.split("//");

            /*Set Date */
            file = new FileModel();
            file.setDate(rowData[0]);

            /*Fill the lis of currencies*/
            List<CurrencyModel> currencyList = new ArrayList<>();
            currencyList.add(new CurrencyModel("EUR", "1"));
            for(int i = 1; i<rowData.length; i++){
                String[] currentCurrency = rowData[i].split("::");
                currencyList.add(new CurrencyModel(currentCurrency[0], currentCurrency[1]));
            }

            /*Set the currencies in the file object*/
            file.setCurrencies(currencyList);
            Log.d(LOG_TAG, "Operation success");
        }catch(Exception e){
            Log.d(LOG_TAG,"ERROR: " + e.getMessage());
            return null;
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
        return file;
    }
}
