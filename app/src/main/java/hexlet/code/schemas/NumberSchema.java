package hexlet.code.schemas;

import lombok.ToString;

@ToString
public final class NumberSchema extends BaseSchema {

    public NumberSchema() {
        this.getRequirements().put("checkType", (data) -> !(data instanceof Number));
    }

    public NumberSchema required() {
        this.getRequirements().put("required", (num) -> num instanceof Integer);
        this.setRequired(true);
        return this;
    }

    public NumberSchema positive() {
        this.getRequirements().put("positive", (num) -> num instanceof Integer && (Integer) num > 0);
        return this;
    }

    public NumberSchema range(final int min, final int max) {
        this.getRequirements().put("range",
                (num) -> num instanceof Integer && ((Integer) num) >= min && ((Integer) num) <= max);
        return this;
    }
}
