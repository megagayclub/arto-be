package kr.co.ync.board.domain.reply.mapper;

import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.reply.dto.Reply;
import kr.co.ync.board.domain.reply.dto.ReplyRegisterRequest;
import kr.co.ync.board.domain.reply.entity.ReplyEntity;

public class ReplyMapper {
    public static ReplyEntity toEntity(ReplyRegisterRequest request, Long boardId) {
        return ReplyEntity.builder()
                .text(request.getText())
                .replyer(request.getReplyer())
                .boardId(boardId)
                .build();
    }

    public static Reply toDTO(ReplyEntity entity) {
        return Reply.builder()
                .id(entity.getId())
                .text(entity.getText())
                .replyer(entity.getReplyer())
                .createdDateTime(entity.getCreatedDateTime())
                .build();
    }
}
