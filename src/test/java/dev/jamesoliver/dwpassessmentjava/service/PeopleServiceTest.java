package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.connector.PeopleConnector;
import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@DisplayName("People Service Tests")
@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    @Mock private PeopleConnector mockPeopleConnector;
    @Mock private LocationService mockLocationService;
    private PeopleService peopleService;
    private List<User> users;
    private User userInLondon;
    private User userOutsideLondon;

    @BeforeEach
    void beforeEach() throws NoSuchFieldException, IllegalAccessException {
        peopleService = new PeopleServiceImpl(mockPeopleConnector, mockLocationService);
        Field maxDistance = peopleService.getClass().getDeclaredField("maxDistance");
        maxDistance.setAccessible(true);
        maxDistance.set(peopleService, 50);

        userInLondon =
                User.builder()
                        .id(1)
                        .firstName("Homer")
                        .lastName("Simpson")
                        .email("chunkylover53@aol.com")
                        .ipAddress("192.168.1.1")
                        .latitude(34.003135)
                        .longitude(-117.7228641)
                        .build();

        userOutsideLondon =
                User.builder()
                        .id(2)
                        .firstName("Bart")
                        .lastName("Simpson")
                        .email("seymour.butts@hotmail.com")
                        .ipAddress("172.16.0.0")
                        .latitude(27.69417)
                        .longitude(109.73583)
                        .build();

        users = new ArrayList<>();
        users.add(userInLondon);
        users.add(userOutsideLondon);
    }

    @Nested
    @DisplayName("Get All People Tests")
    class GetAllPeopleTest {

        @Test
        @DisplayName("Returns all people when PeopleConnector is successful")
        void returnsAllPeopleWhenPeopleConnectorIsSuccessful() {

            when(mockPeopleConnector.getAllUsers()).thenReturn(users);

            List<User> allUsers = peopleService.getAllPeople();
            assertSame(users, allUsers);
        }
    }

    @Nested
    @DisplayName("Get People By City Tests")
    class GetPeopleByCityTest {

        private List<User> londonUsers;

        @BeforeEach
        void beforeEach() {
            User userOne =
                    User.builder()
                            .id(3)
                            .firstName("Marge")
                            .lastName("Simpson")
                            .email("marge-simpson@aol.com")
                            .ipAddress("10.0.0.0")
                            .latitude(-6.5115909)
                            .longitude(105.652983)
                            .build();

            User userTwo =
                    User.builder()
                            .id(4)
                            .firstName("Lisa")
                            .lastName("Simpson")
                            .email("l.simpson@hotmail.com")
                            .ipAddress("127.0.0.1")
                            .latitude(-6.7098551)
                            .longitude(111.3479498)
                            .build();

            londonUsers = new ArrayList<>();
            londonUsers.add(userOne);
            londonUsers.add(userTwo);
        }

        @Test
        @DisplayName("Returns people filtered by city when PeopleConnector is successful")
        void returnsPeopleFilteredByCityWhenPeopleConnectorIsSuccessful() {

            when(mockPeopleConnector.getAllUsers()).thenReturn(users);
            when(mockPeopleConnector.getUsersByCity(Cities.LONDON)).thenReturn(londonUsers);
            when(mockLocationService.distanceFromCity(Cities.LONDON, userInLondon)).thenReturn(40D);
            when(mockLocationService.distanceFromCity(Cities.LONDON, userOutsideLondon))
                    .thenReturn(51D);

            List<User> peopleFromLondon = peopleService.getPeopleByCity(Cities.LONDON);

            assertEquals(3, peopleFromLondon.size());
        }
    }
}
