package kr.co.ync.board.domain.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardModRequest {
    @NotNull(message = "게시글 ID를 입력해주세요.")
    private Long id;
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "게시글 내용을 입력해주세요.")
    private String content;
}
