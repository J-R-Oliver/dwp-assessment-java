package dev.jamesoliver.dwpassessmentjava.model;

import lombok.Getter;

/**
 * Holds information regarding the configured cities for the application.
 *
 * <p>A city contains:
 *
 * <ul>
 *   <li>label - used in the path parameter when calling the upstream API, and used for logging
 *   <li>latitude - used when calculating a person's distance from the city
 *   <li>longitude - used when calculating a person's distance from the city
 * </ul>
 */
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
