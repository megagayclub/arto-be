package kr.co.ync.board.domain.board.repository;

import kr.co.ync.board.domain.board.entity.BoardEntity;
import kr.co.ync.board.domain.member.entity.MemberEntity;
import kr.co.ync.board.domain.reply.entity.ReplyEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    @Transactional
    void test() {
        Optional<BoardEntity> result = boardRepository.findById(2L);
        if(result.isPresent()){
            BoardEntity boardEntity = result.get();
//            MemberEntity memberEntity = boardEntity.getMemberEntity();
        }
    }

    @Test
    void testInsertBoard() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
//            String email = "user" + i + "@aaa.com";
//            MemberEntity memberEntity = MemberEntity.builder().id().build();

            BoardEntity boardEntity = BoardEntity.builder()
                    .memberId(Long.valueOf(i))
                    .title("Title ...." + i)
                    .content("Content ...." + i)
                    .build();

            boardRepository.save(boardEntity);
        });

    }

    @Test
    void testInsert() {
        MemberEntity memberEntity = MemberEntity.builder().email("TEST").build();
//        BoardEntity boardEntity = BoardEntity.builder()
//                .memberEntity(
//                        memberEntity
//                )
//                .title("TEST")
//                .content("TEST")
//                .build();
//        boardRepository.save(boardEntity);
    }

    @Test
    @Transactional
    void testFindById() {
        Optional<BoardEntity> result = boardRepository.findById(1L);
        if (result.isPresent())
            System.out.println(result.get());
        ///////////////////
        BoardEntity boardEntity = result.get();
        System.out.println("========================");
//        System.out.println(boardEntity.getMemberEntity().getEmail());
        System.out.println("========================");
    }

    @Test
    void testJPQL1() {
//        Object result = boardRepository.getBoardWithEmail(1L);
//        Object[] result1 = (Object[]) result; // b, m
//        System.out.println((BoardEntity) result1[0]);
//        System.out.println((MemberEntity) result1[1]);
    }

    @Test
    void testJPQL2() {
//        List<Object[]> result = boardRepository.getBoardWithReply(2L);
//        result.forEach(objects -> {
//            // SELECT b, r
//            System.out.println((BoardEntity) objects[0]);
//            System.out.println((ReplyEntity) objects[1]);
//        });
    }

    @Test
    void testJPQL3() {
//        Pageable pageable
//                = PageRequest.of(0, 10);
//        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
//        // SELECT b, m, COUNT(r)
//        result.get().forEach(
//                objects -> {
//                    System.out.println((BoardEntity)objects[0]);
//                    System.out.println((MemberEntity)objects[1]);
//                    System.out.println((long)objects[2]);
//                }
//        );
    }

//    @Test
//    void testJPQL4() {
//        Object result = boardRepository.getBoardById(2L);
//// 0 : boardEntity, 1: memberEntity, 2: reply count
//        Object[] arr = (Object[]) result;
//        System.out.println(Arrays.toString(arr));
//    }

}