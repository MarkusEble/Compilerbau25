package com.compiler;

import com.compiler.ast.ASTExprNode;
import com.compiler.ast.ASTStmtNode;
import com.compiler.instr.InstrCondJump;
import com.compiler.instr.InstrJump;

import java.io.OutputStreamWriter;

public class ASTForLoopStmtNode extends ASTStmtNode {
    private ASTStmtNode m_initStmt;
    private ASTExprNode m_predicate;
    private ASTStmtNode m_updateStmt;
    private ASTStmtNode m_body;

    public ASTForLoopStmtNode(ASTStmtNode initStmt, ASTExprNode predicate, ASTStmtNode updateStmt, ASTStmtNode body) {
        m_initStmt = initStmt;
        m_predicate = predicate;
        m_updateStmt = updateStmt;
        m_body = body;
    }

    @Override
    public void execute(OutputStreamWriter out) {
        m_initStmt.execute(out);
        while (m_predicate.eval() != 0) {
            m_body.execute(out);
            m_updateStmt.execute(out);
        }
    }

    @Override
    public void codegen(CompileEnvIntf env) {
        InstrBlock initBlock = env.createBlock("for_init");
        InstrBlock predicateBlock = env.createBlock("for_predicate");
        InstrBlock bodyBlock = env.createBlock("for_body");
        InstrBlock updateBlock = env.createBlock("for_update");
        InstrBlock exitBlock = env.createBlock("for_exit");

        env.addInstr(new InstrJump(initBlock));
        env.setCurrentBlock(initBlock);
        m_initStmt.codegen(env);
        env.addInstr(new InstrJump(predicateBlock));

        env.setCurrentBlock(predicateBlock);
        InstrIntf predicateInstr = m_predicate.codegen(env);
        env.addInstr(predicateInstr);
        env.addInstr(new InstrCondJump(predicateInstr, bodyBlock, exitBlock));

        env.setCurrentBlock(bodyBlock);
        m_body.codegen(env);
        env.addInstr(new InstrJump(updateBlock));

        env.setCurrentBlock(updateBlock);
        m_updateStmt.codegen(env);
        env.addInstr(new InstrJump(predicateBlock));

        env.setCurrentBlock(exitBlock);

    }

    @Override
    public void print(OutputStreamWriter outStream, String indent) throws Exception {
        outStream.write(indent + "FOR:\n" );

        outStream.write(indent + "\t" + "initStmt: \n");
        m_initStmt.print(outStream, indent + "\t\t");
        outStream.write("\n");

        outStream.write(indent + "\t" + "predicate: \n");
        m_predicate.print(outStream, indent + "\t\t");
        outStream.write("\n");

        outStream.write(indent + "\t" + "updateStmt: \n");
        m_updateStmt.print(outStream, indent + "\t\t");
        outStream.write("\n");

        outStream.write(indent + "\t" + "body: \n");
        m_body.print(outStream, indent + "\t\t");
        outStream.write("\n");
    }
}
