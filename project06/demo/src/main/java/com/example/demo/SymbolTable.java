package com.example.demo;

public interface SymbolTable {
    /**
     * add symbol,address pair to symbolTable
     *
     * @param symbol  symbol
     * @param address address
     */
    void addEntry(String symbol, int address);

    /**
     * check if symbol table contains symbol
     *
     * @param symbol the symbol to check
     * @return true/false
     */
    boolean contains(String symbol);

    /**
     * get the address of the symbol
     *
     * @param symbol the symbol
     * @return address
     */
    int getAddress(String symbol);
}
