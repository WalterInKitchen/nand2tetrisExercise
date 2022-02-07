package org.example;

import java.io.Writer;

public class CompilationEngineImpl implements CompilationEngine {
    private final Writer writer;

    public CompilationEngineImpl(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void compileClass() {
    }

    @Override
    public void compileClassVarDec() {

    }

    @Override
    public void compileSubroutine() {

    }

    @Override
    public void compileParameterList() {

    }

    @Override
    public void compileVarDec() {

    }

    @Override
    public void compileStatements() {

    }

    @Override
    public void compileDo() {

    }

    @Override
    public void compileLet() {

    }

    @Override
    public void compileWhile() {

    }

    @Override
    public void compileIf() {

    }

    @Override
    public void compileExpression() {

    }

    @Override
    public void compileTern() {

    }

    @Override
    public void compileExpressionList() {

    }
}
