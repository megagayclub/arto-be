package kr.co.ync.board.domain.board.mapper;

import kr.co.ync.board.domain.board.dto.Board;
import kr.co.ync.board.domain.board.dto.BoardRegisterRequest;
import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.member.entity.MemberEntity;
import kr.co.ync.board.domain.member.mapper.MemberMapper;

public class BoardMapper {
    public static BoardEntity toEntity(BoardRegisterRequest request, Long memberId){
        return BoardEntity.builder()
                .memberId(memberId)
                .title(request.getTitle())
                .content(request.getContent())
//                .memberEntity(memberEntity)
                .build();
    }

}
