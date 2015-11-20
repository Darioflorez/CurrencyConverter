package com.example.dario.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.example.dario.currencyconverter.helper.SpinnerAdapterFactory;
import com.example.dario.currencyconverter.models.CurrencyModel;
import com.example.dario.currencyconverter.models.FileModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = ">>>>>>>>>>>>>>" + MainActivity.class.getSimpleName();

    private static final String READ = "read";
    private static final String WRITE = "write";
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
        /*Parse XML and write important information into a private fileModel*/
        XMLParser xmlParser = new XMLParser(URL,this);
        xmlParser.execute(FILE_NAME);
    }

    public void readFile(){
        /*Read the local fileModel that contains the information about the currencies*/
        FileHandler.read(FILE_NAME, this);
    }

    public void writeToFile(){
        FileHandler.write(FILE_NAME, fileModel, this);
    }

    public void updateFile(){
    }

    public void bindListeners(){
        currencyList = fileModel.getCurrencies();
        Log.d(LOG_TAG, "Currency: " + currencyList.get(0).getCurrency());
        button.setOnClickListener(new OnButtonClickListener());

        ArrayAdapter<String> adapter = SpinnerAdapterFactory.newAdapter(this,currencyList);
        masterSpinner.setAdapter(adapter);
        slaveSpinner.setAdapter(adapter);
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

            String result = Converter.convert(
                    masterEditText.getText().toString(),
                    masterSpinner.getSelectedItem().toString(),
                    slaveSpinner.getSelectedItem().toString(),
                    currencyList
            );
            slaveEditText.setText(result);
        }
    }

    public FileModel getFileModel() {
        return fileModel;
    }

    public void setFileModel(FileModel fileModel) {
        this.fileModel = fileModel;
    }
}
