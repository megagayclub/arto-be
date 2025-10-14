package kr.co.ync.board.domain.member.repository;

import kr.co.ync.board.domain.member.dto.Member;
import kr.co.ync.board.domain.member.entity.MemberEntity;
import kr.co.ync.board.domain.member.entity.enums.MemberRole;
import kr.co.ync.board.domain.member.mapper.MemberMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testInertMembers() {
        IntStream.rangeClosed(1, 100)
                .forEach(i -> {
                    String email = "user" + i + "@aaa.com";
                    MemberEntity memberEntity = MemberEntity.builder()
                            .email(email)
                            .password(passwordEncoder.encode("1111"))
                            .name("USER" + i)
                            .role(MemberRole.USER)
                            .build();
                    memberRepository.save(memberEntity);
                });
    }

    @Test
    @Transactional
    @Commit
    void testUpdateMember() {
        memberRepository.findById(100L)
                .map(memberEntity -> {
                    Member member = MemberMapper.toDTO(memberEntity);
                    member.setRole(MemberRole.ADMIN);
                    memberRepository.save(MemberMapper.toEntity(member));
                    return true;
                });
    }

}