package dev.jamesoliver.dwpassessmentjava.model;

import lombok.Getter;

@Getter
public enum Cities {
    LONDON("London", 51.514248, -0.093145);

    private final String label;
    private final double latitude;
    private final double longitude;

    Cities(String label, double latitude, double longitude) {
        this.label = label;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
