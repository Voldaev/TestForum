package MadTests.TestForum.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CommentDTO {
    private Long id;
    private String theme;
    private String user;
    private String text;
    private LocalDateTime published;
    private int score;
}
