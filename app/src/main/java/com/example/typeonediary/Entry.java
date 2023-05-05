package com.example.typeonediary;

public class Entry {
    String id;
    String date;
    Float bloodGlucose;
    Float carbs;
    Float insulin;
    String note;

    public Entry(String id, String date, Float bloodGlucose, Float carbs, Float insulin, String note) {
        this.id = id;
        this.date = date;
        this.bloodGlucose = bloodGlucose;
        this.carbs = carbs;
        this.insulin = insulin;
        this.note = note;
    }
}
