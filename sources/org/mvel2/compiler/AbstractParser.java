package org.mvel2.compiler;

import com.exteragram.messenger.plugins.PluginsConstants;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.WeakHashMap;
import java.util.regex.Pattern;
import org.mvel2.CompileException;
import org.mvel2.DataConversion;
import org.mvel2.ErrorDetail;
import org.mvel2.ParserContext;
import org.mvel2.ast.ASTNode;
import org.mvel2.ast.DeclProtoVarNode;
import org.mvel2.ast.DeclTypedVarNode;
import org.mvel2.ast.DoNode;
import org.mvel2.ast.DoUntilNode;
import org.mvel2.ast.EndOfStatement;
import org.mvel2.ast.ForEachNode;
import org.mvel2.ast.ForNode;
import org.mvel2.ast.Function;
import org.mvel2.ast.IfNode;
import org.mvel2.ast.LiteralDeepPropertyNode;
import org.mvel2.ast.LiteralNode;
import org.mvel2.ast.OperatorNode;
import org.mvel2.ast.Proto;
import org.mvel2.ast.ProtoVarNode;
import org.mvel2.ast.Stacklang;
import org.mvel2.ast.Substatement;
import org.mvel2.ast.TypeDescriptor;
import org.mvel2.ast.TypedVarNode;
import org.mvel2.ast.UntilNode;
import org.mvel2.ast.WhileNode;
import org.mvel2.ast.WithNode;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.ArrayTools;
import org.mvel2.util.ExecutionStack;
import org.mvel2.util.FunctionParser;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;
import org.mvel2.util.ProtoParser;
import org.mvel2.util.Soundex;

/* loaded from: classes4.dex */
public class AbstractParser implements Parser, Serializable {
    public static HashMap<String, Object> CLASS_LITERALS = null;
    private static final WeakHashMap<String, char[]> EX_PRECACHE = new WeakHashMap<>(15);
    protected static final int GET = 2;
    protected static final int GET_OR_CREATE = 3;
    public static final int LEVEL_0_PROPERTY_ONLY = 0;
    public static final int LEVEL_1_BASIC_LANG = 1;
    public static final int LEVEL_2_MULTI_STATEMENT = 2;
    public static final int LEVEL_3_ITERATION = 3;
    public static final int LEVEL_4_ASSIGNMENT = 4;
    public static final int LEVEL_5_CONTROL_FLOW = 5;
    public static HashMap<String, Object> LITERALS = null;
    public static HashMap<String, Integer> OPERATORS = null;
    protected static final int OP_CONTINUE = 1;
    protected static final int OP_NOT_LITERAL = -3;
    protected static final int OP_OVERFLOW = -2;
    protected static final int OP_RESET_FRAME = 0;
    protected static final int OP_TERMINATE = -1;
    protected static final int REMOVE = 1;
    protected static final int SET = 0;
    protected boolean compileMode;
    protected Object ctx;
    protected int cursor;
    protected ExecutionStack dStack;
    protected boolean debugSymbols;
    protected int end;
    protected char[] expr;
    protected int fields;
    protected boolean greedy;
    protected int lastLineStart;
    protected ASTNode lastNode;
    protected boolean lastWasComment;
    protected boolean lastWasIdentifier;
    protected boolean lastWasLineLabel;
    protected int length;
    protected int line;
    protected int literalOnly;
    protected ParserContext pCtx;
    protected ExecutionStack splitAccumulator;

    /* renamed from: st */
    protected int f1420st;
    protected int start;
    protected ExecutionStack stk;
    protected VariableResolverFactory variableFactory;

    protected static boolean isArithmeticOperator(int i) {
        return i != -1 && i < 6;
    }

    static {
        setupParser();
    }

    protected AbstractParser() {
        this.greedy = true;
        this.lastWasIdentifier = false;
        this.lastWasLineLabel = false;
        this.lastWasComment = false;
        this.compileMode = false;
        this.literalOnly = -1;
        this.lastLineStart = 0;
        this.line = 0;
        this.splitAccumulator = new ExecutionStack();
        this.debugSymbols = false;
        this.pCtx = new ParserContext();
    }

    protected AbstractParser(ParserContext parserContext) {
        this.greedy = true;
        this.lastWasIdentifier = false;
        this.lastWasLineLabel = false;
        this.lastWasComment = false;
        this.compileMode = false;
        this.literalOnly = -1;
        this.lastLineStart = 0;
        this.line = 0;
        this.splitAccumulator = new ExecutionStack();
        this.debugSymbols = false;
        this.pCtx = parserContext == null ? new ParserContext() : parserContext;
    }

    public static void setupParser() {
        HashMap<String, Object> map = LITERALS;
        if (map == null || map.isEmpty()) {
            LITERALS = new HashMap<>();
            CLASS_LITERALS = new HashMap<>();
            OPERATORS = new HashMap<>();
            CLASS_LITERALS.put("System", System.class);
            CLASS_LITERALS.put("String", String.class);
            CLASS_LITERALS.put("CharSequence", CharSequence.class);
            CLASS_LITERALS.put("Integer", Integer.class);
            CLASS_LITERALS.put("int", Integer.TYPE);
            CLASS_LITERALS.put("Long", Long.class);
            CLASS_LITERALS.put("long", Long.TYPE);
            CLASS_LITERALS.put("Boolean", Boolean.class);
            CLASS_LITERALS.put("boolean", Boolean.TYPE);
            CLASS_LITERALS.put("Short", Short.class);
            CLASS_LITERALS.put("short", Short.TYPE);
            CLASS_LITERALS.put("Character", Character.class);
            CLASS_LITERALS.put("char", Character.TYPE);
            CLASS_LITERALS.put("Double", Double.class);
            CLASS_LITERALS.put("double", Double.TYPE);
            CLASS_LITERALS.put("Float", Float.class);
            CLASS_LITERALS.put("float", Float.TYPE);
            CLASS_LITERALS.put("Byte", Byte.class);
            CLASS_LITERALS.put("byte", Byte.TYPE);
            CLASS_LITERALS.put("Math", Math.class);
            CLASS_LITERALS.put("Void", Void.class);
            CLASS_LITERALS.put("Object", Object.class);
            CLASS_LITERALS.put("Number", Number.class);
            CLASS_LITERALS.put("Class", Class.class);
            CLASS_LITERALS.put("ClassLoader", ClassLoader.class);
            CLASS_LITERALS.put("Runtime", Runtime.class);
            CLASS_LITERALS.put("Thread", Thread.class);
            CLASS_LITERALS.put("Exception", Exception.class);
            CLASS_LITERALS.put("Array", Array.class);
            CLASS_LITERALS.put("StringBuilder", StringBuilder.class);
            LITERALS.putAll(CLASS_LITERALS);
            LITERALS.put("true", Boolean.TRUE);
            LITERALS.put("false", Boolean.FALSE);
            LITERALS.put("null", null);
            LITERALS.put("nil", null);
            LITERALS.put("empty", BlankLiteral.INSTANCE);
            setLanguageLevel(Boolean.getBoolean("mvel.future.lang.support") ? 6 : 5);
        }
    }

