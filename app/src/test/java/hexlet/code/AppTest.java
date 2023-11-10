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
    void stringOfAnyLengthIsValid(String str) {
        assertTrue(s.isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"5", ","})
    void nullOrNumberIsValid(Object obj) {
        assertTrue(s.isValid(obj));
    }

    @ParameterizedTest
    @EmptySource
    void emptyStringIsInvalid(String emptyStr) {
        assertFalse(s.required().isValid(emptyStr));
    }

    @ParameterizedTest
    @CsvSource({"5, "})
    void nullOrNumberIsInvalid(int num, String nullStr) {
        assertFalse(s.required().isValid(num));
        assertFalse(s.required().isValid(nullStr));
    }

    @ParameterizedTest
    @CsvSource({"hexlet"})
    void stringWithLengthGreaterThenZeroIsValid(String str) {
        assertTrue(s.required().isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"7, hexlet"})
    void stringThatDoesNotMeetMinimalLengthIsInvalid(int out, String str) {
        assertFalse(s.required().minLength(out).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"5, hexlet"})
    void stringThatMeetsMinimalLengthIsValid(int in, String str) {
        assertTrue(s.required().minLength(in).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"hex, hexlet"})
    void stringThatContainsSubstringIsValid(String sub, String str) {
        assertTrue(s.required().contains(sub).isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"hed, hexlet"})
    void stringThatDoesNotContainSubstringIsInvalid(String sub, String str) {
        assertFalse(s.required().contains(sub).isValid(str));
    }

    @Test
    void stringThatDoesNotContainSubstringOrMeetMinimalLengthIsInvalid() {
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
    void stringThatContainsSubstringAndMeetsMinimalLengthIsValid() {
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
    void nullTreatedAsANumberIsValid(String nullStr) {
        assertTrue(n.isValid(nullStr));
    }

    @ParameterizedTest
    @CsvSource({"10"})
    void numberIsValid(int number) {
        assertTrue(n.isValid(number));
    }

    @ParameterizedTest
    @NullSource
    void nullTreatedAsAPositiveNumberIsValid(String nullStr) {
        assertTrue(n.positive().isValid(nullStr));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
             | "5" | 10
            """)
    void nullOrStringValuesAreInvalid(String nullStr, String str) {
        assertFalse(n.required().positive().isValid(nullStr));
        assertFalse(n.required().positive().isValid(str));
    }

    @ParameterizedTest
    @CsvSource({"10"})
    void positiveNumberIsValid(int number) {
        assertTrue(n.required().positive().isValid(number));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            -10 | 0
            """)
    void negativeNumberOrZeroAreInvalid(int negative, int zero) {
        assertFalse(n.required().positive().isValid(negative));
        assertFalse(n.required().positive().isValid(zero));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            5 | 10 | 4
            5 | 10 | 11
            """)
    void positiveNumberThatDoNotFallUnderTheRangeIsInvalid(int min, int max, int number) {
        assertFalse(n.required().positive().range(min, max).isValid(number));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            5 | 10 | 5
            5 | 10 | 10
            """)
    void positiveNumberThatFallUnderTheRangeIsValid(int min, int max, int number) {
        assertTrue(n.required().positive().range(min, max).isValid(number));
    }



    /*
     * Tests covering MapSchema class methods
     */
    @ParameterizedTest
    @NullSource
    void nullTreatedAsAMapIsValid(Object nullValue) {
        assertTrue(m.isValid(nullValue));
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', textBlock = """
            '' | 10 | hexlet
            """)
    void anyInputExceptMapAreInvalid(Object obj) {
        assertFalse(m.required().isValid(obj));
    }

    @Test
    void emptyMapIsValid() {
        Map<Object, Object> emptyMap = new HashMap<>();
        assertTrue(m.required().isValid(emptyMap));
    }

    @Test
    void mapThatHasRightSizeIsValid() {
        Map<Object, Object> mapOfRightSize = Map.of("key1", "value1", "key2", "value2");
        var size = 2;
        assertTrue(m.required().sizeof(size).isValid(mapOfRightSize));
    }

    @Test
    void mapThatHasWrongSizeIsInvalid() {
        Map<Object, Object> mapOfWrongSize = Map.of("key1", "value1");
        var size = 2;
        assertFalse(m.required().sizeof(size).isValid(mapOfWrongSize));
    }

    @ParameterizedTest
    @MethodSource("invalidMapProvider")
    void mapThatHasInvalidValuesIsInvalid(Map<String, Object> invalidMap) {
        assertFalse(m.shape(schemas).isValid(invalidMap));
    }

    @ParameterizedTest
    @MethodSource("validMapProvider")
    void mapThatHasValidValuesIsValid(Map<String, Object> validMap) {
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
