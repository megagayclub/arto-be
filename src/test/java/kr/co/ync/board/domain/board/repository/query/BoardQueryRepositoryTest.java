package kr.co.ync.board.domain.board.repository.query;

import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardQueryRepositoryTest {

    @Autowired
    private BoardQueryRepository boardQueryRepository;

    @Test
    void testFindById() {
        Optional<BoardResponse> result = boardQueryRepository.findById(1L);
        if(result.isPresent()){
            System.out.println(result.get());
        }
    }

    @Test
    void testFindBoardWithReplyCount() {
        PageRequest pageRequest = new PageRequest();

        PageResponse result = boardQueryRepository.findBoardWithReplyCount(pageRequest);

        System.out.println(result);
    }

}