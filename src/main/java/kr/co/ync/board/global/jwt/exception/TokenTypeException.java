package kr.co.ync.board.global.jwt.exception;

import kr.co.ync.board.global.exception.CustomException;

public class TokenTypeException extends CustomException {
    public static final CustomException EXCEPTION = new TokenTypeException();

    private TokenTypeException() {
        super(400, "잘못된 JWT 타입");
    }
}

