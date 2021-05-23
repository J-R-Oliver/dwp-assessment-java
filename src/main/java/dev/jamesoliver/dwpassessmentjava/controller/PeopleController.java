package dev.jamesoliver.dwpassessmentjava.controller;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import dev.jamesoliver.dwpassessmentjava.service.PeopleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "People", description = "Retrieves people")
public class PeopleController {

    private final PeopleService peopleService;

    @Operation(summary = "Retrieve all people")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved all people",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    array =
                                            @ArraySchema(
                                                    schema = @Schema(implementation = User.class)))
                        }),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error",
                        content = {@Content(mediaType = "application/json")})
            })
    @GetMapping()
    public ResponseEntity<List<User>> getAllPeople() {

        log.info("Received request for all people");

        List<User> people = peopleService.getAllPeople();

        log.info("Responding with all people");

        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve people who live in city")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Successfully retrieved all people from city",
                        content = {
                            @Content(
                                    mediaType = "application/json",
                                    array =
                                            @ArraySchema(
                                                    schema = @Schema(implementation = User.class)))
                        }),
                @ApiResponse(
                        responseCode = "404",
                        description = "City not found",
                        content = {@Content(mediaType = "application/json")}),
                @ApiResponse(
                        responseCode = "500",
                        description = "Internal server error",
                        content = {@Content(mediaType = "application/json")})
            })
    @GetMapping("/{city}")
    public ResponseEntity<List<User>> getPeopleByCity(@PathVariable("city") Cities city) {

        log.info("Received request for people from {}", city);

        List<User> people = peopleService.getPeopleByCity(city);

        log.info("Responding with people from {}", city);

        return new ResponseEntity<>(people, HttpStatus.OK);
    }
}
