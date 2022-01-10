package com.test.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CodeWriterImpl implements CodeWriter {
    private final BufferedWriter writer;
    private static final int STACK_MAX = 2047;
    private int stackPointer = 256;

    public CodeWriterImpl(OutputStream outputStream) throws IOException {
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void writeArithmetic(String cmd) {

    }

    public void writePushPop(String cmd, String segment, int index) throws IOException {
        int stack = this.stackPointer++;
        if (stack > STACK_MAX) {
            throw new RuntimeException("stack overflow");
        }
        writer.write("@" + index + System.lineSeparator());
        writer.write("D=A" + System.lineSeparator());
        writer.write("@" + stack + System.lineSeparator());
        writer.write("M=D" + System.lineSeparator());
    }

    public void close() throws IOException {
        this.writer.close();
    }
}
