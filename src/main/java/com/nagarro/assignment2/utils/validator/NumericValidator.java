package com.nagarro.assignment2.utils.validator;

public class NumericValidator implements Validator<Integer> {
    private static final NumericValidator INSTANCE = new NumericValidator();

    // Private constructor to prevent instantiation
    private NumericValidator() {}

    public static NumericValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean isValid(Object val) {
        // Implement numeric validation logic here
        Integer value  = (Integer) val;
        
        return value >= 1 && value <= 5;
    }
}
