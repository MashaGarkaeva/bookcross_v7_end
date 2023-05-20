package com.bookcross;

public class DataClass {//класс для книг
    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public void setDataAuhtor(String dataAuhtor) {
        this.dataAuhtor = dataAuhtor;
    }

    private String dataName;
    private String dataAuhtor;
    private String key;
    private String place;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getdataName() {
        return dataName;
    }
    public String getdataAuhtor() {
        return dataAuhtor;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public DataClass(String dataName, String dataAuhtor, String key, String place) {
        this.dataName = dataName;
        this.dataAuhtor = dataAuhtor;
        this.key = key;
        this.place = place;

    }
    public DataClass(){
    }
}
