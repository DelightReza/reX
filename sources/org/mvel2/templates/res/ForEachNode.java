package org.mvel2.templates.res;

import java.util.HashMap;
import java.util.Iterator;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.TemplateRuntimeError;
import org.mvel2.templates.util.ArrayIterator;
import org.mvel2.templates.util.TemplateOutputStream;

/* loaded from: classes4.dex */
public class ForEachNode extends Node {
    private String[] expression;
    private String[] item;
    public Node nestedNode;
    private char[] sepExpr;

    public ForEachNode(int i, String str, char[] cArr, int i2, int i3) {
        super(i, str, cArr, i2, i3);
        configure();
    }

    public Node getNestedNode() {
        return this.nestedNode;
    }

    public void setNestedNode(Node node) {
        this.nestedNode = node;
    }

    @Override // org.mvel2.templates.res.Node
    public boolean demarcate(Node node, char[] cArr) {
        this.nestedNode = this.next;
        this.next = this.terminus;
        char[] contents = node.getContents();
        this.sepExpr = contents;
        if (contents.length != 0) {
            return false;
        }
        this.sepExpr = null;
        return false;
    }

    @Override // org.mvel2.templates.res.Node
    public Object eval(TemplateRuntime templateRuntime, TemplateOutputStream templateOutputStream, Object obj, VariableResolverFactory variableResolverFactory) {
        int length = this.item.length;
        Iterator[] itArr = new Iterator[length];
        for (int i = 0; i < length; i++) {
            Object objEval = MVEL.eval(this.expression[i], obj, variableResolverFactory);
            if (objEval instanceof Iterable) {
                itArr[i] = ((Iterable) objEval).iterator();
            } else if (objEval instanceof Object[]) {
                itArr[i] = new ArrayIterator((Object[]) objEval);
            } else {
                throw new TemplateRuntimeError("cannot iterate object type: " + objEval.getClass().getName());
            }
        }
        HashMap map = new HashMap();
        MapVariableResolverFactory mapVariableResolverFactory = new MapVariableResolverFactory(map, variableResolverFactory);
        int i2 = length;
        while (true) {
            for (int i3 = 0; i3 < length; i3++) {
                if (!itArr[i3].hasNext()) {
                    i2--;
                    map.put(this.item[i3], "");
                } else {
                    map.put(this.item[i3], itArr[i3].next());
                }
            }
            if (i2 == 0) {
                break;
            }
            this.nestedNode.eval(templateRuntime, templateOutputStream, obj, mapVariableResolverFactory);
            if (this.sepExpr != null) {
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        break;
                    }
                    if (itArr[i4].hasNext()) {
                        templateOutputStream.append(String.valueOf(MVEL.eval(this.sepExpr, obj, variableResolverFactory)));
                        break;
                    }
                    i4++;
                }
            }
        }
        Node node = this.next;
        if (node != null) {
            return node.eval(templateRuntime, templateOutputStream, obj, variableResolverFactory);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void configure() {
        /*
            r9 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            int r2 = r9.cStart
            r3 = r2
        Ld:
            int r4 = r9.cEnd
            if (r2 >= r4) goto L6a
            char[] r4 = r9.contents
            char r5 = r4[r2]
            r6 = 34
            if (r5 == r6) goto L63
            r6 = 44
            if (r5 == r6) goto L3e
            r6 = 58
            if (r5 == r6) goto L32
            r6 = 91
            if (r5 == r6) goto L63
            r6 = 123(0x7b, float:1.72E-43)
            if (r5 == r6) goto L63
            r6 = 39
            if (r5 == r6) goto L63
            r6 = 40
            if (r5 == r6) goto L63
            goto L67
        L32:
            int r5 = r2 - r3
            java.lang.String r3 = org.mvel2.util.ParseTools.createStringTrimmed(r4, r3, r5)
            r0.add(r3)
        L3b:
            int r3 = r2 + 1
            goto L67
        L3e:
            int r4 = r1.size()
            int r5 = r0.size()
            int r5 = r5 + (-1)
            if (r4 != r5) goto L56
            char[] r4 = r9.contents
            int r5 = r2 - r3
            java.lang.String r3 = org.mvel2.util.ParseTools.createStringTrimmed(r4, r3, r5)
            r1.add(r3)
            goto L3b
        L56:
            org.mvel2.CompileException r0 = new org.mvel2.CompileException
            char[] r1 = r9.contents
            int r3 = r9.cStart
            int r3 = r3 + r2
            java.lang.String r2 = "unexpected character ',' in foreach tag"
            r0.<init>(r2, r1, r3)
            throw r0
        L63:
            int r2 = org.mvel2.util.ParseTools.balancedCapture(r4, r2, r5)
        L67:
            int r2 = r2 + 1
            goto Ld
        L6a:
            if (r3 >= r4) goto L91
            int r2 = r1.size()
            int r4 = r0.size()
            int r4 = r4 + (-1)
            if (r2 != r4) goto L85
            char[] r2 = r9.contents
            int r4 = r9.cEnd
            int r4 = r4 - r3
            java.lang.String r2 = org.mvel2.util.ParseTools.createStringTrimmed(r2, r3, r4)
            r1.add(r2)
            goto L91
        L85:
            org.mvel2.CompileException r0 = new org.mvel2.CompileException
            char[] r1 = r9.contents
            int r2 = r9.cEnd
            java.lang.String r3 = "expected character ':' in foreach tag"
            r0.<init>(r3, r1, r2)
            throw r0
        L91:
            int r2 = r0.size()
            java.lang.String[] r2 = new java.lang.String[r2]
            r9.item = r2
            int r2 = r0.size()
            r3 = 0
            r4 = 0
            r5 = 0
        La0:
            if (r5 >= r2) goto Lb2
            java.lang.Object r6 = r0.get(r5)
            int r5 = r5 + 1
            java.lang.String r6 = (java.lang.String) r6
            java.lang.String[] r7 = r9.item
            int r8 = r4 + 1
            r7[r4] = r6
            r4 = r8
            goto La0
        Lb2:
            int r0 = r1.size()
            java.lang.String[] r0 = new java.lang.String[r0]
            r9.expression = r0
            int r0 = r1.size()
            r2 = 0
        Lbf:
            if (r2 >= r0) goto Ld1
            java.lang.Object r4 = r1.get(r2)
            int r2 = r2 + 1
            java.lang.String r4 = (java.lang.String) r4
            java.lang.String[] r5 = r9.expression
            int r6 = r3 + 1
            r5[r3] = r4
            r3 = r6
            goto Lbf
        Ld1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.templates.res.ForEachNode.configure():void");
    }
}
