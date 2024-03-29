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
public class ThemeDTO {
    private Long id;
    private String section;
    private String themeCreator;
    private String theme;
    private String text;
    private LocalDateTime published;
    private int score;
    private int comms;
}
