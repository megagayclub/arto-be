package kr.co.ync.board.domain.member.dto;

import kr.co.ync.board.domain.member.entity.enums.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Member {
    private Long id;
    private String email;
    private String password;
    private String name;
    private MemberRole role;
}
