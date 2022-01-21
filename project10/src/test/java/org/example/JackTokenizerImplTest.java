package org.example;


import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JackTokenizerImplTest {
    @Test
    public void whenLetSentenceThenLetTokens() {
        String stat = "let name=1000; ";
        ByteArrayInputStream byIn = new ByteArrayInputStream(stat.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);
        // let
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.KEYWORD, tokenizer.tokenType());
        Assert.assertEquals(Keyword.LET, tokenizer.keyword());
        Assert.assertTrue(tokenizer.hasMoreTokens());
        // name
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.IDENTIFIER, tokenizer.tokenType());
        Assert.assertEquals("name", tokenizer.identifier());
        Assert.assertTrue(tokenizer.hasMoreTokens());
        // =
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
        Assert.assertEquals("=", tokenizer.symbol());
        Assert.assertTrue(tokenizer.hasMoreTokens());
        // 1000
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.INTEGER_CONST, tokenizer.tokenType());
        Assert.assertEquals(new Integer(1000), tokenizer.intVal());
        Assert.assertTrue(tokenizer.hasMoreTokens());
        // ;
        Assert.assertTrue(tokenizer.hasMoreTokens());
        tokenizer.advance();
        Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
        Assert.assertEquals(";", tokenizer.symbol());
        Assert.assertFalse(tokenizer.hasMoreTokens());
    }

    @Test
    public void whenWhileExprThenWhile() {
        String expr = "int count=0;\n"
                + "while (count++<100) {\n"
                + "    System.out.print(count);\n"
                + "    if(count==50){\n"
                + "        println(\"we reached the middle\"); \n"
                + "    }"
                + "}";
        List<Pair<TokenType, Object>> expecteds = Arrays.asList(
                new Pair<>(TokenType.KEYWORD, Keyword.INT),
                new Pair<>(TokenType.IDENTIFIER, "count"),
                new Pair<>(TokenType.SYMBOL, "="),
                new Pair<>(TokenType.INTEGER_CONST, 0),
                new Pair<>(TokenType.SYMBOL, ";"),
                new Pair<>(TokenType.KEYWORD, Keyword.WHILE),
                new Pair<>(TokenType.SYMBOL, "("),
                new Pair<>(TokenType.IDENTIFIER, "count"),
                new Pair<>(TokenType.SYMBOL, "++"),
                new Pair<>(TokenType.SYMBOL, "<"),
                new Pair<>(TokenType.INTEGER_CONST, 100),
                new Pair<>(TokenType.SYMBOL, ")"),
                new Pair<>(TokenType.SYMBOL, "{"),
                new Pair<>(TokenType.IDENTIFIER, "System"),
                new Pair<>(TokenType.SYMBOL, "."),
                new Pair<>(TokenType.IDENTIFIER, "out"),
                new Pair<>(TokenType.SYMBOL, "."),
                new Pair<>(TokenType.IDENTIFIER, "print"),
                new Pair<>(TokenType.SYMBOL, "("),
                new Pair<>(TokenType.IDENTIFIER, "count"),
                new Pair<>(TokenType.SYMBOL, ")"),
                new Pair<>(TokenType.SYMBOL, ";"),

                // if(count==50)
                new Pair<>(TokenType.KEYWORD, Keyword.IF    ),
                new Pair<>(TokenType.SYMBOL, "("),
                new Pair<>(TokenType.IDENTIFIER, "count"),
                new Pair<>(TokenType.SYMBOL, "=="),
                new Pair<>(TokenType.INTEGER_CONST, 50),
                new Pair<>(TokenType.SYMBOL, ")"),
                // {
                new Pair<>(TokenType.SYMBOL, "{"),
                new Pair<>(TokenType.IDENTIFIER, "println"),
                new Pair<>(TokenType.SYMBOL, "("),
                new Pair<>(TokenType.STRING_CONST, "we reached the middle"),
                new Pair<>(TokenType.SYMBOL, ")"),
                new Pair<>(TokenType.SYMBOL, ";"),
                new Pair<>(TokenType.SYMBOL, "}"),

                //} end while
                new Pair<>(TokenType.SYMBOL, "}")
        );
        ByteArrayInputStream byIn = new ByteArrayInputStream(expr.getBytes(StandardCharsets.UTF_8));
        JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);

        for (Pair<TokenType, Object> expect : expecteds) {
            System.out.println(expect.getValue());
            Assert.assertTrue(tokenizer.hasMoreTokens());
            tokenizer.advance();
            Assert.assertEquals(expect.getKey(), tokenizer.tokenType());
            switch (expect.getKey()) {
                case KEYWORD:
                    Assert.assertEquals(expect.getValue(), tokenizer.keyword());
                    break;
                case SYMBOL:
                    Assert.assertEquals(expect.getValue(), tokenizer.symbol());
                    break;
                case INTEGER_CONST:
                    Assert.assertEquals(expect.getValue(), tokenizer.intVal());
                    break;
                case STRING_CONST:
                    Assert.assertEquals(expect.getValue(), tokenizer.stringVal());
                    break;
                case IDENTIFIER:
                    Assert.assertEquals(expect.getValue(), tokenizer.identifier());
                    break;
            }
        }
    }

    @Test
    public void whenSymbolThenSymbolString() {
        Map<String, String> symbols = new HashMap<String, String>() {{
            put("{", "{");
            put("}", "}");
            put("(", "(");
            put(")", ")");
            put("[", "[");
            put("]", "]");
            put(".", ".");
            put(",", ",");
            put(";", ";");
            put("+", "+");
            put("++", "++");
            put("-", "-");
            put("--", "--");
            put("*", "*");
            put("/", "/");
            put("&", "&");
            put("|", "|");
            put("<", "<");
            put(">", ">");
            put("=", "=");
            put("==", "==");
            put("+=", "+=");
            put("-=", "-=");
            put("<=", "<=");
            put(">=", ">=");
            put("<>", "<>");
            put("~", "~");
        }};

        for (Map.Entry<String, String> entry : symbols.entrySet()) {
            String source = entry.getKey();
            ByteArrayInputStream byIn = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);

            Assert.assertTrue(tokenizer.hasMoreTokens());
            tokenizer.advance();
            Assert.assertEquals(TokenType.SYMBOL, tokenizer.tokenType());
            Assert.assertEquals(entry.getValue(), tokenizer.symbol());
            Assert.assertFalse(tokenizer.hasMoreTokens());
        }
    }

    @Test
    public void whenIntegerConstantThenIntegerConstant() {
        Map<String, Integer> symbols = new HashMap<String, Integer>();
        for (int i = 0; i <= 32767; i++) {
            symbols.put(String.valueOf(i), i);
        }

        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            String source = entry.getKey();
            ByteArrayInputStream byIn = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);

            Assert.assertTrue(tokenizer.hasMoreTokens());
            tokenizer.advance();
            Assert.assertEquals(TokenType.INTEGER_CONST, tokenizer.tokenType());
            Assert.assertEquals(entry.getValue(), tokenizer.intVal());
            Assert.assertFalse(tokenizer.hasMoreTokens());
        }
    }

    @Test
    public void whenStringConstThenStringSymbol() {
        Map<String, String> symbols = new HashMap<String, String>() {{
            put("\"\"", "");
            put("\" \"", " ");
            put("\"abc\"", "abc");
            put("\"a\nb\nc\"", "abc");
            put("\"a\r\nb\r\nc\"", "abc");
        }};

        for (Map.Entry<String, String> entry : symbols.entrySet()) {
            String source = entry.getKey();
            ByteArrayInputStream byIn = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);

            Assert.assertTrue(tokenizer.hasMoreTokens());
            tokenizer.advance();
            Assert.assertEquals(TokenType.STRING_CONST, tokenizer.tokenType());
            Assert.assertEquals(entry.getValue(), tokenizer.stringVal());
            Assert.assertFalse(tokenizer.hasMoreTokens());
        }
    }

    @Test
    public void whenIdentifierThenIdentifierSymbol() {
        Map<String, String> symbols = new HashMap<String, String>() {{
            put("name", "name");
            put("age", "age");
        }};

        for (Map.Entry<String, String> entry : symbols.entrySet()) {
            String source = entry.getKey();
            ByteArrayInputStream byIn = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            JackTokenizerImpl tokenizer = new JackTokenizerImpl(byIn);

            Assert.assertTrue(tokenizer.hasMoreTokens());
            tokenizer.advance();
            Assert.assertEquals(TokenType.IDENTIFIER, tokenizer.tokenType());
            Assert.assertEquals(entry.getValue(), tokenizer.identifier());
            Assert.assertFalse(tokenizer.hasMoreTokens());
        }
    }

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