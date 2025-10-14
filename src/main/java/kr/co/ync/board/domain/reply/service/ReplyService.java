package kr.co.ync.board.domain.reply.service;

import kr.co.ync.board.domain.reply.dto.Reply;
import kr.co.ync.board.domain.reply.dto.ReplyModRequest;
import kr.co.ync.board.domain.reply.dto.ReplyRegisterRequest;

import java.util.List;

public interface ReplyService {
    void register(ReplyRegisterRequest request);

    List<Reply> findByBoardId(Long boardId);

    void modify(ReplyModRequest request);

    void remove(Long id);

}
