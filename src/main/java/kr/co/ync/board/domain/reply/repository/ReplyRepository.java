package kr.co.ync.board.domain.reply.repository;

import kr.co.ync.board.domain.reply.entity.ReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
    // JPQL
    @Query("SELECT r FROM ReplyEntity r " +
            "WHERE r.boardId = :boardId")
    List<ReplyEntity> findByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("DELETE FROM ReplyEntity r " +
            "WHERE r.boardId = :boardId")
    void deleteByBoardId(
            @Param("boardId") Long boardId
    );
}
