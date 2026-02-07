package org.mvel2.templates.res;

import java.io.Serializable;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;

/* loaded from: classes4.dex */
public class CompiledExpressionNode extends ExpressionNode {

    /* renamed from: ce */
    private Serializable f1426ce;

    public CompiledExpressionNode(int i, String str, char[] cArr, int i2, int i3, ParserContext parserContext) {
        this.begin = i;
        this.name = str;
        this.contents = cArr;
        this.cStart = i2;
        int i4 = i3 - 1;
        this.cEnd = i4;
        this.end = i3;
        this.f1426ce = MVEL.compileExpression(cArr, i2, i4 - i2, parserContext);
    }

    @Override // org.mvel2.templates.res.ExpressionNode, org.mvel2.templates.res.Node
    public Object eval(TemplateRuntime templateRuntime, TemplateOutputStream templateOutputStream, Object obj, VariableResolverFactory variableResolverFactory) {
        templateOutputStream.append(String.valueOf(MVEL.executeExpression(this.f1426ce, obj, variableResolverFactory)));
        Node node = this.next;
        if (node != null) {
            return node.eval(templateRuntime, templateOutputStream, obj, variableResolverFactory);
        }
        return null;
    }

    @Override // org.mvel2.templates.res.ExpressionNode
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ExpressionNode:");
        sb.append(this.name);
        sb.append("{");
        char[] cArr = this.contents;
        sb.append(cArr == null ? "" : new String(cArr));
        sb.append("} (start=");
        sb.append(this.begin);
        sb.append(";end=");
        sb.append(this.end);
        sb.append(")");
        return sb.toString();
    }
}
