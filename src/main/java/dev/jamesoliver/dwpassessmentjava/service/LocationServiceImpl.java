package dev.jamesoliver.dwpassessmentjava.service;

import dev.jamesoliver.dwpassessmentjava.model.Cities;
import dev.jamesoliver.dwpassessmentjava.model.User;
import org.apache.lucene.spatial.util.GeoDistanceUtils;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    public double distanceFromCity(Cities city, User user) {
        double distanceInMeters =
                GeoDistanceUtils.vincentyDistance(
                        city.getLatitude(),
                        city.getLongitude(),
                        user.getLatitude(),
                        user.getLongitude());
        return metersToMiles(distanceInMeters);
    }

    public double metersToMiles(double meters) {
        return meters / 1609.344;
    }
}
