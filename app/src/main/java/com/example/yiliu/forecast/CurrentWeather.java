package com.example.yiliu.forecast;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by YiLIU on 11/17/15.
 */
public class CurrentWeather {
    private String summary;
    private String icon;
    private String temp;
    private String precipitation;
    private String chanceOfRain;
    private String windSpeed;
    private String dewPoint;
    private String humidity;
    private String visibility;
    private String sunriseTime;
    private String sunsetTime;

    CurrentWeather(View view, JSONObject data, String degreeType) {
        setCurrentWeather(view, data, degreeType);
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }

    public String getTemp() {
        return temp;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getChanceOfRain() {
        return chanceOfRain;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getSunriseTime() {
        return sunriseTime;
    }

    public String getSunsetTime() {
        return sunsetTime;
    }

    private void setCurrentWeather(View view, JSONObject data, String degreeType) {
        WeatherInfoHandler handler = new WeatherInfoHandler(degreeType);
        // set Summary
        try {
            String value = data.getJSONObject("currently").getString("summary");
            summary = value;
            ((TextView) view.findViewById(R.id.nowSummary)).setText(
                    value + " in " + data.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Icon
        try {
            String raw = data.getJSONObject("currently").getString("icon");
            ((ImageView) view.findViewById(R.id.current_icon)).setImageResource(
                    handler.getIcon(raw));
            icon = raw;
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
            ((TextView) view.findViewById(R.id.nowTemp)).setText(value);
            temp = value.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set High | Low Temp
        try {
            String value = "L:" + data.getJSONObject("daily").getJSONArray("data").getJSONObject(0)
                    .getInt("temperatureMin") + "º | H:" + data.getJSONObject("daily")
                    .getJSONArray("data").getJSONObject(0).getInt("temperatureMax") + "º";
            ((TextView) view.findViewById(R.id.nowTempHL)).setText(value);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Precipitation
        try {
            double value = data.getJSONObject("currently").getDouble("precipIntensity");
            precipitation = handler.getPrecipitation(value);
            ((TextView) view.findViewById(R.id.nowPrecipitation))
                    .setText(precipitation);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Chance of Rain
        try {
            double value = data.getJSONObject("currently").getDouble("precipProbability");
            chanceOfRain = handler.getChanceOfRain(value);
            ((TextView) view.findViewById(R.id.nowChanceOfRain))
                    .setText(chanceOfRain);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Wind Speed
        try {
            double value = data.getJSONObject("currently").getDouble("windSpeed");
            windSpeed = handler.getWindSpeed(value);
            ((TextView) view.findViewById(R.id.nowWindSpeed))
                    .setText(windSpeed);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Dew Point
        try {
            double value = data.getJSONObject("currently").getDouble("dewPoint");
            dewPoint = handler.getDewPoint(value);
            ((TextView) view.findViewById(R.id.nowDewPoint))
                    .setText(dewPoint);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Humidity
        try {
            double value = data.getJSONObject("currently").getDouble("humidity");
            humidity = handler.getHumidity(value);
            ((TextView) view.findViewById(R.id.nowHumidity))
                    .setText(humidity);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Visibility
        try {
            double value = data.getJSONObject("currently").getDouble("visibility");
            visibility = handler.getVisibility(value);
            ((TextView) view.findViewById(R.id.nowVisibility))
                    .setText(visibility);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Sunrise Time
        try {
            long value = data.getJSONObject("daily").getJSONArray("data")
                    .getJSONObject(0).getLong("sunriseTime");
            sunriseTime = handler.getTime(value);
            ((TextView) view.findViewById(R.id.nowSunrise))
                    .setText(sunriseTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // set Sunset Time
        try {
            long value = data.getJSONObject("daily").getJSONArray("data")
                    .getJSONObject(0).getLong("sunsetTime");
            sunsetTime = handler.getTime(value);
            ((TextView) view.findViewById(R.id.nowSunset))
                    .setText(sunsetTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
