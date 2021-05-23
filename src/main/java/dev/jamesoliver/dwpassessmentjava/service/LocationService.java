package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

/** Reusable distance utility methods */
public interface LocationService {

    /**
     * Calculates the distance in miles between a city's coordinates and a user's coordinates
     *
     * @param city city containing longitudinal and latitudinal coordinates
     * @param user user containing longitudinal and latitudinal coordinates
     * @return the distance (in miles) between the city's coordinates and the user's coordinates
     */
    double distanceFromCity(Cities city, User user);

    /**
     * Converts distance from metric meters into imperial miles
     *
     * @param meters distance in meters
     * @return miles distance in miles
     */
    double metersToMiles(double meters);
}
