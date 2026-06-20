package com.farmerretailer.controller;

import com.farmerretailer.entity.Weather;
import com.farmerretailer.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/{location}")
    public ResponseEntity<Weather> getWeather(@PathVariable String location, org.springframework.security.core.Authentication auth) {
        System.out.println("WeatherController: Fetching weather for " + location + " (User: " + (auth != null ? auth.getName() : "anon") + ")");
        return ResponseEntity.ok(weatherService.getLatestWeather(location));
    }

    @GetMapping("/{location}/farmer-ai")
    public ResponseEntity<Map<String, String>> getFarmerAI(@PathVariable String location, org.springframework.security.core.Authentication auth) {
        System.out.println("WeatherController: Fetching Farmer AI for " + location + " (User: " + (auth != null ? auth.getName() : "anon") + ")");
        Weather w = weatherService.getLatestWeather(location);
        return ResponseEntity.ok(weatherService.getAISuggestionsForFarmer(w));
    }

    @GetMapping("/{location}/retailer-ai")
    public ResponseEntity<Map<String, String>> getRetailerAI(@PathVariable String location, org.springframework.security.core.Authentication auth) {
        System.out.println("WeatherController: Fetching Retailer AI for " + location + " (User: " + (auth != null ? auth.getName() : "anon") + ")");
        Weather w = weatherService.getLatestWeather(location);
        return ResponseEntity.ok(weatherService.getAIInsightsForRetailer(w));
    }

    @PutMapping("/{id}/override")
    public ResponseEntity<Weather> overrideWeather(@PathVariable Long id, @RequestBody Weather weather) {
        return ResponseEntity.ok(weatherService.updateWeatherManual(id, weather));
    }
}
