package kr.co.ync.board.domain.board.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRegisterRequest {
    @Email(message = "이메일 주소를 확인해주세요.")
    @NotBlank(message = "회원의 이메일 주소를 입력해주세요.")
    private String email;
    @NotBlank(message = "게시글 제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "게시글 내용을 입력해주세요.")
    private String content;
}
