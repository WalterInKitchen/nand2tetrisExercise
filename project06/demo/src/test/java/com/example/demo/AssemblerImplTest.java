package com.example.demo;

import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AssemblerImplTest {
    /**
     * @provided the source code
     * @expected the target binary
     **/
    @Test
    public void test_assemble_given_cmdWithCompAndDestAndJump_when_convert_then_returnTargetBinary() throws IOException {
        String source = "M=1" + System.lineSeparator() + "M=0" + System.lineSeparator() + "D;JGT";
        String expected = "1110111111001000" + System.lineSeparator() + "1110101010001000" + System.lineSeparator() + "1110001100000001" + System.lineSeparator();

        AssemblerImpl assembler = new AssemblerImpl();
        ByteArrayInputStream ins = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        assembler.assemble(ins, outs);

        String res = outs.toString();
        Assert.assertEquals(expected, res);
    }

    /**
     * @provided the source with comments and empty line
     * @expected then binary without comments or empty line
     **/
    @Test
    public void test_assemble_given_cmdWithComment_when_convert_then_binaryWithoutComments() throws IOException {
        String source = "//this is comments" + System.lineSeparator() + "" + System.lineSeparator();
        String expected = "";

        AssemblerImpl assembler = new AssemblerImpl();
        ByteArrayInputStream ins = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        assembler.assemble(ins, outs);

        String res = outs.toString();
        Assert.assertEquals(expected, res);
    }

    /**
     * @provided the source with variable
     * @expected the binary
     **/
    @Test
    public void test_assemble_given_cmdWithVariable_when_convert_then_binary() throws IOException {
        String source = "@i" + System.lineSeparator()
                + "@2" + System.lineSeparator()
                + "M=1" + System.lineSeparator() + "@sum" + System.lineSeparator() + "M=0" + System.lineSeparator() + "@100" + System.lineSeparator() + "@sum";
        String expected = "0000000000010000" + System.lineSeparator()
                + "0000000000000010" + System.lineSeparator()
                + "1110111111001000" + System.lineSeparator() + "0000000000010001" + System.lineSeparator() + "1110101010001000" + System.lineSeparator() + "0000000001100100" + System.lineSeparator() + "0000000000010001" + System.lineSeparator();

        AssemblerImpl assembler = new AssemblerImpl();
        ByteArrayInputStream ins = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        assembler.assemble(ins, outs);

        String res = outs.toString();
        Assert.assertEquals(expected, res);
    }

    /**
     * @provided the source with label and goto cmd
     * @expected the binary
     **/
    @Test
    public void test_assemble_given_cmdWithLabel_when_convert_then_binary() throws IOException {
        String source = "@i" + System.lineSeparator() //0
                + "(LOOP)" + System.lineSeparator()
                + "M=1" + System.lineSeparator() //1
                + "@END" + System.lineSeparator() //2
                + "M=1" + System.lineSeparator() //3
                + "(END)" + System.lineSeparator()
                + "D;JGT" + System.lineSeparator() //4
                + "@LOOP" + System.lineSeparator(); //5
        String expected = "0000000000010000" + System.lineSeparator() //@i
                + "1110111111001000" + System.lineSeparator() //M=1
                + "0000000000000100" + System.lineSeparator() //@END
                + "1110111111001000" + System.lineSeparator() //M=1
                + "1110001100000001" + System.lineSeparator() //D;JGT
                + "0000000000000001" + System.lineSeparator() //@LOOP
                ;

        AssemblerImpl assembler = new AssemblerImpl();
        ByteArrayInputStream ins = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        assembler.assemble(ins, outs);

        String res = outs.toString();
        Assert.assertEquals(expected, res);
    }

    /**
     * @provided the source contains predefined symbol
     * @expected the binary
     **/
    @Test
    public void test_assemble_given_sourceWithPredefinedSymbol_when_convert_then_binary() throws IOException {
        String source = "@SP" + System.lineSeparator()
                + "@LCL" + System.lineSeparator()
                + "@ARG" + System.lineSeparator()
                + "@THIS" + System.lineSeparator()
                + "@THAT" + System.lineSeparator()
                + "@R0" + System.lineSeparator()
                + "@R1" + System.lineSeparator()
                + "@R2" + System.lineSeparator()
                + "@R3" + System.lineSeparator()
                + "@R4" + System.lineSeparator()
                + "@R5" + System.lineSeparator()
                + "@R6" + System.lineSeparator()
                + "@R7" + System.lineSeparator()
                + "@R8" + System.lineSeparator()
                + "@R9" + System.lineSeparator()
                + "@R10" + System.lineSeparator()
                + "@R11" + System.lineSeparator()
                + "@R12" + System.lineSeparator()
                + "@R13" + System.lineSeparator()
                + "@R14" + System.lineSeparator()
                + "@R15" + System.lineSeparator()
                + "@SCREEN" + System.lineSeparator()
                + "@KBD" + System.lineSeparator();
        String expected = "0000000000000000" + System.lineSeparator()
                + "0000000000000001" + System.lineSeparator()
                + "0000000000000010" + System.lineSeparator()
                + "0000000000000011" + System.lineSeparator()
                + "0000000000000100" + System.lineSeparator()
                + "0000000000000000" + System.lineSeparator() //R0
                + "0000000000000001" + System.lineSeparator()
                + "0000000000000010" + System.lineSeparator()
                + "0000000000000011" + System.lineSeparator()
                + "0000000000000100" + System.lineSeparator()
                + "0000000000000101" + System.lineSeparator()
                + "0000000000000110" + System.lineSeparator()
                + "0000000000000111" + System.lineSeparator()
                + "0000000000001000" + System.lineSeparator()
                + "0000000000001001" + System.lineSeparator()
                + "0000000000001010" + System.lineSeparator()
                + "0000000000001011" + System.lineSeparator()
                + "0000000000001100" + System.lineSeparator()
                + "0000000000001101" + System.lineSeparator()
                + "0000000000001110" + System.lineSeparator()
                + "0000000000001111" + System.lineSeparator()
                + "0100000000000000" + System.lineSeparator()
                + "0110000000000000" + System.lineSeparator();

        AssemblerImpl assembler = new AssemblerImpl();
        ByteArrayInputStream ins = new ByteArrayInputStream(source.getBytes());
        ByteArrayOutputStream outs = new ByteArrayOutputStream();
        assembler.assemble(ins, outs);

        String res = outs.toString();
        Assert.assertEquals(expected, res);
    }

    /**
     * @provided the source file ins
     * @expected the binary match cmp file
     **/
    @Test
    public void test_assemble_given_sourceFile_when_convert_then_binary() throws IOException {
        URL resource = getClass().getClassLoader().getResource("asm");
        assert resource != null;
        String path = resource.getPath();
        System.out.println(path);
        File[] files = new File(path).listFiles();
        List<String> sourceList = new ArrayList<>();
        assert files != null;
        for (File file : files) {
            if (!file.getName().endsWith(".asm")) {
                continue;
            }
            sourceList.add(file.getPath());
        }
        for (String file : sourceList) {
            ByteArrayOutputStream outs = new ByteArrayOutputStream();
            new AssemblerImpl().assemble(new FileInputStream(file), outs);
            String binary = outs.toString();
            outs.close();
            String cmpFile = FilenameUtils.removeExtension(file) + ".cmp";
            String expected = readStringFromFile(cmpFile);
            System.out.println("convert file:" + file);
            Assert.assertEquals(expected, binary);
        }
    }

    private String readStringFromFile(String cmpFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(cmpFile));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while (true) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            builder.append(line)
                    .append(System.lineSeparator());
        }
        reader.close();
        return builder.toString();
    }
}