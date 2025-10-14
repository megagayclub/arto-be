package kr.co.ync.board.domain.reply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyRegisterRequest {
    @NotNull(message = "게시글 ID를 확인해주세요.")
    private Long boardId; // 필수
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String text; // 필수
    @NotBlank(message = "댓글 작성자를 입력해주세요.")
    private String replyer; // 필수
}
