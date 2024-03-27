package com.example.springboot.watchlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.repository.MovieRepository;

@Service
public class MovieService {

	@Autowired
	MovieRepository movieRepo;

	public List<Movie> getAllMovies() throws Exception {
		return movieRepo.findAll();
	}
	
	public Movie getMovieById(Long id) throws Exception{
		return movieRepo.findById(id).get();
	}

	public void saveMovie(Movie movie) throws Exception {
		try {
			movieRepo.save(movie);
		} catch (Exception e) {
			System.out.println("Error in saving movie details");
			throw e;
		}
	}
	
	public void updateMovieDetails(Movie existingMovie){
		try {
			movieRepo.save(existingMovie);
		} catch (Exception e){
			e.printStackTrace();
			throw e;
		}
	}
	
}
