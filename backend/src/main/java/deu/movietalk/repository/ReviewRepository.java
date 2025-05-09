package deu.movietalk.repository;

import deu.movietalk.domain.Member;
import deu.movietalk.domain.Movie;
import deu.movietalk.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 특정 영화에 대한 모든 리뷰 조회 (중복 문제 없음)
    List<Review> findByMovie_MovieId(Long movieId);

    // 사용자가 이미 해당 영화에 대해 리뷰를 작성했는지 확인
    boolean existsByMemberAndMovie(Member member, Movie movie);
}