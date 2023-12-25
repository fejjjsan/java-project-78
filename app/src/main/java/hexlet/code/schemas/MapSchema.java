package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {
    public MapSchema() {
        addRequirenment("required", (data) -> data instanceof Map<?, ?>);
    }

    public MapSchema required() {
        this.setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        this.getRequirements().put("sizeof", (map) -> ((Map<?, ?>) map).size() == size);
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
