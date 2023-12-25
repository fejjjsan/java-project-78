package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {
    public StringSchema() {
        addRequirenment("required", (data) -> data instanceof String && !data.equals(""));
    }

    public StringSchema required() {
        this.setRequired(true);
        return this;
    }

    public StringSchema minLength(final int num) {
        this.getRequirements().put("length", (str) -> ((String) str).length() >= num);
        return this;
    }

    public StringSchema contains(final String sub) {
        this.getRequirements().put("contains",
                (str) -> ((String) str).contains(sub) && !str.equals(""));
        return this;
    }
}
