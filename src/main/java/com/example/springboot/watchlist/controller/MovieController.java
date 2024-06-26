package com.example.springboot.watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView getMovieForm(@RequestParam(name = "id", required = false) Long id) {
		System.out.println("GET: /movie-entry");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("movie-form");
		if (id != null) {
			System.out.println("Existing movie");
			try {
				modelAndView.addObject("movieObject", movieService.getMovieById(id));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			modelAndView.addObject("movieObject", new Movie());
		}
		return modelAndView;
	}

	@PostMapping("/watchlist-movie")
	public ModelAndView submitMovieDetailsAndShowAllMovies(@Valid @ModelAttribute("movieObject") Movie movie,
			BindingResult bindingResult) {
		System.out.println("POST: /watchlist-movies");
		System.out.println(movie);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.hasErrors());
			return new ModelAndView("/movie-form");
		}

		try {
			// if same movie has a different rating in imdb then use that one.
			Float omdbRating = movieService.omdbMovieRating(movie.getTitle());
			if (omdbRating != null) {
				System.out.println("Ignoring entry-form rating and Using OMDB rating...");
				System.out.println("OMDB Rating: " + omdbRating);
				movie.setRating(omdbRating);
			}

			if (movie.getId() != null) {
				System.out.println("Updating the details.");
				movieService.updateMovieDetails(movie);
			} else {
				System.out.println("Saving the details.");
				movieService.saveMovie(movie);
			}
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

	@GetMapping("/deleteMovie")
	public ModelAndView deleteMovieById(@RequestParam(name = "id", required = true) Long id) {
		System.out.println("Delete: /deleteMovie");
		try {
			System.out.println("Trying to delete Id: " + id);
			movieService.deleteMovieById(id);
			RedirectView redirectToWatchlistMoviesPage = new RedirectView();
			redirectToWatchlistMoviesPage.setUrl("/movies");
			return new ModelAndView(redirectToWatchlistMoviesPage);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Some error occured in deletion.");
			ModelAndView modelAndView = new ModelAndView(); 
			modelAndView.setViewName("error-page");
			modelAndView.addObject("message", e.getMessage());
			return modelAndView; 
		}
	}

}
