package kr.co.ync.board.domain.board.repository;

import kr.co.ync.board.domain.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

//    @Query("UPDATE ")//

    // JPQL
//    @Query("SELECT b, m " +
//            "FROM BoardEntity b " +
//            "LEFT JOIN b.memberEntity m " +
//            "WHERE b.id = :id")
//    Object getBoardWithEmail(@Param("id") Long id);
//
//    @Query( "SELECT b, r " +
//            "FROM BoardEntity b " +
//            "LEFT JOIN ReplyEntity r ON r.boardEntity = b " +
//            "WHERE b.id = :id")
//    List<Object[]> getBoardWithReply(@Param("id") Long id);
//
//    //JPQL
//    // BoardEntity b = new BoardEntity();
//    // ReplyEntity r ON r.boardEntity = b
//    @Query( value = "SELECT b, m, COUNT(r) " +
//            "FROM BoardEntity b " +
//            "LEFT JOIN b.memberEntity m " +
//            "LEFT JOIN ReplyEntity r ON r.boardEntity = b " +
//            "GROUP BY b ",
//           countQuery = "SELECT COUNT(b) FROM BoardEntity b")
//    Page<Object[]> getBoardWithReplyCount(Pageable pageable);
//
//
//    @Query("SELECT b, m, COUNT(r) " +
//            "FROM BoardEntity b " +
//            "LEFT JOIN b.memberEntity m " +
//            "LEFT JOIN ReplyEntity r on r.boardEntity = b " +
//            "WHERE b.id = :id")
//    Object getBoardById(@Param("id") Long id);
    // findBoardById

}







