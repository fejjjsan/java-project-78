package hexlet.code.schemas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
public class BaseSchema {

    private boolean isRequired;
    private final ArrayList<Predicate<Object>> requirements = new ArrayList<>();

    public final boolean isValid(Object data) {
        boolean result = requirements.stream().allMatch(p -> p.test(data));
        return data != null ? result : !isRequired;
    };
}
