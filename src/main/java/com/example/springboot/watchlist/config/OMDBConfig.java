package com.example.springboot.watchlist.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
public @Component class OMDBConfig {
	private @Value("${omdb.url}") String apiUrl;
	private @Value("${omdb.api-key}") String apiKey;
}