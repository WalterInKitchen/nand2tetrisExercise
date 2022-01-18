package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class JackTokenizerImpl implements JackTokenizer {
    private final InputStream inputStream;
    private TokenType tokenType;
    private String tokenString;
    private Integer nextNextByte;

    private final Set<String> VALID_KEYWORDS = new HashSet<String>() {{
        add("class");
        add("constructor");
        add("function");
        add("method");
        add("filed");
        add("static");
        add("var");
        add("int");
        add("char");
        add("boolean");
        add("void");
        add("true");
        add("false");
        add("null");
        add("this");
        add("let");
        add("do");
        add("if");
        add("else");
        add("while");
        add("return");
    }};

    private Set<Character> VALID_SYMBOLS_START = new HashSet<Character>() {{
        add('{');
        add('}');
        add('(');
        add(')');
        add('[');
        add(']');
        add('.');
        add(',');
        add(';');
        add('+');
        add('-');
        add('*');
        add('/');
        add('&');
        add('|');
        add('<');
        add('>');
        add('=');
        add('~');
    }};

    public JackTokenizerImpl(InputStream ins) {
        this.inputStream = ins;
        try {
            this.nextNextByte = this.inputStream.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasMoreTokens() {
        return this.nextNextByte >= 0;
    }

    @Override
    public void advance() {
        Integer nextByte = readNextNotWhiteSpaceByte();
        if (byteIsIdentifierStart(nextByte)) {
            readIdentifierOrKeywordToken(nextByte);
        } else if (byteIsSymbolStart(nextByte)) {
            readSymbolToken(nextByte);
        }
    }

    private Integer readNextNotWhiteSpaceByte() {
        Integer nextByte = readNextByte();
        while (isByteWhiteSpace(nextByte)) {
            nextByte = readNextByte();
        }
        return nextByte;
    }

    @Override
    public TokenType tokenType() {
        return this.tokenType;
    }

    private void readSymbolToken(Integer byt) {
        char character = (char) (int) byt;
        StringBuilder builder = new StringBuilder(String.valueOf(character));
        Integer nextByte = readNextByte();
        char nextChar = (char) (int) nextByte;
        switch (character) {
            case '<':
                if (nextChar == '=') {
                    builder.append('=');
                } else if (nextChar == '>') {
                    builder.append('>');
                }
                break;
            case '>':
            case '+':
            case '-':
                if (nextChar == '=') {
                    builder.append('=');
                }
                break;
            default: {

            }
        }
        this.tokenType = TokenType.SYMBOL;
        this.tokenString = builder.toString();
    }

    private boolean byteIsSymbolStart(Integer nextByte) {
        return VALID_SYMBOLS_START.contains((char) (int) nextByte);
    }

    private void readIdentifierOrKeywordToken(Integer byt) {
        StringBuilder builder = new StringBuilder(String.valueOf((char) (int) byt));
        Integer nextByte = readNextByte();
        while (byteIsNotIdentifierEnd(nextByte)) {
            builder.append((char) (int) nextByte);
            nextByte = readNextByte();
        }
        putByteBack(nextByte);
        String identifier = builder.toString();
        if (isIdentifierIsKeyWord(identifier)) {
            this.tokenType = TokenType.KEYWORD;
        } else {
            this.tokenType = TokenType.IDENTIFIER;
        }
        this.tokenString = identifier;
    }

    private void putByteBack(Integer nextByte) {
        this.nextNextByte = nextByte;
    }

    private boolean isIdentifierIsKeyWord(String identifier) {
        return VALID_KEYWORDS.contains(identifier);
    }

    private boolean byteIsNotIdentifierEnd(Integer nextByte) {
        if (isByteAlphabet(nextByte)) {
            return true;
        }
        if (nextByte == '_') {
            return true;
        }
        return isByteNumber(nextByte);
    }

    private boolean isByteNumber(Integer nextByte) {
        return nextByte >= (int) '0' && nextByte <= (int) '9';
    }

    private boolean byteIsIdentifierStart(Integer nextByte) {
        if (nextByte == null) {
            return false;
        }
        if (isByteAlphabet(nextByte)) {
            return true;
        }
        return nextByte == '_';
    }

    private boolean isByteAlphabet(Integer nextByte) {
        if (nextByte >= (int) 'a' && nextByte <= (int) 'z') {
            return true;
        }
        return nextByte >= (int) 'A' && nextByte <= (int) 'Z';
    }

    private boolean isByteWhiteSpace(Integer byt) {
        if (byt == (int) ' ') {
            return true;
        }
        return byt == (int) '\n' || byt == (int) '\r' || byt == (int) '\t';
    }

    private Integer readNextByte() {
        int res = 0;
        try {
            res = this.nextNextByte;
            this.nextNextByte = this.inputStream.read();
            if (isByteWhiteSpace(this.nextNextByte)) {
                this.nextNextByte = this.inputStream.read();
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("stream error:" + e.getMessage());
        }
    }

    private TokenType proceedReadToken(int bt) {
        return null;
    }

    @Override
    public Keyword keyword() {
        return Keyword.parseString(this.tokenString);
    }

    @Override
    public String symbol() {
        return this.tokenString;
    }

    @Override
    public String identifier() {
        return null;
    }

    @Override
    public Integer intVal() {
        return null;
    }

    @Override
    public String stringVal() {
        return null;
    }
}
