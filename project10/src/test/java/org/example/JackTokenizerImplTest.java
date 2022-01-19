package org.example;


import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class JackTokenizerImplTest {
    @Test
    public void whenWhileThenWhileKeyWord() {
        String stat = "while(";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        // while
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.WHILE, tokenizer.keyword());
        Assert.assertTrue(tokenizer.hasMoreTokens());

        tokenizer.advance();
        Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
        Assert.assertEquals("(", tokenizer.symbol());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenIfThenIfKeyWord() {
        String stat = "if";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.IF, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenClassThenClassKeyWord() {
        String stat = "class";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.CLASS, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenConstructorThenConstructorKeyWord() {
        String stat = "constructor";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.CONSTRUCTOR, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenFunctionThenFunctionKeyWord() {
        String stat = "function";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.FUNCTION, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenMethodThenMethodKeyWord() {
        String stat = "method";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.METHOD, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenFieldThenFieldKeyWord() {
        String stat = "field";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.FIELD, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenStaticThenStaticKeyWord() {
        String stat = "static";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.STATIC, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenVarThenVarKeyWord() {
        String stat = "var";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.VAR, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenIntThenIntKeyWord() {
        String stat = "int";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.INT, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenCharThenCharKeyWord() {
        String stat = "char";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.CHAR, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenBooleanThenBooleanKeyWord() {
        String stat = "boolean";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.BOOLEAN, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenVoidThenVoidKeyWord() {
        String stat = "void";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.VOID, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenTrueThenTrueKeyWord() {
        String stat = "true";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.TRUE, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenFalseThenFalseKeyWord() {
        String stat = "false";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.FALSE, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenNullThenNullKeyWord() {
        String stat = "null";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.NULL, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenThisThenThisKeyWord() {
        String stat = "this";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.THIS, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenLetThenLetKeyWord() {
        String stat = "let";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.LET, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenDoThenDoKeyWord() {
        String stat = "do";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.DO, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenElseThenElseKeyWord() {
        String stat = "else";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.ELSE, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenReturnThenReturnKeyWord() {
        String stat = "return";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.RETURN, tokenizer.keyword());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }
}