package com.example.yiliu.forecast;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.DownloadListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    public static final String QUERY = "com.example.yiliu.forecast.STREET";
    private TableRow streetValid;
    private TableRow cityValid;
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
        String street = ((EditText) findViewById(R.id.street)).getText().toString().trim();
        String city = ((EditText) findViewById(R.id.city)).getText().toString().trim();
        String state = ((Spinner) findViewById(R.id.state)).getSelectedItem().toString().trim();
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

            queryWeather(url.toString());
        }
    }

    private void addValidateListener() {
        // get the views of the form
        EditText streetEditText = (EditText) findViewById(R.id.street);
        EditText cityEditText = (EditText) findViewById(R.id.city);
        Spinner stateSpinner = (Spinner) findViewById(R.id.state);
        streetValid = (TableRow) findViewById(R.id.streetValid);
        cityValid = (TableRow) findViewById(R.id.cityValid);
        stateValid = (TableRow) findViewById(R.id.stateValid);

        // set validate watcher to form input
        setValidateListener(streetValid, streetEditText);
        setValidateListener(cityValid, cityEditText);
        stateSpinner.setOnItemSelectedListener(sateWatcher);
    }

    private void setValidateListener(final TableRow hintText, EditText input) {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() != 0) {
                    hintText.setVisibility(View.INVISIBLE);
                }
            }
        };

        input.addTextChangedListener(textWatcher);
    }

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

    private void queryWeather(String url) {
        // check network connection
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // if network connection is ok
        if (networkInfo != null && networkInfo.isConnected()) {
            new GetWeatherInfo().execute(url);
        } else {
            Toast.makeText(getApplicationContext(), "Can't connect to Internet." +
                    " Please check Internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private class GetWeatherInfo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            try {
                return sendRequest(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve weather forecast. Information may be invalid";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(getApplicationContext(), DisplayWeatherActivity.class);
            intent.putExtra(QUERY, result);
            startActivity(intent);
        }
    }

    private String sendRequest(String myQuery) throws IOException {
        InputStream is = null;

        try {
            URL query = new URL(myQuery);
            HttpURLConnection conn = (HttpURLConnection) query.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("Connection", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            return readIt(is);

            // Makes sure that the Input Stream is closed after the app is finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private String readIt(InputStream stream) throws IOException {
        Reader reader = new InputStreamReader(stream, "UTF-8");
        StringBuilder rst = new StringBuilder();
        while (true) {
            int next = reader.read();
            if (next == -1) {
                break;
            } else {
                rst.append((char)next);
            }
        }
        return rst.toString();
    }



}
