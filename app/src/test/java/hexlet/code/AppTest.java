package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.EmptySource;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public final class AppTest {
    private static final Validator VALIDATOR = new Validator();
    private final StringSchema s = VALIDATOR.string();
    private final NumberSchema n = VALIDATOR.number();
    private final MapSchema m = VALIDATOR.map();
    private static Map<String, BaseSchema> schemas;

    @BeforeAll
    static void createSchemasMap() {
        schemas = new HashMap<>();
        schemas.put("name", VALIDATOR.string().required());
        schemas.put("age", VALIDATOR.number().positive());
    }

    /*
    * Tests covering StringSchema class methods
     */
    @ParameterizedTest
    @CsvSource({"''", "hexlet"})
    void String_of_any_length_is_valid(String str) {
        assertTrue(s.isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"5", ","})
    void Null_or_number_is_valid(Object obj) {
        assertTrue(s.isValid(obj));
    }

    @ParameterizedTest
    @EmptySource
    void Empty_string_is_invalid(String emptyStr) {
        assertFalse(s.required().isValid(emptyStr));
    }

    @ParameterizedTest
    @CsvSource({"5, "})
    void Null_or_number_is_invalid(int num, String nullStr) {
        assertFalse(s.required().isValid(num));
        assertFalse(s.required().isValid(nullStr));
    }

    @ParameterizedTest
    @CsvSource({"hexlet"})
    void String_with_length_greater_then_zero_is_valid(String str) {
        assertTrue(s.required().isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"7, hexlet"})
    void String_that_does_not_meet_minimal_length_is_invalid(int out, String str) {
        assertFalse(s.required().minLength(out).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"5, hexlet"})
    void String_that_meets_minimal_length_is_valid(int in, String str) {
        assertTrue(s.required().minLength(in).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"hex, hexlet"})
    void String_that_contains_substring_is_valid(String sub, String str) {
        assertTrue(s.required().contains(sub).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"hed, hexlet"})
    void String_that_does_not_contain_substring_is_invalid(String sub, String str) {
        assertFalse(s.required().contains(sub).isValid(str));
    }

    @Test
    void String_that_does_not_contain_substring_or_meet_minimal_length_is_invalid() {
        var rightSub = "hex";
        var wrongSub = "hed";
        var str = "hexlet";
        var out = 7;
        var in = 6;
        assertFalse(s.required().contains(rightSub).minLength(out).isValid(str));
        assertFalse(s.required().contains(wrongSub).minLength(in).isValid(str));
        assertFalse(s.required().contains(wrongSub).minLength(out).isValid(str));
    }

    @Test
    void String_that_contains_substring_and_meets_minimal_length_is_valid() {
        var rightSub = "hex";
        var str = "hexlet";
        var in = 6;
        assertTrue(s.required().contains(rightSub).minLength(in).isValid(str));
    }


    /*
     * Tests covering NumberSchema class methods
     */
    @ParameterizedTest
    @NullSource
    void Null_treated_as_a_number_is_valid(String nullStr) {
        assertTrue(n.isValid(nullStr));
    }

    @ParameterizedTest
    @NullSource
    void Null_treated_as_a_positive_number_is_valid(String nullStr) {
        assertTrue(n.positive().isValid(nullStr));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
             | "5" | 10
            """)
    void Null_or_String_values_are_invalid(String nullStr, String str) {
        assertFalse(n.required().positive().isValid(nullStr));
        assertFalse(n.required().positive().isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"10"})
    void Positive_number_is_valid(int number) {
        assertTrue(n.required().positive().isValid(number));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            -10 | 0
            """)
    void Negative_number_or_zero_are_invalid(int negative, int zero) {
        assertFalse(n.required().positive().isValid(negative));
        assertFalse(n.required().positive().isValid(zero));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            5 | 10 | 4
            5 | 10 | 11
            """)
    void Positive_number_that_do_not_fall_under_the_range_is_invalid(int min, int max, int number) {
        assertFalse(n.required().positive().range(min, max).isValid(number));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            5 | 10 | 5
            5 | 10 | 10
            """)
    void Positive_number_that_fall_under_the_range_is_valid(int min, int max, int number) {
        assertTrue(n.required().positive().range(min, max).isValid(number));
    }



    /*
     * Tests covering MapSchema class methods
     */
    @ParameterizedTest
    @NullSource
    void Null_treated_as_a_Map_is_valid(Object nullValue) {
        assertTrue(m.isValid(nullValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            '' | 10 | hexlet
            """)
    void Any_input_except_Map_are_invalid(Object obj) {
        assertFalse(m.required().isValid(obj));
    }

    @Test
    void Empty_map_is_valid() {
        Map<Object, Object> emptyMap = new HashMap<>();
        assertTrue(m.required().isValid(emptyMap));
    }

    @Test
    void Map_that_has_right_size_is_valid() {
        Map<Object, Object> mapOfRightSize = Map.of("key1", "value1", "key2", "value2");
        var size = 2;
        assertTrue(m.required().sizeof(size).isValid(mapOfRightSize));
    }

    @Test
    void Map_that_has_wrong_size_is_invalid() {
        Map<Object, Object> mapOfWrongSize = Map.of("key1", "value1");
        var size = 2;
        assertFalse(m.required().sizeof(size).isValid(mapOfWrongSize));
    }

    @ParameterizedTest
    @MethodSource("invalidMapProvider")
    void Map_that_has_invalid_values_is_invalid(Map<String, Object> invalidMap) {
        assertFalse(m.shape(schemas).isValid(invalidMap));
    }

    @ParameterizedTest
    @MethodSource("validMapProvider")
    void Map_that_has_valid_values_is_valid(Map<String, Object> validMap) {
        assertTrue(m.shape(schemas).isValid(validMap));
    }



    static Stream<Map<String, Object>> invalidMapProvider() {
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "");
        human1.put("age", null);

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Valya");
        human2.put("age", -5);

        return Stream.of(human1, human2);
    }

    static Stream<Map<String, Object>> validMapProvider() {
        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", 100);

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);

        return Stream.of(human1, human2);
    }
}
