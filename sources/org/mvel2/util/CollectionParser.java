package org.mvel2.util;

import org.mvel2.CompileException;
import org.mvel2.DataConversion;
import org.mvel2.ParserContext;
import org.mvel2.compiler.ExecutableStatement;

/* loaded from: classes4.dex */
public class CollectionParser {
    public static final int ARRAY = 1;
    private static final Object[] EMPTY_ARRAY = new Object[0];
    public static final int LIST = 0;
    public static final int MAP = 2;
    private Class colType;
    private int cursor;
    private int end;
    private ParserContext pCtx;
    private char[] property;
    private int start;
    private int type;

    public CollectionParser() {
    }

    public CollectionParser(int i) {
        this.type = i;
    }

    public Object parseCollection(char[] cArr, int i, int i2, boolean z, ParserContext parserContext) {
        this.property = cArr;
        this.pCtx = parserContext;
        this.end = i2 + i;
        while (i < this.end && ParseTools.isWhitespace(cArr[i])) {
            i++;
        }
        this.cursor = i;
        this.start = i;
        return parseCollection(z);
    }

    public Object parseCollection(char[] cArr, int i, int i2, boolean z, Class cls, ParserContext parserContext) {
        if (cls != null) {
            this.colType = ParseTools.getBaseComponentType(cls);
        }
        this.property = cArr;
        this.end = i2 + i;
        while (i < this.end && ParseTools.isWhitespace(cArr[i])) {
            i++;
        }
        this.cursor = i;
        this.start = i;
        this.pCtx = parserContext;
        return parseCollection(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00b0  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0149  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object parseCollection(boolean r22) {
        /*
            Method dump skipped, instructions count: 430
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.CollectionParser.parseCollection(boolean):java.lang.Object");
    }

    private void subCompile(int i, int i2) {
        if (this.colType == null) {
            ParseTools.subCompileExpression(this.property, i, i2, this.pCtx);
            return;
        }
        Class knownEgressType = ((ExecutableStatement) ParseTools.subCompileExpression(this.property, i, i2, this.pCtx)).getKnownEgressType();
        if (knownEgressType == null || ReflectionUtil.isAssignableFrom(this.colType, knownEgressType)) {
            return;
        }
        if (isStrongType() || !DataConversion.canConvert(knownEgressType, this.colType)) {
            throw new CompileException("expected type: " + this.colType.getName() + "; but found: " + knownEgressType.getName(), this.property, this.cursor);
        }
    }

    private boolean isStrongType() {
        ParserContext parserContext = this.pCtx;
        return parserContext != null && parserContext.isStrongTyping();
    }

    public int getCursor() {
        return this.cursor;
    }
}
