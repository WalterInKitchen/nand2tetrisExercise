package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class JackTokenizerImpl implements JackTokenizer {
    private final InputStream inputStream;
    private TokenType tokenType;
    private String tokenString;
    private Integer intTokeValue;
    private String stringTokenValue;

    private final LinkedList<Integer> NEXT_BYTE_QUEUE = new LinkedList<>();
    private static final Set<String> VALID_KEYWORDS = new HashSet<>();

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
            int next = this.inputStream.read();
            this.NEXT_BYTE_QUEUE.addLast(next);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasMoreTokens() {
        return !this.NEXT_BYTE_QUEUE.isEmpty();
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
        Integer nextByte = readNextByte();
        if (nextByte == null) {
            return;
        }
        StringBuilder builder = new StringBuilder();
        while (!byteIsStringConstBorderCharacter(nextByte)) {
            char cha = (char) (int) nextByte;
            nextByte = readNextByte();
            if (nextByte == null) {
                return;
            }
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
        Integer nextByte = readNextByte();
        while (nextByte != null && byteIsPartOfIntegerConst(nextByte)) {
            builder.append((char) (int) nextByte);
            nextByte = readNextByte();
        }
        putByteBack(nextByte);
        this.tokenType = TokenType.INTEGER_CONST;
        this.intTokeValue = Integer.parseInt(builder.toString());
    }

    private boolean byteIsPartOfIntegerConst(Integer bte) {
        return bte >= (int) '0' && bte <= (int) '9';
    }

    private Integer readFirstNotBlank() {
        Integer nextByte = readNextByte();
        if (nextByte == null) {
            return -1;
        }
        while (!byteIsNotBlank(nextByte)) {
            nextByte = readNextByte();
            if (nextByte == null) {
                return -1;
            }
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
        if (nextByte == null) {
            this.tokenType = TokenType.SYMBOL;
            this.tokenString = builder.toString();
            return;
        }
        char nextChar = (char) (int) nextByte;
        boolean nextCharIsValid = false;
        switch (character) {
            case '<':
                if (nextChar == '=') {
                    builder.append('=');
                    nextCharIsValid = true;
                } else if (nextChar == '>') {
                    builder.append('>');
                    nextCharIsValid = true;
                }
                break;
            case '+': {
                if (nextChar == '+') {
                    builder.append('+');
                    nextCharIsValid = true;
                    break;
                }
                if (nextChar == '=') {
                    builder.append('=');
                    nextCharIsValid = true;
                    break;
                }
                break;
            }
            case '=':
                if (nextChar == '=') {
                    builder.append('=');
                    nextCharIsValid = true;
                }
                break;
            case '>':
                if (nextChar == '=') {
                    builder.append('=');
                    nextCharIsValid = true;
                }
                break;
            case '-':
                if (nextByte == '-') {
                    builder.append('-');
                    nextCharIsValid = true;
                    break;
                }
                if (nextChar == '=') {
                    builder.append('=');
                    nextCharIsValid = true;
                    break;
                }
                break;
            default:
                break;
        }
        if (!nextCharIsValid && byteIsNotBlank(nextByte)) {
            putByteBack(nextByte);
        }
        this.tokenType = TokenType.SYMBOL;
        this.tokenString = builder.toString();
    }

    private boolean byteIsNotBlank(Integer nextByte) {
        if (nextByte == null) {
            return false;
        }
        char chr = (char) (int) nextByte;
        return !(chr == ' ' || chr == '\t' || chr == '\r' || chr == '\n');
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
        if (nextByte == null) {
            return;
        }
        this.NEXT_BYTE_QUEUE.addFirst(nextByte);
    }

    private boolean isIdentifierIsKeyWord(String identifier) {
        return VALID_KEYWORDS.contains(identifier);
    }

    private boolean byteIsNotIdentifierEnd(Integer nextByte) {
        if (nextByte == null) {
            return false;
        }
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
        if (nextByte == null) {
            return false;
        }
        if (nextByte >= (int) 'a' && nextByte <= (int) 'z') {
            return true;
        }
        return nextByte >= (int) 'A' && nextByte <= (int) 'Z';
    }

    private Integer readNextByte() {
        try {
            if (!this.NEXT_BYTE_QUEUE.isEmpty()) {
                Integer res = this.NEXT_BYTE_QUEUE.removeFirst();
                int next = this.inputStream.read();
                if (next < 0) {
                    return res;
                }
                this.NEXT_BYTE_QUEUE.addLast(next);
                return res;
            }
            int next = this.inputStream.read();
            if (next < 0) {
                return null;
            }
            this.NEXT_BYTE_QUEUE.addLast(next);
            next = this.inputStream.read();
            if (next < 0) {
                return null;
            }
            this.NEXT_BYTE_QUEUE.addLast(next);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("stream error:" + e.getMessage());
        }
        return this.NEXT_BYTE_QUEUE.removeFirst();
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
