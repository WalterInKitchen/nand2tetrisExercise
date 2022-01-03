package com.example.demo;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class AssemblerImpl implements Assembler {
    private final Pattern VARIABLE_CONSTANTS_PATTERN = Pattern.compile("\\d+");
    private final Code code = new CodeImpl();
    private final SymbolTable variableSymbolTable = new SymbolTableImpl();
    private int nextVariableStartAddress = 16;

    @Override
    public void assemble(InputStream ins, OutputStream outs) throws IOException {
        byte[] bytes = getBytes(ins);
        readLabelToSymbolTable(new ByteArrayInputStream(bytes));
        ByteArrayInputStream ins2 = new ByteArrayInputStream(bytes);

        Parser parser = new ParserImpl(ins2);
        while (parser.hasMoreCommands()) {
            parser.advance();
            try {
                CommandType commandType = parser.commandType();
                String binary = convertCmdToBinary(commandType, parser);
                if (binary == null) {
                    continue;
                }
                outs.write(binary.getBytes(StandardCharsets.UTF_8));
                outs.write(System.lineSeparator().getBytes(StandardCharsets.UTF_8));
            } catch (RuntimeException ignored) {
            }
        }
    }

    private void readLabelToSymbolTable(InputStream ins) {
        Parser parser = new ParserImpl(ins);
        int nextLineNumber = 0;
        while (parser.hasMoreCommands()) {
            parser.advance();
            try {
                CommandType commandType = parser.commandType();
                if (!CommandType.LOCATION_CMD.equals(commandType)) {
                    nextLineNumber++;
                    continue;
                }
                String symbol = parser.symbol();
                variableSymbolTable.addEntry(symbol, nextLineNumber);
            } catch (RuntimeException ignored) {
            }
        }
    }

    private byte[] getBytes(InputStream ins) throws IOException {
        ByteArrayOutputStream bts = new ByteArrayOutputStream();
        IOUtils.copy(ins, bts);
        byte[] bytes = bts.toByteArray();
        return bytes;
    }

    private String convertCmdToBinary(CommandType commandType, Parser parser) {
        switch (commandType) {
            case ADDRESS_CMD: {
                String symbol = parser.symbol();
                return "0" + convertVariableSymbolToBinary(symbol);
            }
            case CONTROL_CMD: {
                return "111" + code.comp(parser.comp()) + code.dest(parser.dest()) + code.jump(parser.jump());
            }
        }
        return null;
    }

    private String convertVariableSymbolToBinary(String symbol) {
        if (VARIABLE_CONSTANTS_PATTERN.matcher(symbol).matches()) {
            return binaryVariableSymbol(Integer.parseInt(symbol));
        }
        if (variableSymbolTable.contains(symbol)) {
            return binaryVariableSymbol(variableSymbolTable.getAddress(symbol));
        }
        variableSymbolTable.addEntry(symbol, nextVariableStartAddress++);
        return binaryVariableSymbol(variableSymbolTable.getAddress(symbol));
    }

    private String binaryVariableSymbol(int value) {
        String str = Integer.toBinaryString(value);
        if (str.length() == 15) {
            return str;
        }
        return "000000000000000".substring(0, 15 - str.length()) + str;
    }
}
