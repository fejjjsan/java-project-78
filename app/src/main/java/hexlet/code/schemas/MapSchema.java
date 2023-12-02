package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@ToString
public final class MapSchema extends BaseSchema {

    private Map<?, BaseSchema> schemas = new HashMap<>();

    public MapSchema required() {
        this.getRequirements().add((map) -> map instanceof Map<?, ?>);
        this.setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        this.getRequirements().add((map) -> ((Map<?, ?>) map).size() == size);
        return this;
    }

    public MapSchema shape(final Map<?, BaseSchema> shapeMap) {
        this.schemas = shapeMap;
        this.getRequirements().add((data) -> {
            var map = (Map<?, ?>) data;
            return map.keySet().stream().allMatch(k -> schemas.get(k).isValid(map.get(k)));
        });
        return this;
    }
}
