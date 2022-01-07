package com.test.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * the impl of Parser
 */
public class ParserImpl implements Parser {
    private final BufferedReader reader;
    private String nextLine;
    private String currentLine;
    private final Set<String> ARITHMETIC_CMD = new HashSet<String>() {{
        add("add");
        add("sub");
        add("neg");
        add("eq");
        add("gt");
        add("lt");
        add("and");
        add("or");
        add("not");
    }};

    /**
     * constructor
     *
     * @param inputStream the inputStream
     */
    public ParserImpl(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.readNextLine();
    }

    private void readNextLine() {
        try {
            this.nextLine = reader.readLine();
        } catch (IOException exception) {
            this.nextLine = null;
        }
    }

    public boolean hasMoreCommands() {
        return this.nextLine != null;
    }

    public void advance() {
        if (!hasMoreCommands()) {
            throw new RuntimeException("no more commands");
        }
        this.currentLine = this.nextLine;
        readNextLine();
    }

    public CommandType commandType() {
        if (this.currentLine == null) {
            return null;
        }
        String cmd = this.currentLine.trim();
        if (cmd.startsWith("push")) {
            return CommandType.PUSH;
        } else if (cmd.startsWith("pop")) {
            return CommandType.POP;
        } else if (cmd.startsWith("label")) {
            return CommandType.LABEL;
        } else if (cmd.startsWith("goto")) {
            return CommandType.GOTO;
        } else if (cmd.startsWith("if-goto")) {
            return CommandType.IF;
        } else if (cmd.startsWith("function")) {
            return CommandType.FUNCTION;
        } else if (cmd.startsWith("return")) {
            return CommandType.RETURN;
        } else if (cmd.startsWith("call")) {
            return CommandType.CALL;
        } else if (isArithmeticCmd(cmd)) {
            return CommandType.ARITHMETIC;
        }
        throw new RuntimeException("unknown cmd");
    }

    private boolean isArithmeticCmd(String cmd) {
        return ARITHMETIC_CMD.contains(cmd);
    }

    public String arg1() {
        if (commandType().equals(CommandType.RETURN)) {
            throw new RuntimeException("cmd not support arg1");
        } else if (commandType().equals(CommandType.ARITHMETIC)) {
            return this.currentLine.trim();
        }
        String[] strs = this.currentLine.trim().split("\\s+");
        return strs[1];
    }

    public Integer arg2() {
        CommandType cmd = commandType();
        if (CommandType.PUSH.equals(cmd) || CommandType.POP.equals(cmd) || CommandType.FUNCTION.equals(cmd) || CommandType.CALL.equals(cmd)) {
            String[] cmds = this.currentLine.trim().split("\\s+");
            return Integer.parseInt(cmds[2]);
        }
        throw new RuntimeException("cmd not support arg2");
    }
}
