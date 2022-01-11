package com.test.demo;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CodeWriterImplTest {
    ByteOutputStream outs;
    CodeWriterImpl codeWriter;

    @Before
    public void beforeEach() throws IOException {
        outs = new ByteOutputStream();
        codeWriter = new CodeWriterImpl(outs);
    }

    @After
    public void afterEach() throws IOException {
        codeWriter.close();
    }

    /**
     * 栈为空时push常量0,常量应该被放置在栈顶
     *
     * @provided push constant 0
     * @expected 栈顶
     **/
    @Test
    public void whenPushConstantZeroThenSetZeroToStackAddress() throws IOException {
        codeWriter.writePushPop("push", "constant", 0);
        codeWriter.close();
        String res = outs.toString();
        String expected = "@0" + System.lineSeparator()
                + "D=A" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=D" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=M+1" + System.lineSeparator();
        Assert.assertEquals(expected, res);
    }

    /**
     * 栈中有一个元素时,push一个常量1,应该放在栈的第二个位置
     *
     * @provided push constant 1
     * @expected 255位置
     **/
    @Test
    public void whenPushConstantOneAfterPushZeroThenSetOneToStack() throws IOException {
        codeWriter.writePushPop("push", "constant", 0);
        codeWriter.writePushPop("push", "constant", 1);
        codeWriter.close();
        String res = outs.toString();
        String expected = "@0" + System.lineSeparator()
                + "D=A" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=D" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=M+1" + System.lineSeparator()
                + "@1" + System.lineSeparator()
                + "D=A" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=D" + System.lineSeparator()
                + "@SP" + System.lineSeparator()
                + "M=M+1" + System.lineSeparator();
        Assert.assertEquals(expected, res);
    }

    /**
     * 当栈已满时,push一个常量会抛异常
     *
     * @provided a full stack
     * @expected RuntimeException
     **/
    @Test(expected = RuntimeException.class)
    public void whenStackIsFullThenPushOneThrowRuntimeException() throws IOException {
        boolean expected = false;
        try {
            int stackSize = 2047 - 256 + 1;
            for (int i = 0; i < stackSize; i++) {
                codeWriter.writePushPop("push", "constant", 1);
            }
            try {
                codeWriter.writePushPop("push", "constant", 1);
            } catch (RuntimeException exc) {
                Assert.assertEquals("stack overflow", exc.getMessage());
                expected = true;
                throw exc;
            }
        } finally {
            Assert.assertTrue(expected);
        }
    }

    /**
     * 空栈，push局部变量应该放在栈顶
     *
     * @provided empty stack
     * @expected variable in the top of stack
     **/
    @Test
    public void whenStackIsEmptyThenPushLocalWillSetIntoTopStack() throws IOException {
        codeWriter.writePushPop("push", "local", 10);
        codeWriter.close();
        String res = outs.toString();
        String expected = "@10" + System.lineSeparator() +
                "D=A" + System.lineSeparator() +
                "@LCL" + System.lineSeparator() +
                "A=D+M" + System.lineSeparator() +  //LCL+10
                "D=M" + System.lineSeparator() +
                "@SP" + System.lineSeparator() +
                "M=D" + System.lineSeparator() +
                "@SP" + System.lineSeparator() +
                "M=M+1" + System.lineSeparator();
        Assert.assertEquals(expected, res);
    }


}