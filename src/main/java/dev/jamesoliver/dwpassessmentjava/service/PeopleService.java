package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;

import java.util.List;

public interface PeopleService {

    List<User> getAllPeople();
    List<User> getPeopleByCity(Cities city);
}
