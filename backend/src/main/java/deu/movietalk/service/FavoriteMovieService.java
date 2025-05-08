package deu.movietalk.service;

import deu.movietalk.domain.*;
import deu.movietalk.dto.PlayListUpdateRequestDto;
import deu.movietalk.repository.MemberFavoriteMovieRepository;
import deu.movietalk.repository.MemberRepository;
import deu.movietalk.repository.MovieRepository;
import deu.movietalk.repository.PlaylistRepository;
import deu.movietalk.store.MovieSelectionStore;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class FavoriteMovieService {

    private final MovieRepository movieRepository;
    private final MemberRepository memberRepository;
    private final MovieSelectionStore movieSelectionStore;
    private final MemberFavoriteMovieRepository memberFavoriteMovieRepository;


    @Transactional
    public void updateNickname(String memberId, String newNickname) {
        // 중복 닉네임 체크
        if (memberRepository.existsByNickname(newNickname)) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        // 닉네임 업데이트 로직
        Member member = memberRepository.findByMemberId(memberId);
        if (member != null) {
            member.setNickname(newNickname);
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        }
    }

    //영화 등록
    @Transactional
    public void createFavoriteMovie(String memberId) {
        Set<Long> movieIds = movieSelectionStore.getMovies(memberId);

        if (movieIds.isEmpty()) {
            throw new IllegalArgumentException("선택된 영화가 없습니다.");
        }

        Member member = memberRepository.findByMemberId(memberId);
        if (member == null) throw new IllegalArgumentException("존재하지 않는 회원입니다.");

        // 영화 리스트 가져오기
        List<Movie> movies = movieRepository.findAllById(movieIds);
        for (Movie movie : movies) {
            // MemberFavoriteMovie 객체 생성 후 저장
            MemberFavoriteMovie favoriteMovie = new MemberFavoriteMovie();
            favoriteMovie.setMember(member);
            favoriteMovie.setMovie(movie);
            memberFavoriteMovieRepository.save(favoriteMovie);
        }

    }


}
