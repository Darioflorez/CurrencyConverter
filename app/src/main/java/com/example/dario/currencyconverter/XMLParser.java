package com.example.dario.currencyconverter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.dario.currencyconverter.models.CurrencyModel;
import com.example.dario.currencyconverter.models.FileModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dario on 2015-11-17.
 */
public class XMLParser  extends AsyncTask<String,Void,FileModel> {

    private final String LOG_TAG = ">>>>>>>>>>>>>>" + XMLParser.class.getSimpleName();
    private static final String CUBE = "cube";
    private String mDate;
    private List<CurrencyModel> mCurrencies;

    private MainActivity mContext;
    private String mSiteURL;

    public XMLParser(String siteURL, MainActivity context){
        mCurrencies = new ArrayList<>();
        mSiteURL = siteURL;
        mContext = context;
    }

    @Override
    protected FileModel doInBackground(String... params) {

        if(params.length != 1){
            return null;
        }
        HttpURLConnection http = null;
        InputStream xmlStream = null;
        FileModel fileModel = null;
        try{
            URL url = new URL(mSiteURL);

            /* Open a connection to that URL. */
            http = (HttpURLConnection) url.openConnection();
            Log.d(LOG_TAG, "Opened Connection");

            /* InputStream to read from the urlConnection */
            xmlStream = http.getInputStream();
            Log.d(LOG_TAG, "XMl stream created!");

            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlParser = factory.newPullParser();

            // point the parser to our file.
            xmlParser.setInput(xmlStream,null);

            /* Start read from urlConnection, parse and write to file */
            int parseEvent = xmlParser.getEventType();
            while(parseEvent != XmlPullParser.END_DOCUMENT) {
                switch(parseEvent) {
                    case XmlPullParser.START_DOCUMENT:
                        //
                        break;
                    case XmlPullParser.START_TAG:
                        String tagName = xmlParser.getName();
                        if(tagName.equalsIgnoreCase(CUBE)) {
                            parseAttribute(xmlParser);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        //
                        break;
                    case XmlPullParser.TEXT:
                        //
                        break;
                    default:
                        //
                }

                parseEvent = xmlParser.next();
            }
            /*Create object from parse date*/
            fileModel = new FileModel();
            fileModel.setDate(mDate);
            fileModel.setCurrencies(mCurrencies);
            Log.d(LOG_TAG, "Date: " + fileModel.getDate()+"<<<<<<<<<<<<<<<<<<<<<<<");

        }catch (Exception e){
            Log.d(LOG_TAG, "ERROR: ");
            Log.d(LOG_TAG, e.getMessage());
        }
        finally {
            if(http!=null){
                http.disconnect();
            }
            if(xmlStream != null){
                try {
                    xmlStream.close();
                    Log.d(LOG_TAG, "XML stream closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileModel;
    }

    @Override
    protected void onPostExecute(FileModel result) {
        mContext.setFileModel(result);
        mContext.writeToFile();
    }

    private void parseAttribute(XmlPullParser xmlParser) {

        String currency = xmlParser.getAttributeValue(null, "currency");
        String rate = xmlParser.getAttributeValue(null, "rate");
        if( (currency != null) && (rate != null)){
            Log.d(LOG_TAG, "Currency: "+currency + " Rate: " + rate );
            CurrencyModel currencyModel = new CurrencyModel(currency, rate);
            mCurrencies.add(currencyModel);
            return;
        }
        String date = xmlParser.getAttributeValue(null, "time");
        if(date != null){
            Log.d(LOG_TAG, "Date: " + date);
            mDate = date;
        }

    }
}
