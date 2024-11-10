package project.movie.member.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PasswordChangeReqDto {

    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    @NotNull
    @Size(min = 4, max = 20)
    private String newPassword;

    @NotNull
    @Size(min = 4, max = 20)
    private String confirmPassword;

    @Builder
    public PasswordChangeReqDto(String password, String newPassword, String confirmPassword) {
        this.password = password;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public boolean checkPasswordAndConfirmPassword() {
        return newPassword.equals(confirmPassword);
    }
}
