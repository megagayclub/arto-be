package kr.co.ync.board.domain.board.exception;

import kr.co.ync.board.global.exception.CustomException;

public class BoardNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new BoardNotFoundException();
    private BoardNotFoundException(){
        super(404, "해당 게시글을 찾을 수 없습니다.");
    }
}
