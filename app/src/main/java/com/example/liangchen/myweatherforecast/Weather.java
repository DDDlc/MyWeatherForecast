package com.example.liangchen.myweatherforecast;
public class Weather {
    private String id;
    private String province;
    private String city;
    private String date;
    private String temperature;
    private String humidity;
    private String PM25;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPM25() {
        return PM25;
    }

    public void setPM25(String pM25) {
        PM25 = pM25;
    }
}