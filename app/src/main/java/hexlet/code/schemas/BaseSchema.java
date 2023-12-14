package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema {
    private boolean isRequired;
    private final Map<String, Predicate<Object>> requirements = new HashMap<>();
    public final void setRequired(boolean required) {
        isRequired = required;
    }
    public final Map<String, Predicate<Object>> getRequirements() {
        return requirements;
    }

    public final boolean isValid(final Object data) {
        boolean result = false;
        boolean dataIsValid = requirements.get("required").test(data);
        if (!isRequired && !dataIsValid) {
            result = true;
        } else if (dataIsValid) {
            result = requirements.keySet().stream()
                    .allMatch(k -> requirements.get(k).test(data));
        }
        return result;
    }
}
