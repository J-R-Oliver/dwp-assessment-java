package dev.jamesoliver.dwpassessmentjava.connector;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

import java.util.List;

/** Retrieves users via RESTful API requests */
public interface PeopleConnector {

    /**
     * Retrieves users from all users endpoint
     *
     * @return a list of all users
     */
    List<User> getAllUsers();

    /**
     * Retrieves users from city endpoint
     *
     * @param city location of desired users
     * @return a list of users from provided city
     */
    List<User> getUsersByCity(Cities city);
}
