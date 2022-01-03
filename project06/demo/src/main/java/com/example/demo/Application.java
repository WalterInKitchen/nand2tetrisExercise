package com.example.demo;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new RuntimeException("no file provided");
        }
        File file = new File(args[0]);
        FileInputStream ins = new FileInputStream(file);

        File outF = new File(FilenameUtils.removeExtension(file.getAbsolutePath()) + ".hack");
        FileOutputStream fout = new FileOutputStream(outF);
        AssemblerImpl assembler = new AssemblerImpl();
        assembler.assemble(ins, fout);
    }
}