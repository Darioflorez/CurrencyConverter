package com.example.dario.currencyconverter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //By me
        //Button
        Button button = (Button) findViewById(R.id.button);
        OnClickListener listener = new OnButtonClickListener();
        button.setOnClickListener(listener);
        //TextView
        textView = (TextView) findViewById(R.id.textView);
        resulTextView = (TextView)findViewById(R.id.result_textView);
        //Spinner master
        spinner_master = (Spinner) findViewById(R.id.spinner_master);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.currencies_array,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_master.setAdapter(adapter);
        spinner_master.setOnItemSelectedListener(new SpinnerMasterListener());
        //Spinner slave
        spinner_slave = (Spinner) findViewById(R.id.spinner_slave);
            //Adapter
        ArrayAdapter<CharSequence> spinnerSlaveAdapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.currencies_array,
                        android.R.layout.simple_spinner_item);
        spinnerSlaveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Adapter
        spinner_slave.setAdapter(spinnerSlaveAdapter);
        spinner_slave.setOnItemSelectedListener(new SpinnerSlaveListener());
        //EditText
        masterEditText = (EditText)findViewById(R.id.master_editText);
        masterEditText.addTextChangedListener(new onEditTextMasterUpdate());

        slaveEditText = (EditText)findViewById(R.id.slave_editText);
        slaveEditText.addTextChangedListener(new onEditTextSlaveUpdate());

        writeToPrivateFile();
        readFromPrivateFile();
    }

    //By me
    private void writeToPrivateFile() {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String hello = "HELLO" + "\n" + "Dario";
            fos.write(hello.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos != null){
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFromPrivateFile(){
        FileInputStream fis = null;
        try {
            fis = openFileInput(FILE_NAME);
            int readByres;
            String result = "";

            while ((readByres = fis.read()) != -1){
                result += Character.toString((char)readByres);
            }
            textView.setText("Text from file: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void populateSpinners() {

    }
    //Button
    private class OnButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            //clicks++;
            if(!masterEditText.getText().toString().equals("")){
                double masterCurrencyValue = 0, slaveCurrencyValue = 0, result = 0;
                switch (masterCurrency){
                    case "EUR":
                        masterCurrencyValue = 1;
                        break;
                    case "USD":
                        masterCurrencyValue = 1.07;
                        break;
                    case "JPY":
                        masterCurrencyValue = 132.01;
                        break;
                    case "SEK":
                        masterCurrencyValue = 9.3;
                        break;
                    default:
                        break;
                }
                switch (slaveCurrency){
                    case "EUR":
                        slaveCurrencyValue = 1;
                        break;
                    case "USD":
                        slaveCurrencyValue = 1.07;
                        break;
                    case "JPY":
                        slaveCurrencyValue = 132.01;
                        break;
                    case "SEK":
                        slaveCurrencyValue = 9.3;
                        break;
                    default:
                        break;
                }
                masterValue = Double.parseDouble(masterEditText.getText().toString());
                //formula
                result = (masterValue/masterCurrencyValue) * slaveCurrencyValue;
                //Round to four decimals
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);
                String finalResult = df.format(result);

                resulTextView.setText("Mater value: " + masterValue);
                slaveEditText.setText(finalResult);
            }/*else if(!slaveEditText.getText().toString().equals("")){
                slaveValue = Double.parseDouble(slaveEditText.getText().toString());
                resulTextView.setText("Slave value: " + slaveValue);
                masterEditText.setText(String.valueOf(slaveValue));
            }*/
            textView.setText("Master: " + masterCurrency +" "+ "Slave: " + slaveCurrency);
            showToast("Ouch!");
        }
    }
    //EditText Listener
    private class onEditTextSlaveUpdate implements TextWatcher {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!(masterEditText.getText().toString().equals(""))){
                masterEditText.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            // Update slaveEditText in a thread. look at the Anders lesson.
        }
    }

    private class onEditTextMasterUpdate implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(!(slaveEditText.getText().toString().equals(""))){
                slaveEditText.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

    //Toast
    private void showToast(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    //Spinner
    private class SpinnerMasterListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            masterCurrency = (String) spinner_master.getSelectedItem();
            showToast("Master currency: " + masterCurrency);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
    private class SpinnerSlaveListener implements OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            slaveCurrency = (String) spinner_slave.getSelectedItem();
            showToast("Slave Currency: " + slaveCurrency);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    //

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
}
