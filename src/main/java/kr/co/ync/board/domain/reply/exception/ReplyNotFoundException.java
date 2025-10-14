package kr.co.ync.board.domain.reply.exception;

import kr.co.ync.board.global.exception.CustomException;

public class ReplyNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new ReplyNotFoundException();
    private ReplyNotFoundException(){
        super(404, "해당 댓글을 찾을 수 없습니다.");
    }
}
