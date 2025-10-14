package kr.co.ync.board.domain.board.repository.query;

import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;

import java.util.Optional;

public interface BoardQueryRepository {
    PageResponse findBoardWithReplyCount(PageRequest request);

    Optional<BoardResponse> findById(Long id);
}
