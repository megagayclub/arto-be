package kr.co.ync.board.domain.reply.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyModRequest {
    @NotNull(message = "")
    private Long id;
    @NotBlank(message = "")
    private String text;
}
