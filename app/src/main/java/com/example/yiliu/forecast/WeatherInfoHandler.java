package com.example.yiliu.forecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

/**
 * Created by YiLIU on 11/15/15.
 */
public class WeatherInfoHandler {
    private String degreeType;

    public WeatherInfoHandler(String degreeType) {
        this.degreeType = degreeType;
    }

    public int getIcon(String icon) {
        if (icon.equals("clear-day")) {
            return R.drawable.clear;
        } else if (icon.equals("clear-night")) {
            return R.drawable.clear_night;
        } else if (icon.equals("rain")) {
            return R.drawable.rain;
        } else if (icon.equals("snow")) {
            return R.drawable.snow;
        } else if (icon.equals("sleet")) {
            return R.drawable.sleet;
        } else if (icon.equals("wind")) {
            return R.drawable.wind;
        } else if (icon.equals("fog")) {
            return R.drawable.fog;
        } else if (icon.equals("cloudy")) {
            return R.drawable.cloudy;
        } else if (icon.equals("partly-cloudy-day")) {
            return R.drawable.cloud_day;
        } else if (icon.equals("partly-cloudy-night")) {
            return R.drawable.cloud_night;
        } else {
            return -1;
        }
    }

    public String getPrecipitation(double precipitation) {
        if (precipitation >= 0.4) {
            return "Heavy";
        } else if (precipitation >= 0.1) {
            return "Moderate";
        } else if (precipitation >= 0.017) {
            return "Light";
        } else if (precipitation >= 0.002) {
            return "Very Light";
        } else if (precipitation >= 0) {
            return "None";
        } else {
            return "None";
        }
    }

    public String getChanceOfRain(double chance) {
        int chanceOfRain = (int) (chance * 100.0);
        return "" + chanceOfRain + "%";
    }

    public String getWindSpeed(double speed) {
        String windSpeed = String.format("%.2f", speed)
                + (degreeType.equals("us")? " mph": " m/s");
        return windSpeed;
    }

    public String getDewPoint(double dew) {
        String dewPoint = String.format("%.2f", dew)
                + (degreeType.equals("us")? " ºF": " ºC");
        return dewPoint;
    }

    public String getHumidity(double hum) {
        int humidity = (int) (hum * 100.0);
        return "" + humidity + "%";
    }

    public String getVisibility(double vis) {
        String visibility = String.format("%.2f", vis)
                + (degreeType.equals("us")? " mi": " km");
        return visibility;
    }

    public String getTime(long time) {
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(1000 * time);
            return convertTime(date);
    }

    public String getTemp(double temp) {
        return "" + (int) temp + "º";
    }
    private String convertTime(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(calendar.getTime());
    }
}
