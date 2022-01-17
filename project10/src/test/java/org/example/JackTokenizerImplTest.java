package org.example;


import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class JackTokenizerImplTest {
    @Test
    public void whenWhileThenWhileSymbol() {
        String stat = "while";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        // while
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
        Assert.assertEquals("while", tokenizer.symbol());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenLeftBracketThenLeftBracketSymbol() {
        String stat = "(";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        // (
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
        Assert.assertEquals("(", tokenizer.symbol());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }
}