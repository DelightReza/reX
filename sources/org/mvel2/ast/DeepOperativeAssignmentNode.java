package org.mvel2.ast;

import java.lang.reflect.InvocationTargetException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.PropertyAccessor;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.math.MathProcessor;

/* loaded from: classes4.dex */
public class DeepOperativeAssignmentNode extends DeepAssignmentNode {
    private final int operation;

    public DeepOperativeAssignmentNode(char[] cArr, int i, int i2, int i3, int i4, String str, ParserContext parserContext) {
        super(cArr, i, i2, i3, i4, str, parserContext);
        this.operation = i4;
    }

    @Override // org.mvel2.ast.DeepAssignmentNode, org.mvel2.ast.ASTNode
    public Object getReducedValue(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) throws IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        Object objDoOperations = MathProcessor.doOperations(PropertyAccessor.get(this.property, obj, variableResolverFactory, obj2, this.pCtx), this.operation, MVEL.eval(this.expr, this.start, this.offset, obj, variableResolverFactory));
        PropertyAccessor.set(objDoOperations, variableResolverFactory, this.property, objDoOperations, this.pCtx);
        return objDoOperations;
    }
}
