package deu.movietalk.controller;

import deu.movietalk.dto.PlayListUpdateRequestDto;
import deu.movietalk.service.FavoriteMovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

//구현 목록
//마이페이지(닉네임 수정, 최애영화 3개 등록,수정,삭제)
@RestController
@AllArgsConstructor
@RequestMapping("/mypage")
public class FavoriteMovieController {

    public final FavoriteMovieService favoriteMovieService;

    //닉네임 수정
    @PutMapping("/{member_id}")
    public ResponseEntity<String> updateNickname(@PathVariable String memberId, @RequestParam String newNickname) {
        try {
            // 로그인된 사용자의 memberId를 가져옵니다.
            String loggedInMemberId = SecurityContextHolder.getContext().getAuthentication().getName();

            // 권한이 일치하는지 확인 (비즈니스 로직에 맞게)
            if (!memberId.equals(loggedInMemberId)) {
                throw new IllegalArgumentException("본인만 닉네임을 수정할 수 있습니다.");
            }

            // 닉네임 수정 서비스 호출
            favoriteMovieService.updateNickname(memberId, newNickname);

            return ResponseEntity.ok("닉네임이 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("닉네임 수정 중 오류 발생");
        }
    }

    //최애영화 3개
    // 등록


    //수정


    //삭제




}
