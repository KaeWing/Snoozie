package com.snoozieapp.app.hardware;

public class TemperatureSensor {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isEnabled;

    private float temperature;
    private float heatIndexC;
    private float heatIndexF;
    private float humidity;

    public void setup()
    {
        // do stuff
    }

    public void loop()
    {
        // continuously read the value
    }

    // Getters and Setters

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHeatIndexC() {
        return heatIndexC;
    }

    public void setHeatIndexC(float heatIndexC) {
        this.heatIndexC = heatIndexC;
    }

    public float getHeatIndexF() {
        return heatIndexF;
    }

    public void setHeatIndexF(float heatIndexF) {
        this.heatIndexF = heatIndexF;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
