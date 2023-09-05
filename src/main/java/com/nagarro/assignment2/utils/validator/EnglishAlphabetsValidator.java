package com.nagarro.assignment2.utils.validator;

public class EnglishAlphabetsValidator implements Validator<String> {
    private static final EnglishAlphabetsValidator INSTANCE = new EnglishAlphabetsValidator();

    // Private constructor to prevent instantiation
    private EnglishAlphabetsValidator() {}

    public static EnglishAlphabetsValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValid(Object val) {
        // Implement English alphabet validation logic here

        String value = (String) val;
        return value.matches("^[a-zA-Z]+$");
    }
}
