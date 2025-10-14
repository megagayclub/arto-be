package kr.co.ync.board.domain.reply.repository;

import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.reply.entity.ReplyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void testFindByBoardId() {
        Long boardId = 3L;
        List<ReplyEntity> result
         = replyRepository.findByBoardId(boardId);
        result.forEach(System.out::println);
    }

    @Test
    void testInsertReply() {
        IntStream.rangeClosed(1, 300)
        .forEach(i -> {
          long boardId =
            (int)(Math.random() * 100) + 1; // 1~100
           // BoardEntity boardEntity = BoardEntity.builder().id(boardId).build();
            ReplyEntity replyEntity = ReplyEntity.builder()
                    .boardId(boardId)
                    .text("Reply ...... " + i)
                    .replyer("guest")
                    .build();
            replyRepository.save(replyEntity);
        });
    }
}




