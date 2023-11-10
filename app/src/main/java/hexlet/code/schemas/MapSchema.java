package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@NoArgsConstructor
@ToString
public final class MapSchema extends BaseSchema {

    private boolean shapeRequired;
    private boolean sizeRequired;
    private int mapSize;
    private Map<?, BaseSchema> shape;

    public MapSchema required() {
        this.setRequired(true);
        return this;
    }

    public MapSchema sizeof(int size) {
        this.mapSize = size;
        this.sizeRequired = true;
        return this;
    }

    public MapSchema shape(final Map<?, BaseSchema> map) {
        this.shape = map;
        this.shapeRequired = true;
        return this;
    }

    @Override
    public boolean isValid(final Object data) {
        if (data instanceof Map map && !shapeRequired) {
            boolean hasRightSize = mapSize == map.size();
            return hasRightSize && sizeRequired || isRequired() && !sizeRequired;
        } else if (data instanceof Map map && shape != null) {
            for (Object key : map.keySet()) {
                if (!shape.get(key).isValid(map.get(key))) {
                    return false;
                }
            }
            return !sizeRequired || map.size() == mapSize;
        }
        return data == null && !isRequired() && !sizeRequired && !shapeRequired;
    }
}
