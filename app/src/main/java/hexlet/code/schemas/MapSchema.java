package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@NoArgsConstructor
@ToString
public final class MapSchema extends BaseSchema {

    private Map<?, BaseSchema> shape;

    public MapSchema required() {
        this.getRequirements().add((map) -> map instanceof Map<?, ?>);
        this.setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        this.getRequirements().add((map) -> ((Map<?, ?>) map).size() == size);
        return this;
    }
    public MapSchema shape(final Map<?, BaseSchema> shape) {
        this.shape = shape;
        this.getRequirements().add((data) -> {
            var map = (Map<?, ?>) data;
            for (Object key : map.keySet()) {
                if (!shape.get(key).isValid(map.get(key))) {
                    return false;
                }
            }
            return true;
        });
        return this;
    }
}
