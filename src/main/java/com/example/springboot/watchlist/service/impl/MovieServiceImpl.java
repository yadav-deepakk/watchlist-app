package com.example.springboot.watchlist.service.impl;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.exceptions.ResourceNotFoundException;
import com.example.springboot.watchlist.repository.MovieRepository;
import com.example.springboot.watchlist.service.interfaces.MovieService;
import com.example.springboot.watchlist.service.interfaces.OMDBService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public @Service class MovieServiceImpl implements MovieService {

	private final MovieRepository movieRepo;
	private final OMDBService omdbService;

	public Float getOMDBRating(String title)
			throws HttpClientErrorException, HttpServerErrorException, HttpConnectTimeoutException {
		log.info("MovieServiceImpl || omdbMovieRating | title: {}", title);
		return omdbService.getMovieRating(title);
	}

	public List<Movie> getMovieList() throws ResourceNotFoundException {
		log.info("MovieServiceImpl || getMovieList");
		return movieRepo.findAll();
	}

	public Movie getMovieById(String id) throws ResourceNotFoundException {
		log.info("MovieServiceImpl || getMovieById | movie-id: {}", id);
		Optional<Movie> movie = movieRepo.findById(id);

		if (movie.isEmpty()) {
			log.error("MovieService | getMovieById | Movie not found for id: {}", id);
			throw new ResourceNotFoundException("Movie not found for id: " + id);
		}

		return movie.get();
	}

	public Movie saveMovie(Movie movie) {
		log.info("MovieServiceImpl || saveMovie | movie: {}", movie);
		try {
			return movieRepo.save(movie);
		} catch (Exception e) {
			log.error("MovieServiceImpl || saveMovie | error: {}", e.getMessage(), e);
			throw e;
		}
	}

	public Movie updateMovie(String id, Movie movie) {
		log.info("MovieServiceImpl || updateMovie | id: {}, movie: {}", id, movie);
		try {
			return movieRepo.save(movie);
		} catch (Exception e) {
			log.error("MovieServiceImpl || updateMovie | error: {}", e.getMessage(), e);
			throw e;
		}
	}

	public boolean deleteMovieById(String id) {
		log.info("MovieServiceImpl || deleteMovieById | id: {}", id);

		try {
			movieRepo.deleteById(id);
			return !movieRepo.existsById(id);
		} catch (Exception e) {
			log.error("MovieServiceImpl || deleteMovieById | error: {}", e.getMessage(), e);
			throw e;
		}
	}

}
