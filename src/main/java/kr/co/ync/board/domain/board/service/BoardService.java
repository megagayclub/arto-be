package kr.co.ync.board.domain.board.service;

import kr.co.ync.board.domain.board.dto.BoardModRequest;
import kr.co.ync.board.domain.board.dto.BoardRegisterRequest;

public interface BoardService {
    void register(BoardRegisterRequest request);

    void modify(BoardModRequest request);

    void remove(Long id);
}
