package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@ToString
public final class NumberSchema extends BaseSchema {

    private boolean positiveRequired;
    private boolean rangeRequired;
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
        this.rangeRequired = true;
        return this;
    }

    @Override
    public boolean isValid(final Object data) {
        if (data instanceof Integer number) {
            boolean noRequirements = !positiveRequired && !rangeRequired && !isRequired();
            boolean positive = number > 0 && positiveRequired;
            boolean inRange = (number >= min && number <= max) && rangeRequired;
            return positive && inRange || !rangeRequired && positive || noRequirements;
        }
        return data == null && !isRequired();
    }
}
