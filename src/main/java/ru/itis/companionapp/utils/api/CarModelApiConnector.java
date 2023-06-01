package ru.itis.companionapp.utils.api;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class CarModelApiConnector {
    private static final String API_URL_TEMPLATE = "https://api.api-ninjas.com/v1/cars?limit=50&make=";
    private static final String API_KEY = "P6PYh3f2KGbdQrhxGuUBDQ==yFmYI4QgbZ8mRvVs";

    public String getCarModelsByBrandJson(String brand) {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL_TEMPLATE + brand))
                    .header("X-Api-Key", API_KEY)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException e) {
            return null;
        }

    }
}
