package kr.co.ync.board.domain.member.repository;

import kr.co.ync.board.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // Query Method
    Optional<MemberEntity> findByEmail(String email);
}
