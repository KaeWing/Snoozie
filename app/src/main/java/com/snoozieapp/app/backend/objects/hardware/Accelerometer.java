package com.snoozieapp.app.backend.objects.hardware;

public class Accelerometer {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isEnabled;

    // Are Objects still type "Sensor_event_t" ?

    // Sensor_event_t a;
    // Sensor_event_t g;

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
}
