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
public class FileHandler {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + FileHandler.class.getSimpleName();
    private static final String READ = "read";
    private static final String WRITE = "write";
    private static final String ILLEGAL_NUMBER_OF_PARAMS = "Illegal number of parameters";

    public FileHandler(){
    }

    public static void write(String fileName, FileModel fileContent, MainActivity context){
        FileWriter fileWriter = new FileWriter(fileName,fileContent,context);
        fileWriter.execute();
    }
    public static void read(String filename, MainActivity context){
        FileReader fileReader = new FileReader(filename,context);
        fileReader.execute();
    }
}
