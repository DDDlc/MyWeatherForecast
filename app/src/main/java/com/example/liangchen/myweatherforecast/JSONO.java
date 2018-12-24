package com.example.liangchen.myweatherforecast;

import com.example.liangchen.myweatherforecast.Weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;

public class JSONO {
    public JSONO() {

    }

    public Weather getData(String data) {
        StringBuilder sb = new StringBuilder(data);
        Weather weather=new Weather();
        String data1 = "";
        String[] data2;
        String[] data3;
        try {
            for (int i = 0; i < sb.length(); i++) {
                if (sb.charAt(i) == '[') {
                    sb.delete(i, sb.length());
                }
            }
            data1 = sb.toString();
            data2 = data1.split("\"");
            data3 = new String[data2.length / 2];
            for (int i = 0; i < data3.length; i++) {
                data3[i] = data2[2 * i+1];
            }
            for (int i = 0; i < data3.length; i++) {
                if (data3[i].equals("city")) {
                    weather.setCity(data3[i + 1]);
                    if(weather.getCity().equals(""))
                        continue;
                }
                if (data3[i].equals("cityId")) {
                    weather.setId(data3[i + 1]);
                    continue;
                }
                if (data3[i].equals("parent")) {
                    weather.setProvince(data3[i + 1]);
                    continue;
                }
                if (data3[i].equals("date")) {
                    weather.setDate(data3[i + 1]);
                    continue;
                }
                if (data3[i].equals("shidu")) {
                    weather.setHumidity(data3[i + 1]);
                    continue;
                }
                if (data3[i].equals("pm25")) {
                    weather.setPM25(data3[i + 1]);
                    continue;
                }
                if (data3[i].equals("wendu")) {
                    weather.setTemperature(data3[i + 1]);
                    break;
                }
            }
        } catch (Exception e) {

        }
        return weather;
    }
}
