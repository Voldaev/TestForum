package MadTests.TestForum.exceiption;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ErrorField implements Comparable<ErrorField> {

    /**
     * Наименование поля
     */
    private String field;

    /**
     * Ошибка
     */
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
