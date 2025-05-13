package deu.movietalk.controller;

import deu.movietalk.domain.Movie;
import deu.movietalk.dto.MovieSearchResponseDto;
import deu.movietalk.dto.ReviewAverageDto;
import deu.movietalk.dto.ReviewResponseDto;
import deu.movietalk.service.MovieFindService;
import deu.movietalk.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movie")
@AllArgsConstructor
public class MovieFindController {

    private final MovieFindService movieFindService;
    private final ReviewService reviewService;

    // 영화 제목 키워드로 검색 + 리뷰 + 평균 별점 포함 응답
    @GetMapping("/search")
    public List<MovieSearchResponseDto> searchMovies(@RequestParam String keyword) {
        List<Movie> movies = movieFindService.searchMoviesByTitle(keyword);

        return movies.stream()
                .map(movie -> {
                    List<ReviewResponseDto> reviews = reviewService.getReviewsByMovie(movie.getMovieId());
                    ReviewAverageDto average = reviewService.getAverageRating(movie.getMovieId());

                    return new MovieSearchResponseDto(
                            movie,
                            average.getAverageRating(),
                            average.getTotalReviews(),
                            reviews
                    );
                })
                .collect(Collectors.toList());
    }
}