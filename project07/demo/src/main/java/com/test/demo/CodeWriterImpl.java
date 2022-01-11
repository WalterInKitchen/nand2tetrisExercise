package com.test.demo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class CodeWriterImpl implements CodeWriter {
    private final BufferedWriter writer;
    private static final int STACK_MAX = 2047;
    private static final int STACK_MIN = 256;
    private static final int STACK_SIZE = STACK_MAX - STACK_MIN + 1;
    private int stackSize = 0;

    public CodeWriterImpl(OutputStream outputStream) throws IOException {
        this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public void writeArithmetic(String cmd) {

    }

    public void writePushPop(String cmd, String segment, int index) throws IOException {
        if ("push".equals(cmd)) {
            if (this.stackSize >= STACK_SIZE) {
                throw new RuntimeException("stack overflow");
            }
            pushSegment(segment, index);
            writer.write("@SP" + System.lineSeparator());
            writer.write("M=D" + System.lineSeparator());       //*SP=D
            writer.write("@SP" + System.lineSeparator());
            writer.write("M=M+1" + System.lineSeparator());     //SP++
            this.stackSize++;
        }
    }

    private void pushSegment(String segment, int index) throws IOException {
        if (segment.equals("constant")) {
            writer.write("@" + index + System.lineSeparator());
            writer.write("D=A" + System.lineSeparator());
            return;
        } else if (segment.equals("local")) {
            writer.write("@" + index + System.lineSeparator());
            writer.write("D=A" + System.lineSeparator());
            writer.write("@LCL" + System.lineSeparator());
            writer.write("A=D+M" + System.lineSeparator()); //SP+i
            writer.write("D=M" + System.lineSeparator());   //D=*(SP+i)
            return;
        }
    }

    public void close() throws IOException {
        this.writer.close();
    }
}
