package kr.co.ync.board.domain.board.service;

import kr.co.ync.board.domain.board.dto.BoardModRequest;
import kr.co.ync.board.domain.board.dto.BoardRegisterRequest;
import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.board.exception.BoardNotFoundException;
import kr.co.ync.board.domain.board.mapper.BoardMapper;
import kr.co.ync.board.domain.board.repository.BoardRepository;
import kr.co.ync.board.domain.member.dto.Member;
import kr.co.ync.board.domain.member.entity.MemberEntity;
import kr.co.ync.board.domain.member.exception.MemberNotFoundException;
import kr.co.ync.board.domain.member.repository.MemberRepository;
import kr.co.ync.board.domain.reply.repository.ReplyRepository;
import kr.co.ync.board.global.security.MemberSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ReplyRepository replyRepository;
    private final MemberSecurity memberSecurity;
    @Override
    public void register(BoardRegisterRequest request){
//        MemberEntity memberEntity = memberRepository
//                .findByEmail(request.getEmail())
//                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);
        Member member = memberSecurity.getMember();
        boardRepository.save(
                BoardMapper.toEntity(request, member.getId())
        );
    }

    @Override
    public void modify(BoardModRequest request) {
        BoardEntity boardEntity = boardRepository.findById(request.getId())
                .orElseThrow(() -> BoardNotFoundException.EXCEPTION);
        boardEntity.uptTitle(request.getTitle());
        boardEntity.uptContent(request.getContent());
        boardRepository.save(boardEntity);
    }

    @Override
    public void remove(Long id) {
        boardRepository.findById(id)
             .orElseThrow(() -> BoardNotFoundException.EXCEPTION);
        replyRepository.deleteByBoardId(id);
        boardRepository.deleteById(id);
    }

}






