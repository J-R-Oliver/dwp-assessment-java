package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

public interface LocationService {

    double distanceFromCity(Cities city, User user);
    double metersToMiles(double meters);
}
