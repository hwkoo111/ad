package deu.movietalk.service;

import deu.movietalk.domain.*;
import deu.movietalk.dto.PlayListUpdateRequestDto;
import deu.movietalk.dto.UpdateFavoriteMovie;
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
import java.util.Optional;
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

    //영화 수정
    @Transactional
    public void updateMovie(String memberId, UpdateFavoriteMovie dto) {
        // 해당 memberId로 즐겨찾기 영화 목록 조회
        List<MemberFavoriteMovie> favoriteMovies = memberFavoriteMovieRepository.findByMember_MemberId(memberId);

        if (favoriteMovies == null || favoriteMovies.isEmpty()) {
            throw new IllegalArgumentException("최애 영화 목록이 비어있거나 존재하지 않습니다.");
        }

        // 수정할 영화 찾기
        MemberFavoriteMovie favoriteMovie = memberFavoriteMovieRepository
                .findByMemberIdAndMovieId(memberId, dto.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("수정할 최애 영화가 존재하지 않습니다."));

        // favoriteMovie.getMovie()가 null인지 확인
        if (favoriteMovie.getMovie() == null) {
            throw new IllegalArgumentException("영화 정보가 없습니다.");
        }

        // movieId가 이미 존재하는지 확인 (현재 사용자가 이미 선택한 영화 목록에서 확인)
        Optional<MemberFavoriteMovie> existingFavoriteMovie = memberFavoriteMovieRepository
                .findByMemberIdAndMovieId(memberId, dto.getNewMovieId());

        // 이미 존재하는 movieId가 있을 경우 예외 처리
        if (existingFavoriteMovie.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 영화입니다.");
        }

        // 새로운 Movie 객체로 영화 수정 (id를 수정하지 않고 새로운 객체로 교체)
        Movie newMovie = new Movie();
        newMovie.setMovieId(dto.getNewMovieId()); // 새로운 movieId를 설정

        // 새로운 movie 객체를 기존의 favoriteMovie에 설정
        favoriteMovie.setMovie(newMovie);  // 새로 만든 movie 객체로 교체

        // 수정된 엔티티는 JPA에서 자동으로 업데이트 됨
    }

    //영화 삭제
    @Transactional
    public void deleteFavoriteMovie(String memberId) {
        memberFavoriteMovieRepository.deleteByMember_MemberId(memberId);
    }



}
