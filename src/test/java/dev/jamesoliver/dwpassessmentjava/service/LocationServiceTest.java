package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Location Service Tests")
class LocationServiceTest {

    private final LocationService locationService = new LocationServiceImpl();

    @Nested
    @DisplayName("Distance From City Tests")
    class distanceFromCityTest {

        @Test
        @DisplayName(
                "Returns correct distance in miles when passed a User and a instance of Cities")
        void returnsCorrectDistanceInMilesWhenPassedAUserAndAInstanceOfCities() {
            User user =
                    User.builder()
                            .id(1)
                            .firstName("Homer")
                            .lastName("Simpson")
                            .email("chunkylover53@aol.com")
                            .ipAddress("192.168.1.1")
                            .latitude(34.003135)
                            .longitude(-117.7228641)
                            .build();

            double distanceMiles = locationService.distanceFromCity(Cities.LONDON, user);

            assertEquals(5433.428353701655, distanceMiles);
        }
    }

    @Nested
    @DisplayName("Meters To Miles")
    class MetersToMilesTest {

        @Test
        @DisplayName("Returns one mile when passed the equivalent number of meters")
        void returnsOneMileWhenPassedTheEquivalentNumberOfMeters() {

            double miles = locationService.metersToMiles(1609.344);

            assertEquals(1, miles);
        }
    }
}
