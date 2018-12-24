
package com.example.liangchen.myweatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.LinkedList;
import com.example.liangchen.myweatherforecast.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private String textStr;
    private Handler handler;
    private TextView textView;
    private EditText editText;
    private LinkedList<Weather> weatherList;
    private char Case;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            switch (Case) {
                case '1':
                    textView.setText("id格式输入错误");
                    Case = '0';
                    break;
                case '2':
                    textView.setText("城市不存在");
                    Case = '0';
                    break;
                case '3':
                    WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, R.layout.weather_item, weatherList);
                    ListView listView = findViewById(R.id.list_view);
                    listView.setAdapter(adapter);
                    textView.setText("");
                    Case = '0';
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherList = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            if (exist(i)) {
                weatherList.addLast(get(i));
            } else {
                break;
            }
        }
        WeatherAdapter adapter = new WeatherAdapter(MainActivity.this, R.layout.weather_item, weatherList);
        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        handler = new Handler();
        editText = findViewById(R.id.search);
        textView = findViewById(R.id.mark);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("page", i);
        editor.apply();
        startActivity(new Intent(MainActivity.this, ContentActivity.class));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_button:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String cityid = editText.getText().toString();
                        if (cityid.length() != 9) {
                            Case = '1';
                        } else {
                            API api = new API(cityid);
                            JSONO jsono = new JSONO();
                            Weather weather = jsono.getData(api.getJSON());
                            if (weather.getId() == null || weather.getId().length() < 1) {
                                Case = '2';
                            } else {
                                if (weatherList.size() >= 3) {
                                    weatherList.removeLast();
                                }
                                add(weather);
                                weatherList.addFirst(weather);
                                Case = '3';
                            }
                        }
                        handler.post(runnable);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void add(Weather w) {
        if (weatherList.size() == 0) {
            set(0, w);
        } else if (weatherList.size() == 1) {
            set(1, get(0));
            set(0, w);
        } else if (weatherList.size() == 2 || weatherList.size() == 3) {
            set(2, get(1));
            set(1, get(0));
            set(0, w);
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

    public boolean exist(int num) {
        Weather w = new Weather();
        SharedPreferences sharedPreferences = getSharedPreferences("weather0" + num, MODE_PRIVATE);
        return sharedPreferences.getString("id", "").length() > 0;
    }
}
