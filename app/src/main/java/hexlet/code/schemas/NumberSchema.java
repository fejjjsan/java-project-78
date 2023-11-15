package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public final class NumberSchema extends BaseSchema {

    public NumberSchema required() {
        this.getRequirements().add((num) -> num instanceof Integer);
        this.setRequired(true);
        return this;
    }
    public NumberSchema positive() {
        this.getRequirements().add((num) -> num instanceof Integer && (Integer) num > 0);
        return this;
    }
    public NumberSchema range(final int min, final int max) {
        this.getRequirements().add((num) -> ((Integer) num) >= min && ((Integer) num) <= max);
        return this;
    }
}
