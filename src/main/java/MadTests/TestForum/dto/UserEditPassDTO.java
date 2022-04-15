package MadTests.TestForum.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserEditPassDTO {

    private String oldPass;

    @NotBlank(message = "пароль не может быть пустым")
    @Size(min = 6, message = "не менее 6 символов")
    private String newPass;
}
