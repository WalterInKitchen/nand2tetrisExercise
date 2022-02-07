package org.example;

/**
 * The compilation engine
 */
public interface CompilationEngine {

    void compileClass();

    void compileClassVarDec();

    void compileSubroutine();

    void compileParameterList();

    void compileVarDec();

    void compileStatements();

    void compileDo();

    void compileLet();

    void compileWhile();

    void compileIf();

    void compileExpression();

    void compileTern();

    void compileExpressionList();
}
