package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ParserImplTest {
    /**
     * @provided none empty inputStream
     * @expected true
     **/
    @Test
    public void test_hasMoreCommands_given_ins_when_insNotEmpty_then_returnTrue() {
        ByteArrayInputStream ins = new ByteArrayInputStream("the input cmd".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        Assert.assertTrue(parser.hasMoreCommands());
    }

    /**
     * @provided empty inputStream
     * @expected false
     **/
    @Test
    public void test_hasMoreCommands_given_stream_when_streamIsEmpty_then_returnFalse() {
        ByteArrayInputStream ins = new ByteArrayInputStream("".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        Assert.assertFalse(parser.hasMoreCommands());
    }

    /**
     * @provided the init state
     * @expected exception
     **/
    @Test(expected = RuntimeException.class)
    public void test_commandType_given_initState_when_getCommandType_then_throwException() {
        ByteArrayInputStream ins = new ByteArrayInputStream("command".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.commandType();
    }

    /**
     * @provided the address cmd and advance invoked
     * @expected the address cmd type
     **/
    @Test
    public void test_commandType_given_addressCmd_when_getCmd_then_returnCmd() {
        String builder = "@32" + System.lineSeparator() + "@label";
        ByteArrayInputStream ins = new ByteArrayInputStream(builder.getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        CommandType cmd = parser.commandType();
        Assert.assertEquals(CommandType.ADDRESS_CMD, cmd);
        parser.advance();
        cmd = parser.commandType();
        Assert.assertEquals(CommandType.ADDRESS_CMD, cmd);
    }

    /**
     * @provided the control cmd and advance invoked
     * @expected the control cmd type
     **/
    @Test
    public void test_commandType_given_ctlCmd_when_getCmd_then_returnCtlCmd() {
        List<String> cmds = Arrays.asList("M=A", "M=D-1", "D-1;M", "M=b;C", "b;c");
        String str = String.join(System.lineSeparator(), cmds);
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);

        for (String ignored : cmds) {
            parser.advance();
            Assert.assertEquals(CommandType.CONTROL_CMD, parser.commandType());
        }
    }

    /**
     * @provided then location cmd and advance invoked
     * @expected the location cmd
     **/
    @Test
    public void test_commandType_given_lCommand_when_getCmd_then_returnCmd() {
        List<String> cmds = Arrays.asList("(end)", "(LOOP)");
        String str = String.join(System.lineSeparator(), cmds);
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);

        for (String ignored : cmds) {
            parser.advance();
            Assert.assertEquals(CommandType.LOCATION_CMD, parser.commandType());
        }
    }

    /**
     * @provided the address command
     * @expected the cmd content
     **/
    @Test
    public void test_symbol_given_addressCmd_when_getSymbol_then_returnTheStringSymbol() {
        String builder = "@32" + System.lineSeparator() + "@label";
        ByteArrayInputStream ins = new ByteArrayInputStream(builder.getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals("32", parser.symbol());
        parser.advance();
        Assert.assertEquals("label", parser.symbol());
    }

    /**
     * @provided the location cmd
     * @expected the cmd content
     **/
    @Test
    public void test_symbol_given_locationCmd_when_getSymbol_then_returnStringSymbol() {
        List<String> cmds = Arrays.asList("(end)", "(LOOP)");
        String str = String.join(System.lineSeparator(), cmds);
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);

        parser.advance();
        Assert.assertEquals("end", parser.symbol());
        parser.advance();
        Assert.assertEquals("LOOP", parser.symbol());
    }

    /**
     * @provided the address cmd
     * @expected the dest part or null
     **/
    @Test
    public void test_dest_given_ctlCmd_when_getDest_then_returnDest() {
        Map<String, String> map = new HashMap<>();
        map.put("cmd=a", "null");
        map.put("M=A", "M");
        map.put("D=A", "D");
        map.put("MD=A", "MD");
        map.put("A=M", "A");
        map.put("AM=M", "AM");
        map.put("AD=M", "AD");
        map.put("AMD=M", "AMD");

        String str = map.keySet().stream().collect(Collectors.joining(System.lineSeparator()));
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);

        for (String expected : map.values()) {
            parser.advance();
            Assert.assertEquals(expected, parser.dest());
        }
    }

    /**
     * @provided control cmd and advance invoked
     * @expected the comp content
     **/
    @Test
    public void test_comp_given_ctlCmd_when_getComp_then_parseCorrectDest() {
        Map<String, String> map = new HashMap<>();
        map.put("M=0", "0");
        map.put("M=1", "1");
        map.put("M=-1", "-1");
        map.put("M=D", "D");
        map.put("M=A", "A");
        map.put("M=!D", "!D");
        map.put("M=!A", "!A");
        map.put("M=-D", "-D");
        map.put("M=-A", "-A");
        map.put("M=D+1", "D+1");
        map.put("M=A+1", "A+1");
        map.put("M=D-1", "D-1");
        map.put("D-1;A", "D-1");
        map.put("M=A-1", "A-1");
        map.put("M=D+A;M", "D+A");
        map.put("M=D-A;A", "D-A");
        map.put("M=A-D;M", "A-D");
        map.put("D&A;A", "D&A");
        map.put("M=D|A;M", "D|A");
        map.put("M=M;D", "M");
        map.put("M=!M", "!M");
        map.put("M=-M", "-M");
        map.put("M=M+1", "M+1");
        map.put("M=M-1", "M-1");
        map.put("M=D+M", "D+M");
        map.put("M=D-M", "D-M");
        map.put("M=M-D", "M-D");
        map.put("M=D&M;M", "D&M");
        map.put("D|M;A", "D|M");

        String str = map.keySet().stream().collect(Collectors.joining(System.lineSeparator()));
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);
        for (String expected : map.values()) {
            parser.advance();
            Assert.assertEquals(expected, parser.comp());
        }
    }

    /**
     * @provided the control cmd
     * @expected the jump symbol
     **/
    @Test
    public void test_jump_given_ctlCmd_when_getJump_then_parseJump() {
        Map<String, String> map = new HashMap<>();
        map.put("M=0", "null");
        map.put("M=0;Jxx", "null");

        map.put("M=0;JGT", "JGT");
        map.put("M=D&M;JGT", "JGT");
        map.put("M;JGT", "JGT");

        map.put("M=0;JEQ", "JEQ");
        map.put("M=D&M;JEQ", "JEQ");
        map.put("M;JEQ", "JEQ");

        map.put("M=A;JGE", "JGE");
        map.put("M=D+1;JGE", "JGE");
        map.put("M;JGE", "JGE");

        map.put("M=A;JLT", "JLT");
        map.put("M=D+1;JLT", "JLT");
        map.put("M;JLT", "JLT");

        map.put("M=A;JNE", "JNE");
        map.put("M=D+1;JNE", "JNE");
        map.put("M;JNE", "JNE");

        map.put("M=A;JLE", "JLE");
        map.put("M=D+1;JLE", "JLE");
        map.put("M;JLE", "JLE");

        map.put("M=A;JMP", "JMP");
        map.put("M=D+1;JMP", "JMP");
        map.put("M;JMP", "JMP");

        String str = map.keySet().stream().collect(Collectors.joining(System.lineSeparator()));
        ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
        ParserImpl parser = new ParserImpl(ins);
        for (String expected : map.values()) {
            parser.advance();
            Assert.assertEquals(expected, parser.jump());
        }
    }
}