package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParserImpl implements Parser {
    private final BufferedReader reader;
    private String currentLine;
    private String nextLine;

    public ParserImpl(InputStream ins) {
        this.reader = new BufferedReader(new InputStreamReader(ins));
        readNextLine();
    }

    private void readNextLine() {
        try {
            this.nextLine = reader.readLine();
        } catch (IOException exception) {
            this.nextLine = null;
        }
    }

    @Override
    public Boolean hasMoreCommands() {
        return this.nextLine != null;
    }

    @Override
    public void advance() {
        this.currentLine = this.nextLine;
        readNextLine();
        if (this.currentLine == null) {
            throw new RuntimeException("file reached the end");
        }
    }

    @Override
    public CommandType commandType() {
        for (CommandType value : CommandType.values()) {
            if (value.match(this.currentLine)) {
                return value;
            }
        }
        throw new RuntimeException("unknown command");
    }

    @Override
    public String symbol() {
        return commandType().parseSymbol(this.currentLine);
    }

    @Override
    public String dest() {
        return commandType().parseDest(this.currentLine);
    }

    @Override
    public String comp() {
        return commandType().parseComp(this.currentLine);
    }

    @Override
    public String jump() {
        return commandType().parseJump(this.currentLine);
    }
}
