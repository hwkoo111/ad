package deu.movietalk.service;

import deu.movietalk.domain.Movie;
import deu.movietalk.repository.MovieFindRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieFindService {

    private final MovieFindRepository movieFindRepository;

    public MovieFindService(MovieFindRepository movieRepository) {
        this.movieFindRepository = movieRepository;
    }

    public List<Movie> searchMoviesByTitle(String keyword) {
        return movieFindRepository.findByTitleContainingIgnoreCase(keyword);
    }
}