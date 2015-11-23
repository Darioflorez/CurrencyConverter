package com.example.dario.currencyconverter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.dario.currencyconverter.handlers.Converter;
import com.example.dario.currencyconverter.handlers.FileHandler;
import com.example.dario.currencyconverter.helper.Printer;
import com.example.dario.currencyconverter.helper.SpinnerAdapterFactory;
import com.example.dario.currencyconverter.models.CurrencyModel;
import com.example.dario.currencyconverter.models.FileModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + MainActivity.class.getSimpleName();

    private static final String FILE_NAME = "currency";
    private static final String  URL = "http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
    private Spinner masterSpinner; //ArrayList av currencyModel
    private Spinner slaveSpinner;  //ArrayList av currencyModel
    private EditText masterEditText; //String
    private EditText slaveEditText; //String
    private Button button;
    private List<CurrencyModel> currencyList;
    private FileModel fileModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button = (Button)findViewById(R.id.button);
        masterSpinner = (Spinner)findViewById(R.id.master_spinner);
        slaveSpinner = (Spinner)findViewById(R.id.slave_spinner);
        masterEditText = (EditText)findViewById(R.id.master_editText);
        slaveEditText = (EditText)findViewById(R.id.slave_editText);
    }

    @Override
    public void onStart(){
        super.onStart();
        readFile();
    }

    private void readFile(){
        /*Read the local fileModel that contains the information about the currencies*/
        FileHandler.read(FILE_NAME, this);
    }

    //Callback method
    public void createFileFromXML(){
        /*Parse XML and write important information into a private fileModel*/
        if(isNetworkAvailable()){
            XMLParser xmlParser = new XMLParser(URL,this);
            xmlParser.execute();
        }else {
            Printer.showToast("No network connection available", this);
        }
    }
    //Callback method
    public void updateFileFromXML(){
        if(isNetworkAvailable()){
            XMLParser xmlParser = new XMLParser(URL,this);
            xmlParser.execute();
        }else {
            bindListeners();
            Printer.showToast("No network connection available. Currencies out of date!", this);
        }
    }
    //Callback method
    public void writeToFile(){
        FileHandler.write(FILE_NAME, fileModel, this);
    }
    //Callback method
    public void bindListeners(){
        currencyList = fileModel.getCurrencies();
        button.setOnClickListener(new OnButtonClickListener());

        ArrayAdapter<String> masterAdapter = SpinnerAdapterFactory.newAdapter(this,currencyList);
        ArrayAdapter<String> slaveAdapter = SpinnerAdapterFactory.newAdapter(this,currencyList);
        masterSpinner.setAdapter(masterAdapter);
        slaveSpinner.setAdapter(slaveAdapter);
    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {

            String userInput = masterEditText.getText().toString();
            String rateMaster = masterSpinner.getSelectedItem().toString();
            String rateSlave = slaveSpinner.getSelectedItem().toString();
            String result = Converter.convert(
                    userInput,
                    rateMaster,
                    rateSlave,
                    currencyList
            );
            slaveEditText.setText(result);
        }
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }
}
