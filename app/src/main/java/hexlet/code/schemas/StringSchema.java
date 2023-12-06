package hexlet.code.schemas;

import lombok.ToString;

@ToString
public final class StringSchema extends BaseSchema {

    public StringSchema() {
        this.getRequirements().put("checkType", (data) -> !(data instanceof String) || data.equals(""));
    }

    public StringSchema required() {
        this.getRequirements().put("required", (str) -> isString(str) && !str.equals(""));
        this.setRequired(true);
        return this;
    }

    public StringSchema minLength(final int num) {
        this.getRequirements().put("length", (str) -> isString(str) && ((String) str).length() >= num);
        return this;
    }

    public StringSchema contains(final String sub) {
        this.getRequirements().put("contains",
                (str) -> isString(str) && ((String) str).contains(sub) && !str.equals(""));
        return this;
    }

    private static boolean isString(Object obj) {
        return obj instanceof String;
    }
}
