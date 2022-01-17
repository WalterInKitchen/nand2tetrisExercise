package org.example;

public interface JackTokenizer {

    boolean hasMoreTokens();

    void advance();

    TokenType tokenType();

    Keyword keyword();

    String symbol();

    String identifier();

    Integer intVal();

    String stringVal();
}
