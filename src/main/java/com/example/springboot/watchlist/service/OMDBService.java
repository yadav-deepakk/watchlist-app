package com.example.springboot.watchlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.springboot.watchlist.components.ApplicationProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class OMDBService {

	@Autowired
	ApplicationProperty applicationProperty;

	public Float getMovieRating(String movieTitle) {
		try {
			String OMDB_URL = applicationProperty.getOMDBURL();
			String OMDB_API_KEY = applicationProperty.getOMDBAPIKey();
			String ombdUrlWithReqParams = OMDB_URL + "?apikey=" + OMDB_API_KEY + "&t=" + movieTitle;
			RestTemplate restTemplate = new RestTemplate();
			System.out.println("URL: " + ombdUrlWithReqParams);
			ResponseEntity<ObjectNode> restResponse = restTemplate.getForEntity(ombdUrlWithReqParams, ObjectNode.class);
			ObjectNode jsonResp = restResponse.getBody();
			String movieRatingString = jsonResp.path("imdbRating").asText();
			return movieRatingString == null ? null : Float.valueOf(movieRatingString);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
