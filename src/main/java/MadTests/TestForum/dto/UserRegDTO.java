package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserRegDTO {
    private String name;
    private String sign;
    private String pass;
    private String mail;
}
