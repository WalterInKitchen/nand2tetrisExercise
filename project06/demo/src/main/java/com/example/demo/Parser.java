package com.example.demo;

public interface Parser {
    /**
     * return true if has more commands
     *
     * @return true/false
     */
    Boolean hasMoreCommands();

    /**
     * move next
     */
    void advance();

    /**
     * get the current command type
     *
     * @return the type
     */
    CommandType commandType();

    /**
     * get the symbol
     *
     * @return the symbol
     */
    String symbol();

    /**
     * get the dest part of command
     *
     * @return string or null
     */
    String dest();

    /**
     * get the comp part of command
     *
     * @return string or null
     */
    String comp();

    /**
     * get the jump part
     *
     * @return string or null
     */
    String jump();
}
