package org.example.lab4.service;

import org.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentLinkedQueue;

public class AsyncApiRequester {
    private WebClient webClient;
    private ConcurrentLinkedQueue<Double> temperatures = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Double> humidities = new ConcurrentLinkedQueue<>();
    private String latitude;
    private String longitude;
    private static final String OPEN_WEATHER_KEY = "Secret ;)";
    private static final String WEATHER_API_KEY = "Secret ;)";

    public AsyncApiRequester(String latitude, String longitude) {
        this.webClient = WebClient.builder().build();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Mono<Void> makeRequestOpenMeteo() {
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.open-meteo.com")
                        .path("v1/forecast")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .queryParam("current", "temperature_2m,relative_humidity_2m")
                        .queryParam("forecast_days", "1")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        return response.flatMap(json -> {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject current = jsonObject.getJSONObject("current");
            temperatures.add(current.getDouble("temperature_2m"));
            humidities.add(current.getDouble("relative_humidity_2m"));
            return Mono.empty();
        });
    }

    public Mono<Void> makeRequestOpenWeather() {
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.openweathermap.org")
                        .path("data/2.5/weather")
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", OPEN_WEATHER_KEY)
                        .queryParam("units", "metric")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        return response.flatMap(json -> {
            JSONObject jsonObject = new JSONObject(json);
            temperatures.add(jsonObject.getJSONObject("main").getDouble("temp"));
            humidities.add(jsonObject.getJSONObject("main").getDouble("humidity"));
            return Mono.empty();
        });
    }

    public Mono<Void> makeRequestWeatherApi() {
        Mono<String> response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("api.weatherapi.com")
                        .path("v1/current.json")
                        .queryParam("key", WEATHER_API_KEY)
                        .queryParam("q", latitude + "," + longitude)
                        .queryParam("aqi", "no")
                        .build())
                .retrieve()
                .bodyToMono(String.class);
        return response.flatMap(json -> {
            JSONObject jsonObject = new JSONObject(json);
            temperatures.add(jsonObject.getJSONObject("current").getDouble("temp_c"));
            humidities.add(jsonObject.getJSONObject("current").getDouble("humidity"));
            return Mono.empty();
        });
    }

    public ConcurrentLinkedQueue<Double> getTemperatures() {
        return temperatures;
    }

    public ConcurrentLinkedQueue<Double> getHumidities() {
        return humidities;
    }

    public double getAverageTemperatures() {
        return temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public double getAverageHumidities() {
        return humidities.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

}