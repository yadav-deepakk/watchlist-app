package com.example.springboot.watchlist.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

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
	private static final String ERROR_MSG = "errorMessage";

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
			return "watchlist-movies";
			
		} catch (Exception e) {
			
			log.error("getAllWatchlistMovies | error:{}", e);
			model.addAttribute(ERROR_MSG, e.getMessage());
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
			log.error("getAllWatchlistMovies | error : {}", e);
			throw e;
		}

		return "movie-form";
	}

	@PostMapping(path = "watchlist-movie", consumes = "application/x-www-form-urlencoded", produces = "text/html")
	public String submitMovieDetailsAndShowAllMovies(@Valid @ModelAttribute("movieObject") Movie movie,
			BindingResult bindingResult) {
		log.info("MovieController | POST: /watchlist-movies | submitMovieDetailsAndShowAllMovies | movie:{}", movie);
		if (bindingResult.hasErrors()) {
			log.info("", bindingResult.hasErrors());
			return "/movie-form";
		}

		try {
			// if same movie has a different rating in omdb then use that one.
			Float omdbRating = movieService.omdbMovieRating(movie.getTitle());
			if (omdbRating != null) {
				log.info("Ignoring entry-form rating and Using OMDB rating...");
				log.info("OMDB Rating: " + omdbRating);
				movie.setRating(omdbRating);
			}

			if (movie.getId() != null) {
				log.info("Updating the details.");
				movieService.updateMovieDetails(movie);
			} else {
				log.info("Saving the details.");
				movieService.saveMovie(movie);
			}
			return "redirect:/movies";

		} catch (Exception e) {
			e.printStackTrace();
			RedirectView errorPage = new RedirectView();
			errorPage.setUrl("error-page");
			Map<String, String> model = new HashMap<>();
			model.put("message", e.getMessage());
			return new ModelAndView(errorPage, model);
		}
	}

	@GetMapping("deleteMovie")
	public String deleteMovieById(@RequestParam(required = true) Long id) {
		log.info("Delete: /deleteMovie");
		try {
			log.info("Trying to delete Id: " + id);
			movieService.deleteMovieById(id);
			RedirectView redirectToWatchlistMoviesPage = new RedirectView();
			redirectToWatchlistMoviesPage.setUrl("/movies");
			return new ModelAndView(redirectToWatchlistMoviesPage);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Some error occured in deletion.");
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("error-page");
			modelAndView.addObject("message", e.getMessage());
			return modelAndView;
		}
	}

}
