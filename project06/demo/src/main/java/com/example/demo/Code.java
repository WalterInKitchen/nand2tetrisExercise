package com.example.demo;

/**
 * the code module that translate
 * each part of command to binary
 */
public interface Code {
    /**
     * translate dest part to binary
     *
     * @param source source
     * @return binary
     */
    String dest(String source);

    /**
     * translate comp part to binary
     *
     * @param source source
     * @return binary
     */
    String comp(String source);

    /**
     * translate jump part to binary
     *
     * @param source source
     * @return binary
     */
    String jump(String source);
}
