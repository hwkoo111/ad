package deu.movietalk.service;

import deu.movietalk.domain.Movie;
import deu.movietalk.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public Map<String, List<Map<String, String>>> getMoviesForMainPage() {
        Map<String, List<Map<String, String>>> result = new HashMap<>();

        // 오늘 날짜를 yyyyMMdd 문자열로 변환
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // release_dts가 8자리 날짜 형식이고, 오늘 이하인 영화만 필터링
        List<Movie> recent = movieRepository
                .findTop100ByPosterUrlIsNotNullOrderByReleaseDtsDesc()
                .stream()
                .filter(m -> m.getReleaseDts() != null && m.getReleaseDts().matches("\\d{8}"))
                .filter(m -> m.getReleaseDts().compareTo(today) <= 0)
                .limit(5)
                .collect(Collectors.toList());

        // 각 장르에 맞는 영화를 조회 (호러, 액션, 로맨스)
        List<Movie> horror = movieRepository
                .findTop10ByGenreContainingIgnoreCaseAndPosterUrlIsNotNullOrderByReleaseDtsDesc("스릴러");

        List<Movie> action = movieRepository
                .findTop10ByGenreContainingIgnoreCaseAndPosterUrlIsNotNullOrderByReleaseDtsDesc("액션");

        List<Movie> romance = movieRepository
                .findTop10ByGenreContainingIgnoreCaseAndPosterUrlIsNotNullOrderByReleaseDtsDesc("공포");



        // Map에 데이터를 넣기
        result.put("상영작", toSimpleList(recent));
        result.put("호러", toSimpleList(horror.subList(0, Math.min(5, horror.size()))));
        result.put("액션", toSimpleList(action.subList(0, Math.min(5, action.size()))));
        result.put("공포", toSimpleList(romance.subList(0, Math.min(5, romance.size()))));

        return result;
    }

    private List<Map<String, String>> toSimpleList(List<Movie> movies) {
        return movies.stream().map(movie -> {
            Map<String, String> map = new HashMap<>();
            map.put("title", movie.getTitle());
            map.put("posterUrl", movie.getPosterUrl());
            return map;
        }).collect(Collectors.toList());
    }
}
