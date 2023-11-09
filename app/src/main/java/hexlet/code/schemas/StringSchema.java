package hexlet.code.schemas;

import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
public final class StringSchema extends BaseSchema {

    private int length;
    private String subString;

    public StringSchema required() {
        this.setRequired(true);
        return this;
    }

    public StringSchema minLength(final int length) {
        this.length = length;
        return this;
    }

    public StringSchema contains(final String subString) {
        this.subString = subString;
        return this;
    }

    @Override
    public boolean isValid(final Object data) {
        if (data instanceof String str && !data.equals("")) {
            boolean subIsPresent = subString != null && str.contains(subString);
            boolean inRange = length <= str.length();
            return subIsPresent && inRange || inRange && subString == null;
        }
        return !isRequired();
    }
}
