package deu.movietalk.controller;

import deu.movietalk.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie")
    public ResponseEntity<Map<String, List<Map<String, String>>>> getMoviesForHome() {
        return ResponseEntity.ok(movieService.getMoviesForMainPage());
    }
}
