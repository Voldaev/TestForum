package MadTests.TestForum.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserRegDTO {

    @NotNull
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @NotNull
    @NotBlank(message = "Логин не может быть пустым")
    private String sign;

    @NotNull
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, message = "Минимальная длинна пароля {min}")
    private String pass;

    @NotNull
    @NotBlank(message = "Почта не может быть пустой")
    @Email
    private String mail;
}
