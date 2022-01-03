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
        inputs.put("M", "001");
        inputs.put("D", "010");
        inputs.put("MD", "011");
        inputs.put("A", "100");
        inputs.put("AM", "101");
        inputs.put("AD", "110");
        inputs.put("AMD", "111");

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
        inputs.put("0", "0101010");
        inputs.put("1", "0111111");
        inputs.put("-1", "0111010");
        inputs.put("D", "0001100");
        inputs.put("A", "0110000");
        inputs.put("!D", "0001101");
        inputs.put("!A", "0110001");
        inputs.put("-D", "0001111");
        inputs.put("-A", "0110011");
        inputs.put("D+1", "0011111");
        inputs.put("A+1", "0110111");
        inputs.put("D-1", "0001110");
        inputs.put("A-1", "0110010");
        inputs.put("D+A", "0000010");
        inputs.put("D-A", "0010011");
        inputs.put("A-D", "0000111");
        inputs.put("D&A", "0000000");
        inputs.put("D|A", "0010101");
        inputs.put("M", "1110000");
        inputs.put("!M", "1110001");
        inputs.put("-M", "1110011");
        inputs.put("M+1", "1110111");
        inputs.put("M-1", "1110010");
        inputs.put("D+M", "1000010");
        inputs.put("D-M", "1010011");
        inputs.put("M-D", "1000111");
        inputs.put("D&M", "1000000");
        inputs.put("D|M", "1010101");

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
        inputs.put("null", "000");
        inputs.put("JGT", "001");
        inputs.put("JEQ", "010");
        inputs.put("JGE", "011");
        inputs.put("JLT", "100");
        inputs.put("JNE", "101");
        inputs.put("JLE", "110");
        inputs.put("JMP", "111");

        CodeImpl code = new CodeImpl();
        for (Map.Entry<String, String> entry : inputs.entrySet()) {
            Assert.assertEquals(entry.getValue(), code.jump(entry.getKey()));
        }
    }
}