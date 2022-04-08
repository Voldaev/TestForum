package MadTests.TestForum.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserRegDTO {

    @NotBlank(message = "имя не может быть пустым")
    @Size(min = 2, max = 20, message = "нереальное имя")
    private String name;

    @NotBlank(message = "логин не может быть пустым")
    @Size(min = 4, max = 50, message = "от 4 до 50 символов")
    private String sign;

    @NotBlank(message = "пароль не может быть пустым")
    @Size(min = 6, message = "не менее 6 символов")
    private String pass;

    @NotBlank(message = "email не может быть пустым")
    @Email(message = "некорректный email")
    private String mail;
}
