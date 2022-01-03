package com.example.demo;

import java.util.HashMap;
import java.util.Map;

public class SymbolTableImpl implements SymbolTable {
    private final Map<String, Integer> map = new HashMap<>();

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
