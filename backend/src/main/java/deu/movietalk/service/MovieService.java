package deu.movietalk.service;

import java.util.List;
import java.util.Map;

public interface MovieService {
    Map<String, List<Map<String, String>>> getMoviesForMainPage();
}
