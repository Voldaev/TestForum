package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserEditRegDTO {
    private String name;
    private String sign;
    private String mail;
}
