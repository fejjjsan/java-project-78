
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
`required()` method defined in all schemas. When `required()` is applied, `isValid(Object obj)` method will return `false`
everytime the type of argument is different from `String, Integer` or `Map` depends on a schema you use. For example 
`StringSchema` will wait for an argument only with type `String` (empty string is not allowed too).

```java
// NumberSchema
number.isValid(null); // true
number.isValid("5"); // true
number.isValid(5); // true
        
number.required();

number.isValid(null); // false
number.isValid(5); // true

// StringSchema
string.isValid(null); // true
string.isValid(""); // true
string.isValid("hexlet"); // true

string.required();
        
string.isValid(null); // false
string.isValid(""); // false
string.isValid("hexlet"); // true

// MapSchema
Map<Object, Object> hashmap = new HashMap<>();

map.isValid(null); // true
map.isValid(hashmap); // true

map.required();
        
map.isValid(null); // false
map.isValid(hashmap); // true
```

### More examples

```java
// validate positive number
number.required().positive();
number.isValid(-10); // false
number.isValid(0); // false
number.isValid(10); // true

// validate number if it's in range and positive
number.range(5, 10)
number.isValid(10); // true

// validate string if it's meets minimum length requirement 
string.minLength(6).isValid("hexlet"); // true

// validate string if it's contains substring
string.contains("hex").isValid("hexlet"); // true

// validate map if it's has right size
Map<String, String> validMap = Map.of("key1", "value1", "key2", "value2");
map.sizeof(2).isValid(validMap); // true
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