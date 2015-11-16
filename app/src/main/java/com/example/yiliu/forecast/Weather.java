package com.example.yiliu.forecast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Weather extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
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

        Intent intent = getIntent();
        try {
            JSONObject data = new JSONObject(intent.getStringExtra(MainActivity.QUERY));
            JSONArray daily = data.getJSONObject("daily").getJSONArray("data");
            String degreeType = intent.getStringExtra(MainActivity.DEGREE_TYPE);
            setCurrentWeather(data, degreeType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentWeather(JSONObject data, String degreeType) {
        WeatherInfoHandler handler = new WeatherInfoHandler(degreeType);

        // set Summary
        try {
            String value = data.getJSONObject("currently").getString("summary")
                    + " in " + data.getString("address");
            ((TextView) findViewById(R.id.nowSummary)).setText(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Icon
        try {
            ((ImageView) findViewById(R.id.current_icon)).setImageResource(
                    handler.getIcon(data.getJSONObject("currently").getString("icon")));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Temp
        try {
            SpannableString value = new SpannableString(
                    "" + data.getJSONObject("currently").getInt("temperature")
                            + (degreeType.equals("us")? " ºF": " ºC"));
            value.setSpan(new AbsoluteSizeSpan(128), 0, value.length() - 2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            value.setSpan(new SuperscriptSpan(), value.length() - 2, value.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView) findViewById(R.id.nowTemp)).setText(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Precipitation
        try {
            double value = data.getJSONObject("currently").getDouble("precipIntensity");
            ((TextView) findViewById(R.id.nowPrecipitation))
                    .setText(handler.getPrecipitation(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Chance of Rain
        try {
            double value = data.getJSONObject("currently").getDouble("precipProbability");
            ((TextView) findViewById(R.id.nowChanceOfRain))
                    .setText(handler.getChanceOfRain(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Wind Speed
        try {
            double value = data.getJSONObject("currently").getDouble("windSpeed");
            ((TextView) findViewById(R.id.nowWindSpeed))
                    .setText(handler.getWindSpeed(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Dew Point
        try {
            double value = data.getJSONObject("currently").getDouble("dewPoint");
            ((TextView) findViewById(R.id.nowDewPoint))
                    .setText(handler.getDewPoint(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Humidity
        try {
            double value = data.getJSONObject("currently").getDouble("humidity");
            ((TextView) findViewById(R.id.nowHumidity))
                    .setText(handler.getHumidity(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Visibility
        try {
            double value = data.getJSONObject("currently").getDouble("visibility");
            ((TextView) findViewById(R.id.nowVisibility))
                    .setText(handler.getVisibility(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Sunrise Time
        try {
            long value = data.getJSONObject("daily").getJSONArray("data")
                    .getJSONObject(0).getLong("sunriseTime");
            ((TextView) findViewById(R.id.nowSunrise))
                    .setText(handler.getTime(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Sunset Time
        try {
            long value = data.getJSONObject("daily").getJSONArray("data")
                    .getJSONObject(0).getLong("sunsetTime");
            ((TextView) findViewById(R.id.nowSunset))
                    .setText(handler.getTime(value));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
