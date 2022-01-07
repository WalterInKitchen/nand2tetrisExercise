package com.test.demo;

/**
 * the parser
 */
public interface Parser {
    /**
     * check if has more commands
     *
     * @return true/false
     */
    boolean hasMoreCommands();

    /**
     * advance
     */
    void advance();

    /**
     * return the current command type
     *
     * @return type
     */
    CommandType commandType();

    /**
     * get the arg1
     *
     * @return the string
     */
    String arg1();

    /**
     * get the arg2
     *
     * @return arg or null
     */
    Integer arg2();
}
