package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class JackTokenizerImpl implements JackTokenizer {
    private final InputStream inputStream;
    private TokenType tokenType;
    private String tokenString;
    private Integer intTokeValue;
    private Integer nextNextByte;
    private String stringTokenValue;

    private static final Set<String> VALID_KEYWORDS = new HashSet<String>();

    private final Set<Character> VALID_SYMBOLS_START = new HashSet<Character>() {{
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

    static {
        for (Keyword keyword : Keyword.values()) {
            VALID_KEYWORDS.add(keyword.getStringValue());
        }
    }

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
        Integer nextByte = readFirstNotBlank();
        if (byteIsIdentifierStart(nextByte)) {
            readIdentifierOrKeywordToken(nextByte);
        } else if (byteIsSymbolStart(nextByte)) {
            readSymbolToken(nextByte);
        } else if (byteIsPartOfIntegerConst(nextByte)) {
            readIntegerConst(nextByte);
        } else if (byteIsStringConstBorderCharacter(nextByte)) {
            readStringConst(nextByte);
        }
    }

    private void readStringConst(Integer byt) {
        Integer nextByte = readNextByte(false);
        StringBuilder builder = new StringBuilder();
        while (!byteIsStringConstBorderCharacter(nextByte)) {
            char cha = (char) (int) nextByte;
            nextByte = readNextByte(false);
            if (cha == '\r' || cha == '\n') {
                continue;
            }
            builder.append(cha);
        }
        this.tokenType = TokenType.STRING_CONST;
        this.stringTokenValue = builder.toString();
    }

    private boolean byteIsStringConstBorderCharacter(Integer nextByte) {
        char cha = (char) (int) nextByte;
        return cha == '"';
    }

    private void readIntegerConst(Integer bte) {
        char character = (char) (int) bte;
        StringBuilder builder = new StringBuilder(String.valueOf(character));
        Integer nextByte = readNextByte(true);
        while (byteIsPartOfIntegerConst(nextByte)) {
            builder.append((char) (int) nextByte);
            nextByte = readNextByte(true);
        }
        this.tokenType = TokenType.INTEGER_CONST;
        this.intTokeValue = Integer.parseInt(builder.toString());
    }

    private boolean byteIsPartOfIntegerConst(Integer bte) {
        return bte >= (int) '0' && bte <= (int) '9';
    }

    private Integer readFirstNotBlank() {
        Integer nextByte = readNextByte(false);
        while (nextByte != null && ((char) (int) nextByte) == ' ') {
            nextByte = readNextByte(false);
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
        Integer nextByte = readNextByte(true);
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
        Integer nextByte = readNextByte(false);
        while (byteIsNotIdentifierEnd(nextByte)) {
            builder.append((char) (int) nextByte);
            nextByte = readNextByte(false);
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

    private boolean isByteWhiteSpace(Integer byt, boolean ignoreSpace) {
        if (ignoreSpace && byt == (int) ' ') {
            return true;
        }
        return byt == (int) '\n' || byt == (int) '\r' || byt == (int) '\t';
    }

    private Integer readNextByte(boolean ignoreSpace) {
        int res = 0;
        try {
            res = this.nextNextByte;
            this.nextNextByte = this.inputStream.read();
            if (isByteWhiteSpace(this.nextNextByte, ignoreSpace)) {
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
        return this.tokenString;
    }

    @Override
    public Integer intVal() {
        return this.intTokeValue;
    }

    @Override
    public String stringVal() {
        return this.stringTokenValue;
    }
}
