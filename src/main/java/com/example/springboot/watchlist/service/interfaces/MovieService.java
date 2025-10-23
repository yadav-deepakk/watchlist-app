package com.example.springboot.watchlist.service.interfaces;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.exceptions.ResourceNotFoundException;

public interface MovieService {
	Float getOMDBRating(String title) throws HttpClientErrorException, HttpServerErrorException, HttpConnectTimeoutException; 
	Movie getMovieById(String id) throws ResourceNotFoundException;
	List<Movie> getMovieList() throws ResourceNotFoundException; 
	Movie saveMovie(Movie movie); 
	Movie updateMovie(String id, Movie movie); 
	boolean deleteMovieById(String id); 
}
