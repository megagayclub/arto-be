package kr.co.ync.board.domain.member.mapper;

import kr.co.ync.board.domain.member.dto.Member;
import kr.co.ync.board.domain.member.entity.MemberEntity;

public class MemberMapper {
    public static Member toDTO(MemberEntity entity) {
        return Member.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .name(entity.getName())
                .role(entity.getRole())
                .build();
    }

    public static MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .name(member.getName())
                .role(member.getRole())
                .build();
    }
}
