package org.mvel2.ast;

import java.io.Serializable;
import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.compiler.ExecutableStatement;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;

/* loaded from: classes4.dex */
public class WithNode extends BlockNode implements NestedStatement {
    protected String nestParm;
    protected ParmValuePair[] withExpressions;

    public WithNode(char[] cArr, int i, int i2, int i3, int i4, int i5, ParserContext parserContext) {
        super(parserContext);
        this.expr = cArr;
        this.start = i;
        this.offset = i2;
        this.nestParm = ParseTools.createStringTrimmed(cArr, i, i2);
        this.blockStart = i3;
        this.blockOffset = i4;
        if ((i5 & 16) != 0) {
            parserContext.setBlockSymbols(true);
            ExecutableStatement executableStatement = (ExecutableStatement) ParseTools.subCompileExpression(cArr, i, i2, parserContext);
            this.compiledBlock = executableStatement;
            Class knownEgressType = executableStatement.getKnownEgressType();
            this.egressType = knownEgressType;
            this.withExpressions = compileWithExpressions(cArr, i3, i4, this.nestParm, knownEgressType, parserContext);
            parserContext.setBlockSymbols(false);
        }
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValueAccelerated(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        Object value = this.compiledBlock.getValue(obj, obj2, variableResolverFactory);
        if (value == null) {
            throw new CompileException("with-block against null pointer", this.expr, this.start);
        }
        for (ParmValuePair parmValuePair : this.withExpressions) {
            parmValuePair.eval(value, variableResolverFactory);
        }
        return value;
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValue(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        String str = this.nestParm;
        char[] cArr = this.expr;
        int i = this.blockStart;
        int i2 = this.blockOffset;
        Object objEval = MVEL.eval(cArr, this.start, this.offset, obj, variableResolverFactory);
        ParseTools.parseWithExpressions(str, cArr, i, i2, objEval, variableResolverFactory);
        return objEval;
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0165, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01b7 A[Catch: CompileException -> 0x019f, TryCatch #1 {CompileException -> 0x019f, blocks: (B:93:0x0173, B:97:0x017c, B:99:0x0181, B:103:0x01a8, B:102:0x01a1, B:104:0x01af, B:105:0x01b4, B:107:0x01b7, B:109:0x01d5, B:108:0x01be), top: B:117:0x0173 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01be A[Catch: CompileException -> 0x019f, TryCatch #1 {CompileException -> 0x019f, blocks: (B:93:0x0173, B:97:0x017c, B:99:0x0181, B:103:0x01a8, B:102:0x01a1, B:104:0x01af, B:105:0x01b4, B:107:0x01b7, B:109:0x01d5, B:108:0x01be), top: B:117:0x0173 }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x011d A[PHI: r9
      0x011d: PHI (r9v8 int) = (r9v6 int), (r9v9 int) binds: [B:75:0x0145, B:62:0x011b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.mvel2.ast.WithNode.ParmValuePair[] compileWithExpressions(char[] r18, int r19, int r20, java.lang.String r21, java.lang.Class r22, org.mvel2.ParserContext r23) {
        /*
            Method dump skipped, instructions count: 512
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.ast.WithNode.compileWithExpressions(char[], int, int, java.lang.String, java.lang.Class, org.mvel2.ParserContext):org.mvel2.ast.WithNode$ParmValuePair[]");
    }

    @Override // org.mvel2.ast.NestedStatement
    public ExecutableStatement getNestedStatement() {
        return this.compiledBlock;
    }

    public ParmValuePair[] getWithExpressions() {
        return this.withExpressions;
    }

    public static final class ParmValuePair implements Serializable {
        private Serializable setExpression;
        private ExecutableStatement statement;

        public ParmValuePair(String str, ExecutableStatement executableStatement, Class cls, ParserContext parserContext) {
            if (str != null && str.length() != 0) {
                this.setExpression = MVEL.compileSetExpression(str, cls != null ? PropertyTools.getReturnType(cls, str, parserContext) : Object.class, parserContext);
            }
            this.statement = executableStatement;
        }

        public Serializable getSetExpression() {
            return this.setExpression;
        }

        public ExecutableStatement getStatement() {
            return this.statement;
        }

        public void eval(Object obj, VariableResolverFactory variableResolverFactory) {
            Serializable serializable = this.setExpression;
            if (serializable == null) {
                this.statement.getValue(obj, variableResolverFactory);
            } else {
                MVEL.executeSetExpression(serializable, obj, variableResolverFactory, this.statement.getValue(obj, variableResolverFactory));
            }
        }
    }
}
