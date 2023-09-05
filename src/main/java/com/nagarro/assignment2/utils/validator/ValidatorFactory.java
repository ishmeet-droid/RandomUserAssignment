package com.nagarro.assignment2.utils.validator;

public class ValidatorFactory {
    public static Validator<?> getValidator(Object value) {
        if (value instanceof Integer) {
            return NumericValidator.getInstance();
        } else if (value instanceof String) {
            return EnglishAlphabetsValidator.getInstance();
        } else {
            throw new IllegalArgumentException("Unsupported parameter type");
        }
    }
}
