package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class BaseSchema {
    private boolean isRequired;
    private final Map<String, Predicate<Object>> requirements = new LinkedHashMap<>();
    public final void setRequired(boolean required) {
        isRequired = required;
    }
    public final Map<String, Predicate<Object>> getRequirements() {
        return requirements;
    }

    public final void addRequirenment(String disc, Predicate<Object> p) {
        this.getRequirements().put(disc, p);
    }

    public final boolean isValid(final Object data) {
        if (!isRequired && !requirements.get("required").test(data)) {
            return true;
        }
        return requirements.keySet().stream().allMatch(k -> requirements.get(k).test(data));
    }
}
