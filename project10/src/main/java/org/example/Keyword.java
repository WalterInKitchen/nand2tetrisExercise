package org.example;

public enum Keyword {
    WHILE("while");

    private final String stringValue;

    Keyword(String stringValue) {
        this.stringValue = stringValue;
    }

    public static Keyword parseString(String tokenString) {
        for (Keyword value : values()) {
            if (value.stringValue.equals(tokenString)) {
                return value;
            }
        }
        return null;
    }
}
