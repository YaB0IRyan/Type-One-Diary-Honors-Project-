package com.example.typeonediary;

public class Entry {
    String id;
    String date;
    String time;
    Float bloodGlucose;
    Float carbs;
    Float insulin;
    String note;

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", bloodGlucose=" + bloodGlucose +
                ", carbs=" + carbs +
                ", insulin=" + insulin +
                ", note='" + note + '\'' +
                '}';
    }

    public Entry(String id, String date, String time, Float bloodGlucose, Float carbs, Float insulin, String note) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.bloodGlucose = bloodGlucose;
        this.carbs = carbs;
        this.insulin = insulin;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Float getBloodGlucose() {
        return bloodGlucose;
    }

    public void setBloodGlucose(Float bloodGlucose) {
        this.bloodGlucose = bloodGlucose;
    }

    public Float getCarbs() {
        return carbs;
    }

    public void setCarbs(Float carbs) {
        this.carbs = carbs;
    }

    public Float getInsulin() {
        return insulin;
    }

    public void setInsulin(Float insulin) {
        this.insulin = insulin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

