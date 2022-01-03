package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class SymbolTableImpl implements SymbolTable {
    private final Map<String, Integer> map = new HashMap<String, Integer>() {{
        put("SP", 0);
        put("LCL", 1);
        put("ARG", 2);
        put("THIS", 3);
        put("THAT", 4);
        put("R0", 0);
        put("R1", 1);
        put("R2", 2);
        put("R3", 3);
        put("R4", 4);
        put("R5", 5);
        put("R6", 6);
        put("R7", 7);
        put("R8", 8);
        put("R9", 9);
        put("R10", 10);
        put("R11", 11);
        put("R12", 12);
        put("R13", 13);
        put("R14", 14);
        put("R15", 15);
        put("SCREEN", 16384);
        put("KBD", 24576);
    }};

    @Override
    public void addEntry(String symbol, int address) {
        map.put(symbol, address);
    }

    @Override
    public boolean contains(String symbol) {
        return map.containsKey(symbol);
    }

    @Override
    public int getAddress(String symbol) {
        Integer res = map.get(symbol);
        if (res == null) {
            throw new RuntimeException("symbol not exist");
        }
        return res;
    }
}
