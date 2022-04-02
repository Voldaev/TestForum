package MadTests.TestForum.exceiption;

import java.util.SortedSet;
import java.util.TreeSet;

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
public class ErrorBody {

    /**
     * Ошибший текст ошибки
     */
    private String message;

    /**
     * Поля в которых произошла ошибка. Если речь идет об ошибке валидации.
     * Если тут пришли элементы можно не смотреть на значение поля message,
     * так как ошибки будут тут
     */
    @Builder.Default
    private SortedSet<ErrorField> fields = new TreeSet<>();
}
