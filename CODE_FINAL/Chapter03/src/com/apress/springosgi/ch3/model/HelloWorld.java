package com.apress.springosgi.ch3.model;

import java.util.Date;

public class HelloWorld {

    private String message;
    private Date currentTime;
    private double modelVersion;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setModelVersion(double modelVersion) {
        this.modelVersion = modelVersion;
    }

    public double getModelVersion() {
        return modelVersion;
    }

    public HelloWorld(String message, Date currentTime, double modelVersion) {
        this.message = message;
        this.currentTime = currentTime;
        this.modelVersion = modelVersion;
    }

    public HelloWorld() {
    }
}
