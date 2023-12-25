package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {
    public NumberSchema() {
        addRequirenment("required", (data) -> data instanceof Number);
    }

    public NumberSchema required() {
        this.setRequired(true);
        return this;
    }

    public NumberSchema positive() {
        this.getRequirements().put("positive", (num) -> (Integer) num > 0);
        return this;
    }

    public NumberSchema range(final int min, final int max) {
        this.getRequirements().put("range",
                (num) -> ((Integer) num) >= min && ((Integer) num) <= max);
        return this;
    }
}
