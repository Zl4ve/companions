package ru.itis.companionapp.controllers.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.companionapp.exceptions.CityNotFoundException;
import ru.itis.companionapp.models.City;
import ru.itis.companionapp.services.CityService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @ApiOperation(value = "Get all cities starts with param")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
    })
    @GetMapping
    public List<City> getSuggestedCities(@RequestParam String startsWith) {
        List<City> cities = cityService.getCitiesStartingWith(startsWith);
        if (cities.isEmpty()) {
            throw new CityNotFoundException("Cities not found");
        }
        return cities;
    }
}
