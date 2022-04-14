package MadTests.TestForum.exception;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ErrorField implements Comparable<ErrorField> {

    private String field;

    private String error;

    @Override
    public int compareTo(ErrorField o) {
        int i = StringUtils.compare(field, o.field);
        if (i == 0) {
            i = StringUtils.compare(error, o.error);
        }
        return i;
    }

}
