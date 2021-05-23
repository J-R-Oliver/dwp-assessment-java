package dev.jamesoliver.dwpassessmentjava.connector;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("People Connector Tests")
@ExtendWith(MockitoExtension.class)
class PeopleConnectorTest {

    private PeopleConnector peopleConnector;
    @Mock private RestTemplate mockRestTemplate;
    private User[] userResponse;
    private List<User> expectedUsers;

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        peopleConnector = new PeopleConnectorImpl(mockRestTemplate);

        Field endpoint = peopleConnector.getClass().getDeclaredField("endpoint");
        endpoint.setAccessible(true);
        endpoint.set(peopleConnector, "domain");

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

        userResponse = new User[] {userOne, userTwo};
        expectedUsers = Arrays.asList(userResponse);
    }

    @Nested
    @DisplayName("Get All Users Test")
    class GetAllUsersTest {

        @Test
        @DisplayName("Returns a list of User when API returns a successful response")
        void returnsAListOfUserWhenApiReturnsASuccessfulResponse() {
            when(mockRestTemplate.getForObject("domain/users", User[].class))
                    .thenReturn(userResponse);

            List<User> actualUsers = peopleConnector.getAllUsers();
            assertEquals(expectedUsers, actualUsers);
        }

        @Test
        @DisplayName("Throws ResponseStatusException when API returns a failure response")
        void throwsResponseStatusExceptionWhenApiReturnsAFailureResponse() {

            HttpServerErrorException internalServerError =
                    HttpServerErrorException.InternalServerError.create(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            new HttpHeaders(),
                            null,
                            null);

            when(mockRestTemplate.getForObject("domain/users", User[].class))
                    .thenThrow(internalServerError);

            ResponseStatusException exception =
                    assertThrows(
                            ResponseStatusException.class, () -> peopleConnector.getAllUsers());

            assertSame(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
            assertEquals("Error retrieving people", exception.getReason());
        }
    }

    @Nested
    @DisplayName("Get Users By City Test")
    class GetUsersByCityTest {

        @Test
        @DisplayName("Returns a list of User when API returns a successful response")
        void returnsAListOfUserWhenApiReturnsASuccessfulResponse() {
            when(mockRestTemplate.getForObject(
                            "domain/city/{city}/users", User[].class, Cities.LONDON))
                    .thenReturn(userResponse);

            List<User> actualUsers = peopleConnector.getUsersByCity(Cities.LONDON);
            assertEquals(expectedUsers, actualUsers);
        }

        @Test
        @DisplayName("Throws ResponseStatusException when API returns a failure response")
        void throwsResponseStatusExceptionWhenApiReturnsAFailureResponse() {

            HttpServerErrorException internalServerError =
                    HttpServerErrorException.InternalServerError.create(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Internal Server Error",
                            new HttpHeaders(),
                            null,
                            null);

            when(mockRestTemplate.getForObject(
                            "domain/city/{city}/users", User[].class, Cities.LONDON))
                    .thenThrow(internalServerError);

            ResponseStatusException exception =
                    assertThrows(
                            ResponseStatusException.class,
                            () -> peopleConnector.getUsersByCity(Cities.LONDON));

            assertSame(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
            assertEquals("Error retrieving people", exception.getReason());
        }
    }
}
