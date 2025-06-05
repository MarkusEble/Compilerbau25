package com.compiler;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

public class InterpreterDumpTestBase {
    protected void testInterpreter(String program, String expectedOutputRegex) throws Exception {
        // create out stream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // compile program
        CompileEnv compileEnv = new CompileEnv(program, false);
        compileEnv.compile();
        // execute
        compileEnv.dump(os);
        // check result
        String output = os.toString();
        // assert that the regex matches the output
        assert(output.matches(expectedOutputRegex));
    }

}
