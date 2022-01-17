package org.example;

import java.io.IOException;
import java.io.InputStream;

public class JackTokenizerImpl implements JackTokenizer {
    private final InputStream inputStream;
    private int nextByte = 0;
    private int currentByte = 0;

    public JackTokenizerImpl(InputStream ins) {
        this.inputStream = ins;
        this.nextByte = readNextByte();
    }

    @Override
    public boolean hasMoreTokens() {
        return this.nextByte >= 0;
    }

    @Override
    public void advance() {
        this.currentByte = this.nextByte;
        this.nextByte = readNextByte();
    }

    @Override
    public TokenType tokenType() {
        while (true) {
            Integer nextByte = readNextByte();
            TokenType type = proceedReadToken(nextByte);
            if (type != null) {
                return type;
            }
        }
    }

    private Integer readNextByte() {
        int res = 0;
        try {
            res = this.inputStream.read();
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
        return null;
    }

    @Override
    public String symbol() {

        return "while";
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
