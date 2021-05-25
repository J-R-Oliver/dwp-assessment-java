package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.connector.PeopleConnector;
import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleConnector peopleConnector;
    private final LocationService locationService;

    @Value("${people.max-distance}")
    private double maxDistance;

    public List<User> getAllPeople() {
        return peopleConnector.getAllUsers();
    }

    @Override
    public List<User> getPeopleByCity(Cities city) {
        List<User> allPeople = peopleConnector.getAllUsers();
        List<User> peopleByCity = peopleConnector.getUsersByCity(city);

        Predicate<User> filter =
                user -> {
                    double distanceFromCity = locationService.distanceFromCity(city, user);

                    return distanceFromCity <= maxDistance;
                };

        Stream<User> filteredPeople = allPeople.stream().filter(filter);
        return Stream.concat(filteredPeople, peopleByCity.stream()).collect(Collectors.toList());
    }
}
