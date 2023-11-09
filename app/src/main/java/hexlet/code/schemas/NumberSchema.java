package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
public final class NumberSchema extends BaseSchema {

    private boolean positiveRequired;
    private int min;
    private int max;

    public NumberSchema required() {
        this.setRequired(true);
        return this;
    }
    public NumberSchema positive() {
        this.positiveRequired = true;
        return this;
    }
    public NumberSchema range(final int min, final int max) {
        this.min = min;
        this.max = max;
        return this;
    }

    @Override
    public boolean isValid(final Object data) {
        if (data instanceof Integer number) {
            boolean positive = number > 0 && positiveRequired;
            boolean inRange = (number >= min && number <= max);
            return positive && inRange || positive && min == 0 && max == 0;
        }
        return !isRequired();
    }
}
