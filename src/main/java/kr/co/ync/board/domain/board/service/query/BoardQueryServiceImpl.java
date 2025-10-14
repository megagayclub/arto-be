package kr.co.ync.board.domain.board.service.query;

import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.domain.board.exception.BoardNotFoundException;
import kr.co.ync.board.domain.board.repository.query.BoardQueryRepository;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardQueryServiceImpl implements BoardQueryService {

    private final BoardQueryRepository boardQueryRepository;

    @Override
    public PageResponse findBoardWithReplyCount(PageRequest request){
        return boardQueryRepository.findBoardWithReplyCount(request);
    }

    @Override
    public BoardResponse findById(Long id){
        return boardQueryRepository.findById(id)
        .orElseThrow(() -> BoardNotFoundException.EXCEPTION);
    }

}
