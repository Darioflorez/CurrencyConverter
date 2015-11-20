package com.example.dario.currencyconverter.handlers;

import com.example.dario.currencyconverter.MainActivity;
import com.example.dario.currencyconverter.models.FileModel;

/**
 * Created by Dario on 2015-11-19.
 */
public class FileHandler {

    public static void write(String fileName, FileModel fileContent, MainActivity context){
        FileWriter fileWriter = new FileWriter(fileName,fileContent,context);
        fileWriter.execute();
    }
    public static void read(String filename, MainActivity context){
        FileReader fileReader = new FileReader(filename,context);
        fileReader.execute();
    }
}
