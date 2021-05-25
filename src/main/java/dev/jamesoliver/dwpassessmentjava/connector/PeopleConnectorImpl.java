package dev.jamesoliver.dwpassessmentjava.connector;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PeopleConnectorImpl implements PeopleConnector {

    private final RestTemplate restTemplate;

    @Value("${people.endpoint}")
    private String endpoint;

    @Override
    public List<User> getAllUsers() {
        try {
            log.info("Retrieving all users from: {}", endpoint);

            User[] users = restTemplate.getForObject(endpoint + "/users", User[].class);
            return Arrays.asList(users);

        } catch (Exception exception) {

            log.error("Error retrieving all users from: {} - {}", endpoint, exception.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving people", exception);
        }
    }

    @Override
    public List<User> getUsersByCity(Cities city) {
        try {
            log.info("Retrieving {} users from: {}", city, endpoint);

            User[] users =
                    restTemplate.getForObject(endpoint + "/city/{city}/users", User[].class, city);
            return Arrays.asList(users);

        } catch (Exception exception) {

            log.error(
                    "Error retrieving {} users from: {} - {}",
                    city,
                    endpoint,
                    exception.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving people", exception);
        }
    }
}
