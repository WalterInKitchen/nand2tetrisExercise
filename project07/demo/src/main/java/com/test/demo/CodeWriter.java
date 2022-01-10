package com.test.demo;

import java.io.IOException;

public interface CodeWriter {
    /**
     * writearithmetic cmd
     *
     * @param cmd cmd
     */
    void writeArithmetic(String cmd);

    /**
     * write push pop
     *
     * @param cmd     cmd
     * @param segment segment
     * @param index   index
     */
    void writePushPop(String cmd, String segment, int index) throws IOException;

    /**
     * close fileStream
     */
    void close() throws IOException;
}
