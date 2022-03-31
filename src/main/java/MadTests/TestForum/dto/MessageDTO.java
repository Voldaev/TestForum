package MadTests.TestForum.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MessageDTO {
    private boolean success;
    private String message;

    public static MessageDTO failed(String message) {
        return MessageDTO.builder()
                .success(false)
                .message(message)
                .build();
    }

    public static MessageDTO succeed(String message) {
        return MessageDTO.builder()
                .success(true)
                .message(message)
                .build();
    }
}
