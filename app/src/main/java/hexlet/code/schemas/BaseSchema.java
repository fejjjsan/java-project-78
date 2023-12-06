package hexlet.code.schemas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

@Getter
@Setter
@ToString
public class BaseSchema {

    private boolean isRequired;
    private final Map<String, Predicate<Object>> requirements = new HashMap<>();
    public final boolean isValid(final Object data) {
            if (!isRequired && requirements.get("checkType").test(data)) {
                return true;
            } else {
                return requirements.keySet().stream()
                        .filter(k -> !k.equals("checkType"))
                        .allMatch(k -> requirements.get(k).test(data));
            }
    }
}
