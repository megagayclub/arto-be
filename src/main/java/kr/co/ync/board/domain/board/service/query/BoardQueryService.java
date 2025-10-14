package kr.co.ync.board.domain.board.service.query;

import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;

public interface BoardQueryService {
    PageResponse findBoardWithReplyCount(PageRequest request);

    BoardResponse findById(Long id);
}
