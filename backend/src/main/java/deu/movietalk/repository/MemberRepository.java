package deu.movietalk.repository;

import deu.movietalk.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,String> {
    Member findByMemberId(String memberId);
    Boolean existsByNickname(String nickname);
}