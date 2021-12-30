package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * CodeImplTest
 */
public class CodeImplTest {
    /**
     * @provided the control cmd
     * @expected return the parsed binary
     **/
    @Test
    public void test_dest_given_none_when_cmdIsCtl_then_returnBinary() {
        Map<String, String> inputs = new HashMap<>();
        inputs.put("", "000");
        inputs.put("A;JGT", "000");
        inputs.put("M=A;JGT", "001");
        inputs.put("D=A;JGT", "010");
        inputs.put("MD=A;JGT", "011");
        inputs.put("A=A;JGT", "100");
        inputs.put("AM=A;JGT", "101");
        inputs.put("AD=A;JGT", "110");
        inputs.put("AMD=A;JGT", "111");

        CodeImpl code = new CodeImpl();
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            Assert.assertEquals(entry.getValue(), code.dest(entry.getKey()));
        }
    }

    /**
     * @provided the control cmd
     * @expected the binary
     **/
    @Test
    public void test_comp_given_none_when_cmdIsCtl_then_returnBinary() {
        Map<String, String> inputs = new HashMap<>();
        inputs.put("A;JGT", "0110000");
        inputs.put("M=0;JGT", "0101010");
        inputs.put("M=1;JGT", "0111111");
        inputs.put("M=-1;JGT", "0111010");
        inputs.put("D;JGT", "0001100");
        inputs.put("M=A;JGT", "0110000");
        inputs.put("M=!D;JGT", "0001101");
        inputs.put("M=!A", "0110001");
        inputs.put("M=-D;JGT", "0001111");
        inputs.put("M=-A;JGT", "0110011");
        inputs.put("M=D+1;JGT", "0011111");
        inputs.put("M=A+1;JGT", "0110111");
        inputs.put("M=D-1;JGT", "0001110");
        inputs.put("M=A-1;JGT", "0110010");
        inputs.put("D+A;JGT", "0000010");
        inputs.put("M=D-A;JGE", "0010011");
        inputs.put("M=A-D;JGT", "0000111");
        inputs.put("M=D&A;JGT", "0000000");
        inputs.put("M=D|A;JGT", "0010101");
        inputs.put("A=M;JGT", "1110000");
        inputs.put("A=!M;JGT", "1110001");
        inputs.put("A=-M;JGT", "1110011");
        inputs.put("A=M+1;JGT", "1110111");
        inputs.put("A=M-1;JGT", "1110010");
        inputs.put("D+M;JLT", "1000010");
        inputs.put("A=D-M;JGT", "1010011");
        inputs.put("A=M-D;JGT", "1000111");
        inputs.put("A=D&M;JGT", "1000000");
        inputs.put("A=D|M;JGT", "1010101");

        CodeImpl code = new CodeImpl();
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            Assert.assertEquals(entry.getValue(), code.comp(entry.getKey()));
        }
    }

    /**
     * @provided control cmd
     * @expected binary
     **/
    @Test
    public void test_jump_given_none_when_cmdIsCtl_then_returnBinary() {
        Map<String, String> inputs = new HashMap<>();
        inputs.put("", "000");
        inputs.put("A=M", "000");
        inputs.put("0;JGT", "001");
        inputs.put("M=1;JEQ", "010");
        inputs.put("-1;JGE", "011");
        inputs.put("D;JLT", "100");
        inputs.put("M=A;JNE", "101");
        inputs.put("M=!D;JLE", "110");
        inputs.put("M=!A;JMP", "111");

        CodeImpl code = new CodeImpl();
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            Assert.assertEquals(entry.getValue(), code.jump(entry.getKey()));
        }
    }
}