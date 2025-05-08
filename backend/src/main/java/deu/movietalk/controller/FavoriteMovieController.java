package deu.movietalk.controller;

import deu.movietalk.dto.MovieSearchSummaryDto;
import deu.movietalk.dto.PlayListUpdateRequestDto;
import deu.movietalk.dto.UpdateNicknameRequest;
import deu.movietalk.service.FavoriteMovieService;
import deu.movietalk.service.PlaylistService;
import deu.movietalk.store.MovieSelectionStore;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

//구현 목록
//마이페이지(닉네임 수정, 최애영화 3개 등록,수정,삭제)
@RestController
@AllArgsConstructor
@RequestMapping("/mypage")
public class FavoriteMovieController {

    public final FavoriteMovieService favoriteMovieService;
    public final PlaylistService playlistService;
    private final MovieSelectionStore movieSelectionStore;


    //닉네임 수정
    @PutMapping("/update-nickname")
    public ResponseEntity<String> updateNickname(
            @RequestParam String newNickname,  // @RequestParam을 사용하여 쿼리 파라미터로 받기
            @AuthenticationPrincipal UserDetails userDetails) {

        // 로그인된 사용자의 memberId 가져오기
        String memberId = userDetails.getUsername();

        try {
            // 닉네임 수정 서비스 호출
            favoriteMovieService.updateNickname(memberId, newNickname);
            return ResponseEntity.ok("닉네임이 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 중복 닉네임 오류 메시지 처리
        } catch (Exception e) {
            return ResponseEntity.status(500).body("닉네임 수정 중 오류가 발생했습니다.");
        }
    }


    //최애영화 3개
    // 등록 전 db에서 영화 탐색
    @GetMapping("/enter/search")
    public ResponseEntity<List<MovieSearchSummaryDto>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(playlistService.searchMovies(keyword));
    }

    // 찾은 영화 + 버튼으로 메모리에 임시 저장 -> 리스트 형태로 제작
    @PostMapping("/enter/search/add")
    public ResponseEntity<String> addMovie(@RequestParam Long movieId, Authentication authentication) {
        String memberId = authentication.getName(); // JWT에서 추출된 사용자 ID

        // 현재 사용자가 선택한 영화 개수를 확인
        Set<Long> currentMovies = movieSelectionStore.getMovies(memberId);

        // 이미 3개 이상의 영화를 선택한 경우
        if (currentMovies.size() >= 3) {
            return ResponseEntity.status(400).body("선택 목록에 영화가 3개가 이미 추가되었습니다.");
        }

        // 영화 선택 목록에 추가
        movieSelectionStore.addMovie(memberId, movieId);

        return ResponseEntity.ok("영화가 선택 목록에 추가되었습니다.");
    }

    // 임시 저장된 영화 목록 반환
    @GetMapping("/enter/search/selection")
    public ResponseEntity<Set<Long>> getSelectedMovies(Authentication authentication) {
        String memberId = authentication.getName();
        Set<Long> selected = playlistService.getSelectedMovies(memberId);
        return ResponseEntity.ok(selected);
    }

    // 등록
    @PostMapping("/enter/create")
    public ResponseEntity<String> createFavoriteMovie(Authentication authentication) {
        String memberId = authentication.getName(); // JWT에서 추출된 사용자 ID
        try {
            // 즐겨찾기 영화 생성 서비스 호출
            favoriteMovieService.createFavoriteMovie(memberId);
            return ResponseEntity.ok("플레이리스트 생성 완료");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage()); // 예외 처리
        } catch (Exception e) {
            return ResponseEntity.status(500).body("플레이리스트 생성 중 오류가 발생했습니다.");
        }
    }


    //수정



    //삭제





}
