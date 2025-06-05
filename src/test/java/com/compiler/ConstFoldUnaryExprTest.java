package com.compiler;

import org.junit.Test;

public class ConstFoldUnaryExprTest extends InterpreterDumpTestBase {
    @Test
    public void testUnaryMinus() throws Exception {
        String input = """
                {
                    PRINT -5;
                }
                """;
        String expectedRegex = "entry:\n%(\\d+) = LITERAL -5\nPRINT %\\1\n\n";
        testInterpreter(input, expectedRegex);
    }

    @Test
    public void testNotZero() throws Exception {
        String input = """
                {
                    PRINT !0;
                }
                """;
        String expectedRegex = "entry:\n%(\\d+) = LITERAL 1\nPRINT %\\1\n\n";
        testInterpreter(input, expectedRegex);

    }

}