    protected ASTNode nextTokenSkipSymbols() {
        ASTNode aSTNodeNextToken = nextToken();
        return (aSTNodeNextToken == null || aSTNodeNextToken.getFields() != -1) ? aSTNodeNextToken : nextToken();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:200)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.processFallThroughCases(SwitchRegionMaker.java:105)
        	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:112)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:95)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.makeEndlessLoop(LoopRegionMaker.java:281)
        	at jadx.core.dex.visitors.regions.maker.LoopRegionMaker.process(LoopRegionMaker.java:64)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:89)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:101)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:106)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:66)
        	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:48)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
        */
    protected org.mvel2.ast.ASTNode nextToken() {
        /*
            Method dump skipped, instructions count: 5082
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.nextToken():org.mvel2.ast.ASTNode");
    }

    public ASTNode handleSubstatement(Substatement substatement) {
        return (substatement.getStatement() == null || !substatement.getStatement().isLiteralOnly()) ? substatement : new LiteralNode(substatement.getStatement().getValue(null, null, null), this.pCtx);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected org.mvel2.ast.ASTNode handleUnion(org.mvel2.ast.ASTNode r11) {
        /*
            r10 = this;
            int r0 = r10.cursor
            int r1 = r10.end
            if (r0 == r1) goto L3b
            r10.skipWhitespace()
            int r0 = r10.cursor
            int r1 = r10.end
            r2 = -1
            if (r0 >= r1) goto L22
            char[] r1 = r10.expr
            char r1 = r1[r0]
            r3 = 46
            if (r1 == r3) goto L1f
            r3 = 91
            if (r1 == r3) goto L1d
            goto L22
        L1d:
            r5 = r0
            goto L23
        L1f:
            int r0 = r0 + 1
            goto L1d
        L22:
            r5 = -1
        L23:
            if (r5 == r2) goto L3b
            r10.captureToEOT()
            org.mvel2.ast.Union r3 = new org.mvel2.ast.Union
            char[] r4 = r10.expr
            int r0 = r10.cursor
            int r6 = r0 - r5
            int r7 = r10.fields
            org.mvel2.ParserContext r9 = r10.pCtx
            r8 = r11
            r3.<init>(r4, r5, r6, r7, r8, r9)
            r10.lastNode = r3
            return r3
        L3b:
            r8 = r11
            r10.lastNode = r8
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.handleUnion(org.mvel2.ast.ASTNode):org.mvel2.ast.ASTNode");
    }

    private ASTNode createOperator(char[] cArr, int i, int i2) {
        this.lastWasIdentifier = false;
        OperatorNode operatorNode = new OperatorNode(OPERATORS.get(new String(cArr, i, i2 - i)), cArr, i, this.pCtx);
        this.lastNode = operatorNode;
        return operatorNode;
    }

    private char[] subArray(int i, int i2) {
        if (i >= i2) {
            return new char[0];
        }
        int i3 = i2 - i;
        char[] cArr = new char[i3];
        for (int i4 = 0; i4 != i3; i4++) {
            cArr[i4] = this.expr[i4 + i];
        }
        return cArr;
    }

    private ASTNode createPropertyToken(int i, int i2) {
        if (ParseTools.isPropertyOnly(this.expr, i, i2)) {
            ParserContext parserContext = this.pCtx;
            if (parserContext != null && parserContext.hasImports()) {
                int iFindFirst = ArrayTools.findFirst('.', i, i2 - i, this.expr);
                if (iFindFirst != -1) {
                    String str = new String(this.expr, i, iFindFirst - i);
                    if (this.pCtx.hasImport(str)) {
                        this.lastWasIdentifier = true;
                        LiteralDeepPropertyNode literalDeepPropertyNode = new LiteralDeepPropertyNode(this.expr, iFindFirst + 1, (i2 - iFindFirst) - 1, this.fields, this.pCtx.getImport(str), this.pCtx);
                        this.lastNode = literalDeepPropertyNode;
                        return literalDeepPropertyNode;
                    }
                } else {
                    ParserContext parserContext2 = this.pCtx;
                    String str2 = new String(this.expr, i, this.cursor - i);
                    if (parserContext2.hasImport(str2)) {
                        this.lastWasIdentifier = true;
                        LiteralNode literalNode = new LiteralNode(this.pCtx.getStaticOrClassImport(str2), this.pCtx);
                        this.lastNode = literalNode;
                        return literalNode;
                    }
                }
            }
            HashMap<String, Object> map = LITERALS;
            String str3 = new String(this.expr, i, i2 - i);
            if (map.containsKey(str3)) {
                this.lastWasIdentifier = true;
                LiteralNode literalNode2 = new LiteralNode(LITERALS.get(str3), this.pCtx);
                this.lastNode = literalNode2;
                return literalNode2;
            }
            if (OPERATORS.containsKey(str3)) {
                this.lastWasIdentifier = false;
                OperatorNode operatorNode = new OperatorNode(OPERATORS.get(str3), this.expr, i, this.pCtx);
                this.lastNode = operatorNode;
                return operatorNode;
            }
            if (this.lastWasIdentifier) {
                return procTypedNode(true);
            }
        }
        if (this.pCtx != null && ParseTools.isArrayType(this.expr, i, i2) && this.pCtx.hasImport(new String(this.expr, i, (this.cursor - i) - 2))) {
            this.lastWasIdentifier = true;
            TypeDescriptor typeDescriptor = new TypeDescriptor(this.expr, i, this.cursor - i, this.fields);
            try {
                LiteralNode literalNode3 = new LiteralNode(typeDescriptor.getClassReference(this.pCtx), this.pCtx);
                this.lastNode = literalNode3;
                return literalNode3;
            } catch (ClassNotFoundException unused) {
                throw new CompileException("could not resolve class: " + typeDescriptor.getClassName(), this.expr, i);
            }
        }
        this.lastWasIdentifier = true;
        ASTNode aSTNode = new ASTNode(this.expr, trimRight(i), trimLeft(i2) - i, this.fields, this.pCtx);
        this.lastNode = aSTNode;
        return aSTNode;
    }

    private ASTNode procTypedNode(boolean z) {
        while (true) {
            if (this.lastNode.getLiteralValue() instanceof String) {
                char[] charArray = ((String) this.lastNode.getLiteralValue()).toCharArray();
                try {
                    this.lastNode.setLiteralValue(TypeDescriptor.getClassReference(this.pCtx, new TypeDescriptor(charArray, 0, charArray.length, 0)));
                    this.lastNode.discard();
                } catch (Exception unused) {
                }
            }
            if (this.lastNode.isLiteral() && (this.lastNode.getLiteralValue() instanceof Class)) {
                this.lastNode.discard();
                captureToEOS();
                if (z) {
                    ExecutionStack executionStack = this.splitAccumulator;
                    char[] cArr = this.expr;
                    int i = this.f1420st;
                    String str = new String(cArr, i, this.cursor - i);
                    char[] cArr2 = this.expr;
                    int i2 = this.f1420st;
                    executionStack.add(new DeclTypedVarNode(str, cArr2, i2, this.cursor - i2, (Class) this.lastNode.getLiteralValue(), this.fields | 128, this.pCtx));
                } else {
                    captureToEOS();
                    this.splitAccumulator.add(new TypedVarNode(this.expr, this.f1420st, (this.cursor - r3) - 1, this.fields | 128, (Class) this.lastNode.getLiteralValue(), this.pCtx));
                }
            } else if (this.lastNode instanceof Proto) {
                captureToEOS();
                if (z) {
                    ExecutionStack executionStack2 = this.splitAccumulator;
                    char[] cArr3 = this.expr;
                    int i3 = this.f1420st;
                    executionStack2.add(new DeclProtoVarNode(new String(cArr3, i3, this.cursor - i3), (Proto) this.lastNode, this.fields | 128, this.pCtx));
                } else {
                    ExecutionStack executionStack3 = this.splitAccumulator;
                    char[] cArr4 = this.expr;
                    int i4 = this.f1420st;
                    executionStack3.add(new ProtoVarNode(cArr4, i4, this.cursor - i4, this.fields | 128, (Proto) this.lastNode, this.pCtx));
                }
            } else if ((this.fields & 16) == 0) {
                if (this.stk.peek() instanceof Class) {
                    captureToEOS();
                    if (z) {
                        ExecutionStack executionStack4 = this.splitAccumulator;
                        char[] cArr5 = this.expr;
                        int i5 = this.f1420st;
                        String str2 = new String(cArr5, i5, this.cursor - i5);
                        char[] cArr6 = this.expr;
                        int i6 = this.f1420st;
                        executionStack4.add(new DeclTypedVarNode(str2, cArr6, i6, this.cursor - i6, (Class) this.stk.pop(), this.fields | 128, this.pCtx));
                    } else {
                        ExecutionStack executionStack5 = this.splitAccumulator;
                        char[] cArr7 = this.expr;
                        int i7 = this.f1420st;
                        executionStack5.add(new TypedVarNode(cArr7, i7, this.cursor - i7, this.fields | 128, (Class) this.stk.pop(), this.pCtx));
                    }
                } else if (this.stk.peek() instanceof Proto) {
                    captureToEOS();
                    if (z) {
                        ExecutionStack executionStack6 = this.splitAccumulator;
                        char[] cArr8 = this.expr;
                        int i8 = this.f1420st;
                        executionStack6.add(new DeclProtoVarNode(new String(cArr8, i8, this.cursor - i8), (Proto) this.stk.pop(), this.fields | 128, this.pCtx));
                    } else {
                        ExecutionStack executionStack7 = this.splitAccumulator;
                        char[] cArr9 = this.expr;
                        int i9 = this.f1420st;
                        executionStack7.add(new ProtoVarNode(cArr9, i9, this.cursor - i9, this.fields | 128, (Proto) this.stk.pop(), this.pCtx));
                    }
                } else {
                    throw new CompileException("unknown class or illegal statement: " + this.lastNode.getLiteralValue(), this.expr, this.cursor);
                }
            } else {
                throw new CompileException("unknown class or illegal statement: " + this.lastNode.getLiteralValue(), this.expr, this.cursor);
            }
            skipWhitespace();
            int i10 = this.cursor;
            if (i10 >= this.end || this.expr[i10] != ',') {
                break;
            }
            int i11 = i10 + 1;
            this.cursor = i11;
            this.f1420st = i11;
            this.splitAccumulator.add(new EndOfStatement(this.pCtx));
        }
        return (ASTNode) this.splitAccumulator.pop();
    }

    private ASTNode createBlockToken(int i, int i2, int i3, int i4, int i5) {
        this.lastWasIdentifier = false;
        this.cursor++;
        if (isStatementNotManuallyTerminated()) {
            this.splitAccumulator.add(new EndOfStatement(this.pCtx));
        }
        int i6 = i2 - i;
        int i7 = i4 - i3;
        int i8 = i7 < 0 ? 0 : i7;
        if (i5 == 2048) {
            return new IfNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
        }
        if (i5 != 4096) {
            if (i5 == 16384) {
                return new UntilNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
            }
            if (i5 == 32768) {
                return new WhileNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
            }
            if (i5 == 65536) {
                return new DoNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
            }
            if (i5 == 131072) {
                return new DoUntilNode(this.expr, i, i6, i3, i8, this.pCtx);
            }
            if (i5 != 262144) {
                return new WithNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
            }
            for (int i9 = i; i9 < i2; i9++) {
                char[] cArr = this.expr;
                char c = cArr[i9];
                if (c == ';') {
                    return new ForNode(cArr, i, i6, i3, i8, this.fields, this.pCtx);
                }
                if (c == ':') {
                    break;
                }
            }
        }
        return new ForEachNode(this.expr, i, i6, i3, i8, this.fields, this.pCtx);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private org.mvel2.ast.ASTNode captureCodeBlock(int r9) {
        /*
            r8 = this;
            r0 = 2048(0x800, float:2.87E-42)
            r1 = 0
            r2 = 1
            r3 = 0
            if (r9 == r0) goto L22
            r0 = 65536(0x10000, float:9.1835E-41)
            if (r9 == r0) goto L18
            r8.captureToNextTokenJunction()
            r8.skipWhitespace()
            char[] r0 = r8.expr
            org.mvel2.ast.ASTNode r9 = r8._captureBlock(r3, r0, r2, r9)
            return r9
        L18:
            r8.skipWhitespace()
            char[] r0 = r8.expr
            org.mvel2.ast.ASTNode r9 = r8._captureBlock(r3, r0, r1, r9)
            return r9
        L22:
            r0 = r3
            r4 = 1
        L24:
            if (r3 == 0) goto L53
            r8.captureToNextTokenJunction()
            r8.skipWhitespace()
            char[] r4 = r8.expr
            int r5 = r8.cursor
            char r6 = r4[r5]
            r7 = 123(0x7b, float:1.72E-43)
            if (r6 == r7) goto L52
            r7 = 105(0x69, float:1.47E-43)
            if (r6 != r7) goto L52
            int r5 = r5 + 1
            r8.cursor = r5
            char r5 = r4[r5]
            r6 = 102(0x66, float:1.43E-43)
            if (r5 != r6) goto L52
            int r5 = r8.incNextNonBlank()
            r8.cursor = r5
            char r4 = r4[r5]
            r5 = 40
            if (r4 != r5) goto L52
            r4 = 1
            goto L53
        L52:
            r4 = 0
        L53:
            char[] r5 = r8.expr
            org.mvel2.ast.ASTNode r3 = r8._captureBlock(r3, r5, r4, r9)
            r5 = r3
            org.mvel2.ast.IfNode r5 = (org.mvel2.ast.IfNode) r5
            org.mvel2.compiler.ExecutableStatement r5 = r5.getElseBlock()
            if (r5 == 0) goto L68
            int r9 = r8.cursor
            int r9 = r9 + r2
            r8.cursor = r9
            return r0
        L68:
            if (r0 != 0) goto L6b
            r0 = r3
        L6b:
            int r5 = r8.cursor
            int r6 = r8.end
            if (r5 == r6) goto L7d
            char[] r6 = r8.expr
            char r6 = r6[r5]
            r7 = 59
            if (r6 == r7) goto L7d
            int r5 = r5 + 1
            r8.cursor = r5
        L7d:
            boolean r5 = r8.ifThenElseBlockContinues()
            if (r5 != 0) goto L24
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.captureCodeBlock(int):org.mvel2.ast.ASTNode");
    }

    private ASTNode _captureBlock(ASTNode aSTNode, char[] cArr, boolean z, int i) {
        int iBalancedCaptureWithLineAccounting;
        int i2;
        int iBalancedCaptureWithLineAccounting2;
        skipWhitespace();
        if (i == 48) {
            if (ProtoParser.isUnresolvedWaiting()) {
                ProtoParser.checkForPossibleUnresolvedViolations(cArr, this.cursor, this.pCtx);
            }
            int i3 = this.cursor;
            captureToNextTokenJunction();
            String strCreateStringTrimmed = ParseTools.createStringTrimmed(cArr, i3, this.cursor - i3);
            if (ParseTools.isReservedWord(strCreateStringTrimmed) || ParseTools.isNotValidNameorLabel(strCreateStringTrimmed)) {
                throw new CompileException("illegal prototype name or use of reserved word", cArr, this.cursor);
            }
            int iNextNonBlank = nextNonBlank();
            this.cursor = iNextNonBlank;
            if (cArr[iNextNonBlank] != '{') {
                throw new CompileException("expected '{' but found: " + cArr[this.cursor], cArr, this.cursor);
            }
            int i4 = iNextNonBlank + 1;
            this.cursor = ParseTools.balancedCaptureWithLineAccounting(cArr, i4, this.end, '{', this.pCtx);
            ProtoParser protoParser = new ProtoParser(cArr, i4, this.cursor, strCreateStringTrimmed, this.pCtx, this.fields, this.splitAccumulator);
            Proto proto = protoParser.parse();
            this.pCtx.addImport(proto);
            proto.setCursorPosition(i4, this.cursor);
            this.cursor = protoParser.getCursor();
            ProtoParser.notifyForLateResolution(proto);
            this.lastNode = proto;
            return proto;
        }
        if (i == 100) {
            int i5 = this.cursor;
            captureToNextTokenJunction();
            int i6 = this.cursor;
            if (i6 == this.end) {
                throw new CompileException("unexpected end of statement", cArr, i5);
            }
            String strCreateStringTrimmed2 = ParseTools.createStringTrimmed(cArr, i5, i6 - i5);
            if (ParseTools.isReservedWord(strCreateStringTrimmed2) || ParseTools.isNotValidNameorLabel(strCreateStringTrimmed2)) {
                throw new CompileException("illegal function name or use of reserved word", cArr, this.cursor);
            }
            int i7 = this.cursor;
            FunctionParser functionParser = new FunctionParser(strCreateStringTrimmed2, i7, this.end - i7, cArr, this.fields, this.pCtx, this.splitAccumulator);
            Function function = functionParser.parse();
            this.cursor = functionParser.getCursor();
            this.lastNode = function;
            return function;
        }
        if (i == 101) {
            int iNextNonBlank2 = nextNonBlank();
            this.cursor = iNextNonBlank2;
            if (cArr[iNextNonBlank2] != '{') {
                throw new CompileException("expected '{' but found: " + cArr[this.cursor], cArr, this.cursor);
            }
            int i8 = iNextNonBlank2 + 1;
            this.cursor = ParseTools.balancedCaptureWithLineAccounting(cArr, i8, this.end, '{', this.pCtx);
            Stacklang stacklang = new Stacklang(cArr, i8, this.cursor - i8, this.fields, this.pCtx);
            this.cursor++;
            this.lastNode = stacklang;
            return stacklang;
        }
        if (z) {
            int i9 = this.cursor;
            if (cArr[i9] != '(') {
                throw new CompileException("expected '(' but encountered: " + cArr[this.cursor], cArr, this.cursor);
            }
            iBalancedCaptureWithLineAccounting = ParseTools.balancedCaptureWithLineAccounting(cArr, i9, this.end, '(', this.pCtx);
            i2 = i9 + 1;
            this.cursor = iBalancedCaptureWithLineAccounting + 1;
        } else {
            iBalancedCaptureWithLineAccounting = 0;
            i2 = 0;
        }
        skipWhitespace();
        int i10 = this.cursor;
        int i11 = this.end;
        if (i10 >= i11) {
            throw new CompileException("unexpected end of statement", cArr, this.end);
        }
        if (cArr[i10] == '{') {
            iBalancedCaptureWithLineAccounting2 = ParseTools.balancedCaptureWithLineAccounting(cArr, i10, i11, '{', this.pCtx);
            this.cursor = iBalancedCaptureWithLineAccounting2;
        } else {
            i10--;
            captureToEOSorEOL();
            iBalancedCaptureWithLineAccounting2 = this.cursor + 1;
        }
        if (i == 2048) {
            IfNode ifNode = (IfNode) aSTNode;
            if (aSTNode == null) {
                return createBlockToken(i2, iBalancedCaptureWithLineAccounting, i10 + 1, iBalancedCaptureWithLineAccounting2, i);
            }
            if (!z) {
                int iTrimRight = trimRight(i10 + 1);
                this.f1420st = iTrimRight;
                return ifNode.setElseBlock(cArr, iTrimRight, trimLeft(iBalancedCaptureWithLineAccounting2) - this.f1420st, this.pCtx);
            }
            return ifNode.setElseIf((IfNode) createBlockToken(i2, iBalancedCaptureWithLineAccounting, trimRight(i10 + 1), trimLeft(iBalancedCaptureWithLineAccounting2), i));
        }
        if (i == 65536) {
            this.cursor++;
            skipWhitespace();
            this.f1420st = this.cursor;
            captureToNextTokenJunction();
            int i12 = this.f1420st;
            String str = new String(cArr, i12, this.cursor - i12);
            if ("while".equals(str)) {
                skipWhitespace();
                int i13 = this.cursor;
                int iBalancedCaptureWithLineAccounting3 = ParseTools.balancedCaptureWithLineAccounting(cArr, i13, this.end, '(', this.pCtx);
                this.cursor = iBalancedCaptureWithLineAccounting3;
                return createBlockToken(i13 + 1, iBalancedCaptureWithLineAccounting3, trimRight(i10 + 1), trimLeft(iBalancedCaptureWithLineAccounting2), i);
            }
            if ("until".equals(str)) {
                skipWhitespace();
                int i14 = this.cursor;
                int iBalancedCaptureWithLineAccounting4 = ParseTools.balancedCaptureWithLineAccounting(cArr, i14, this.end, '(', this.pCtx);
                this.cursor = iBalancedCaptureWithLineAccounting4;
                return createBlockToken(i14 + 1, iBalancedCaptureWithLineAccounting4, trimRight(i10 + 1), trimLeft(iBalancedCaptureWithLineAccounting2), 131072);
            }
            throw new CompileException("expected 'while' or 'until' but encountered: " + str, cArr, this.cursor);
        }
        return createBlockToken(i2, iBalancedCaptureWithLineAccounting, trimRight(i10 + 1), trimLeft(iBalancedCaptureWithLineAccounting2), i);
    }

    protected boolean ifThenElseBlockContinues() {
        int i = this.cursor;
        if (i + 4 < this.end) {
            if (this.expr[i] != ';') {
                this.cursor = i - 1;
            }
            skipWhitespace();
            int i2 = this.cursor;
            if (i2 + 4 < this.end) {
                char[] cArr = this.expr;
                if (cArr[i2] == 'e' && cArr[i2 + 1] == 'l' && cArr[i2 + 2] == 's' && cArr[i2 + 3] == 'e' && (ParseTools.isWhitespace(cArr[i2 + 4]) || this.expr[this.cursor + 4] == '{')) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean tokenContinues() {
        char c;
        int i = this.cursor;
        if (i == this.end) {
            return false;
        }
        char c2 = this.expr[i];
        if (c2 == '.' || c2 == '[') {
            return true;
        }
        if (ParseTools.isWhitespace(c2)) {
            int i2 = this.cursor;
            skipWhitespace();
            int i3 = this.cursor;
            if (i3 != this.end && ((c = this.expr[i3]) == '.' || c == '[')) {
                return true;
            }
            this.cursor = i2;
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void expectEOS() {
        /*
            r5 = this;
            r5.skipWhitespace()
            int r0 = r5.cursor
            int r1 = r5.end
            if (r0 == r1) goto L88
            char[] r1 = r5.expr
            char r0 = r1[r0]
            r1 = 59
            if (r0 == r1) goto L88
            r1 = 33
            r2 = 61
            if (r0 == r1) goto L55
            r1 = 38
            if (r0 == r1) goto L4e
            r1 = 45
            if (r0 == r1) goto L47
            r3 = 47
            if (r0 == r3) goto L47
            r3 = 124(0x7c, float:1.74E-43)
            if (r0 == r3) goto L40
            r3 = 42
            if (r0 == r3) goto L47
            r4 = 43
            if (r0 == r4) goto L47
            switch(r0) {
                case 60: goto L88;
                case 61: goto L33;
                case 62: goto L88;
                default: goto L32;
            }
        L32:
            goto L5c
        L33:
            char r0 = r5.lookAhead()
            if (r0 == r3) goto L88
            if (r0 == r4) goto L88
            if (r0 == r1) goto L88
            if (r0 == r2) goto L88
            goto L5c
        L40:
            char r0 = r5.lookAhead()
            if (r0 != r3) goto L5c
            goto L88
        L47:
            char r0 = r5.lookAhead()
            if (r0 != r2) goto L5c
            goto L88
        L4e:
            char r0 = r5.lookAhead()
            if (r0 != r1) goto L5c
            goto L88
        L55:
            char r0 = r5.lookAhead()
            if (r0 != r2) goto L5c
            goto L88
        L5c:
            org.mvel2.CompileException r0 = new org.mvel2.CompileException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "expected end of statement but encountered: "
            r1.append(r2)
            int r2 = r5.cursor
            int r3 = r5.end
            if (r2 != r3) goto L71
            java.lang.String r2 = "<end of stream>"
            goto L79
        L71:
            char[] r3 = r5.expr
            char r2 = r3[r2]
            java.lang.Character r2 = java.lang.Character.valueOf(r2)
        L79:
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            char[] r2 = r5.expr
            int r3 = r5.cursor
            r0.<init>(r1, r2, r3)
            throw r0
        L88:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.expectEOS():void");
    }

    protected boolean isNextIdentifier() {
        while (true) {
            int i = this.cursor;
            if (i == this.end || !ParseTools.isWhitespace(this.expr[i])) {
                break;
            }
            this.cursor++;
        }
        int i2 = this.cursor;
        return i2 != this.end && ParseTools.isIdentifierPart(this.expr[i2]);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0038  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void captureToEOS() {
        /*
            r5 = this;
        L0:
            int r0 = r5.cursor
            int r1 = r5.end
            if (r0 == r1) goto L45
            char[] r2 = r5.expr
            char r3 = r2[r0]
            r4 = 34
            if (r3 == r4) goto L38
            r4 = 44
            if (r3 == r4) goto L45
            r4 = 59
            if (r3 == r4) goto L45
            r4 = 91
            if (r3 == r4) goto L2b
            r4 = 123(0x7b, float:1.72E-43)
            if (r3 == r4) goto L2b
            r4 = 125(0x7d, float:1.75E-43)
            if (r3 == r4) goto L45
            r4 = 39
            if (r3 == r4) goto L38
            r4 = 40
            if (r3 == r4) goto L2b
            goto L3e
        L2b:
            org.mvel2.ParserContext r4 = r5.pCtx
            int r0 = org.mvel2.util.ParseTools.balancedCaptureWithLineAccounting(r2, r0, r1, r3, r4)
            r5.cursor = r0
            int r1 = r5.end
            if (r0 < r1) goto L3e
            goto L45
        L38:
            int r0 = org.mvel2.util.ParseTools.captureStringLiteral(r3, r2, r0, r1)
            r5.cursor = r0
        L3e:
            int r0 = r5.cursor
            int r0 = r0 + 1
            r5.cursor = r0
            goto L0
        L45:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.captureToEOS():void");
    }

    protected void captureToEOSorEOL() {
        char c;
        while (true) {
            int i = this.cursor;
            if (i == this.end || (c = this.expr[i]) == '\n' || c == '\r' || c == ';') {
                return;
            } else {
                this.cursor = i + 1;
            }
        }
    }

    protected void captureIdentifier() {
        char c;
        if (this.cursor == this.end) {
            throw new CompileException("unexpected end of statement: EOF", this.expr, this.cursor);
        }
        boolean z = false;
        while (true) {
            int i = this.cursor;
            if (i == this.end || (c = this.expr[i]) == ';') {
                return;
            }
            if (!ParseTools.isIdentifierPart(c)) {
                if (z) {
                    return;
                }
                throw new CompileException("unexpected symbol (was expecting an identifier): " + this.expr[this.cursor], this.expr, this.cursor);
            }
            this.cursor++;
            z = true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x006b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void captureToEOT() {
        /*
            r5 = this;
            r5.skipWhitespace()
        L3:
            char[] r0 = r5.expr
            int r1 = r5.cursor
            char r2 = r0[r1]
            r3 = 34
            if (r2 == r3) goto L85
            r3 = 59
            if (r2 == r3) goto L97
            r3 = 61
            if (r2 == r3) goto L97
            r3 = 91
            if (r2 == r3) goto L6b
            r3 = 46
            if (r2 == r3) goto L5d
            r4 = 47
            if (r2 == r4) goto L97
            r4 = 123(0x7b, float:1.72E-43)
            if (r2 == r4) goto L6b
            r4 = 124(0x7c, float:1.74E-43)
            if (r2 == r4) goto L97
            switch(r2) {
                case 37: goto L97;
                case 38: goto L97;
                case 39: goto L52;
                case 40: goto L6b;
                default: goto L2c;
            }
        L2c:
            switch(r2) {
                case 42: goto L97;
                case 43: goto L97;
                case 44: goto L97;
                default: goto L2f;
            }
        L2f:
            boolean r0 = org.mvel2.util.ParseTools.isWhitespace(r2)
            if (r0 == 0) goto L8d
            r5.skipWhitespace()
            int r0 = r5.cursor
            int r1 = r5.end
            if (r0 >= r1) goto L4e
            char[] r2 = r5.expr
            char r2 = r2[r0]
            if (r2 != r3) goto L4e
            if (r0 == r1) goto L4a
            int r0 = r0 + 1
            r5.cursor = r0
        L4a:
            r5.skipWhitespace()
            goto L8d
        L4e:
            r5.trimWhitespace()
            return
        L52:
            r2 = 39
            int r3 = r5.end
            int r0 = org.mvel2.util.ParseTools.captureStringLiteral(r2, r0, r1, r3)
            r5.cursor = r0
            goto L8d
        L5d:
            int r1 = r1 + 1
            r5.cursor = r1
            r5.skipWhitespace()
            int r0 = r5.cursor
            int r0 = r0 + (-1)
            r5.cursor = r0
            goto L8d
        L6b:
            int r3 = r5.end
            org.mvel2.ParserContext r4 = r5.pCtx
            int r0 = org.mvel2.util.ParseTools.balancedCaptureWithLineAccounting(r0, r1, r3, r2, r4)
            r5.cursor = r0
            r1 = -1
            if (r0 == r1) goto L79
            goto L8d
        L79:
            org.mvel2.CompileException r0 = new org.mvel2.CompileException
            char[] r1 = r5.expr
            int r2 = r5.cursor
            java.lang.String r3 = "unbalanced braces"
            r0.<init>(r3, r1, r2)
            throw r0
        L85:
            int r2 = r5.end
            int r0 = org.mvel2.util.ParseTools.captureStringLiteral(r3, r0, r1, r2)
            r5.cursor = r0
        L8d:
            int r0 = r5.cursor
            int r0 = r0 + 1
            r5.cursor = r0
            int r1 = r5.end
            if (r0 < r1) goto L3
        L97:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.captureToEOT():void");
    }

    protected boolean lastNonWhite(char c) {
        int i = this.cursor - 1;
        while (ParseTools.isWhitespace(this.expr[i])) {
            i--;
        }
        return c == this.expr[i];
    }

    protected int trimLeft(int i) {
        int i2 = this.end;
        if (i > i2) {
            i = i2;
        }
        while (i > 0 && i >= this.f1420st) {
            int i3 = i - 1;
            if (!ParseTools.isWhitespace(this.expr[i3]) && this.expr[i3] != ';') {
                break;
            }
            i--;
        }
        return i;
    }

    protected int trimRight(int i) {
        while (i != this.end && ParseTools.isWhitespace(this.expr[i])) {
            i++;
        }
        return i;
    }

    protected void skipWhitespace() {
        int i;
        int i2;
        int i3;
        while (true) {
            int i4 = this.cursor;
            int i5 = this.end;
            if (i4 == i5) {
                return;
            }
            char[] cArr = this.expr;
            char c = cArr[i4];
            if (c == '\n') {
                this.line++;
                this.lastLineStart = i4;
            } else if (c != '\r') {
                if (c == '/' && i4 + 1 != i5) {
                    char c2 = cArr[i4 + 1];
                    if (c2 == '*') {
                        int i6 = i5 - 1;
                        this.cursor = i4 + 1;
                        while (true) {
                            i3 = this.cursor;
                            if (i3 == i6) {
                                break;
                            }
                            char[] cArr2 = this.expr;
                            if (cArr2[i3] == '*' && cArr2[i3 + 1] == '/') {
                                break;
                            } else {
                                this.cursor = i3 + 1;
                            }
                        }
                        if (i3 != i6) {
                            this.cursor = i3 + 2;
                        }
                        while (i4 < this.cursor) {
                            this.expr[i4] = ' ';
                            i4++;
                        }
                    } else {
                        if (c2 != '/') {
                            return;
                        }
                        this.cursor = i4 + 1;
                        cArr[i4] = ' ';
                        while (true) {
                            i = this.cursor;
                            i2 = this.end;
                            if (i == i2) {
                                break;
                            }
                            char[] cArr3 = this.expr;
                            if (cArr3[i] == '\n') {
                                break;
                            }
                            this.cursor = i + 1;
                            cArr3[i] = ' ';
                        }
                        if (i != i2) {
                            this.cursor = i + 1;
                        }
                        this.line++;
                        this.lastLineStart = this.cursor;
                    }
                } else if (!ParseTools.isWhitespace(c)) {
                    return;
                } else {
                    this.cursor++;
                }
            }
            this.cursor = i4 + 1;
        }
    }

    protected void captureToNextTokenJunction() {
        char[] cArr;
        char c;
        while (true) {
            int i = this.cursor;
            int i2 = this.end;
            if (i == i2 || (c = (cArr = this.expr)[i]) == '(') {
                return;
            }
            if (c != '/') {
                if (c != '[') {
                    if (c == '{' || ParseTools.isWhitespace(c)) {
                        return;
                    } else {
                        this.cursor++;
                    }
                }
            } else if (cArr[i + 1] == '*') {
                return;
            }
            this.cursor = ParseTools.balancedCaptureWithLineAccounting(cArr, i, i2, '[', this.pCtx) + 1;
        }
    }

    protected void trimWhitespace() {
        while (true) {
            int i = this.cursor;
            if (i == 0 || !ParseTools.isWhitespace(this.expr[i - 1])) {
                return;
            } else {
                this.cursor--;
            }
        }
    }

    protected void setExpression(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        WeakHashMap<String, char[]> weakHashMap = EX_PRECACHE;
        synchronized (weakHashMap) {
            try {
                char[] cArr = weakHashMap.get(str);
                this.expr = cArr;
                if (cArr == null) {
                    char[] charArray = str.toCharArray();
                    this.expr = charArray;
                    int length = charArray.length;
                    this.length = length;
                    this.end = length;
                    while (true) {
                        int i = this.start;
                        if (i >= this.length || !ParseTools.isWhitespace(this.expr[i])) {
                            break;
                        } else {
                            this.start++;
                        }
                    }
                    while (true) {
                        int i2 = this.length;
                        if (i2 == 0 || !ParseTools.isWhitespace(this.expr[i2 - 1])) {
                            break;
                        } else {
                            this.length--;
                        }
                    }
                    int i3 = this.length;
                    char[] cArr2 = new char[i3];
                    for (int i4 = 0; i4 != i3; i4++) {
                        cArr2[i4] = this.expr[i4];
                    }
                    EX_PRECACHE.put(str, cArr2);
                } else {
                    int length2 = cArr.length;
                    this.length = length2;
                    this.end = length2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    protected void setExpression(char[] cArr) {
        this.expr = cArr;
        int length = cArr.length;
        this.length = length;
        this.end = length;
        while (true) {
            int i = this.start;
            if (i >= this.length || !ParseTools.isWhitespace(this.expr[i])) {
                break;
            } else {
                this.start++;
            }
        }
        while (true) {
            int i2 = this.length;
            if (i2 == 0 || !ParseTools.isWhitespace(this.expr[i2 - 1])) {
                return;
            } else {
                this.length--;
            }
        }
    }

    protected char lookToLast() {
        int i = this.cursor;
        if (i == this.start) {
            return (char) 0;
        }
        while (i != this.start) {
            i--;
            if (!ParseTools.isWhitespace(this.expr[i])) {
                break;
            }
        }
        return this.expr[i];
    }

    protected char lookBehind() {
        int i = this.cursor;
        if (i == this.start) {
            return (char) 0;
        }
        return this.expr[i - 1];
    }

    protected char lookAhead() {
        int i = this.cursor;
        if (i + 1 != this.end) {
            return this.expr[i + 1];
        }
        return (char) 0;
    }

    protected char lookAhead(int i) {
        int i2 = this.cursor;
        if (i2 + i >= this.end) {
            return (char) 0;
        }
        return this.expr[i2 + i];
    }

    protected boolean isNextIdentifierOrLiteral() {
        int i = this.cursor;
        if (i == this.end) {
            return false;
        }
        while (i != this.end && ParseTools.isWhitespace(this.expr[i])) {
            i++;
        }
        if (i == this.end) {
            return false;
        }
        char c = this.expr[i];
        return ParseTools.isIdentifierPart(c) || ParseTools.isDigit(c) || c == '\'' || c == '\"';
    }

    public int incNextNonBlank() {
        this.cursor++;
        return nextNonBlank();
    }

    public int nextNonBlank() {
        int i = this.cursor;
        if (i + 1 >= this.end) {
            throw new CompileException("unexpected end of statement", this.expr, this.f1420st);
        }
        while (i != this.end && ParseTools.isWhitespace(this.expr[i])) {
            i++;
        }
        return i;
    }

    public void expectNextChar_IW(char c) {
        nextNonBlank();
        int i = this.cursor;
        if (i == this.end) {
            throw new CompileException("unexpected end of statement", this.expr, this.f1420st);
        }
        if (this.expr[i] == c) {
            return;
        }
        throw new CompileException("unexpected character ('" + this.expr[this.cursor] + "'); was expecting: " + c, this.expr, this.f1420st);
    }

    protected boolean isStatementNotManuallyTerminated() {
        int i = this.cursor;
        if (i >= this.end) {
            return false;
        }
        while (i != this.end && ParseTools.isWhitespace(this.expr[i])) {
            i++;
        }
        return i == this.end || this.expr[i] != ';';
    }

    protected void addFatalError(String str) {
        this.pCtx.addError(new ErrorDetail(this.expr, this.f1420st, true, str));
    }

    protected void addFatalError(String str, int i) {
        this.pCtx.addError(new ErrorDetail(this.expr, i, true, str));
    }

    public static void setLanguageLevel(int i) {
        OPERATORS.clear();
        OPERATORS.putAll(loadLanguageFeaturesByLevel(i));
    }

    public static HashMap<String, Integer> loadLanguageFeaturesByLevel(int i) {
        HashMap<String, Integer> map = new HashMap<>();
        switch (i) {
            case 6:
                map.put("proto", 48);
            case 5:
                map.put("if", 39);
                map.put("else", 40);
                map.put("?", 29);
                map.put(PluginsConstants.Settings.TYPE_SWITCH, 44);
                map.put("function", 100);
                map.put("def", 100);
                map.put("stacklang", 101);
            case 4:
                map.put("=", 31);
                map.put("var", 98);
                map.put("+=", 52);
                map.put("-=", 53);
                map.put("/=", 55);
                map.put("%=", 56);
            case 3:
                map.put("foreach", 38);
                map.put("while", 41);
                map.put("until", 42);
                map.put("for", 43);
                map.put("do", 45);
            case 2:
                map.put("return", 99);
                map.put(";", 37);
            case 1:
                map.put("+", 0);
                map.put("-", 1);
                map.put("*", 2);
                map.put("**", 5);
                map.put("/", 3);
                map.put("%", 4);
                map.put("==", 18);
                map.put("!=", 19);
                map.put(">", 15);
                map.put(">=", 17);
                map.put("<", 14);
                map.put("<=", 16);
                map.put("&&", 21);
                map.put("and", 21);
                map.put("||", 22);
                map.put(PluginsConstants.HookFilterTypes.f185OR, 23);
                map.put("~=", 24);
                map.put("instanceof", 25);
                map.put("is", 25);
                map.put("contains", 26);
                map.put("soundslike", 27);
                map.put("strsim", 28);
                map.put("convertable_to", 36);
                map.put("isdef", 47);
                map.put("#", 20);
                map.put("&", 6);
                map.put("|", 7);
                map.put("^", 8);
                map.put("<<", 10);
                map.put("<<<", 12);
                map.put(">>", 9);
                map.put(">>>", 11);
                map.put("new", 34);
                map.put("in", 35);
                map.put("with", 46);
                map.put("assert", 97);
                map.put("import", 96);
                map.put("import_static", 95);
                map.put("++", 50);
                map.put("--", 51);
            case 0:
                map.put(":", 30);
                break;
        }
        return map;
    }

    /* JADX WARN: Code restructure failed: missing block: B:84:0x0187, code lost:
    
        if (r8.dStack.size() <= 1) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0189, code lost:
    
        dreduce();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0192, code lost:
    
        if (r8.stk.isReduceable() == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0194, code lost:
    
        r8.stk.xswap();
     */
    /* JADX WARN: Removed duplicated region for block: B:89:0x019a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected int arithmeticFunctionReduction(int r9) {
        /*
            Method dump skipped, instructions count: 483
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.compiler.AbstractParser.arithmeticFunctionReduction(int):int");
    }

    private void dreduce() {
        this.stk.copy2(this.dStack);
        this.stk.m1139op();
    }

    protected void reduce() {
        try {
            int iIntValue = ((Integer) this.stk.pop()).intValue();
            if (iIntValue != 0) {
                boolean z = true;
                if (iIntValue != 1 && iIntValue != 2 && iIntValue != 3 && iIntValue != 4 && iIntValue != 5) {
                    if (iIntValue != 36) {
                        switch (iIntValue) {
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20:
                                break;
                            case 21:
                                Object objPop = this.stk.pop();
                                ExecutionStack executionStack = this.stk;
                                if (!((Boolean) executionStack.pop()).booleanValue() || !((Boolean) objPop).booleanValue()) {
                                    z = false;
                                }
                                executionStack.push(Boolean.valueOf(z));
                                return;
                            case 22:
                                Object objPop2 = this.stk.pop();
                                ExecutionStack executionStack2 = this.stk;
                                if (!((Boolean) executionStack2.pop()).booleanValue() && !((Boolean) objPop2).booleanValue()) {
                                    z = false;
                                }
                                executionStack2.push(Boolean.valueOf(z));
                                return;
                            case 23:
                                Object objPop3 = this.stk.pop();
                                Object objPop4 = this.stk.pop();
                                if (PropertyTools.isEmpty(objPop4) && PropertyTools.isEmpty(objPop3)) {
                                    this.stk.push(null);
                                    return;
                                }
                                this.stk.clear();
                                ExecutionStack executionStack3 = this.stk;
                                if (!PropertyTools.isEmpty(objPop4)) {
                                    objPop3 = objPop4;
                                }
                                executionStack3.push(objPop3);
                                return;
                            case 24:
                                ExecutionStack executionStack4 = this.stk;
                                executionStack4.push(Boolean.valueOf(Pattern.compile(String.valueOf(executionStack4.pop())).matcher(String.valueOf(this.stk.pop())).matches()));
                                return;
                            case 25:
                                ExecutionStack executionStack5 = this.stk;
                                executionStack5.push(Boolean.valueOf(((Class) executionStack5.pop()).isInstance(this.stk.pop())));
                                return;
                            case 26:
                                ExecutionStack executionStack6 = this.stk;
                                executionStack6.push(Boolean.valueOf(ParseTools.containsCheck(executionStack6.peek2(), this.stk.pop2())));
                                return;
                            case 27:
                                ExecutionStack executionStack7 = this.stk;
                                executionStack7.push(Boolean.valueOf(Soundex.soundex(String.valueOf(executionStack7.pop())).equals(Soundex.soundex(String.valueOf(this.stk.pop())))));
                                return;
                            case 28:
                                ExecutionStack executionStack8 = this.stk;
                                executionStack8.push(Float.valueOf(ParseTools.similarity(String.valueOf(executionStack8.pop()), String.valueOf(this.stk.pop()))));
                                return;
                            default:
                                reduceNumeric(iIntValue);
                                return;
                        }
                    } else {
                        ExecutionStack executionStack9 = this.stk;
                        executionStack9.push(Boolean.valueOf(DataConversion.canConvert(executionStack9.peek2().getClass(), (Class) this.stk.pop2())));
                        return;
                    }
                }
            }
            this.stk.m1140op(iIntValue);
        } catch (ArithmeticException e) {
            throw new CompileException("arithmetic error: " + e.getMessage(), this.expr, this.f1420st, e);
        } catch (ClassCastException e2) {
            throw new CompileException("syntax error or incompatable types", this.expr, this.f1420st, e2);
        } catch (Exception e3) {
            throw new CompileException("failed to subEval expression", this.expr, this.f1420st, e3);
        }
    }

    private void reduceNumeric(int i) {
        Object objPeek2 = this.stk.peek2();
        Object objPop2 = this.stk.pop2();
        if (objPeek2 instanceof Integer) {
            if (objPop2 instanceof Integer) {
                reduce(((Integer) objPeek2).intValue(), i, ((Integer) objPop2).intValue());
                return;
            } else {
                reduce(((Integer) objPeek2).intValue(), i, ((Long) objPop2).longValue());
                return;
            }
        }
        if (objPop2 instanceof Integer) {
            reduce(((Long) objPeek2).longValue(), i, ((Integer) objPop2).intValue());
        } else {
            reduce(((Long) objPeek2).longValue(), i, ((Long) objPop2).longValue());
        }
    }

    private void reduce(int i, int i2, int i3) {
        switch (i2) {
            case 6:
                this.stk.push(Integer.valueOf(i & i3));
                break;
            case 7:
                this.stk.push(Integer.valueOf(i | i3));
                break;
            case 8:
                this.stk.push(Integer.valueOf(i ^ i3));
                break;
            case 9:
                this.stk.push(Integer.valueOf(i >> i3));
                break;
            case 10:
                this.stk.push(Integer.valueOf(i << i3));
                break;
            case 11:
                this.stk.push(Integer.valueOf(i >>> i3));
                break;
            case 12:
                if (i < 0) {
                    i *= -1;
                }
                this.stk.push(Integer.valueOf(i << i3));
                break;
        }
    }

    private void reduce(int i, int i2, long j) {
        switch (i2) {
            case 6:
                this.stk.push(Long.valueOf(j & i));
                break;
            case 7:
                this.stk.push(Long.valueOf(j | i));
                break;
            case 8:
                this.stk.push(Long.valueOf(j ^ i));
                break;
            case 9:
                this.stk.push(Integer.valueOf(i >> ((int) j)));
                break;
            case 10:
                this.stk.push(Integer.valueOf(i << ((int) j)));
                break;
            case 11:
                this.stk.push(Integer.valueOf(i >>> ((int) j)));
                break;
            case 12:
                if (i < 0) {
                    i *= -1;
                }
                this.stk.push(Integer.valueOf(i << ((int) j)));
                break;
        }
    }

    private void reduce(long j, int i, int i2) {
        switch (i) {
            case 6:
                this.stk.push(Long.valueOf(j & i2));
                break;
            case 7:
                this.stk.push(Long.valueOf(j | i2));
                break;
            case 8:
                this.stk.push(Long.valueOf(j ^ i2));
                break;
            case 9:
                this.stk.push(Long.valueOf(j >> i2));
                break;
            case 10:
                this.stk.push(Long.valueOf(j << i2));
                break;
            case 11:
                this.stk.push(Long.valueOf(j >>> i2));
                break;
            case 12:
                if (j < 0) {
                    j *= -1;
                }
                this.stk.push(Long.valueOf(j << i2));
                break;
        }
    }

    private void reduce(long j, int i, long j2) {
        switch (i) {
            case 6:
                this.stk.push(Long.valueOf(j & j2));
                break;
            case 7:
                this.stk.push(Long.valueOf(j | j2));
                break;
            case 8:
                this.stk.push(Long.valueOf(j ^ j2));
                break;
            case 9:
                this.stk.push(Long.valueOf(j >> ((int) j2)));
                break;
            case 10:
                this.stk.push(Long.valueOf(j << ((int) j2)));
                break;
            case 11:
                this.stk.push(Long.valueOf(j >>> ((int) j2)));
                break;
            case 12:
                if (j < 0) {
                    j *= -1;
                }
                this.stk.push(Long.valueOf(j << ((int) j2)));
                break;
        }
    }

    @Override // org.mvel2.compiler.Parser
    public int getCursor() {
        return this.cursor;
    }

    @Override // org.mvel2.compiler.Parser
    public char[] getExpression() {
        return this.expr;
    }

    private static int asInt(Object obj) {
        return ((Integer) obj).intValue();
    }
}
