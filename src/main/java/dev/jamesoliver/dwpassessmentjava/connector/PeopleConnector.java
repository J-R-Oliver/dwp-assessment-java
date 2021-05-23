package dev.jamesoliver.dwpassessmentjava.connector;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

import java.util.List;

public interface PeopleConnector {

    List<User> getAllUsers();
    List<User> getUsersByCity(Cities city);
}
