package com.StudentLibrary.Studentlibrary.Services;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleBooksService {
    private final RestTemplate restTemplate = new RestTemplate();

    public String searchBook(String query) {

        String url =
                "https://www.googleapis.com/books/v1/volumes?q=" + query;

        return restTemplate.getForObject(url, String.class);
    }
}
