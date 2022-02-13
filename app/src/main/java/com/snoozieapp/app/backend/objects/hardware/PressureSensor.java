package com.snoozieapp.app.backend.objects.hardware;

public class PressureSensor {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isEnabled;

    private boolean isPressed;
    private float pressureReading;


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

    public boolean isPressed() {
        return isPressed;
    }

    public void setPressed(boolean pressed) {
        isPressed = pressed;
    }

    public float getPressureReading() {
        return pressureReading;
    }

    public void setPressureReading(float pressureReading) {
        this.pressureReading = pressureReading;
    }
}


