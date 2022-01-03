package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Assembler {
    /**
     * assemble the asm to binary
     * and write to the outputStream
     *
     * @param ins  the source file input stream
     * @param outs the target outPutStream to write
     */
    void assemble(InputStream ins, OutputStream outs) throws IOException;
}
