package com.example.springboot.watchlist.service.impl;

import java.net.http.HttpConnectTimeoutException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.springboot.watchlist.config.OMDBConfig;
import com.example.springboot.watchlist.service.interfaces.OMDBService;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public @Service class OMDBServiceImpl implements OMDBService {

	private final OMDBConfig omdbConfig;
	private final RestTemplate restTemplate;

	@Override
	public String getMovie(String title)
			throws HttpConnectTimeoutException, HttpClientErrorException, HttpServerErrorException {
		log.info("OMDBServiceImpl || getMovie | title: {}", title);
		return null;
	}

	public Float getMovieRating(String title) {
		try {

			String destUrl = omdbConfig.getApiUrl() + "?apikey=" + omdbConfig.getApiKey() + "&t=" + title;
			log.info("URL: " + destUrl);

			ResponseEntity<ObjectNode> restResponse = restTemplate.getForEntity(destUrl, ObjectNode.class);
			ObjectNode jsonResp = restResponse.getBody();

			String movieRatingString = jsonResp.path("imdbRating").asText();

			return movieRatingString == null ? null : Float.valueOf(movieRatingString);

		} catch (Exception e) {
			log.error("OMDBService | getMovieRating | error : {}", e.getMessage(), e);
			return null;
		}

	}

}
