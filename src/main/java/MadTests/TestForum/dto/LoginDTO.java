package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class LoginDTO {
    private String sign;
    private String pass;
}
