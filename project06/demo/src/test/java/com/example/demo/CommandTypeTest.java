package com.example.demo;

import org.junit.Assert;
import org.junit.Test;

public class CommandTypeTest {
    /**
     * @provided the cmd with comments
     * @expected the comp part
     **/
    @Test
    public void test_parseComp_given_cmdWithComments_when_parse_then_returnCmdPart() {
        String cmd = "D=D-M            // D = first number - second number";
        String res = CommandType.CONTROL_CMD.parseComp(cmd);
        Assert.assertEquals("D-M", res);
    }
}