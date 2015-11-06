package com.example.yiliu.forecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    public static final String QUERY = "com.example.yiliu.forecast.STREET";
    private EditText streetEditText;
    private TableRow streetValid;
    private EditText cityEditText;
    private TableRow cityValid;
    private Spinner stateSpinner;
    private TableRow stateValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set the state spinner
        Spinner state = (Spinner) findViewById(R.id.state);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.state_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);

        addValidateListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    public void submitForm(View view) {
        boolean valid = true;

        // get the input information
        String street = ((EditText) findViewById(R.id.street)).getText().toString();
        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        String state = ((Spinner) findViewById(R.id.state)).getSelectedItem().toString();
        String degree = ((RadioGroup) findViewById(R.id.degree))
                .getCheckedRadioButtonId() == 0? "us": "si";

        // validate the form input
        if (street.trim().length() == 0) {
            streetValid.setVisibility(View.VISIBLE);
            valid = false;
        }
        if (city.trim().length() == 0) {
            cityValid.setVisibility(View.VISIBLE);
            valid = false;
        }
        if (state.equals("Select your state...")) {
            stateValid.setVisibility(View.VISIBLE);
            valid = false;
        }

        // if valid query the weather
        if (valid) {
            // generate the query url
            Uri.Builder url = new Uri.Builder();
            url.scheme("http");
            url.authority("www.glistenlau.com");
            url.appendPath("forecast");
            url.appendPath("api");
            url.appendPath("weather");
            url.appendQueryParameter("street", street);
            url.appendQueryParameter("city", city);
            url.appendQueryParameter("state", state);
            url.appendQueryParameter("degreeType", degree);

            Intent intent = new Intent(this, DisplayWeatherActivity.class);
            intent.putExtra(QUERY, url.toString());
            startActivity(intent);
        }
    }

    private void addValidateListener() {
        // get the views of the form
        streetEditText = (EditText) findViewById(R.id.street);
        streetValid = (TableRow) findViewById(R.id.streetValid);
        cityEditText = (EditText) findViewById(R.id.city);
        cityValid = (TableRow) findViewById(R.id.cityValid);
        stateSpinner = (Spinner) findViewById(R.id.state);
        stateValid = (TableRow) findViewById(R.id.stateValid);

        // set validate watcher to form input
        streetEditText.addTextChangedListener(streetWatcher);
        cityEditText.addTextChangedListener(cityWatcher);
        stateSpinner.setOnItemSelectedListener(sateWatcher);
    }

    private final TextWatcher streetWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                streetValid.setVisibility(View.INVISIBLE);
            } else {
                if (s.toString().trim().length() == 0) {
                    streetValid.setVisibility(View.VISIBLE);
                } else {
                    streetValid.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private final TextWatcher cityWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                cityValid.setVisibility(View.INVISIBLE);
            } else {
                if (s.toString().trim().length() == 0) {
                    cityValid.setVisibility(View.VISIBLE);
                } else {
                    cityValid.setVisibility(View.INVISIBLE);
                }
            }
        }
    };

    private final AdapterView.OnItemSelectedListener sateWatcher = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
            if (!selected.equals("Select your state...")) {
                stateValid.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

}
