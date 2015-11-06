package com.example.yiliu.forecast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    public static final String QUERY = "com.example.yiliu.forecast.STREET";

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

    public void submitForm(View view) throws MalformedURLException {

        String street = ((EditText) findViewById(R.id.street)).getText().toString();
        String city = ((EditText) findViewById(R.id.city)).getText().toString();
        String state = ((Spinner) findViewById(R.id.state)).getSelectedItem().toString();
        String degree = ((RadioGroup) findViewById(R.id.degree))
                .getCheckedRadioButtonId() == 0? "us": "si";

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
