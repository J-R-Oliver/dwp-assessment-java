package dev.jamesoliver.dwpassessmentjava.controller;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import dev.jamesoliver.dwpassessmentjava.service.PeopleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/people")
@RequiredArgsConstructor
@Slf4j
public class PeopleController {

    private final PeopleService peopleService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllPeople() {

        log.info("Received request for all people");

        List<User> people = peopleService.getAllPeople();

        log.info("Responding with all people");

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @GetMapping("/{city}")
    public ResponseEntity<List<User>> getPeopleByCity(@PathVariable("city") Cities city) {

        log.info("Received request for people from {}", city);

        List<User> people = peopleService.getPeopleByCity(city);

        log.info("Responding with people from {}", city);

        return new ResponseEntity<>(people, HttpStatus.OK);
    }
}
