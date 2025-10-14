package kr.co.ync.board.domain.reply.service;

import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.board.exception.BoardNotFoundException;
import kr.co.ync.board.domain.board.repository.BoardRepository;
import kr.co.ync.board.domain.reply.dto.Reply;
import kr.co.ync.board.domain.reply.dto.ReplyModRequest;
import kr.co.ync.board.domain.reply.dto.ReplyRegisterRequest;
import kr.co.ync.board.domain.reply.entity.ReplyEntity;
import kr.co.ync.board.domain.reply.exception.ReplyNotFoundException;
import kr.co.ync.board.domain.reply.mapper.ReplyMapper;
import kr.co.ync.board.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;

    @Override
    public void register(ReplyRegisterRequest request) {
        BoardEntity boardEntity = boardRepository
                .findById(request.getBoardId())
                .orElseThrow(
                        () -> BoardNotFoundException.EXCEPTION
                );
        replyRepository.save(
                ReplyMapper.toEntity(request, boardEntity.getId())
        );
    }

    @Override
    public List<Reply> findByBoardId(Long boardId) {
        return replyRepository.findByBoardId(boardId)
                .stream()
                .map(ReplyMapper::toDTO)
                .toList();
    }

    @Override
    public void modify(ReplyModRequest request) {
        ReplyEntity replyEntity = replyRepository.findById(request.getId())
                .orElseThrow(() -> ReplyNotFoundException.EXCEPTION);
        replyEntity.uptText(request.getText());
        replyRepository.save(replyEntity);
    }

    @Override
    public void remove(Long id) {
        replyRepository.findById(id)
        .orElseThrow(() -> ReplyNotFoundException.EXCEPTION);
        replyRepository.deleteById(id);
    }

}
