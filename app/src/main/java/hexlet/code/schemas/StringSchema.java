package hexlet.code.schemas;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
public final class StringSchema extends BaseSchema {

    public StringSchema required() {
        this.getRequirements().add((str) -> str instanceof String && !str.equals(""));
        this.setRequired(true);
        return this;
    }

    public StringSchema minLength(final int num) {
        this.getRequirements().add((str) -> ((String) str).length() >= num);
        return this;
    }

    public StringSchema contains(final String sub) {
        this.getRequirements().add((str) -> ((String) str).contains(sub) && !str.equals(""));
        return this;
    }
}
