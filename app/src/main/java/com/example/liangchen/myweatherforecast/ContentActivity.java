package com.example.liangchen.myweatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangchen.myweatherforecast.R;
import com.example.liangchen.myweatherforecast.WeatherAdapter;
import com.example.liangchen.myweatherforecast.Weather;
import com.example.liangchen.myweatherforecast.API;
import com.example.liangchen.myweatherforecast.JSONO;

public class ContentActivity extends AppCompatActivity {
    private int page;
    private Weather weather;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        handler = new Handler();
        SharedPreferences pageReader = getSharedPreferences("data", MODE_PRIVATE);
        page = pageReader.getInt("page", 0);
        weather = get(page);
        refresh();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            refresh();
        }
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.return_button:
                startActivity(new Intent(ContentActivity.this, MainActivity.class));
                break;
            case R.id.refresh_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String cityid = weather.getId() + "";
                        API api = new API(cityid);
                        JSONO jsono = new JSONO();
                        Weather w = jsono.getData(api.getJSON());
                        set(page, w);
                        weather = w;
                        handler.post(runnable);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void set(int num, Weather w) {
        SharedPreferences.Editor editor = getSharedPreferences("weather0" + num, MODE_PRIVATE).edit();
        editor.putString("id", w.getId());
        editor.putString("province", w.getProvince());
        editor.putString("city", w.getCity());
        editor.putString("date", w.getDate());
        editor.putString("temperature", w.getTemperature());
        editor.putString("humidity", w.getHumidity());
        editor.putString("PM25", w.getPM25());
        editor.apply();
    }

    public Weather get(int num) {
        Weather w = new Weather();
        SharedPreferences sharedPreferences = getSharedPreferences("weather0" + num, MODE_PRIVATE);
        w.setId(sharedPreferences.getString("id", ""));
        w.setProvince(sharedPreferences.getString("province", ""));
        w.setCity(sharedPreferences.getString("city", ""));
        w.setDate(sharedPreferences.getString("date", ""));
        w.setTemperature(sharedPreferences.getString("temperature", ""));
        w.setHumidity(sharedPreferences.getString("humidity", ""));
        w.setPM25(sharedPreferences.getString("PM25", ""));
        return w;
    }

    public void refresh() {
        TextView city = findViewById(R.id.cityText);
        city.setText(weather.getProvince() + weather.getCity());
        TextView date = findViewById(R.id.dateText);
        date.setText(weather.getDate());
        TextView temperature = findViewById(R.id.temperatureText);
        temperature.setText(weather.getTemperature());
        TextView humidity = findViewById(R.id.humidityText);
        humidity.setText(weather.getHumidity());
        TextView PM25 = findViewById(R.id.PM25Text);
        PM25.setText(weather.getPM25());
    }
}
