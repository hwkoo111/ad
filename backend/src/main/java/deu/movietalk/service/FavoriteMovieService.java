package deu.movietalk.service;

import deu.movietalk.domain.Member;
import deu.movietalk.domain.Movie;
import deu.movietalk.domain.MoviePlayList;
import deu.movietalk.domain.PlayList;
import deu.movietalk.dto.PlayListUpdateRequestDto;
import deu.movietalk.repository.MemberRepository;
import deu.movietalk.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FavoriteMovieService {

    private final MemberRepository memberRepository;


    @Transactional
    public void updateNickname(String memberId, String newNickname) {
        Member member = memberRepository.findByMemberId(memberId);

        member.setNickname(newNickname);
        memberRepository.save(member);
    }


}
