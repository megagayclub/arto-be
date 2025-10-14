package kr.co.ync.board.domain.auth.service;

import kr.co.ync.board.domain.auth.dto.request.AuthenticationRequest;
import kr.co.ync.board.domain.auth.dto.response.JsonWebTokenResponse;

public interface AuthService {
    JsonWebTokenResponse auth(AuthenticationRequest request);
    JsonWebTokenResponse refresh(String token);
}






