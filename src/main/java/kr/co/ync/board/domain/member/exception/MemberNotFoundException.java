package kr.co.ync.board.domain.member.exception;

import kr.co.ync.board.global.exception.CustomException;

public class MemberNotFoundException extends CustomException {
    public static final CustomException EXCEPTION = new MemberNotFoundException();
    private MemberNotFoundException(){
        super(404, "해당 회원의 정보가 존재하지 않습니다.");
    }
}
