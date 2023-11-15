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
        if (data != null) {
            for (Predicate<Object> p : requirements) {
                if (!p.test(data)) {
                    return false;
                }
            }
            return true;
        }
        return !isRequired;
    };
}
