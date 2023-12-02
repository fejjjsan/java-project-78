package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import hexlet.code.schemas.BaseSchema;

import java.util.HashMap;
import java.util.Map;

public final class AppTest {
    private static final Validator VALIDATOR = new Validator();
    private static Map<String, BaseSchema> schemas;
    private static Map<String, Object> human1;
    private static Map<String, Object> human2;
    private static Map<Object, Object> map;

    @BeforeAll
    static void createSchemasMap() {
        schemas = new HashMap<>();
        map = Map.of("key1", "value1", "key2", "value2");
        schemas.put("name", VALIDATOR.string().required());
        schemas.put("age", VALIDATOR.number().positive());

        human1 = new HashMap<>();
        human1.put("name", null);
        human1.put("age", -10);

        human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
    }

    @Test
    void testStringSchema() {
        assertTrue(VALIDATOR.string().isValid(null));
        assertTrue(VALIDATOR.string().isValid(5));

        assertFalse(VALIDATOR.string().required().isValid(""));
        assertFalse(VALIDATOR.string().required().isValid(null));
        assertTrue(VALIDATOR.string().required().isValid("hexlet"));

        assertFalse(VALIDATOR.string().required().minLength(7).isValid("hexlet"));
        assertTrue(VALIDATOR.string().required().minLength(5).isValid("hexlet"));

        assertTrue(VALIDATOR.string().required().contains("hex").isValid("hexlet"));
        assertFalse(VALIDATOR.string().required().contains("hed").isValid("hexlet"));

        assertFalse(VALIDATOR.string().required().contains("hex").minLength(7).isValid("hexlet"));
        assertFalse(VALIDATOR.string().required().contains("hed").minLength(6).isValid("hexlet"));
        assertFalse(VALIDATOR.string().required().contains("hed").minLength(7).isValid("hexlet"));
        assertTrue(VALIDATOR.string().required().contains("hex").minLength(6).isValid("hexlet"));
    }

    @Test
    void testNumberSchema() {
        assertTrue(VALIDATOR.number().isValid(null));
        assertTrue(VALIDATOR.number().isValid(10));

        assertTrue(VALIDATOR.number().positive().isValid(null));

        assertFalse(VALIDATOR.number().required().positive().isValid(null));
        assertFalse(VALIDATOR.number().required().positive().isValid("hexlet"));
        assertTrue(VALIDATOR.number().required().positive().isValid(10));
        assertFalse(VALIDATOR.number().required().positive().isValid(-10));
        assertFalse(VALIDATOR.number().required().positive().isValid(0));

        assertFalse(VALIDATOR.number().required().positive().range(11, 20).isValid(10));
        assertTrue(VALIDATOR.number().required().positive().range(1, 10).isValid(10));
    }

    @Test
    void testMapSchema() {
        assertTrue(VALIDATOR.map().isValid(null));
        assertFalse(VALIDATOR.map().required().isValid(null));
        assertTrue(VALIDATOR.map().required().isValid(new HashMap<>()));

        assertTrue(VALIDATOR.map().required().sizeof(2).isValid(map));
        assertFalse(VALIDATOR.map().required().sizeof(1).isValid(map));

        assertFalse(VALIDATOR.map().shape(schemas).isValid(human1));
        assertTrue(VALIDATOR.map().shape(schemas).isValid(human2));
    }
}
