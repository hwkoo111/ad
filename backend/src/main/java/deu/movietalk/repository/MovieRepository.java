package deu.movietalk.repository;

import deu.movietalk.domain.Movie;
import deu.movietalk.dto.MovieSearchSummaryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleIn(List<String> title);
    @Query("SELECT new deu.movietalk.dto.MovieSearchSummaryDto(m.title, m.titleEng, m.createDts, m.genre, m.plot) " +
            "FROM Movie m WHERE REPLACE(m.title, ' ', '') LIKE %:keyword%")
    List<MovieSearchSummaryDto> searchByTitleIgnoreSpace(@Param("keyword") String keyword);
}
