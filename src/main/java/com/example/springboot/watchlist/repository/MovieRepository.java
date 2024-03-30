package com.example.springboot.watchlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springboot.watchlist.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{
}
