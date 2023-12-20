package org.example.lab4.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.example.lab4.model.Coordinates;
import org.springframework.stereotype.Service;

@Service
public class GeocodingService {
    private static final String GEOCODING_API_KEY = "Secret ;)";
    public Coordinates getCoordinatesByCity(String city) throws Exception {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(GEOCODING_API_KEY)
                .build();

        GeocodingResult[] results =  GeocodingApi.geocode(context, city).await();
        if (results != null && results.length > 0) {
            double lat = results[0].geometry.location.lat;
            double lon = results[0].geometry.location.lng;
            return new Coordinates(lat, lon);
        } else {
            throw new RuntimeException("No results found for " + city);
        }
    }


}