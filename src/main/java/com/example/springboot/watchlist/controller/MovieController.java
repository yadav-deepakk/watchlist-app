package com.example.springboot.watchlist.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.springboot.watchlist.entity.Movie;
import com.example.springboot.watchlist.exceptions.ResourceNotFoundException;
import com.example.springboot.watchlist.service.interfaces.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/")
public @Controller class MovieController {

	private static final String ERROR_PAGE = "error-page";
	private static final String ERROR_MSG_PARAM = "errorMessage";

	private final MovieService movieService;

	@GetMapping(path = "movies", produces = "text/html")
	public String getAllWatchlistMovies(Model model) {
		log.info("MovieController | getAllWatchlistMovies | GET: /movies");
		try {
			List<Movie> movies = movieService.getMovieList();
			if (movies.isEmpty())
				throw new ResourceNotFoundException("Empty Movie List");
			model.addAttribute("moviesObject", movies);
			model.addAttribute("totalMovies", movies.size());
			return "movies";

		} catch (Exception e) {

			log.error("getAllWatchlistMovies | error:{}", e.getMessage(), e);
			model.addAttribute(ERROR_MSG_PARAM, e.getMessage());
			return ERROR_PAGE;
		}
	}

	@GetMapping(path = "movie-entry", produces = "text/html")
	public String getMovieForm(@RequestParam(required = false) String id, Model model) {
		log.info("MovieController | getMovieForm | GET: /movie-entry");
		try {

			if (id != null) {
				log.info("Existing movie.");
				model.addAttribute("movieObject", movieService.getMovieById(id));
			} else {
				model.addAttribute("movieObject", new Movie());
			}

		} catch (Exception e) {
			log.error("getMovieForm | error : {}", e.getMessage(), e);
			throw e;
		}

		return "movie-form";
	}

	// same method is used to saveMovie and updateMovie
	@PostMapping(path = "submitMovie", consumes = "application/x-www-form-urlencoded", produces = "text/html")
	public String submitMovieDetailsAndShowAllMovies(@Valid @ModelAttribute("movieObject") Movie movie,
			BindingResult bindingResult, Model model) {
		log.info("POST: /submitMovie");
		log.info("MovieController | submitMovieDetailsAndShowAllMovies | movie: {}", movie);

		if (bindingResult.hasErrors()) {
			log.info("Form has errors : {}", bindingResult.getAllErrors().toString());
			return "/movie-form";
		}

		try {
			// if same movie has a different rating in OMDB then use omdbRating.
			Float omdbRating = movieService.getOMDBRating(movie.getTitle());
			if (omdbRating != null) {
				log.info("Ignoring entry-form rating and Using OMDB rating...");
				log.info("OMDB Rating: " + omdbRating);
				movie.setRating(omdbRating);
			}

			if (movie.getId() == null || movie.getId().isEmpty()) {
				log.info("id is null or empty, saving new movie details.");
			    movie.setId(null); // Important: ensure null for UUID generation
				movieService.saveMovie(movie);
			} else {
				log.info("id is not null, updating movie details.");
				movieService.updateMovie(movie.getId(), movie);
			}

			return "redirect:/movies";

		} catch (Exception e) {

			log.info("MovieController | submitMovieDetailsAndShowAllMovies | error: {}", e.getMessage(), e);
			model.addAttribute(ERROR_MSG_PARAM, e.getMessage());
			return ERROR_PAGE;
		}
	}

	@GetMapping("deleteMovie")
	public String deleteMovieById(@RequestParam(required = true) String id, Model model) {
		log.info("GET: /deleteMovie");
		log.info("MovieController | deleteMovie | id: {}", id);

		try {
			boolean success = movieService.deleteMovieById(id);
			if (!success) {
				throw new Exception("error occured in movie deltion.");
			}
			return "redirect:/movies";

		} catch (Exception e) {

			log.info("MovieController | deleteMovie | error: {}", e.getMessage(), e);
			model.addAttribute(ERROR_MSG_PARAM, e.getMessage());
			return ERROR_PAGE;
		}
	}

}
