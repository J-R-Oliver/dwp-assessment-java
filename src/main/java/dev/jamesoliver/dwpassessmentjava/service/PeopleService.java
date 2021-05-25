package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

import java.util.List;

/** Service methods to retrieve and process people */
public interface PeopleService {

    /**
     * Retrieves all people
     *
     * @return a list of people
     */
    List<User> getAllPeople();

    /**
     * Retrieves all people, filters them by provided city, and returns them in a list
     *
     * @param city location of desired people
     * @return a list of people from provided city
     */
    List<User> getPeopleByCity(Cities city);
}
