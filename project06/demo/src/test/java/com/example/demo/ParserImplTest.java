package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;

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
}