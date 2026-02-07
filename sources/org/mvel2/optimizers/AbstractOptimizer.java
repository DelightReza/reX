package org.mvel2.optimizers;

import org.mvel2.CompileException;
import org.mvel2.ParserContext;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.util.ParseTools;

/* loaded from: classes4.dex */
public class AbstractOptimizer extends AbstractParser {
    protected static final int BEAN = 0;
    protected static final int COL = 2;
    protected static final int METH = 1;
    protected static final int WITH = 3;
    protected boolean collection;
    protected Class currType;
    protected boolean nullSafe;
    protected boolean staticAccess;
    protected int tkStart;

    protected AbstractOptimizer() {
        this.collection = false;
        this.nullSafe = false;
        this.currType = null;
        this.staticAccess = false;
    }

    protected AbstractOptimizer(ParserContext parserContext) {
        super(parserContext);
        this.collection = false;
        this.nullSafe = false;
        this.currType = null;
        this.staticAccess = false;
    }

    /* JADX WARN: Removed duplicated region for block: B:144:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected java.lang.Object tryStaticAccess() {
        /*
            Method dump skipped, instructions count: 334
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.optimizers.AbstractOptimizer.tryStaticAccess():java.lang.Object");
    }

    protected int nextSubToken() {
        int i;
        skipWhitespace();
        this.nullSafe = false;
        char[] cArr = this.expr;
        int i2 = this.cursor;
        this.tkStart = i2;
        char c = cArr[i2];
        if (c != '.') {
            if (c != '?') {
                if (c == '[') {
                    return 2;
                }
                if (c == '{' && cArr[i2 - 1] == '.') {
                    return 3;
                }
            } else if (this.start == i2) {
                this.tkStart = i2 + 1;
                this.cursor = i2 + 1;
                this.nullSafe = true;
            }
        } else if (this.start + 1 != this.end) {
            int i3 = i2 + 1;
            this.tkStart = i3;
            this.cursor = i3;
            char c2 = cArr[i3];
            if (c2 == '?') {
                skipWhitespace();
                int i4 = this.tkStart + 1;
                this.tkStart = i4;
                this.cursor = i4;
                if (i4 == this.end) {
                    throw new CompileException("unexpected end of statement", this.expr, this.start);
                }
                this.nullSafe = true;
                this.fields = -1;
            } else {
                if (c2 == '{') {
                    return 3;
                }
                if (ParseTools.isWhitespace(c2)) {
                    skipWhitespace();
                    this.tkStart = this.cursor;
                }
            }
        } else {
            throw new CompileException("unexpected end of statement", this.expr, this.start);
        }
        do {
            i = this.cursor + 1;
            this.cursor = i;
            if (i >= this.end) {
                break;
            }
        } while (ParseTools.isIdentifierPart(this.expr[i]));
        skipWhitespace();
        int i5 = this.cursor;
        if (i5 >= this.end) {
            return 0;
        }
        char c3 = this.expr[i5];
        if (c3 != '(') {
            return c3 != '[' ? 0 : 2;
        }
        return 1;
    }

    protected String capture() {
        char[] cArr = this.expr;
        int iTrimRight = trimRight(this.tkStart);
        this.tkStart = iTrimRight;
        return new String(cArr, iTrimRight, trimLeft(this.cursor) - this.tkStart);
    }

    protected void whiteSpaceSkip() {
        if (this.cursor < this.length) {
            while (ParseTools.isWhitespace(this.expr[this.cursor])) {
                int i = this.cursor + 1;
                this.cursor = i;
                if (i == this.length) {
                    return;
                }
            }
        }
    }

    protected boolean scanTo(char c) {
        while (true) {
            int i = this.cursor;
            int i2 = this.end;
            if (i >= i2) {
                return true;
            }
            char[] cArr = this.expr;
            char c2 = cArr[i];
            if (c2 == '\"' || c2 == '\'') {
                this.cursor = ParseTools.captureStringLiteral(c2, cArr, i, i2);
            }
            char[] cArr2 = this.expr;
            int i3 = this.cursor;
            if (cArr2[i3] == c) {
                return false;
            }
            this.cursor = i3 + 1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int findLastUnion() {
        /*
            r7 = this;
            int r0 = r7.start
            int r1 = r7.length
            int r0 = r0 + r1
            r1 = 1
            int r0 = r0 - r1
            r2 = -1
            r3 = 0
            r4 = -1
        La:
            int r5 = r7.start
            if (r0 == r5) goto L3b
            char[] r5 = r7.expr
            char r5 = r5[r0]
            r6 = 46
            if (r5 == r6) goto L32
            r6 = 91
            if (r5 == r6) goto L2a
            r6 = 93
            if (r5 == r6) goto L27
            r6 = 123(0x7b, float:1.72E-43)
            if (r5 == r6) goto L2a
            r6 = 125(0x7d, float:1.75E-43)
            if (r5 == r6) goto L27
            goto L35
        L27:
            int r3 = r3 + 1
            goto L35
        L2a:
            int r3 = r3 + (-1)
            if (r3 != 0) goto L35
            r7.collection = r1
        L30:
            r4 = r0
            goto L35
        L32:
            if (r3 != 0) goto L35
            goto L30
        L35:
            if (r4 == r2) goto L38
            return r4
        L38:
            int r0 = r0 + (-1)
            goto La
        L3b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.optimizers.AbstractOptimizer.findLastUnion():int");
    }
}
