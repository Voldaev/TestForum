package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class InputDTO {
    private String desc;
    private String text;
}
