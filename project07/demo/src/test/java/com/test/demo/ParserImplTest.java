package com.test.demo;


import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserImplTest {
    /**
     * @provided empty stream
     * @expected return false
     **/
    @Test
    public void test_hasMoreCommands_given_emptyStream_when_invoke_then_returnFalse() {
        ByteArrayInputStream ins = new ByteArrayInputStream(new byte[0]);
        ParserImpl parser = new ParserImpl(ins);
        Assert.assertFalse(parser.hasMoreCommands());
    }

    /**
     * @provided empty stream
     * @expected return true
     **/
    @Test
    public void test_hasMoreCommands_given_noneEmptyStream_when_invoke_then_returnTrue() {
        ByteArrayInputStream ins = new ByteArrayInputStream("abc".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        Assert.assertTrue(parser.hasMoreCommands());
    }

    /**
     * @provided hasMoreCommands return false
     * @expected RuntimeException
     **/
    @Test(expected = RuntimeException.class)
    public void test_advance_given_noMoreCommands_when_invoke_then_RuntimeException() {
        ByteArrayInputStream ins = new ByteArrayInputStream(new byte[0]);
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
    }

    /**
     * @provided hasMoreCommands return true
     * @expected without exceptin
     **/
    @Test
    public void test_advance_given_hasMoreCommands_when_invoke_then_withoutException() {
        ByteArrayInputStream ins = new ByteArrayInputStream("abc".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
    }

    /**
     * @provided the arithmetic cmd
     * @expected arithmetic cmd type
     **/
    @Test
    public void test_commandType_given_arithmeticCmd_when_invoke_then_arithmeticCmd() {
        List<String> cmds = Arrays.asList(" add ", " sub ", " neg ", " eq ", " gt ", " lt ", " and ", " or ", " not ");
        for (String cmd : cmds) {
            ByteArrayInputStream ins = new ByteArrayInputStream(cmd.getBytes());
            ParserImpl parser = new ParserImpl(ins);
            parser.advance();
            Assert.assertEquals(CommandType.ARITHMETIC, parser.commandType());
        }
    }

    /**
     * @provided push statement
     * @expected push command type
     **/
    @Test
    public void test_commandType_given_pushCmd_when_invoke_then_pushCmdType() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" push argument 0".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.PUSH, parser.commandType());
    }

    /**
     * @provided pop statement
     * @expected pop command type
     **/
    @Test
    public void test_commandType_given_popCmd_when_invoke_then_popCmdType() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" pop pointer 0".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.POP, parser.commandType());
    }

    /**
     * @provided label statement
     * @expected label command type
     **/
    @Test
    public void test_commandType_given_labelCmd_when_invoke_then_labelCmdType() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" label xxxxx".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.LABEL, parser.commandType());
    }

    /**
     * @provided goto statement
     * @expected goto cmd type
     **/
    @Test
    public void test_commandType_given_gotoCmd_when_invoke_then_gotoCmdType() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" goto symbol".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.GOTO, parser.commandType());
    }

    /**
     * @provided if statement
     * @expected if cmd type
     **/
    @Test
    public void test_commandType_given_ifCmd_when_invoke_then_ifCmdType() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" if-goto symbol".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.IF, parser.commandType());
    }

    /**
     * @provided function statement
     * @expected function type
     **/
    @Test
    public void test_commandType_given_functionCmd_when_invoke_then_functionCmd() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" function name args".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.FUNCTION, parser.commandType());
    }

    /**
     * @provided return statement
     * @expected return cmd type
     **/
    @Test
    public void test_commandType_given_returnCmd_when_invoke_then_returnCmd() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" return".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.RETURN, parser.commandType());
    }

    /**
     * @provided call statement
     * @expected return call cmd type
     **/
    @Test
    public void test_commandType_given_callCmd_when_invoke_then_callCmd() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" call name arg".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        Assert.assertEquals(CommandType.CALL, parser.commandType());
    }

    /**
     * @provided unknown cmd
     * @expected RuntimeException
     **/
    @Test(expected = RuntimeException.class)
    public void test_commandType_given_unknownCmd_when_invoke_then_RuntimeException() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" ".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        parser.commandType();
    }

    /**
     * @provided the arithmetic cmd
     * @expected return cmd itself
     **/
    @Test
    public void test_arg1_given_arithmeticCmd_when_invoke_then_returnCmd() {
        List<String> cmds = Arrays.asList(" add ", " sub ", " neg ", " eq ", " gt ", " lt ", " and ", " or ", " not ");
        for (String cmd : cmds) {
            ByteArrayInputStream ins = new ByteArrayInputStream(cmd.getBytes());
            ParserImpl parser = new ParserImpl(ins);
            parser.advance();
            Assert.assertEquals(cmd.trim(), parser.arg1());
        }
    }

    /**
     * @provided the return cmd
     * @expected RuntimeException
     **/
    @Test(expected = RuntimeException.class)
    public void test_arg1_given_returnCmd_when_invoke_then_runtimeException() {
        ByteArrayInputStream ins = new ByteArrayInputStream(" return".getBytes());
        ParserImpl parser = new ParserImpl(ins);
        parser.advance();
        try {
            parser.arg1();
        } catch (RuntimeException exception) {
            Assert.assertEquals("cmd not support arg1", exception.getMessage());
            throw exception;
        }
    }

    /**
     * @provided push cmd
     * @expected arg1
     **/
    @Test
    public void test_arg1_given_cmd_when_invoke_then_returnArg1() {
        Map<String, String> map = new HashMap<String, String>() {{
            put(" push constant 2", "constant");
            put(" pop pointer 2", "pointer");
            put(" label  loop", "loop");
            put(" goto symbol", "symbol");
            put(" if-goto symbol", "symbol");
            put(" function name args", "name");
            put(" call name args", "name");
        }};

        for (Map.Entry<String, String> entry : map.entrySet()) {
            ByteArrayInputStream ins = new ByteArrayInputStream(entry.getKey().getBytes());
            ParserImpl parser = new ParserImpl(ins);
            parser.advance();
            Assert.assertEquals(entry.getValue(), parser.arg1());
        }
    }

    /**
     * @provided push cmd
     * @expected the arg2
     **/
    @Test
    public void test_arg2_given_pushCmd_when_invoke_then_returnArg2() {
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put(" push constant 2", 2);
            put(" pop pointer 3", 3);
            put(" function name 1", 1);
            put(" call name 4", 4);
        }};

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            ByteArrayInputStream ins = new ByteArrayInputStream(entry.getKey().getBytes());
            ParserImpl parser = new ParserImpl(ins);
            parser.advance();
            Assert.assertEquals(entry.getValue(), parser.arg2());
        }
    }

    /**
     * @provided other cmd
     * @expected runtimeException
     **/
    @Test
    public void test_arg2_given_otherCmd_when_invoke_then_runtimeException() {
        List<String> cmds = Arrays.asList("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not", "label loop", "goto loop", "if-goto end");

        boolean asserted = false;
        for (String cmd : cmds) {
            asserted = false;
            ByteArrayInputStream ins = new ByteArrayInputStream(cmd.getBytes());
            ParserImpl parser = new ParserImpl(ins);
            parser.advance();
            try {
                parser.arg2();
            } catch (RuntimeException exception) {
                Assert.assertEquals("cmd not support arg2", exception.getMessage());
                asserted = true;
            }
            Assert.assertTrue(asserted);
        }
    }
}