package hexlet.code.schemas;

import lombok.ToString;
import java.util.Map;

@ToString
public final class MapSchema extends BaseSchema {
    public MapSchema() {
        this.getRequirements().put("checkType", (data) -> !(data instanceof Map<?, ?>));
    }

    public MapSchema required() {
        this.getRequirements().put("required", (map) -> map instanceof Map<?, ?>);
        this.setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        this.getRequirements().put("sizeof", (map) -> map instanceof Map<?, ?> && ((Map<?, ?>) map).size() == size);
        return this;
    }

    public MapSchema shape(final Map<?, BaseSchema> shapeMap) {
        this.getRequirements().put("shape", (data) -> {
            var map = (Map<?, ?>) data;
                return map.keySet().stream().allMatch(k -> shapeMap.get(k).isValid(map.get(k)));
        });
        return this;
    }
}
