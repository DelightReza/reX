package org.mvel2.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;
import org.mvel2.CompileException;
import org.mvel2.ParserContext;
import org.mvel2.ast.Proto;
import org.mvel2.compiler.ExecutableStatement;

/* loaded from: classes4.dex */
public class ProtoParser {
    private static ThreadLocal<Queue<DeferredTypeResolve>> deferred = new ThreadLocal<>();
    private int cursor;
    private String deferredName;
    private int endOffset;
    private char[] expr;
    private boolean interpreted;
    private String name;
    private ParserContext pCtx;
    private String protoName;
    private ExecutionStack splitAccumulator;
    String tk1 = null;
    String tk2 = null;
    private Class type;

    private interface DeferredTypeResolve {
        String getName();

        boolean isWaitingFor(Proto proto);
    }

    public ProtoParser(char[] cArr, int i, int i2, String str, ParserContext parserContext, int i3, ExecutionStack executionStack) {
        this.interpreted = false;
        this.expr = cArr;
        this.cursor = i;
        this.endOffset = i2;
        this.protoName = str;
        this.pCtx = parserContext;
        this.interpreted = (i3 & 16) == 0;
        this.splitAccumulator = executionStack;
    }

    /* JADX WARN: Removed duplicated region for block: B:59:0x0124  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public org.mvel2.ast.Proto parse() {
        /*
            Method dump skipped, instructions count: 459
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ProtoParser.parse():org.mvel2.ast.Proto");
    }

    private void calculateDecl() {
        if (this.tk2 != null) {
            try {
                if (this.pCtx.hasProtoImport(this.tk1)) {
                    this.type = Proto.class;
                } else {
                    this.type = ParseTools.findClass(null, this.tk1, this.pCtx);
                }
                this.name = this.tk2;
            } catch (ClassNotFoundException e) {
                if (this.interpreted) {
                    this.type = DeferredTypeResolve.class;
                    this.deferredName = this.tk1;
                    this.name = this.tk2;
                } else {
                    throw new CompileException("could not resolve class: " + this.tk1, this.expr, this.cursor, e);
                }
            }
        } else {
            this.type = Object.class;
            this.name = this.tk1;
        }
        this.tk1 = null;
        this.tk2 = null;
    }

    private void enqueueReceiverForLateResolution(final String str, final Proto.Receiver receiver, final String str2) {
        Queue<DeferredTypeResolve> queue = deferred.get();
        if (queue == null) {
            ThreadLocal<Queue<DeferredTypeResolve>> threadLocal = deferred;
            LinkedList linkedList = new LinkedList();
            threadLocal.set(linkedList);
            queue = linkedList;
        }
        queue.add(new DeferredTypeResolve() { // from class: org.mvel2.util.ProtoParser.1
            @Override // org.mvel2.util.ProtoParser.DeferredTypeResolve
            public boolean isWaitingFor(Proto proto) {
                if (!str.equals(proto.getName())) {
                    return false;
                }
                receiver.setType(Proto.ReceiverType.PROPERTY);
                receiver.setInitValue((ExecutableStatement) ParseTools.subCompileExpression(str2, ProtoParser.this.pCtx));
                return true;
            }

            @Override // org.mvel2.util.ProtoParser.DeferredTypeResolve
            public String getName() {
                return str;
            }
        });
    }

    public static void notifyForLateResolution(Proto proto) {
        if (deferred.get() != null) {
            Queue<DeferredTypeResolve> queue = deferred.get();
            HashSet hashSet = new HashSet();
            for (DeferredTypeResolve deferredTypeResolve : queue) {
                if (deferredTypeResolve.isWaitingFor(proto)) {
                    hashSet.add(deferredTypeResolve);
                }
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                queue.remove((DeferredTypeResolve) it.next());
            }
        }
    }

    public int getCursor() {
        return this.cursor;
    }

    public static void checkForPossibleUnresolvedViolations(char[] cArr, int i, ParserContext parserContext) {
        if (isUnresolvedWaiting()) {
            Object obj = ((LinkedHashMap) parserContext.getParserConfiguration().getImports()).values().toArray()[r5.size() - 1];
            if (obj instanceof Proto) {
                Proto proto = (Proto) obj;
                int cursorEnd = proto.getCursorEnd();
                do {
                    i--;
                    if (i <= cursorEnd) {
                        break;
                    }
                } while (ParseTools.isWhitespace(cArr[i]));
                while (i > cursorEnd && ParseTools.isIdentifierPart(cArr[i])) {
                    i--;
                }
                while (i > cursorEnd && (ParseTools.isWhitespace(cArr[i]) || cArr[i] == ';')) {
                    i--;
                }
                if (i == cursorEnd) {
                    return;
                }
                throw new CompileException("unresolved reference (possible illegal forward-reference?): " + getNextUnresolvedWaiting(), cArr, proto.getCursorStart());
            }
        }
    }

    public static boolean isUnresolvedWaiting() {
        return (deferred.get() == null || deferred.get().isEmpty()) ? false : true;
    }

    public static String getNextUnresolvedWaiting() {
        if (deferred.get() == null || deferred.get().isEmpty()) {
            return null;
        }
        return deferred.get().poll().getName();
    }
}
