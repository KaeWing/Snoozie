package com.snoozieapp.app.backend.objects.alarms;

import com.snoozieapp.app.backend.objects.audio.Audio;
import com.snoozieapp.app.backend.objects.tracking.SmartWake;

import java.time.LocalTime;

public class Alarm {

    // Fields are subject to be added/removed
    // Save commenting for the end

    private boolean isActive;
    private String label;
    private LocalTime wakeTime;
    private Audio wakeTone;
    private boolean scheduled;
    // size 7
    private boolean [] activeDays;
    private SmartWake smartWake;

    // Getters and Setters

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LocalTime getWakeTime() {
        return wakeTime;
    }

    public void setWakeTime(LocalTime wakeTime) {
        this.wakeTime = wakeTime;
    }

    public Audio getWakeTone() {
        return wakeTone;
    }

    public void setWakeTone(Audio wakeTone) {
        this.wakeTone = wakeTone;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public boolean[] getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(boolean[] activeDays) {
        this.activeDays = activeDays;
    }

    public SmartWake getSmartWake() {
        return smartWake;
    }

    public void setSmartWake(SmartWake smartWake) {
        this.smartWake = smartWake;
    }
}
