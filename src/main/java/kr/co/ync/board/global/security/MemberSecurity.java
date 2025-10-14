package kr.co.ync.board.global.security;

import kr.co.ync.board.domain.member.dto.Member;
import kr.co.ync.board.global.security.auth.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MemberSecurity {
    public Member getMember(){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getMember();
    }
}
