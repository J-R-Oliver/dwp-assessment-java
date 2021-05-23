package dev.jamesoliver.dwpassessmentjava.controller;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import dev.jamesoliver.dwpassessmentjava.service.PeopleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("People Controller Tests")
@Slf4j
class PeopleControllerTest {

    private List<User> users;
    @MockBean private PeopleService mockPeopleService;
    @Autowired private MockMvc mockMvc;

    @BeforeEach
    void beforeEach() {
        User userOne =
                User.builder()
                        .id(1)
                        .firstName("Homer")
                        .lastName("Simpson")
                        .email("chunkylover53@aol.com")
                        .ipAddress("192.168.1.1")
                        .latitude(34.003135)
                        .longitude(-117.7228641)
                        .build();

        User userTwo =
                User.builder()
                        .id(2)
                        .firstName("Bart")
                        .lastName("Simpson")
                        .email("seymour.butts@hotmail.com")
                        .ipAddress("172.16.0.0")
                        .latitude(15.45033)
                        .longitude(44.12768)
                        .build();

        users = new ArrayList<>();
        users.add(userOne);
        users.add(userTwo);
    }

    @Nested
    @DisplayName("/api/people")
    class getAllPeopleTest {

        @Test
        @DisplayName("status: 200 - responds with an array of people")
        void respondsWithAnArrayOfPeople() throws Exception {
            when(mockPeopleService.getAllPeople()).thenReturn(users);

            mockMvc.perform(get("/api/people"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.[0].id").value("1"))
                    .andExpect(jsonPath("$.[0].first_name").value("Homer"))
                    .andExpect(jsonPath("$.[0].last_name").value("Simpson"))
                    .andExpect(jsonPath("$.[0].email").value("chunkylover53@aol.com"))
                    .andExpect(jsonPath("$.[0].ip_address").value("192.168.1.1"))
                    .andExpect(jsonPath("$.[0].latitude").value(34.003135))
                    .andExpect(jsonPath("$.[0].longitude").value(-117.7228641))
                    .andExpect(jsonPath("$.[1].id").value("2"))
                    .andExpect(jsonPath("$.[1].first_name").value("Bart"))
                    .andExpect(jsonPath("$.[1].last_name").value("Simpson"))
                    .andExpect(jsonPath("$.[1].email").value("seymour.butts@hotmail.com"))
                    .andExpect(jsonPath("$.[1].ip_address").value("172.16.0.0"))
                    .andExpect(jsonPath("$.[1].latitude").value(15.45033))
                    .andExpect(jsonPath("$.[1].longitude").value(44.12768));
        }
    }

    @Nested
    @DisplayName("/api/people/city")
    class getPeopleByCityTest {

        @Test
        @DisplayName("status: 200 - responds with an array of people from London")
        void respondsWithAnArrayOfPeopleFromLondon() throws Exception {
            when(mockPeopleService.getPeopleByCity(Cities.LONDON)).thenReturn(users);

            mockMvc.perform(get("/api/people/london"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json"))
                    .andExpect(jsonPath("$.[0].id").value("1"))
                    .andExpect(jsonPath("$.[0].first_name").value("Homer"))
                    .andExpect(jsonPath("$.[0].last_name").value("Simpson"))
                    .andExpect(jsonPath("$.[0].email").value("chunkylover53@aol.com"))
                    .andExpect(jsonPath("$.[0].ip_address").value("192.168.1.1"))
                    .andExpect(jsonPath("$.[0].latitude").value(34.003135))
                    .andExpect(jsonPath("$.[0].longitude").value(-117.7228641))
                    .andExpect(jsonPath("$.[1].id").value("2"))
                    .andExpect(jsonPath("$.[1].first_name").value("Bart"))
                    .andExpect(jsonPath("$.[1].last_name").value("Simpson"))
                    .andExpect(jsonPath("$.[1].email").value("seymour.butts@hotmail.com"))
                    .andExpect(jsonPath("$.[1].ip_address").value("172.16.0.0"))
                    .andExpect(jsonPath("$.[1].latitude").value(15.45033))
                    .andExpect(jsonPath("$.[1].longitude").value(44.12768));
        }

        @Test
        @DisplayName("status: 200 - responds successfully regardless of city casing")
        void respondsSuccessfullyRegardlessOfCityCasing() throws Exception {
            when(mockPeopleService.getPeopleByCity(Cities.LONDON)).thenReturn(users);

            mockMvc.perform(get("/api/people/LoNDoN")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        @DisplayName(
                "status: 404 - throws ResponseStatusException with City Not Found when city in path is unavailable")
        void throwsResponseStatusExceptionWithCityNotFoundWhenCityInPathIsUnavailable()
                throws Exception {

            // due to MockMvc container-less testing we are unable to retrieve the error response
            // testing root exception instead

            ResponseStatusException exception =
                    (ResponseStatusException)
                            mockMvc.perform(get("/api/people/notacity"))
                                    .andReturn()
                                    .getResolvedException()
                                    .getCause()
                                    .getCause();

            assertSame(HttpStatus.NOT_FOUND, exception.getStatus());
            assertEquals("City Not Found", exception.getReason());
        }
    }
}
