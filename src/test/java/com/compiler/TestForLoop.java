package com.compiler;

import org.junit.Test;

public class TestForLoop extends InterpreterTestBase {

    @Test
    public void testForLoop1() throws Exception {
        String code = """
                {
                  DECLARE index;
                  DECLARE sum;
                  index = 0;
                  sum = 0;
                  FOR(index = 10; index; index = index - 1;) {
                    sum = sum + index;
                  };
                  PRINT sum;
                }
                """;
        testInterpreter(code, "55\n");
    }

    @Test
    public void testForLoop2() throws Exception {
        String code = """
                {
                  DECLARE index;
                  DECLARE sum;
                  index = 64;
                  sum = 0;
                  FOR(index = index * 2; index; index = index / 2;) {
                    sum = sum + index;
                  };
                  PRINT sum;
                }
                """;
        testInterpreter(code, "255\n");
    }

    @Test
    public void testForLoop3() throws Exception {
        String code = """
                {
                  DECLARE index;
                  DECLARE sum;
                  index = 10 - 20;
                  sum = 0;
                  FOR(PRINT index; index; index = index + 2;) {
                    sum = sum + index;
                  };
                  PRINT sum;
                }
                """;
        testInterpreter(code, "-10\n-30\n");
    }
}
