package ru.itis.companionapp.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.companionapp.exceptions.CityNotFoundException;
import ru.itis.companionapp.models.City;
import ru.itis.companionapp.repositories.CityRepository;
import ru.itis.companionapp.services.CityService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Override
    public List<City> getCitiesStartingWith(String startsWith) {
        return cityRepository.findStartingWith(startsWith);
    }

    @Override
    public City getByName(String name) {
        return cityRepository.findByName(name).orElseThrow(() -> new CityNotFoundException("City not found"));
    }
}
