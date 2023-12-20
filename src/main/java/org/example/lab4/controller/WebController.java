package org.example.lab4.controller;

import org.example.lab4.service.AsyncApiRequester;
import org.example.lab4.model.Coordinates;
import org.example.lab4.service.GeocodingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.CountDownLatch;

@Controller
public class WebController {
    private final GeocodingService geocodingService;

    @Autowired
    public WebController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/weather")
    public ModelAndView page1(@RequestParam("lat") String lat, @RequestParam("lon") String lon, Model model) throws InterruptedException {
        AsyncApiRequester requester = new AsyncApiRequester(lat, lon);
        CountDownLatch latch = new CountDownLatch(3); // 3 for the number of API requests
        requester.makeRequestOpenMeteo().doOnTerminate(latch::countDown).subscribe();
        requester.makeRequestOpenWeather().doOnTerminate(latch::countDown).subscribe();
        requester.makeRequestWeatherApi().doOnTerminate(latch::countDown).subscribe();
        latch.await(); // wait for all requests to complete

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("showWeather");
        modelAndView.addObject("temperature", requester.getAverageTemperatures());
        modelAndView.addObject("humidity", requester.getAverageHumidities());
        return modelAndView;
    }

    @GetMapping("/geocode")
    public String page2(@RequestParam ("city") String city, Model model) throws Exception {
        Coordinates response = geocodingService.getCoordinatesByCity(city);
        return "redirect:/weather?lat=" + response.getLat() + "&lon=" + response.getLon();
    }
}