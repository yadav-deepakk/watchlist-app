package com.example.springboot.watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	MovieService movieService;

	@GetMapping("/movies")
	public ModelAndView getAllWatchlistMovies() {
		System.out.println("GET: /movies");
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.setViewName("watchlist-movies");
			List<Movie> movies = movieService.getAllMovies();
			if (movies.size() == 0)
				throw new Exception("Empty Movie List");
			modelAndView.addObject("moviesObject", movies);
			modelAndView.addObject("totalMovies", movies.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelAndView.setViewName("error-page");
			modelAndView.addObject("message", e.getMessage());
		}
		return modelAndView;
	}

	@GetMapping("/movie-entry")
	public ModelAndView getMovieForm() {
		System.out.println("GET: /movie-entry");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("movie-form");
		Movie movie = new Movie();
		modelAndView.addObject("movieObject", movie);
		return modelAndView;
	}

	@PostMapping("/watchlist-movie")
	public ModelAndView submitMovieDetailsAndShowAllMovies(Movie movie) {
		System.out.println("POST: /watchlist-movies");
		try {
			movieService.saveMovie(movie);
			RedirectView redirectToWatchlistMoviesPage = new RedirectView();
			redirectToWatchlistMoviesPage.setUrl("/movies");
			return new ModelAndView(redirectToWatchlistMoviesPage);
		} catch (Exception e) {
			e.printStackTrace();
			RedirectView errorPage = new RedirectView();
			errorPage.setUrl("error-page");
			Map<String, String> model = new HashMap<>();
			model.put("message", e.getMessage());
			return new ModelAndView(errorPage, model);
		}
	}

}
