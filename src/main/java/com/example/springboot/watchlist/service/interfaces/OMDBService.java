package com.example.springboot.watchlist.service.interfaces;

import java.net.http.HttpConnectTimeoutException;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

public interface OMDBService {
	String getMovie(String title) throws HttpConnectTimeoutException, HttpClientErrorException, HttpServerErrorException; 
	Float getMovieRating(String title) throws HttpConnectTimeoutException, HttpClientErrorException, HttpServerErrorException; 
}
