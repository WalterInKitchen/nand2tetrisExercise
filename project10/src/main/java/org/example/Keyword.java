package org.example;

import lombok.Getter;

public enum Keyword {
    WHILE("while"),
    CLASS("class"),
    CONSTRUCTOR("constructor"),
    FUNCTION("function"),
    METHOD("method"),
    FIELD("field"),
    STATIC("static"),
    VAR("var"),
    INT("int"),
    CHAR("char"),
    BOOLEAN("boolean"),
    VOID("void"),
    TRUE("true"),
    FALSE("false"),
    NULL("null"),
    THIS("this"),
    LET("let"),
    DO("do"),
    IF("if"),
    ELSE("else"),
    RETURN("return");

    @Getter
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
