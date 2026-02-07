package org.mvel2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.mvel2.CompileException;
import org.mvel2.DataConversion;
import org.mvel2.DataTypes;
import org.mvel2.MVEL;
import org.mvel2.OptimizationFailure;
import org.mvel2.ParserContext;
import org.mvel2.asm.signature.SignatureVisitor;
import org.mvel2.ast.ASTNode;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.compiler.BlankLiteral;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExecutableAccessor;
import org.mvel2.compiler.ExecutableAccessorSafe;
import org.mvel2.compiler.ExecutableLiteral;
import org.mvel2.compiler.ExpressionCompiler;
import org.mvel2.integration.ResolverTools;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.ClassImportResolverFactory;
import org.mvel2.math.MathProcessor;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
public class ParseTools {
    private static final Map<Class, Integer> typeCodes;
    private static final HashMap<Class, Integer> typeResolveMap;
    public static final Object[] EMPTY_OBJ_ARR = new Object[0];
    public static final Class[] EMPTY_CLS_ARR = new Class[0];
    private static final Map<Constructor, WeakReference<Class[]>> CONSTRUCTOR_PARMS_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));
    private static final Map<ClassLoader, Map<String, WeakReference<Class>>> CLASS_RESOLVER_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(1, 1.0f));
    private static final Map<Class, WeakReference<Constructor[]>> CLASS_CONSTRUCTOR_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));

    public static boolean isDigit(int i) {
        return i > 47 && i < 58;
    }

    public static boolean isWhitespace(char c) {
        return c < '!';
    }

    public static int opLookup(char c) {
        if (c == '%') {
            return 4;
        }
        if (c == '&') {
            return 6;
        }
        if (c == '*') {
            return 2;
        }
        if (c == '+') {
            return 0;
        }
        if (c == '/') {
            return 3;
        }
        if (c == '^') {
            return 8;
        }
        if (c == '|') {
            return 7;
        }
        if (c == 187) {
            return 9;
        }
        if (c != 171) {
            return c != 172 ? -1 : 11;
        }
        return 10;
    }

    private static int skipStringEscape(int i) {
        return i + 2;
    }

    static {
        HashMap<Class, Integer> map = new HashMap<>();
        typeResolveMap = map;
        map.put(BigDecimal.class, 110);
        map.put(BigInteger.class, 111);
        map.put(String.class, 1);
        Class cls = Integer.TYPE;
        map.put(cls, 101);
        map.put(Integer.class, 106);
        Class cls2 = Short.TYPE;
        map.put(cls2, 100);
        map.put(Short.class, 105);
        Class cls3 = Float.TYPE;
        map.put(cls3, 104);
        map.put(Float.class, 108);
        Class cls4 = Double.TYPE;
        map.put(cls4, 103);
        map.put(Double.class, 109);
        Class cls5 = Long.TYPE;
        map.put(cls5, 102);
        map.put(Long.class, 107);
        Class cls6 = Boolean.TYPE;
        map.put(cls6, 7);
        map.put(Boolean.class, 15);
        Class cls7 = Byte.TYPE;
        map.put(cls7, 9);
        map.put(Byte.class, 113);
        Class cls8 = Character.TYPE;
        map.put(cls8, 8);
        map.put(Character.class, 112);
        map.put(BlankLiteral.class, Integer.valueOf(DataTypes.EMPTY));
        HashMap map2 = new HashMap(30, 0.5f);
        typeCodes = map2;
        map2.put(Integer.class, 106);
        map2.put(Double.class, 109);
        map2.put(Boolean.class, 15);
        map2.put(String.class, 1);
        map2.put(Long.class, 107);
        map2.put(Short.class, 105);
        map2.put(Float.class, 108);
        map2.put(Byte.class, 113);
        map2.put(Character.class, 112);
        map2.put(BigDecimal.class, 110);
        map2.put(BigInteger.class, 111);
        map2.put(cls, 101);
        map2.put(cls4, 103);
        map2.put(cls6, 7);
        map2.put(cls5, 102);
        map2.put(cls2, 100);
        map2.put(cls3, 104);
        map2.put(cls7, 9);
        map2.put(cls8, 8);
        map2.put(BlankLiteral.class, Integer.valueOf(DataTypes.EMPTY));
    }

    public static List<char[]> parseMethodOrConstructor(char[] cArr) {
        int i;
        int i2 = 0;
        while (true) {
            if (i2 >= cArr.length) {
                i = -1;
                break;
            }
            if (cArr[i2] == '(') {
                i = i2 + 1;
                break;
            }
            i2++;
        }
        if (i != -1) {
            int i3 = i - 1;
            return parseParameterList(cArr, i, (balancedCapture(cArr, i3, '(') - i3) - 1);
        }
        return Collections.EMPTY_LIST;
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0045  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String[] parseParameterDefList(char[] r5, int r6, int r7) {
        /*
            java.util.LinkedList r0 = new java.util.LinkedList
            r0.<init>()
            r1 = -1
            if (r7 != r1) goto L9
            int r7 = r5.length
        L9:
            int r1 = r6 + r7
            r2 = r6
        Lc:
            if (r6 >= r1) goto L7a
            char r3 = r5[r6]
            r4 = 34
            if (r3 == r4) goto L72
            r4 = 44
            if (r3 == r4) goto L4a
            r4 = 91
            if (r3 == r4) goto L45
            r4 = 123(0x7b, float:1.72E-43)
            if (r3 == r4) goto L45
            r4 = 39
            if (r3 == r4) goto L3f
            r4 = 40
            if (r3 == r4) goto L45
            boolean r3 = isWhitespace(r3)
            if (r3 != 0) goto L77
            char r3 = r5[r6]
            boolean r3 = isIdentifierPart(r3)
            if (r3 == 0) goto L37
            goto L77
        L37:
            org.mvel2.CompileException r6 = new org.mvel2.CompileException
            java.lang.String r7 = "expected parameter"
            r6.<init>(r7, r5, r2)
            throw r6
        L3f:
            int r3 = r5.length
            int r6 = captureStringLiteral(r4, r5, r6, r3)
            goto L77
        L45:
            int r6 = balancedCapture(r5, r6, r3)
            goto L77
        L4a:
            if (r6 <= r2) goto L64
        L4c:
            char r3 = r5[r2]
            boolean r3 = isWhitespace(r3)
            if (r3 == 0) goto L57
            int r2 = r2 + 1
            goto L4c
        L57:
            java.lang.String r3 = new java.lang.String
            int r4 = r6 - r2
            r3.<init>(r5, r2, r4)
            checkNameSafety(r3)
            r0.add(r3)
        L64:
            char r2 = r5[r6]
            boolean r2 = isWhitespace(r2)
            if (r2 == 0) goto L6f
            int r6 = r6 + 1
            goto L64
        L6f:
            int r2 = r6 + 1
            goto L77
        L72:
            int r3 = r5.length
            int r6 = captureStringLiteral(r4, r5, r6, r3)
        L77:
            int r6 = r6 + 1
            goto Lc
        L7a:
            if (r2 >= r1) goto L90
            if (r6 <= r2) goto L90
            int r6 = r6 - r2
            java.lang.String r5 = createStringTrimmed(r5, r2, r6)
            int r6 = r5.length()
            if (r6 <= 0) goto La6
            checkNameSafety(r5)
            r0.add(r5)
            goto La6
        L90:
            int r6 = r0.size()
            if (r6 != 0) goto La6
            java.lang.String r5 = createStringTrimmed(r5, r2, r7)
            int r6 = r5.length()
            if (r6 <= 0) goto La6
            checkNameSafety(r5)
            r0.add(r5)
        La6:
            int r5 = r0.size()
            java.lang.String[] r5 = new java.lang.String[r5]
            java.lang.Object[] r5 = r0.toArray(r5)
            java.lang.String[] r5 = (java.lang.String[]) r5
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.parseParameterDefList(char[], int, int):java.lang.String[]");
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<char[]> parseParameterList(char[] r5, int r6, int r7) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = -1
            if (r7 != r1) goto L9
            int r7 = r5.length
        L9:
            int r1 = r6 + r7
            r2 = r6
        Lc:
            if (r6 >= r1) goto L60
            char r3 = r5[r6]
            r4 = 34
            if (r3 == r4) goto L58
            r4 = 44
            if (r3 == r4) goto L34
            r4 = 91
            if (r3 == r4) goto L2f
            r4 = 123(0x7b, float:1.72E-43)
            if (r3 == r4) goto L2f
            r4 = 39
            if (r3 == r4) goto L29
            r4 = 40
            if (r3 == r4) goto L2f
            goto L5d
        L29:
            int r3 = r5.length
            int r6 = captureStringLiteral(r4, r5, r6, r3)
            goto L5d
        L2f:
            int r6 = balancedCapture(r5, r6, r3)
            goto L5d
        L34:
            if (r6 <= r2) goto L4a
        L36:
            char r3 = r5[r2]
            boolean r3 = isWhitespace(r3)
            if (r3 == 0) goto L41
            int r2 = r2 + 1
            goto L36
        L41:
            int r3 = r6 - r2
            char[] r2 = subsetTrimmed(r5, r2, r3)
            r0.add(r2)
        L4a:
            char r2 = r5[r6]
            boolean r2 = isWhitespace(r2)
            if (r2 == 0) goto L55
            int r6 = r6 + 1
            goto L4a
        L55:
            int r2 = r6 + 1
            goto L5d
        L58:
            int r3 = r5.length
            int r6 = captureStringLiteral(r4, r5, r6, r3)
        L5d:
            int r6 = r6 + 1
            goto Lc
        L60:
            if (r2 >= r1) goto L70
            if (r6 <= r2) goto L70
            int r6 = r6 - r2
            char[] r5 = subsetTrimmed(r5, r2, r6)
            int r6 = r5.length
            if (r6 <= 0) goto L80
            r0.add(r5)
            return r0
        L70:
            int r6 = r0.size()
            if (r6 != 0) goto L80
            char[] r5 = subsetTrimmed(r5, r2, r7)
            int r6 = r5.length
            if (r6 <= 0) goto L80
            r0.add(r5)
        L80:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.parseParameterList(char[], int, int):java.util.List");
    }

    public static Method getBestCandidate(Object[] objArr, String str, Class cls, Method[] methodArr, boolean z) {
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            clsArr[i] = obj != null ? obj.getClass() : null;
        }
        return getBestCandidate(clsArr, str, cls, methodArr, z);
    }

    public static Method getBestCandidate(Class[] clsArr, String str, Class cls, Method[] methodArr, boolean z) {
        return getBestCandidate(clsArr, str, cls, methodArr, z, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x005e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.reflect.Method getBestCandidate(java.lang.Class[] r10, java.lang.String r11, java.lang.Class r12, java.lang.reflect.Method[] r13, boolean r14, boolean r15) throws java.lang.SecurityException {
        /*
            int r0 = r13.length
            r1 = 0
            if (r0 != 0) goto L5
            return r1
        L5:
            r0 = 0
            r2 = -1
            r3 = 0
        L8:
            int r4 = r13.length
            r5 = 0
        La:
            if (r5 >= r4) goto L62
            r6 = r13[r5]
            if (r15 == 0) goto L1b
            int r7 = r6.getModifiers()
            boolean r7 = java.lang.reflect.Modifier.isStatic(r7)
            if (r7 != 0) goto L1b
            goto L5f
        L1b:
            java.lang.String r7 = r6.getName()
            boolean r7 = r11.equals(r7)
            if (r7 == 0) goto L5f
            java.lang.Class[] r7 = r6.getParameterTypes()
            int r8 = r7.length
            if (r8 != 0) goto L38
            int r8 = r10.length
            if (r8 != 0) goto L38
            if (r1 == 0) goto L5e
            boolean r7 = isMoreSpecialized(r6, r1)
            if (r7 == 0) goto L5f
            goto L5e
        L38:
            boolean r8 = r6.isVarArgs()
            boolean r9 = isArgsNumberNotCompatible(r10, r7, r8)
            if (r9 == 0) goto L43
            goto L5f
        L43:
            int r7 = getMethodScore(r10, r14, r7, r8)
            if (r7 == 0) goto L5f
            if (r7 <= r2) goto L4e
            r1 = r6
            r2 = r7
            goto L5f
        L4e:
            if (r7 != r2) goto L5f
            boolean r7 = isMoreSpecialized(r6, r1)
            if (r7 != 0) goto L5c
            boolean r7 = isMorePreciseForBigDecimal(r6, r1, r10)
            if (r7 == 0) goto L5f
        L5c:
            if (r8 != 0) goto L5f
        L5e:
            r1 = r6
        L5f:
            int r5 = r5 + 1
            goto La
        L62:
            if (r1 == 0) goto L65
            goto L94
        L65:
            if (r3 != 0) goto L94
            boolean r3 = r12.isInterface()
            if (r3 == 0) goto L94
            java.lang.Class<java.lang.Object> r3 = java.lang.Object.class
            java.lang.reflect.Method[] r3 = r3.getMethods()
            int r4 = r13.length
            int r5 = r3.length
            int r4 = r4 + r5
            java.lang.reflect.Method[] r4 = new java.lang.reflect.Method[r4]
            r5 = 0
        L79:
            int r6 = r13.length
            if (r5 >= r6) goto L83
            r6 = r13[r5]
            r4[r5] = r6
            int r5 = r5 + 1
            goto L79
        L83:
            r5 = 0
        L84:
            int r6 = r3.length
            if (r5 >= r6) goto L90
            int r6 = r13.length
            int r6 = r6 + r5
            r7 = r3[r5]
            r4[r6] = r7
            int r5 = r5 + 1
            goto L84
        L90:
            r3 = 1
            r13 = r4
            goto L8
        L94:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.getBestCandidate(java.lang.Class[], java.lang.String, java.lang.Class, java.lang.reflect.Method[], boolean, boolean):java.lang.reflect.Method");
    }

    private static boolean isArgsNumberNotCompatible(Class[] clsArr, Class<?>[] clsArr2, boolean z) {
        return (z && clsArr2.length - 1 > clsArr.length) || !(z || clsArr2.length == clsArr.length);
    }

    private static boolean isMoreSpecialized(Method method, Method method2) {
        return method2.getReturnType().isAssignableFrom(method.getReturnType()) && method2.getDeclaringClass().isAssignableFrom(method.getDeclaringClass());
    }

    private static boolean isMorePreciseForBigDecimal(Executable executable, Executable executable2, Class[] clsArr) {
        Class<?>[] parameterTypes = executable.getParameterTypes();
        Class<?>[] parameterTypes2 = executable2.getParameterTypes();
        int iComparePrecision = 0;
        for (int i = 0; i != clsArr.length; i++) {
            Class<?> cls = parameterTypes[i];
            Class<?> cls2 = parameterTypes2[i];
            if (clsArr[i] == BigDecimal.class && isNumeric(cls2) && isNumeric(cls)) {
                iComparePrecision += comparePrecision(unboxPrimitive(cls), unboxPrimitive(cls2));
            }
        }
        return iComparePrecision > 0;
    }

    private static int comparePrecision(Class<?> cls, Class<?> cls2) {
        if (cls == cls2) {
            return 0;
        }
        if (cls == BigDecimal.class) {
            return 1;
        }
        Class<?> cls3 = Double.TYPE;
        Class<?> cls4 = Float.TYPE;
        Class<?> cls5 = Long.TYPE;
        Class<?> cls6 = Short.TYPE;
        Class<?> cls7 = Integer.TYPE;
        if (cls == cls3 && (cls2 == cls4 || cls2 == cls5 || cls2 == cls7 || cls2 == cls6 || cls2 == BigInteger.class)) {
            return 1;
        }
        if (cls == cls4 && (cls2 == cls5 || cls2 == cls7 || cls2 == cls6 || cls2 == BigInteger.class)) {
            return 1;
        }
        if (cls == BigInteger.class && (cls2 == cls5 || cls2 == cls7 || cls2 == cls6)) {
            return 1;
        }
        if (cls == cls5 && (cls2 == cls7 || cls2 == cls6)) {
            return 1;
        }
        return (cls == cls7 && cls2 == cls6) ? 1 : -1;
    }

    private static int getMethodScore(Class[] clsArr, boolean z, Class<?>[] clsArr2, boolean z2) {
        Class componentType;
        Class cls;
        int i = 0;
        int i2 = 0;
        int iScoreInterface = 0;
        while (true) {
            if (i2 == clsArr.length) {
                i = iScoreInterface;
                break;
            }
            if (z2 && i2 >= clsArr2.length - 1) {
                componentType = clsArr2[clsArr2.length - 1].getComponentType();
            } else {
                componentType = clsArr2[i2];
            }
            Class cls2 = clsArr[i2];
            if (cls2 == null) {
                if (componentType.isPrimitive()) {
                    break;
                }
                iScoreInterface += 7;
            } else if (componentType == cls2) {
                iScoreInterface += 8;
            } else if ((componentType.isPrimitive() && boxPrimitive(componentType) == clsArr[i2]) || (clsArr[i2].isPrimitive() && unboxPrimitive(clsArr[i2]) == componentType)) {
                iScoreInterface += 7;
            } else if (componentType.isAssignableFrom(clsArr[i2])) {
                iScoreInterface += 6;
            } else if (isPrimitiveSubtype(clsArr[i2], componentType)) {
                iScoreInterface += 5;
            } else if (isNumericallyCoercible(clsArr[i2], componentType)) {
                iScoreInterface += 4;
            } else if (boxPrimitive(componentType).isAssignableFrom(boxPrimitive(clsArr[i2])) && Object.class != (cls = clsArr[i2])) {
                iScoreInterface += scoreInterface(componentType, cls) + 3;
            } else {
                if (!z && DataConversion.canConvert(componentType, clsArr[i2])) {
                    if ((componentType.isArray() && clsArr[i2].isArray()) || (componentType == Character.TYPE && clsArr[i2] == String.class)) {
                        iScoreInterface++;
                    }
                } else if (componentType != Object.class && clsArr[i2] != NullType.class) {
                    break;
                }
                iScoreInterface++;
            }
            i2++;
        }
        return (i == 0 && z2 && clsArr2.length + (-1) == clsArr.length) ? i + 3 : i;
    }

    public static int scoreInterface(Class<?> cls, Class<?> cls2) {
        Class<?>[] interfaces;
        if (cls.isInterface() && (interfaces = cls2.getInterfaces()) != null) {
            for (Class<?> cls3 : interfaces) {
                if (cls3 == cls) {
                    return 1;
                }
                if (cls.isAssignableFrom(cls3)) {
                    return scoreInterface(cls, cls2.getSuperclass());
                }
            }
        }
        return 0;
    }

    public static Method getExactMatch(String str, Class[] clsArr, Class cls, Class cls2) throws SecurityException {
        for (Method method : cls2.getMethods()) {
            if (str.equals(method.getName()) && cls == method.getReturnType()) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == clsArr.length) {
                    for (int i = 0; i < parameterTypes.length; i++) {
                        if (parameterTypes[i] != clsArr[i]) {
                            break;
                        }
                    }
                    return method;
                }
                continue;
            }
        }
        return null;
    }

    public static Method getWidenedTarget(Method method) {
        return getWidenedTarget(method.getDeclaringClass(), method);
    }

    public static Method getWidenedTarget(Class cls, Method method) throws SecurityException {
        if (Modifier.isStatic(method.getModifiers())) {
            return method;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        String name = method.getName();
        Class<?> returnType = method.getReturnType();
        Method method2 = method;
        for (Class superclass = cls; superclass != null; superclass = superclass.getSuperclass()) {
            for (Class<?> cls2 : superclass.getInterfaces()) {
                Method exactMatch = getExactMatch(name, parameterTypes, returnType, cls2);
                if (exactMatch != null) {
                    method2 = exactMatch;
                }
            }
        }
        if (method2 != method) {
            return method2;
        }
        while (cls != null) {
            Method exactMatch2 = getExactMatch(name, parameterTypes, returnType, cls);
            if (exactMatch2 != null) {
                method2 = exactMatch2;
            }
            cls = cls.getSuperclass();
        }
        return method2;
    }

    private static Class[] getConstructors(Constructor constructor) {
        Class[] clsArr;
        Map<Constructor, WeakReference<Class[]>> map = CONSTRUCTOR_PARMS_CACHE;
        WeakReference<Class[]> weakReference = map.get(constructor);
        if (weakReference != null && (clsArr = weakReference.get()) != null) {
            return clsArr;
        }
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        map.put(constructor, new WeakReference<>(parameterTypes));
        return parameterTypes;
    }

    public static Constructor getBestConstructorCandidate(Object[] objArr, Class cls, boolean z) {
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i != objArr.length; i++) {
            Object obj = objArr[i];
            if (obj != null) {
                clsArr[i] = obj.getClass();
            }
        }
        return getBestConstructorCandidate(clsArr, cls, z);
    }

    public static Constructor getBestConstructorCandidate(Class[] clsArr, Class cls, boolean z) throws SecurityException {
        Constructor constructor = null;
        int i = 0;
        boolean z2 = false;
        for (Constructor constructor2 : getConstructors(cls)) {
            boolean zIsVarArgs = constructor2.isVarArgs();
            Class[] constructors = getConstructors(constructor2);
            if (!isArgsNumberNotCompatible(clsArr, constructors, zIsVarArgs)) {
                if (clsArr.length == 0 && constructors.length == 0) {
                    return constructor2;
                }
                int methodScore = getMethodScore(clsArr, z, constructors, zIsVarArgs);
                if (methodScore != 0) {
                    if (methodScore > i) {
                        constructor = constructor2;
                        z2 = zIsVarArgs;
                        i = methodScore;
                    } else if (methodScore == i && (isMorePreciseForBigDecimal(constructor2, constructor, clsArr) || (z2 && !zIsVarArgs))) {
                        constructor = constructor2;
                        z2 = zIsVarArgs;
                    }
                }
            }
        }
        return constructor;
    }

    public static Class createClass(String str, ParserContext parserContext) throws ClassNotFoundException {
        Class<?> cls;
        Class cls2;
        ClassLoader classLoader = parserContext != null ? parserContext.getClassLoader() : Thread.currentThread().getContextClassLoader();
        Map<ClassLoader, Map<String, WeakReference<Class>>> map = CLASS_RESOLVER_CACHE;
        Map<String, WeakReference<Class>> mapSynchronizedMap = map.get(classLoader);
        if (mapSynchronizedMap == null) {
            mapSynchronizedMap = DesugarCollections.synchronizedMap(new WeakHashMap(10));
            map.put(classLoader, mapSynchronizedMap);
        }
        WeakReference<Class> weakReference = mapSynchronizedMap.get(str);
        if (weakReference != null && (cls2 = weakReference.get()) != null) {
            return cls2;
        }
        try {
            cls = Class.forName(str, true, classLoader);
        } catch (ClassNotFoundException e) {
            if (classLoader != Thread.currentThread().getContextClassLoader()) {
                cls = Class.forName(str, true, Thread.currentThread().getContextClassLoader());
            } else {
                throw e;
            }
        }
        mapSynchronizedMap.put(str, new WeakReference<>(cls));
        return cls;
    }

    public static Constructor[] getConstructors(Class cls) throws SecurityException {
        Constructor[] constructorArr;
        Map<Class, WeakReference<Constructor[]>> map = CLASS_CONSTRUCTOR_CACHE;
        WeakReference<Constructor[]> weakReference = map.get(cls);
        if (weakReference != null && (constructorArr = weakReference.get()) != null) {
            return constructorArr;
        }
        Constructor<?>[] constructors = cls.getConstructors();
        map.put(cls, new WeakReference<>(constructors));
        return constructors;
    }

    public static String[] captureContructorAndResidual(char[] cArr, int i, int i2) {
        int i3 = i + i2;
        boolean z = false;
        int i4 = 0;
        for (int i5 = i; i5 < i3; i5++) {
            char c = cArr[i5];
            if (c == '\"') {
                z = !z;
            } else if (c == '(') {
                i4++;
            } else if (c == ')' && !z) {
                int i6 = i4 - 1;
                if (1 == i4) {
                    int i7 = i5 + 1;
                    return new String[]{createStringTrimmed(cArr, i, i7 - i), createStringTrimmed(cArr, i7, i3 - i7)};
                }
                i4 = i6;
            }
        }
        return new String[]{new String(cArr, i, i2)};
    }

    public static Class<?> boxPrimitive(Class cls) {
        Class cls2 = Integer.class;
        if (cls != Integer.TYPE && cls != cls2) {
            cls2 = Integer[].class;
            if (cls != int[].class && cls != cls2) {
                cls2 = Character.class;
                if (cls != Character.TYPE && cls != cls2) {
                    cls2 = Character[].class;
                    if (cls != char[].class && cls != cls2) {
                        cls2 = Long.class;
                        if (cls != Long.TYPE && cls != cls2) {
                            cls2 = Long[].class;
                            if (cls != long[].class && cls != cls2) {
                                cls2 = Short.class;
                                if (cls != Short.TYPE && cls != cls2) {
                                    cls2 = Short[].class;
                                    if (cls != short[].class && cls != cls2) {
                                        cls2 = Double.class;
                                        if (cls != Double.TYPE && cls != cls2) {
                                            cls2 = Double[].class;
                                            if (cls != double[].class && cls != cls2) {
                                                cls2 = Float.class;
                                                if (cls != Float.TYPE && cls != cls2) {
                                                    cls2 = Float[].class;
                                                    if (cls != float[].class && cls != cls2) {
                                                        cls2 = Boolean.class;
                                                        if (cls != Boolean.TYPE && cls != cls2) {
                                                            cls2 = Boolean[].class;
                                                            if (cls != boolean[].class && cls != cls2) {
                                                                cls2 = Byte.class;
                                                                if (cls != Byte.TYPE && cls != cls2) {
                                                                    cls2 = Byte[].class;
                                                                    if (cls != byte[].class && cls != cls2) {
                                                                        return cls;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return cls2;
    }

    public static Class unboxPrimitive(Class cls) {
        Class cls2 = Integer.TYPE;
        if (cls != Integer.class && cls != cls2) {
            cls2 = int[].class;
            if (cls != Integer[].class && cls != cls2) {
                cls2 = Long.TYPE;
                if (cls != Long.class && cls != cls2) {
                    cls2 = long[].class;
                    if (cls != Long[].class && cls != cls2) {
                        cls2 = Character.TYPE;
                        if (cls != Character.class && cls != cls2) {
                            cls2 = char[].class;
                            if (cls != Character[].class && cls != cls2) {
                                cls2 = Short.TYPE;
                                if (cls != Short.class && cls != cls2) {
                                    cls2 = short[].class;
                                    if (cls != Short[].class && cls != cls2) {
                                        cls2 = Double.TYPE;
                                        if (cls != Double.class && cls != cls2) {
                                            cls2 = double[].class;
                                            if (cls != Double[].class && cls != cls2) {
                                                cls2 = Float.TYPE;
                                                if (cls != Float.class && cls != cls2) {
                                                    cls2 = float[].class;
                                                    if (cls != Float[].class && cls != cls2) {
                                                        cls2 = Boolean.TYPE;
                                                        if (cls != Boolean.class && cls != cls2) {
                                                            cls2 = boolean[].class;
                                                            if (cls != Boolean[].class && cls != cls2) {
                                                                cls2 = Byte.TYPE;
                                                                if (cls != Byte.class && cls != cls2) {
                                                                    cls2 = byte[].class;
                                                                    if (cls != Byte[].class && cls != cls2) {
                                                                        return cls;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return cls2;
    }

    public static boolean containsCheck(Object obj, Object obj2) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            return ((String) obj).contains(String.valueOf(obj2));
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).contains(obj2);
        }
        if (obj instanceof Map) {
            return ((Map) obj).containsKey(obj2);
        }
        if (obj.getClass().isArray()) {
            if (obj.getClass().getComponentType().isPrimitive()) {
                return containsCheckOnPrimitveArray(obj, obj2);
            }
            for (Object obj3 : (Object[]) obj) {
                if ((obj2 == null && obj3 == null) || ((Boolean) MathProcessor.doOperations(obj3, 18, obj2)).booleanValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean containsCheckOnPrimitveArray(Object obj, Object obj2) {
        Class<?> componentType = obj.getClass().getComponentType();
        return componentType == Boolean.TYPE ? (obj2 instanceof Boolean) && containsCheckOnBooleanArray((boolean[]) obj, (Boolean) obj2) : componentType == Integer.TYPE ? (obj2 instanceof Integer) && containsCheckOnIntArray((int[]) obj, (Integer) obj2) : componentType == Long.TYPE ? (obj2 instanceof Long) && containsCheckOnLongArray((long[]) obj, (Long) obj2) : componentType == Double.TYPE ? (obj2 instanceof Double) && containsCheckOnDoubleArray((double[]) obj, (Double) obj2) : componentType == Float.TYPE ? (obj2 instanceof Float) && containsCheckOnFloatArray((float[]) obj, (Float) obj2) : componentType == Character.TYPE ? (obj2 instanceof Character) && containsCheckOnCharArray((char[]) obj, (Character) obj2) : componentType == Short.TYPE ? (obj2 instanceof Short) && containsCheckOnShortArray((short[]) obj, (Short) obj2) : componentType == Byte.TYPE && (obj2 instanceof Byte) && containsCheckOnByteArray((byte[]) obj, (Byte) obj2);
    }

    private static boolean containsCheckOnBooleanArray(boolean[] zArr, Boolean bool) {
        boolean zBooleanValue = bool.booleanValue();
        for (boolean z : zArr) {
            if (z == zBooleanValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnIntArray(int[] iArr, Integer num) {
        int iIntValue = num.intValue();
        for (int i : iArr) {
            if (i == iIntValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnLongArray(long[] jArr, Long l) {
        long jLongValue = l.longValue();
        for (long j : jArr) {
            if (j == jLongValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnDoubleArray(double[] dArr, Double d) {
        double dDoubleValue = d.doubleValue();
        for (double d2 : dArr) {
            if (d2 == dDoubleValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnFloatArray(float[] fArr, Float f) {
        float fFloatValue = f.floatValue();
        for (float f2 : fArr) {
            if (f2 == fFloatValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnCharArray(char[] cArr, Character ch) {
        char cCharValue = ch.charValue();
        for (char c : cArr) {
            if (c == cCharValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnShortArray(short[] sArr, Short sh) {
        short sShortValue = sh.shortValue();
        for (short s : sArr) {
            if (s == sShortValue) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsCheckOnByteArray(byte[] bArr, Byte b) {
        byte bByteValue = b.byteValue();
        for (byte b2 : bArr) {
            if (b2 == bByteValue) {
                return true;
            }
        }
        return false;
    }

    public static int handleEscapeSequence(char[] cArr, int i) {
        char c;
        int i2;
        int i3 = i - 1;
        cArr[i3] = 0;
        char c2 = cArr[i];
        if (c2 == '\"') {
            cArr[i] = '\"';
            return 1;
        }
        if (c2 == '\'') {
            cArr[i] = '\'';
            return 1;
        }
        if (c2 == '\\') {
            cArr[i] = '\\';
            return 1;
        }
        if (c2 == 'b') {
            cArr[i] = '\b';
            return 1;
        }
        if (c2 == 'f') {
            cArr[i] = '\f';
            return 1;
        }
        if (c2 == 'n') {
            cArr[i] = '\n';
            return 1;
        }
        if (c2 == 'r') {
            cArr[i] = '\r';
            return 1;
        }
        if (c2 == 't') {
            cArr[i] = '\t';
            return 1;
        }
        if (c2 == 'u') {
            int i4 = i + 4;
            if (i4 > cArr.length) {
                throw new CompileException("illegal unicode escape sequence", cArr, i);
            }
            int i5 = i;
            while (true) {
                i5++;
                if (i5 - i != 5) {
                    char c3 = cArr[i5];
                    if (c3 <= '/' || c3 >= ':') {
                        if (c3 <= '@' || c3 >= 'G') {
                            break;
                        }
                    }
                } else {
                    StringBuilder sb = new StringBuilder();
                    sb.append("0x");
                    int i6 = i + 1;
                    sb.append(new String(cArr, i6, 4));
                    cArr[i3] = (char) Integer.decode(sb.toString()).intValue();
                    cArr[i] = 0;
                    cArr[i6] = 0;
                    cArr[i + 2] = 0;
                    cArr[i + 3] = 0;
                    cArr[i4] = 0;
                    return 5;
                }
            }
            throw new CompileException("illegal unicode escape sequence", cArr, i5);
        }
        int i7 = i;
        do {
            c = cArr[i7];
            if (c < '0' || c >= '8') {
                throw new CompileException("illegal escape sequence: " + cArr[i7], cArr, i7);
            }
            if (i7 != i && cArr[i] > '3') {
                cArr[i3] = (char) Integer.decode(MVEL.VERSION_SUB + new String(cArr, i, (i7 - i) + 1)).intValue();
                cArr[i] = 0;
                cArr[i + 1] = 0;
                return 2;
            }
            i2 = i7 - i;
            if (i2 == 2) {
                cArr[i3] = (char) Integer.decode(MVEL.VERSION_SUB + new String(cArr, i, i2 + 1)).intValue();
                cArr[i] = 0;
                cArr[i + 1] = 0;
                cArr[i + 2] = 0;
                return 3;
            }
            i7++;
            if (i7 == cArr.length || c < '0') {
                break;
            }
        } while (c <= '7');
        cArr[i3] = (char) Integer.decode(MVEL.VERSION_SUB + new String(cArr, i, i2 + 1)).intValue();
        cArr[i] = 0;
        return 1;
    }

    public static char[] createShortFormOperativeAssignment(String str, char[] cArr, int i, int i2, int i3) {
        char c;
        if (i3 == -1) {
            return cArr;
        }
        if (i3 == 0) {
            c = SignatureVisitor.EXTENDS;
        } else if (i3 == 1) {
            c = SignatureVisitor.SUPER;
        } else if (i3 == 2) {
            c = '*';
        } else if (i3 == 3) {
            c = '/';
        } else if (i3 == 4) {
            c = '%';
        } else if (i3 == 6) {
            c = '&';
        } else if (i3 == 7) {
            c = '|';
        } else if (i3 != 20) {
            switch (i3) {
                case 9:
                    c = 187;
                    break;
                case 10:
                    c = 171;
                    break;
                case 11:
                    c = 172;
                    break;
                default:
                    c = 0;
                    break;
            }
        } else {
            c = '#';
        }
        char[] charArray = str.toCharArray();
        char[] cArr2 = new char[str.length() + i2 + 1];
        System.arraycopy(charArray, 0, cArr2, 0, str.length());
        cArr2[str.length()] = c;
        System.arraycopy(cArr, i, cArr2, str.length() + 1, i2);
        return cArr2;
    }

    public static ClassImportResolverFactory findClassImportResolverFactory(VariableResolverFactory variableResolverFactory, ParserContext parserContext) {
        if (variableResolverFactory == null) {
            throw new OptimizationFailure("unable to import classes.  no variable resolver factory available.");
        }
        for (VariableResolverFactory nextFactory = variableResolverFactory; nextFactory != null; nextFactory = nextFactory.getNextFactory()) {
            if (nextFactory instanceof ClassImportResolverFactory) {
                return (ClassImportResolverFactory) nextFactory;
            }
        }
        return (ClassImportResolverFactory) ResolverTools.appendFactory(variableResolverFactory, new ClassImportResolverFactory(null, null, false));
    }

    public static Class findClass(VariableResolverFactory variableResolverFactory, String str, ParserContext parserContext) throws ClassNotFoundException {
        try {
            if (AbstractParser.LITERALS.containsKey(str)) {
                return (Class) AbstractParser.LITERALS.get(str);
            }
            if (variableResolverFactory != null && variableResolverFactory.isResolveable(str)) {
                return (Class) variableResolverFactory.getVariableResolver(str).getValue();
            }
            if (parserContext != null && parserContext.hasImport(str)) {
                return parserContext.getImport(str);
            }
            return createClass(str, parserContext);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e2) {
            throw new RuntimeException("class not found: " + str, e2);
        }
    }

    public static char[] subsetTrimmed(char[] cArr, int i, int i2) {
        if (i2 <= 0) {
            return new char[0];
        }
        int i3 = i2 + i;
        while (i3 > 0 && isWhitespace(cArr[i3 - 1])) {
            i3--;
        }
        while (isWhitespace(cArr[i]) && i < i3) {
            i++;
        }
        int i4 = i3 - i;
        if (i4 == 0) {
            return new char[0];
        }
        return subset(cArr, i, i4);
    }

    public static char[] subset(char[] cArr, int i, int i2) {
        char[] cArr2 = new char[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            cArr2[i3] = cArr[i3 + i];
        }
        return cArr2;
    }

    public static char[] subset(char[] cArr, int i) {
        int length = cArr.length - i;
        char[] cArr2 = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            cArr2[i2] = cArr[i2 + i];
        }
        return cArr2;
    }

    public static int resolveType(Object obj) {
        if (obj == null) {
            return 0;
        }
        return __resolveType(obj.getClass());
    }

    public static int __resolveType(Class cls) {
        Integer num = typeCodes.get(cls);
        if (num == null) {
            return (cls == null || !Collection.class.isAssignableFrom(cls)) ? 0 : 50;
        }
        return num.intValue();
    }

    private static boolean isPrimitiveSubtype(Class cls, Class<?> cls2) {
        if (!cls2.isPrimitive()) {
            return false;
        }
        Class<?> clsUnboxPrimitive = unboxPrimitive(cls);
        if (!clsUnboxPrimitive.isPrimitive()) {
            return false;
        }
        Class<?> cls3 = Double.TYPE;
        Class<?> cls4 = Float.TYPE;
        if (cls2 == cls3 && clsUnboxPrimitive == cls4) {
            return true;
        }
        Class<?> cls5 = Long.TYPE;
        if (cls2 == cls4 && clsUnboxPrimitive == cls5) {
            return true;
        }
        Class<?> cls6 = Integer.TYPE;
        if (cls2 == cls5 && clsUnboxPrimitive == cls6) {
            return true;
        }
        if (cls2 == cls6 && clsUnboxPrimitive == Character.TYPE) {
            return true;
        }
        Class<?> cls7 = Short.TYPE;
        if (cls2 == cls6 && clsUnboxPrimitive == cls7) {
            return true;
        }
        return cls2 == cls7 && clsUnboxPrimitive == Byte.TYPE;
    }

    public static boolean isNumericallyCoercible(Class cls, Class cls2) {
        if ((cls.isPrimitive() ? boxPrimitive(cls) : cls) == null || !Number.class.isAssignableFrom(cls)) {
            return false;
        }
        if (cls2.isPrimitive()) {
            cls2 = boxPrimitive(cls2);
        }
        if (cls2 != null) {
            return Number.class.isAssignableFrom(cls2);
        }
        return false;
    }

    public static Object narrowType(BigDecimal bigDecimal, int i) {
        if (i == 109 || bigDecimal.scale() > 0) {
            return Double.valueOf(bigDecimal.doubleValue());
        }
        if (i == 107 || bigDecimal.longValue() > 2147483647L) {
            return Long.valueOf(bigDecimal.longValue());
        }
        return Integer.valueOf(bigDecimal.intValue());
    }

    public static Method determineActualTargetMethod(Method method) {
        return determineActualTargetMethod(method.getDeclaringClass(), method);
    }

    private static Method determineActualTargetMethod(Class cls, Method method) throws SecurityException {
        String name = method.getName();
        for (Class<?> cls2 : cls.getInterfaces()) {
            for (Method method2 : cls2.getMethods()) {
                if (method2.getParameterTypes().length == 0 && name.equals(method2.getName())) {
                    return method2;
                }
            }
        }
        if (cls.getSuperclass() != null) {
            return determineActualTargetMethod(cls.getSuperclass(), method);
        }
        return null;
    }

    public static int captureToNextTokenJunction(char[] cArr, int i, int i2, ParserContext parserContext) {
        char c;
        while (i != cArr.length && (c = cArr[i]) != '(') {
            if (c == '[') {
                i = balancedCaptureWithLineAccounting(cArr, i, i2, '[', parserContext);
            } else if (c == '{' || isWhitespace(c)) {
                break;
            }
            i++;
        }
        return i;
    }

    public static int nextNonBlank(char[] cArr, int i) {
        if (i + 1 >= cArr.length) {
            throw new CompileException("unexpected end of statement", cArr, i);
        }
        while (i != cArr.length && isWhitespace(cArr[i])) {
            i++;
        }
        return i;
    }

    public static int skipWhitespace(char[] cArr, int i) {
        int i2;
        while (i != cArr.length) {
            char c = cArr[i];
            if (c != '\n' && c != '\r') {
                if (c == '/' && (i2 = i + 1) != cArr.length) {
                    char c2 = cArr[i2];
                    if (c2 == '*') {
                        int length = cArr.length - 1;
                        cArr[i] = ' ';
                        i = i2;
                        while (i != length && (cArr[i] != '*' || cArr[i + 1] != '/')) {
                            cArr[i] = ' ';
                            i++;
                        }
                        if (i != length) {
                            cArr[i + 1] = ' ';
                            cArr[i] = ' ';
                            i += 2;
                        }
                    } else {
                        if (c2 != '/') {
                            break;
                        }
                        cArr[i] = ' ';
                        i = i2;
                        while (i != cArr.length && cArr[i] != '\n') {
                            cArr[i] = ' ';
                            i++;
                        }
                        if (i != cArr.length) {
                            cArr[i] = ' ';
                            i++;
                        }
                    }
                } else if (!isWhitespace(c)) {
                    break;
                }
            }
            i++;
        }
        return i;
    }

    public static boolean isStatementNotManuallyTerminated(char[] cArr, int i) {
        if (i >= cArr.length) {
            return false;
        }
        while (i != cArr.length && isWhitespace(cArr[i])) {
            i++;
        }
        return i == cArr.length || cArr[i] != ';';
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x002e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int captureToEOS(char[] r2, int r3, int r4, org.mvel2.ParserContext r5) {
        /*
        L0:
            int r0 = r2.length
            if (r3 == r0) goto L36
            char r0 = r2[r3]
            r1 = 34
            if (r0 == r1) goto L2e
            r1 = 44
            if (r0 == r1) goto L36
            r1 = 59
            if (r0 == r1) goto L36
            r1 = 91
            if (r0 == r1) goto L26
            r1 = 123(0x7b, float:1.72E-43)
            if (r0 == r1) goto L26
            r1 = 125(0x7d, float:1.75E-43)
            if (r0 == r1) goto L36
            r1 = 39
            if (r0 == r1) goto L2e
            r1 = 40
            if (r0 == r1) goto L26
            goto L33
        L26:
            int r3 = balancedCaptureWithLineAccounting(r2, r3, r4, r0, r5)
            int r0 = r2.length
            if (r3 < r0) goto L33
            return r3
        L2e:
            int r1 = r2.length
            int r3 = captureStringLiteral(r0, r2, r3, r1)
        L33:
            int r3 = r3 + 1
            goto L0
        L36:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.captureToEOS(char[], int, int, org.mvel2.ParserContext):int");
    }

    public static int trimLeft(char[] cArr, int i, int i2) {
        if (i2 > cArr.length) {
            i2 = cArr.length;
        }
        while (i2 != 0 && i2 >= i && isWhitespace(cArr[i2 - 1])) {
            i2--;
        }
        return i2;
    }

    public static int trimRight(char[] cArr, int i) {
        while (i != cArr.length && isWhitespace(cArr[i])) {
            i++;
        }
        return i;
    }

    public static char[] subArray(char[] cArr, int i, int i2) {
        if (i >= i2) {
            return new char[0];
        }
        int i3 = i2 - i;
        char[] cArr2 = new char[i3];
        for (int i4 = 0; i4 != i3; i4++) {
            cArr2[i4] = cArr[i4 + i];
        }
        return cArr2;
    }

    public static int balancedCapture(char[] cArr, int i, char c) {
        return balancedCapture(cArr, i, cArr.length, c);
    }

    public static int balancedCapture(char[] cArr, int i, int i2, char c) {
        int iCaptureStringLiteral;
        int i3;
        char c2 = c != '(' ? c != '[' ? c != '{' ? c : '}' : ']' : ')';
        if (c != c2) {
            iCaptureStringLiteral = i + 1;
            int i4 = 1;
            while (iCaptureStringLiteral < i2) {
                if (iCaptureStringLiteral < i2 && cArr[iCaptureStringLiteral] == '/') {
                    int i5 = iCaptureStringLiteral + 1;
                    if (i5 == i2) {
                        return iCaptureStringLiteral;
                    }
                    char c3 = cArr[i5];
                    if (c3 == '/') {
                        iCaptureStringLiteral = i5;
                        while (iCaptureStringLiteral < i2 && cArr[iCaptureStringLiteral] != '\n') {
                            iCaptureStringLiteral++;
                        }
                    } else if (c3 == '*') {
                        iCaptureStringLiteral += 2;
                        while (iCaptureStringLiteral < i2 && (cArr[iCaptureStringLiteral] != '*' || (i3 = iCaptureStringLiteral + 1) >= i2 || cArr[i3] != '/')) {
                            iCaptureStringLiteral++;
                        }
                    }
                }
                if (iCaptureStringLiteral != i2) {
                    char c4 = cArr[iCaptureStringLiteral];
                    if (c4 == '\'' || c4 == '\"') {
                        iCaptureStringLiteral = captureStringLiteral(c4, cArr, iCaptureStringLiteral, i2);
                    } else if (c4 == c) {
                        i4++;
                    } else if (c4 != c2 || i4 - 1 != 0) {
                    }
                    iCaptureStringLiteral++;
                }
                return iCaptureStringLiteral;
            }
        }
        iCaptureStringLiteral = i + 1;
        while (iCaptureStringLiteral < i2) {
            if (cArr[iCaptureStringLiteral] == '\\') {
                iCaptureStringLiteral = skipStringEscape(iCaptureStringLiteral);
            }
            if (cArr[iCaptureStringLiteral] == c) {
                return iCaptureStringLiteral;
            }
            iCaptureStringLiteral++;
        }
        if (c == '(') {
            throw new CompileException("unbalanced braces ( ... )", cArr, iCaptureStringLiteral);
        }
        if (c == '[') {
            throw new CompileException("unbalanced braces [ ... ]", cArr, iCaptureStringLiteral);
        }
        if (c == '{') {
            throw new CompileException("unbalanced braces { ... }", cArr, iCaptureStringLiteral);
        }
        throw new CompileException("unterminated string literal", cArr, iCaptureStringLiteral);
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00b6 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int balancedCaptureWithLineAccounting(char[] r17, int r18, int r19, char r20, org.mvel2.ParserContext r21) {
        /*
            Method dump skipped, instructions count: 233
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.balancedCaptureWithLineAccounting(char[], int, int, char, org.mvel2.ParserContext):int");
    }

    public static String handleStringEscapes(char[] cArr) {
        int i = 0;
        int iHandleEscapeSequence = 0;
        while (i < cArr.length) {
            if (cArr[i] == '\\') {
                i++;
                iHandleEscapeSequence += handleEscapeSequence(cArr, i);
            }
            i++;
        }
        if (iHandleEscapeSequence == 0) {
            return new String(cArr);
        }
        char[] cArr2 = new char[cArr.length - iHandleEscapeSequence];
        int i2 = 0;
        for (char c : cArr) {
            if (c != 0) {
                cArr2[i2] = c;
                i2++;
            }
        }
        return new String(cArr2);
    }

    public static int captureStringLiteral(char c, char[] cArr, int i, int i2) {
        int i3;
        char c2;
        while (true) {
            i3 = i + 1;
            if (i3 >= i2 || (c2 = cArr[i3]) == c) {
                break;
            }
            i = c2 == '\\' ? i + 2 : i3;
        }
        if (i3 >= i2 || cArr[i3] != c) {
            throw new CompileException("unterminated string literal", cArr, i3);
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x014b, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0105 A[PHI: r8
      0x0105: PHI (r8v8 int) = (r8v6 int), (r8v9 int) binds: [B:70:0x012d, B:57:0x0103] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void parseWithExpressions(java.lang.String r17, char[] r18, int r19, int r20, java.lang.Object r21, org.mvel2.integration.VariableResolverFactory r22) {
        /*
            Method dump skipped, instructions count: 474
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.parseWithExpressions(java.lang.String, char[], int, int, java.lang.Object, org.mvel2.integration.VariableResolverFactory):void");
    }

    public static Object handleNumericConversion(char[] cArr, int i, int i2) {
        if (i2 != 1 && cArr[i] == '0' && cArr[i + 1] != '.') {
            int i3 = (i + i2) - 1;
            if (!isDigit(cArr[i3])) {
                char c = cArr[i3];
                if (c == 'B') {
                    return new BigDecimal(new String(cArr, i, i2 - 1));
                }
                if (c == 'I') {
                    return new BigInteger(new String(cArr, i, i2 - 1));
                }
                if (c == 'L' || c == 'l') {
                    return Long.decode(new String(cArr, i, i2 - 1));
                }
            }
            return Integer.decode(new String(cArr, i, i2));
        }
        int i4 = (i + i2) - 1;
        if (!isDigit(cArr[i4])) {
            char c2 = cArr[i4];
            if (c2 != '.') {
                if (c2 == 'B') {
                    return new BigDecimal(new String(cArr, i, i2 - 1));
                }
                if (c2 != 'D') {
                    if (c2 != 'F') {
                        if (c2 != 'I') {
                            if (c2 != 'L') {
                                if (c2 != 'd') {
                                    if (c2 != 'f') {
                                        if (c2 != 'l') {
                                            throw new CompileException("unrecognized numeric literal", cArr, i);
                                        }
                                    }
                                }
                            }
                            return Long.valueOf(Long.parseLong(new String(cArr, i, i2 - 1)));
                        }
                        return new BigInteger(new String(cArr, i, i2 - 1));
                    }
                    return Float.valueOf(Float.parseFloat(new String(cArr, i, i2 - 1)));
                }
            }
            return Double.valueOf(Double.parseDouble(new String(cArr, i, i2 - 1)));
        }
        int iNumericTest = numericTest(cArr, i, i2);
        if (iNumericTest != 110) {
            switch (iNumericTest) {
                case 101:
                    return Integer.valueOf(Integer.parseInt(new String(cArr, i, i2)));
                case 102:
                    return Long.valueOf(Long.parseLong(new String(cArr, i, i2)));
                case 103:
                    return Double.valueOf(Double.parseDouble(new String(cArr, i, i2)));
                case 104:
                    return Float.valueOf(Float.parseFloat(new String(cArr, i, i2)));
                default:
                    return new String(cArr, i, i2);
            }
        }
        return new BigDecimal(cArr, MathContext.DECIMAL128);
    }

    public static boolean isNumeric(Object obj) {
        Class<?> cls;
        if (obj == null) {
            return false;
        }
        if (obj instanceof Class) {
            cls = (Class) obj;
        } else {
            cls = obj.getClass();
        }
        return cls == Integer.TYPE || cls == Long.TYPE || cls == Short.TYPE || cls == Double.TYPE || cls == Float.TYPE || Number.class.isAssignableFrom(cls);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0019  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static int numericTest(char[] r8, int r9, int r10) {
        /*
            r0 = 45
            r1 = 1
            if (r10 <= r1) goto L19
            char r2 = r8[r9]
            if (r2 != r0) goto Lc
            int r2 = r9 + 1
            goto L1a
        Lc:
            r3 = 126(0x7e, float:1.77E-43)
            if (r2 != r3) goto L19
            int r2 = r9 + 1
            char r3 = r8[r2]
            if (r3 != r0) goto L1a
            int r2 = r9 + 2
            goto L1a
        L19:
            r2 = r9
        L1a:
            int r9 = r9 + r10
            r3 = 0
        L1c:
            r4 = -1
            r5 = 101(0x65, float:1.42E-43)
            if (r2 >= r9) goto L44
            char r6 = r8[r2]
            boolean r7 = isDigit(r6)
            if (r7 != 0) goto L42
            r3 = 46
            if (r6 == r3) goto L3e
            r3 = 69
            if (r6 == r3) goto L34
            if (r6 == r5) goto L34
            return r4
        L34:
            int r3 = r2 + 1
            if (r2 >= r9) goto L40
            char r4 = r8[r3]
            if (r4 != r0) goto L40
            int r2 = r2 + 2
        L3e:
            r3 = 1
            goto L42
        L40:
            r2 = r3
            goto L3e
        L42:
            int r2 = r2 + r1
            goto L1c
        L44:
            if (r10 == 0) goto L53
            if (r3 == 0) goto L4b
            r8 = 103(0x67, float:1.44E-43)
            return r8
        L4b:
            r8 = 9
            if (r10 <= r8) goto L52
            r8 = 102(0x66, float:1.43E-43)
            return r8
        L52:
            return r5
        L53:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.numericTest(char[], int, int):int");
    }

    public static boolean isNumber(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj instanceof String ? isNumber((String) obj) : obj instanceof char[] ? isNumber(new String((char[]) obj)) : (obj instanceof Integer) || (obj instanceof BigDecimal) || (obj instanceof BigInteger) || (obj instanceof Float) || (obj instanceof Double) || (obj instanceof Long) || (obj instanceof Short) || (obj instanceof Character);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /* JADX WARN: Removed duplicated region for block: B:6:0x0010  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isNumber(java.lang.String r7) {
        /*
            int r0 = r7.length()
            r1 = 1
            r2 = 0
            if (r0 <= r1) goto L23
            char r3 = r7.charAt(r2)
            r4 = 45
            if (r3 != r4) goto L13
        L10:
            r3 = 1
        L11:
            r4 = 1
            goto L25
        L13:
            char r3 = r7.charAt(r2)
            r5 = 126(0x7e, float:1.77E-43)
            if (r3 != r5) goto L23
            char r3 = r7.charAt(r1)
            if (r3 != r4) goto L10
            r3 = 2
            goto L11
        L23:
            r3 = 0
            goto L11
        L25:
            if (r3 >= r0) goto L3d
            char r5 = r7.charAt(r3)
            boolean r6 = isDigit(r5)
            if (r6 != 0) goto L3a
            r6 = 46
            if (r5 != r6) goto L39
            if (r4 == 0) goto L39
            r4 = 0
            goto L3a
        L39:
            return r2
        L3a:
            int r3 = r3 + 1
            goto L25
        L3d:
            if (r0 <= 0) goto L40
            return r1
        L40:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.isNumber(java.lang.String):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0013, code lost:
    
        if (r6 != '~') goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean isNumber(char[] r18, int r19, int r20) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.util.ParseTools.isNumber(char[], int, int):boolean");
    }

    public static int find(char[] cArr, int i, int i2, char c) {
        int i3 = i2 + i;
        while (i < i3) {
            if (cArr[i] == c) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public static int findLast(char[] cArr, int i, int i2, char c) {
        for (int i3 = i2 + i; i3 >= i; i3--) {
            if (cArr[i3] == c) {
                return i3;
            }
        }
        return -1;
    }

    public static String createStringTrimmed(char[] cArr) {
        int length = cArr.length;
        int i = 0;
        while (i != length && cArr[i] < '!') {
            i++;
        }
        while (length != i && cArr[length - 1] < '!') {
            length--;
        }
        return new String(cArr, i, length - i);
    }

    public static String createStringTrimmed(char[] cArr, int i, int i2) {
        int i3 = i2 + i;
        if (i3 > cArr.length) {
            return new String(cArr);
        }
        while (i != i3 && cArr[i] < '!') {
            i++;
        }
        while (i3 != i && cArr[i3 - 1] < '!') {
            i3--;
        }
        return new String(cArr, i, i3 - i);
    }

    public static boolean endsWith(char[] cArr, int i, int i2, char[] cArr2) {
        if (cArr2.length > cArr.length) {
            return false;
        }
        int length = cArr2.length - 1;
        int i3 = (i + i2) - 1;
        while (length >= 0) {
            int i4 = i3 - 1;
            int i5 = length - 1;
            if (cArr[i3] != cArr2[length]) {
                return false;
            }
            i3 = i4;
            length = i5;
        }
        return true;
    }

    public static boolean isIdentifierPart(int i) {
        if (i > 96 && i < 123) {
            return true;
        }
        if (i <= 64 || i >= 91) {
            return (i > 47 && i < 58) || i == 95 || i == 36 || Character.isJavaIdentifierPart(i);
        }
        return true;
    }

    public static float similarity(String str, String str2) {
        float length;
        float f = 0.0f;
        if (str == null || str2 == null) {
            return (str == null && str2 == null) ? 1.0f : 0.0f;
        }
        char[] charArray = str.toCharArray();
        char[] charArray2 = str2.toCharArray();
        if (charArray.length > charArray2.length) {
            length = charArray.length;
        } else {
            length = charArray2.length;
            charArray2 = charArray;
            charArray = charArray2;
        }
        for (int i = 0; i < charArray.length && i < charArray2.length; i++) {
            if (charArray[i] == charArray2[i]) {
                f += 1.0f;
            }
        }
        return f / length;
    }

    public static int findAbsoluteLast(char[] cArr) {
        int i = 0;
        for (int length = cArr.length - 1; length >= 0; length--) {
            char c = cArr[length];
            if (c == ']') {
                i++;
            }
            if (c == '[') {
                i--;
            }
            if ((i == 0 && c == '.') || c == '[') {
                return length;
            }
        }
        return -1;
    }

    public static Class getBaseComponentType(Class cls) {
        while (cls.isArray()) {
            cls = cls.getComponentType();
        }
        return cls;
    }

    public static Class getSubComponentType(Class cls) {
        return cls.isArray() ? cls.getComponentType() : cls;
    }

    public static boolean isJunct(char c) {
        if (c == '(' || c == '[') {
            return true;
        }
        return isWhitespace(c);
    }

    public static boolean isReservedWord(String str) {
        return AbstractParser.LITERALS.containsKey(str) || AbstractParser.OPERATORS.containsKey(str);
    }

    public static boolean isNotValidNameorLabel(String str) {
        for (char c : str.toCharArray()) {
            if (c == '.' || !isIdentifierPart(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPropertyOnly(char[] cArr, int i, int i2) {
        while (i < i2) {
            if (!isIdentifierPart(cArr[i])) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static boolean isArrayType(char[] cArr, int i, int i2) {
        if (i2 <= i + 2) {
            return false;
        }
        int i3 = i2 - 2;
        return isPropertyOnly(cArr, i, i3) && cArr[i3] == '[' && cArr[i2 - 1] == ']';
    }

    public static void checkNameSafety(String str) {
        if (isReservedWord(str)) {
            throw new RuntimeException("illegal use of reserved word: " + str);
        }
        if (isDigit(str.charAt(0))) {
            throw new RuntimeException("not an identifier: " + str);
        }
    }

    public static FileWriter getDebugFileWriter() {
        return new FileWriter(new File(MVEL.getDebuggingOutputFileName()), true);
    }

    public static boolean isPrimitiveWrapper(Class cls) {
        return cls == Integer.class || cls == Boolean.class || cls == Long.class || cls == Double.class || cls == Float.class || cls == Character.class || cls == Short.class || cls == Byte.class;
    }

    public static Serializable subCompileExpression(char[] cArr) {
        return _optimizeTree(new ExpressionCompiler(cArr)._compile());
    }

    public static Serializable subCompileExpression(char[] cArr, ParserContext parserContext) {
        return _optimizeTree(new ExpressionCompiler(cArr, parserContext)._compile());
    }

    public static Serializable subCompileExpression(char[] cArr, int i, int i2, ParserContext parserContext) {
        return _optimizeTree(new ExpressionCompiler(cArr, i, i2, parserContext)._compile());
    }

    public static Serializable subCompileExpression(String str, ParserContext parserContext) {
        return _optimizeTree(new ExpressionCompiler(str, parserContext)._compile());
    }

    public static Serializable optimizeTree(CompiledExpression compiledExpression) {
        return (!compiledExpression.isImportInjectionRequired() && compiledExpression.getParserConfiguration().isAllowBootstrapBypass() && compiledExpression.isSingleNode()) ? _optimizeTree(compiledExpression) : compiledExpression;
    }

    private static Serializable _optimizeTree(CompiledExpression compiledExpression) {
        if (!compiledExpression.isSingleNode()) {
            return compiledExpression;
        }
        ASTNode firstNode = compiledExpression.getFirstNode();
        if (!firstNode.isLiteral() || firstNode.isThisVal()) {
            return firstNode.canSerializeAccessor() ? new ExecutableAccessorSafe(firstNode, compiledExpression.getKnownEgressType()) : new ExecutableAccessor(firstNode, compiledExpression.getKnownEgressType());
        }
        return new ExecutableLiteral(firstNode.getLiteralValue());
    }

    public static String repeatChar(char c, int i) {
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = c;
        }
        return new String(cArr);
    }

    public static char[] loadFromFile(File file) {
        return loadFromFile(file, null);
    }

    public static char[] loadFromFile(File file, String str) throws Throwable {
        FileChannel channel;
        FileInputStream fileInputStream;
        if (!file.exists()) {
            throw new RuntimeException("cannot find file: " + file.getName());
        }
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                channel = fileInputStream.getChannel();
            } catch (FileNotFoundException unused) {
                channel = null;
            } catch (Throwable th) {
                th = th;
                channel = null;
            }
        } catch (FileNotFoundException unused2) {
            fileInputStream = null;
            channel = null;
        } catch (Throwable th2) {
            th = th2;
            channel = null;
        }
        try {
            ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(10);
            StringAppender stringAppender = new StringAppender((int) file.length(), str);
            int i = 0;
            while (i >= 0) {
                byteBufferAllocateDirect.rewind();
                i = channel.read(byteBufferAllocateDirect);
                byteBufferAllocateDirect.rewind();
                while (i > 0) {
                    stringAppender.append(byteBufferAllocateDirect.get());
                    i--;
                }
            }
            char[] chars = stringAppender.toChars();
            fileInputStream.close();
            if (channel != null) {
                channel.close();
            }
            return chars;
        } catch (FileNotFoundException unused3) {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (channel != null) {
                channel.close();
            }
            return null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (channel != null) {
                channel.close();
            }
            throw th;
        }
    }

    public static char[] readIn(InputStream inputStream, String str) throws IOException {
        try {
            byte[] bArr = new byte[10];
            StringAppender stringAppender = new StringAppender(10, str);
            while (true) {
                int i = inputStream.read(bArr);
                if (i <= 0) {
                    char[] chars = stringAppender.toChars();
                    inputStream.close();
                    return chars;
                }
                for (int i2 = 0; i2 < i; i2++) {
                    stringAppender.append(bArr[i2]);
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                inputStream.close();
            }
            throw th;
        }
    }

    public static Class forNameWithInner(String str, ClassLoader classLoader) {
        try {
            return classLoader.loadClass(str);
        } catch (ClassNotFoundException e) {
            return findInnerClass(str, classLoader, e);
        }
    }

    public static Class findInnerClass(String str, ClassLoader classLoader, ClassNotFoundException classNotFoundException) throws ClassNotFoundException {
        while (true) {
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf > 0) {
                str = str.substring(0, iLastIndexOf) + "$" + str.substring(iLastIndexOf + 1);
                try {
                    return classLoader.loadClass(str);
                } catch (ClassNotFoundException unused) {
                }
            } else {
                throw classNotFoundException;
            }
        }
    }
}
