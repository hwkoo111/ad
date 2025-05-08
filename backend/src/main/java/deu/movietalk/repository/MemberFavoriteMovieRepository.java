package deu.movietalk.repository;

import deu.movietalk.domain.MemberFavoriteMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFavoriteMovieRepository extends JpaRepository<MemberFavoriteMovie,String> {

}
