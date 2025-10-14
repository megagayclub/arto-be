package kr.co.ync.board.global.security.auth;

import kr.co.ync.board.domain.member.dto.Member;
import kr.co.ync.board.domain.member.exception.MemberNotFoundException;
import kr.co.ync.board.domain.member.mapper.MemberMapper;
import kr.co.ync.board.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .map(MemberMapper::toDTO)
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
        return new CustomUserDetails(member);
    }
}
