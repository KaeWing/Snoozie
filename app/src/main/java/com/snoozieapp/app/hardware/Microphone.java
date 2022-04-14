package com.snoozieapp.app.hardware;

public class Microphone {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isEnabled;
    private int sampleWindow;
    private long sample;
    private long startMillis;
    private long peakToPeak;
    private long signalMax;
    private long getSignalMin;
    private long volts;

    public void setup()
    {
        // do stuff
    }

    public void loop()
    {
        // continuously read the value
    }

    // Getters and Setter

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public int getSampleWindow() {
        return sampleWindow;
    }

    public void setSampleWindow(int sampleWindow) {
        this.sampleWindow = sampleWindow;
    }

    public long getSample() {
        return sample;
    }

    public void setSample(long sample) {
        this.sample = sample;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getPeakToPeak() {
        return peakToPeak;
    }

    public void setPeakToPeak(long peakToPeak) {
        this.peakToPeak = peakToPeak;
    }

    public long getSignalMax() {
        return signalMax;
    }

    public void setSignalMax(long signalMax) {
        this.signalMax = signalMax;
    }

    public long getGetSignalMin() {
        return getSignalMin;
    }

    public void setGetSignalMin(long getSignalMin) {
        this.getSignalMin = getSignalMin;
    }

    public long getVolts() {
        return volts;
    }

    public void setVolts(long volts) {
        this.volts = volts;
    }
}
