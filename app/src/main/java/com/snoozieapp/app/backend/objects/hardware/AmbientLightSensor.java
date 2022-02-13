package com.snoozieapp.app.backend.objects.hardware;

public class AmbientLightSensor {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isEnabled;

    // Shouldn't these be floats/doubles for precision
    private int photocellPin;
    private int photocellReading;

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

    public int getPhotocellPin() {
        return photocellPin;
    }

    public void setPhotocellPin(int photocellPin) {
        this.photocellPin = photocellPin;
    }

    public int getPhotocellReading() {
        return photocellReading;
    }

    public void setPhotocellReading(int photocellReading) {
        this.photocellReading = photocellReading;
    }
}
