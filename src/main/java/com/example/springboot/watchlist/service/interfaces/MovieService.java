package com.example.springboot.watchlist.service.interfaces;

import java.util.List;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.exceptions.ResourceNotFoundException;

public interface MovieService {
	Movie getMovieById(String id) throws ResourceNotFoundException;
	List<Movie> getMovieList() throws ResourceNotFoundException; 
	Movie saveMovie(Movie movie); 
	Movie updateMovie(String id, Movie movie); 
	boolean deleteMovieById(String id); 
}
