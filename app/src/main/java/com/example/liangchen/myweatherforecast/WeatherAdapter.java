package com.example.liangchen.myweatherforecast;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class WeatherAdapter extends ArrayAdapter<Weather> {
    private int resourceId;

    public WeatherAdapter(Context context, int textViewResourceId, List<Weather> object) {
        super(context, textViewResourceId, object);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Weather weather = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView dateText = view.findViewById(R.id.date);
        TextView cityText = view.findViewById(R.id.city);
        TextView temperatureText = view.findViewById(R.id.temperature);
        dateText.setText(weather.getDate());
        cityText.setText(weather.getProvince() + weather.getCity());
        temperatureText.setText(weather.getTemperature());
        return view;
    }
}
