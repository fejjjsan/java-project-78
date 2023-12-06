package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
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
    private static Map<String, Object> human3;
    private static Map<String, Object> human4;
    private static Map<Object, Object> map;


    @BeforeAll
    static void createSchemasMap() {
        schemas = Map.of("name", VALIDATOR.string().required(), "age", VALIDATOR.number().positive());
        map = Map.of("key1", "value1", "key2", "value2");

        human1 = Map.of("name", "Kolya", "age", 100);

        human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);

        human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);

        human4 = Map.of("name", "Valya", "age", -5);
    }

    @Test
    void testStringSchema() {
        StringSchema schema = VALIDATOR.string();

        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(5));
        assertTrue(schema.isValid("what does the fox say"));
        assertTrue(schema.isValid("hexlet"));

        assertTrue(schema.contains("wh").isValid("what does the fox say"));
        assertTrue(schema.contains("what").isValid("what does the fox say"));
        assertFalse(schema.contains("whatthe").minLength(7).isValid("what does the fox say"));

        assertFalse(schema.isValid("what does the fox say"));
    }

    @Test
    void testNumberSchema() {
        NumberSchema schema = VALIDATOR.number();

        assertTrue(schema.isValid(null));
        assertTrue(schema.positive().isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("5"));
        assertTrue(schema.isValid(5));
        assertFalse(schema.isValid(-10));
        assertFalse(schema.isValid(0));

        schema.range(5, 10);

        assertTrue(schema.isValid(5));
        assertTrue(schema.isValid(10));
        assertFalse(schema.isValid(4));
        assertFalse(schema.isValid(11));
    }

    @Test
    void testMapSchema() {
        MapSchema schema = VALIDATOR.map();

        assertTrue(schema.isValid(null));

        schema.required();

        assertFalse(schema.isValid(null));
        assertTrue(schema.isValid(new HashMap<>()));
        assertTrue(schema.isValid(map));

        schema.sizeof(3);
        assertFalse(schema.isValid(map));
        schema.sizeof(2);
        assertTrue(schema.isValid(map));

        schema.shape(schemas);

        assertTrue(schema.isValid(human1));
        assertTrue(schema.isValid(human2));
        assertFalse(schema.isValid(human3));
        assertFalse(schema.isValid(human4));
    }
}
