package MadTests.TestForum.exception;

import java.util.SortedSet;
import java.util.TreeSet;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ErrorBody {

    private String message;

    @Builder.Default
    private SortedSet<ErrorField> fields = new TreeSet<>();
}