package kr.co.ync.board.domain.board.repository.query;


import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.ync.board.domain.board.dto.BoardResponse;
import kr.co.ync.board.global.common.dto.request.PageRequest;
import kr.co.ync.board.global.common.dto.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static kr.co.ync.board.domain.board.entity.QBoardEntity.boardEntity;
import static kr.co.ync.board.domain.member.entity.QMemberEntity.memberEntity;
import static kr.co.ync.board.domain.reply.entity.QReplyEntity.replyEntity;

@Repository
@RequiredArgsConstructor
public class BoardQueryRepositoryImpl implements BoardQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public PageResponse findBoardWithReplyCount(PageRequest request) {
        List<BoardResponse> result = queryFactory
                .select(projBoard())
                .from(boardEntity)
                .leftJoin(memberEntity).on(
                        boardEntity.memberId.eq(memberEntity.id)
                )
                .leftJoin(replyEntity).on(
                        replyEntity.boardId.eq(boardEntity.id)
                )
                .offset((request.getPage() - 1) * request.getSize())
                .limit(request.getSize())
                .groupBy(boardEntity.id)
                .orderBy(boardEntity.id.desc())
                .fetch();

        long total = queryFactory
                .select(boardEntity.id.count())
                .from(boardEntity)
                .fetchOne();

        return new PageResponse(result, request.getPage(), request.getSize(), total);
    }

    @Override
    public Optional<BoardResponse> findById(Long id) {
        return Optional.ofNullable(
                queryFactory
                        .select(projBoard())
                        .from(boardEntity)
                        .leftJoin(boardEntity).on(
                                boardEntity.memberId.eq(memberEntity.id)
                        )
                        .leftJoin(replyEntity).on(
                                replyEntity.boardId.eq(boardEntity.id)
                        )
                        .where(boardEntity.id.eq(id))
                        .groupBy(boardEntity.id)
                        .fetchOne()
        );
    }

    private ConstructorExpression<BoardResponse> projBoard() {
        return Projections.constructor(
                BoardResponse.class,
                boardEntity.id,
                boardEntity.title,
                memberEntity.email,
                memberEntity.name,
                replyEntity.count(),
                boardEntity.createdDateTime
        );
    }


}
