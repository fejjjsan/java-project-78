
# Data Validator 

[![Actions Status](https://github.com/fejjjsan/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/fejjjsan/java-project-78/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/af2a1a7ae3c1e56d70c4/maintainability)](https://codeclimate.com/github/fejjjsan/java-project-78/maintainability)
![Actions Status](https://github.com/fejjjsan/java-project-78/actions/workflows/project-78-check.yml/badge.svg)
[![Test Coverage](https://api.codeclimate.com/v1/badges/af2a1a7ae3c1e56d70c4/test_coverage)](https://codeclimate.com/github/fejjjsan/java-project-78/test_coverage)

## Overview
This app provides `3 schemas` of validation to meet your needs for different types of data.
This `schemas` allow you to create flexible method chaining for validating `String, Integer` and `Map` type values.

## How to use

```java
// Start with declaration and instantiaton of validator variable
Validator validator = new Validator();

// Invoke the method specified for type you would like to validate
// for int values
NumberSchema number = validator.number();
// for String values
StringSchema string = validator.string();
// for Map values
MapSchema map = validator.map();

```
In all schemas defined `required()` and `isValid(Object obj)` methods. When method `required()` is applied depends on a schema,
it allows to pass only values of type `String, Integer` and `Map` when `isValid(Object obj)` method applied and restricts null values.

```java
// NumberSchema
number.isValid(null); // true
number.isValid("5"); // true
number.isValid(5); // true

number.required().isValid(null); // false
number.required().isValid(5); // true

// StringSchema
string.isValid(null); // true
string.isValid(""); // true
string.isValid("hexlet"); // true
        
string.required().isValid(null); // false
string.required().isValid(""); // false
string.required().isValid("hexlet"); // true

// MapSchema
Map<Object, Object> hashmap = new HashMap<>();

map.isValid(null); // true
map.isValid(hashmap); // true

map.required().isValid(null); // false
map.required().isValid(hashmap); // true


/** Note: to actually start validation process required() method should be applied. 
 * But to validate a positive number it's not necessary. 
*/ 

number.positive().isValid(-5); // false
number.positive().isValid(5); // true

```

### More examples

```java
// validate positive number
number.required().positive().isValid(-10); // false
number.required().positive().isValid(0); // false
number.required().positive().isValid(10); // true

// validate number if it's in range
number.required().range(-10, 10).isValid(-10); // true

// validate number if it's in range and positive
number.required().positive().range(-10, 10).isValid(-10); // false

// validate string if it's meets minimum length requirement 
string.required().minLength(7).isValid("hexlet"); // false

// validate string if it's contains substring
string.required().contains("hex").isValid("hexlet"); // true

// validate map if it's has right size
Map<String, String> validMap = Map.of("key1", "value1", "key2", "value2");
map.required().sizeof(2).isValid(validMap); // true


```
`MapSchema` defines `shape(Map<Object, BaseSchema> map)` method which allows to validate values inside a map.
It also provides tailored schemas for values against which it will process validation. 

```java
Map<String, BaseSchema> schemas = new HashMap<>();

// value which corresponds with name key should be string
schemas.put("name", v.string().required());
// value which corresponds with age key should be positive
schemas.put("age", v.number().positive());

// setting up MapSchema
// passing schemas to shape() made
schema.shape(schemas);

Map<String, Object> human1 = new HashMap<>();
human1.put("name", "Kolya");
human1.put("age", 100);
schema.isValid(human1); // true

Map<String, Object> human2 = new HashMap<>();
human2.put("name", "Maya");
human2.put("age", null);
schema.isValid(human2); // true

Map<String, Object> human3 = new HashMap<>();
human3.put("name", "");
human3.put("age", null);
schema.isValid(human3); // false

Map<String, Object> human4 = new HashMap<>();
human4.put("name", "Valya");
human4.put("age", -5);
schema.isValid(human4); // false
```

## Setup

```bash
make build
```

## Run

```bash
make run
```

## Run-dist
```bash
make run-dist
```

## Run tests

```bash
make test
```

## Run checkstyle

```bash
make lint
```

## Author

👤 **Artur Lastovskiy**

- Github: [@fejjsan](https://github.com/fejjjsan)