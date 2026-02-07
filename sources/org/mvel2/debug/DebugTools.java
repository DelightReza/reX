package org.mvel2.debug;

import com.exteragram.messenger.plugins.PluginsConstants;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.Operator;
import org.mvel2.ast.ASTNode;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExecutableAccessor;
import org.mvel2.compiler.ExecutableLiteral;
import org.mvel2.integration.VariableResolver;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.util.ASTLinkedList;

/* loaded from: classes4.dex */
public class DebugTools {
    public static String decompile(Serializable serializable) {
        if (serializable instanceof CompiledExpression) {
            return decompile((CompiledExpression) serializable);
        }
        if (serializable instanceof ExecutableAccessor) {
            return "CANNOT DECOMPILE OPTIMIZED STATEMENT (Run with -Dmvel.optimizer=false)";
        }
        if (serializable instanceof ExecutableLiteral) {
            return "LITERAL: " + ((ExecutableLiteral) serializable).getValue(null, null);
        }
        return "NOT A KNOWN PAYLOAD: " + serializable.getClass().getName();
    }

    public static String decompile(CompiledExpression compiledExpression) {
        return decompile(compiledExpression, false, new DecompileContext());
    }

    private static final class DecompileContext {
        public int node;

        private DecompileContext() {
            this.node = 0;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00c6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String decompile(org.mvel2.compiler.CompiledExpression r8, boolean r9, org.mvel2.debug.DebugTools.DecompileContext r10) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.debug.DebugTools.decompile(org.mvel2.compiler.CompiledExpression, boolean, org.mvel2.debug.DebugTools$DecompileContext):java.lang.String");
    }

    public static String getOperatorSymbol(int i) {
        switch (i) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            case 4:
                return "%";
            case 5:
                return "**";
            case 6:
                return "&";
            case 7:
                return "|";
            case 8:
                return "^";
            case 9:
                return ">>";
            case 10:
                return "<<";
            case 11:
                return ">>>";
            case 12:
                return "<<<";
            default:
                switch (i) {
                    case 14:
                        return "<";
                    case 15:
                        return ">";
                    case 16:
                        return "<=";
                    case 17:
                        return ">=";
                    case 18:
                        return "==";
                    case 19:
                        return "!=";
                    case 20:
                        return "+";
                    case 21:
                        return "&&";
                    case 22:
                        return "||";
                    case 23:
                        return PluginsConstants.HookFilterTypes.f185OR;
                    case 24:
                        return "REGEX";
                    case 25:
                        return "instanceof";
                    case 26:
                        return "contains";
                    case 27:
                        return "SOUNDEX";
                    case 28:
                        return "SIMILARITY";
                    case 29:
                        return "TERNARY_IF";
                    case 30:
                        return "TERNARY_ELSE";
                    case 31:
                        return "=";
                    case 32:
                    case 33:
                        return "++";
                    case 34:
                        return "new";
                    case Operator.PROJECTION /* 35 */:
                        return "PROJECT";
                    case Operator.CONVERTABLE_TO /* 36 */:
                        return "convertable_to";
                    case Operator.END_OF_STMT /* 37 */:
                        return ";";
                    case Operator.FOREACH /* 38 */:
                        return "foreach";
                    case Operator.f1408IF /* 39 */:
                        return "if";
                    case Operator.ELSE /* 40 */:
                        return "else";
                    case Operator.WHILE /* 41 */:
                        return "while";
                    default:
                        switch (i) {
                            case Operator.FOR /* 43 */:
                                return "for";
                            case Operator.SWITCH /* 44 */:
                                return "SWITCH";
                            case Operator.f1407DO /* 45 */:
                                return "do";
                            default:
                                switch (i) {
                                    case 50:
                                        return "++";
                                    case 51:
                                        return "--";
                                    case 52:
                                        return "=+";
                                    case 53:
                                        return "=";
                                    case 54:
                                        return "=+";
                                    default:
                                        switch (i) {
                                            case 99:
                                                return "RETURN";
                                            case 100:
                                                return "function";
                                            case 101:
                                                return "stacklang";
                                            default:
                                                return "UNKNOWN_OPERATOR";
                                        }
                                }
                        }
                }
        }
    }

