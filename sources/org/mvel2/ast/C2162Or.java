package org.mvel2.ast;

import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.CompilerTools;

/* renamed from: org.mvel2.ast.Or */
/* loaded from: classes4.dex */
public class C2162Or extends BooleanNode {
    public C2162Or(ASTNode aSTNode, ASTNode aSTNode2, boolean z, ParserContext parserContext) {
        super(parserContext);
        this.left = aSTNode;
        CompilerTools.expectType(parserContext, aSTNode, Boolean.class, z);
        this.right = aSTNode2;
        CompilerTools.expectType(parserContext, aSTNode2, Boolean.class, z);
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValueAccelerated(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        return Boolean.valueOf(((Boolean) this.left.getReducedValueAccelerated(obj, obj2, variableResolverFactory)).booleanValue() || ((Boolean) this.right.getReducedValueAccelerated(obj, obj2, variableResolverFactory)).booleanValue());
    }

    @Override // org.mvel2.ast.ASTNode
    public Object getReducedValue(Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        throw new RuntimeException("improper use of AST element");
    }

    @Override // org.mvel2.ast.BooleanNode
    public void setRightMost(ASTNode aSTNode) {
        C2162Or c2162Or = this;
        while (true) {
            ASTNode aSTNode2 = c2162Or.right;
            if (aSTNode2 == null || !(aSTNode2 instanceof C2162Or)) {
                break;
            } else {
                c2162Or = (C2162Or) aSTNode2;
            }
        }
        c2162Or.right = aSTNode;
    }

    @Override // org.mvel2.ast.BooleanNode
    public ASTNode getRightMost() {
        ASTNode aSTNode;
        C2162Or c2162Or = this;
        while (true) {
            aSTNode = c2162Or.right;
            if (aSTNode == null || !(aSTNode instanceof C2162Or)) {
                break;
            }
            c2162Or = (C2162Or) aSTNode;
        }
        return aSTNode;
    }

    @Override // org.mvel2.ast.ASTNode
    public String toString() {
        return "(" + this.left.toString() + " || " + this.right.toString() + ")";
    }

    @Override // org.mvel2.ast.ASTNode
    public Class getEgressType() {
        return Boolean.class;
    }
}
