package com.example.multipletask;

public class RetriveDataModel {
    String Title,year;

    public RetriveDataModel() {
    }

    public RetriveDataModel(String title, String year) {
        Title = title;
        this.year = year;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