    public static String getOperatorName(int i) {
        switch (i) {
            case 0:
                return "ADD";
            case 1:
                return "SUBTRACT";
            case 2:
                return "MULTIPLY";
            case 3:
                return "DIVIDE";
            case 4:
                return "MODULUS";
            case 5:
                return "POWER_OF";
            case 6:
                return "BIT_AND";
            case 7:
                return "BIT_OR";
            case 8:
                return "BIT_XOR";
            case 9:
                return "BIT_SHIFT_RIGHT";
            case 10:
                return "BIT_SHIFT_LEFT";
            case 11:
                return "BIT_UNSIGNED_SHIFT_RIGHT";
            case 12:
                return "BIT_UNSIGNED_SHIFT_LEFT";
            default:
                switch (i) {
                    case 14:
                        return "LESS_THAN";
                    case 15:
                        return "GREATHER_THAN";
                    case 16:
                        return "LESS_THAN_OR_EQUAL";
                    case 17:
                        return "GREATER_THAN_OR_EQUAL";
                    case 18:
                        return "EQUAL";
                    case 19:
                        return "NOT_EQUAL";
                    case 20:
                        return "STR_APPEND";
                    case 21:
                        return "AND";
                    case 22:
                        return "OR";
                    case 23:
                        return "CHAINED_OR";
                    case 24:
                        return "REGEX";
                    case 25:
                        return "INSTANCEOF";
                    case 26:
                        return "CONTAINS";
                    case 27:
                        return "SOUNDEX";
                    case 28:
                        return "SIMILARITY";
                    case 29:
                        return "TERNARY_IF";
                    case 30:
                        return "TERNARY_ELSE";
                    case 31:
                        return "ASSIGN";
                    case 32:
                        return "INCREMENT_ASSIGN";
                    case 33:
                        return "DECREMENT_ASSIGN";
                    case 34:
                        return "NEW_OBJECT";
                    case Operator.PROJECTION /* 35 */:
                        return "PROJECT";
                    case Operator.CONVERTABLE_TO /* 36 */:
                        return "CONVERTABLE_TO";
                    case Operator.END_OF_STMT /* 37 */:
                        return "END_OF_STATEMENT";
                    case Operator.FOREACH /* 38 */:
                        return "FOREACH";
                    case Operator.f1408IF /* 39 */:
                        return "IF";
                    case Operator.ELSE /* 40 */:
                        return "ELSE";
                    case Operator.WHILE /* 41 */:
                        return "WHILE";
                    default:
                        switch (i) {
                            case Operator.FOR /* 43 */:
                                return "FOR";
                            case Operator.SWITCH /* 44 */:
                                return "SWITCH";
                            case Operator.f1407DO /* 45 */:
                                return "DO";
                            default:
                                switch (i) {
                                    case 50:
                                        return "INCREMENT";
                                    case 51:
                                        return "DECREMENT";
                                    case 52:
                                        return "ASSIGN_ADD";
                                    case 53:
                                        return "ASSIGN_SUB";
                                    case 54:
                                        return "ASSIGN_STR_APPEND";
                                    default:
                                        switch (i) {
                                            case 99:
                                                return "RETURN";
                                            case 100:
                                                return "FUNCTION";
                                            case 101:
                                                return "STACKLANG";
                                            default:
                                                return "UNKNOWN_OPERATOR";
                                        }
                                }
                        }
                }
        }
    }

    public static Class determineType(String str, CompiledExpression compiledExpression) {
        ASTLinkedList aSTLinkedList = new ASTLinkedList(compiledExpression.getFirstNode());
        while (aSTLinkedList.hasMoreNodes()) {
            ASTNode aSTNodeNextNode = aSTLinkedList.nextNode();
            if (str.equals(aSTNodeNextNode.getName()) && aSTNodeNextNode.isAssignment()) {
                return aSTNodeNextNode.getEgressType();
            }
        }
        return null;
    }

    public static Map<String, VariableResolver> getAllVariableResolvers(VariableResolverFactory variableResolverFactory) {
        HashMap map = new HashMap();
        do {
            for (String str : variableResolverFactory.getKnownVariables()) {
                map.put(str, variableResolverFactory.getVariableResolver(str));
            }
            variableResolverFactory = variableResolverFactory.getNextFactory();
        } while (variableResolverFactory != null);
        return map;
    }
}
