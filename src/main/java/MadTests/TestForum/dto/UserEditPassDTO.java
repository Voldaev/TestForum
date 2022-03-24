package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserEditPassDTO {
    private String oldPass;
    private String newPass;
}
