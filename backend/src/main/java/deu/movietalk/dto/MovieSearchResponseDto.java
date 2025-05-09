package deu.movietalk.dto;

import deu.movietalk.domain.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieSearchResponseDto {
    private Long movieId;
    private String title;
    private String genre;
    private String director;
    private String actor;
    private Integer runtime;
    private String rating;
    private String releaseDts;
    private String posterUrl;

    private double averageRating;
    private int reviewCount;
    private List<ReviewResponseDto> reviews;

    public MovieSearchResponseDto(Movie movie, double averageRating, int reviewCount, List<ReviewResponseDto> reviews) {
        this.movieId = movie.getMovieId();
        this.title = movie.getTitle();
        this.genre = movie.getGenre();
        this.director = movie.getDirector();
        this.actor = movie.getActor();
        this.runtime = movie.getRuntime();
        this.rating = movie.getRating();
        this.releaseDts = movie.getReleaseDts();
        this.posterUrl = movie.getPosterUrl();
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.reviews = reviews;
    }
}