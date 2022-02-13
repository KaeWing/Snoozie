package com.snoozieapp.app.backend.handlers;

import com.snoozieapp.app.backend.objects.hardware.Accelerometer;
import com.snoozieapp.app.backend.objects.hardware.AmbientLightSensor;
import com.snoozieapp.app.backend.objects.hardware.Microphone;
import com.snoozieapp.app.backend.objects.hardware.PressureSensor;
import com.snoozieapp.app.backend.objects.hardware.Speaker;
import com.snoozieapp.app.backend.objects.hardware.TemperatureSensor;

public class HardwareHandler {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private Microphone microphone;
    private PressureSensor pressureSensor;
    private Accelerometer accelerometer;
    private AmbientLightSensor ambientLightSensor;
    private TemperatureSensor temperatureSensor;
    private Speaker leftSpeaker;
    private Speaker rightSpeaker;

    // User controlled, for sleep cycle tracking

    public void enableMicrophone()
    {

    }

    public void disableMicrophone()
    {

    }

    public void enablePressureSensor()
    {

    }

    public void disablePressureSensor()
    {

    }

    public void enableAccelerometer()
    {

    }

    public void disableAccelerometer()
    {

    }

    public void enableAmbientLightSensor()
    {

    }

    public void disableAmbientLightSensor()
    {

    }
}
