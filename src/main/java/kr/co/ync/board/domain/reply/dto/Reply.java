package kr.co.ync.board.domain.reply.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Reply {
    private Long id;
    private String text;
    private String replyer;
    private LocalDateTime createdDateTime;
}
