package ru.itis.companionapp.services;

import org.springframework.stereotype.Service;
import ru.itis.companionapp.models.City;

import java.util.List;

public interface CityService {
    List<City> getCitiesStartingWith(String startsWith);
    City getByName(String name);
}
