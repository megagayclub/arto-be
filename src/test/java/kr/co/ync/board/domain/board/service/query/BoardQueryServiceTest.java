package kr.co.ync.board.domain.board.service.query;

import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BoardQueryServiceTest {
    @Autowired
    private BoardQueryService boardQueryService;

    @Test
    void testFindBoard() {
        PageResponse result = boardQueryService.findBoardWithReplyCount(new PageRequest());
        System.out.println(result);
    }

    @Test
    void testFindById() {
        BoardResponse result = boardQueryService.findById(10000L);
        System.out.println(result);
    }
}