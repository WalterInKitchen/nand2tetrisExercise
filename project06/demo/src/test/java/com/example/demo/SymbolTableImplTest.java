package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

public class SymbolTableImplTest {
    /**
     * @provided add entry
     * @expected get address should match the address added
     **/
    @Test
    public void test_addEntry_given_table_when_addEntity_then_getAddressShouldEqToInput() {
        SymbolTableImpl symbolTable = new SymbolTableImpl();
        symbolTable.addEntry("symbol", 22);
        Assert.assertEquals(0, symbolTable.getAddress("symbol") - 22);
    }

    /**
     * @provided table that does contains key
     * @expected exception
     **/
    @Test(expected = RuntimeException.class)
    public void test_getAddress_given_tableNotContainsSymbol_when_getAddress_then_exception() {
        SymbolTableImpl symbolTable = new SymbolTableImpl();
        symbolTable.getAddress("symbol");
    }

    /**
     * @provided table that not contains the symbol
     * @expected return false
     **/
    @Test
    public void test_contains_given_tableNotContainsSymbol_when_contains_then_returnFalse() {
        SymbolTableImpl symbolTable = new SymbolTableImpl();
        Assert.assertFalse(symbolTable.contains("symbol"));
    }

    /**
     * @provided table contains the symbol
     * @expected return true
     **/
    @Test
    public void test_contains_given_tableContainsSymbol_when_contains_then_returnTrue() {
        SymbolTableImpl symbolTable = new SymbolTableImpl();
        symbolTable.addEntry("symbol", 21);
        Assert.assertTrue(symbolTable.contains("symbol"));
    }
}