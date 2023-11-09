package hexlet.code.schemas;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public abstract class BaseSchema {

    private boolean isRequired;
    abstract boolean isValid(Object data);
}
