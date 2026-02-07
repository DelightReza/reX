package org.mvel2.optimizers.impl.asm;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.mvel2.CompileException;
import org.mvel2.DataConversion;
import org.mvel2.MVEL;
import org.mvel2.OptimizationFailure;
import org.mvel2.ParserContext;
import org.mvel2.PropertyAccessException;
import org.mvel2.asm.ClassWriter;
import org.mvel2.asm.Label;
import org.mvel2.asm.MethodVisitor;
import org.mvel2.asm.Opcodes;
import org.mvel2.asm.Type;
import org.mvel2.ast.TypeDescriptor;
import org.mvel2.ast.WithNode;
import org.mvel2.compiler.AbstractParser;
import org.mvel2.compiler.Accessor;
import org.mvel2.compiler.ExecutableLiteral;
import org.mvel2.compiler.ExecutableStatement;
import org.mvel2.compiler.PropertyVerifier;
import org.mvel2.integration.GlobalListenerFactory;
import org.mvel2.integration.PropertyHandler;
import org.mvel2.integration.PropertyHandlerFactory;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.optimizers.AbstractOptimizer;
import org.mvel2.optimizers.AccessorOptimizer;
import org.mvel2.optimizers.OptimizationNotSupported;
import org.mvel2.optimizers.impl.refl.nodes.Union;
import org.mvel2.util.ArrayTools;
import org.mvel2.util.JITClassLoader;
import org.mvel2.util.MVELClassLoader;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;
import org.mvel2.util.ReflectionUtil;
import org.mvel2.util.StringAppender;

/* loaded from: classes4.dex */
public class ASMAccessorOptimizer extends AbstractOptimizer implements AccessorOptimizer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ARRAY = 0;
    private static final Object[] EMPTYARG;
    private static final Class[] EMPTYCLS;
    private static final int LIST = 1;
    private static String LIST_IMPL = null;
    private static final int MAP = 2;
    private static final String MAP_IMPL = "java/util/HashMap";
    private static String NAMESPACE = null;
    private static final int OPCODES_VERSION;
    private static final int VAL = 3;
    private static MVELClassLoader classLoader;
    private StringAppender buildLog;
    private String className;
    private int compileDepth;
    private ArrayList<ExecutableStatement> compiledInputs;
    private Object ctx;

    /* renamed from: cw */
    private ClassWriter f1421cw;
    private boolean deferFinish;
    private boolean first;
    private Class ingressType;
    private boolean literal;
    private int maxlocals;
    private boolean methNull;

    /* renamed from: mv */
    private MethodVisitor f1422mv;
    private boolean noinit;
    private boolean propNull;
    private Class returnType;
    private int stacksize;
    private Object thisRef;
    private long time;
    private Object val;
    private VariableResolverFactory variableFactory;

    static {
        String javaVersion = PropertyTools.getJavaVersion();
        if (javaVersion.startsWith("1.4")) {
            OPCODES_VERSION = 48;
        } else if (javaVersion.startsWith("1.5")) {
            OPCODES_VERSION = 49;
        } else {
            OPCODES_VERSION = 50;
        }
        String property = System.getProperty("mvel2.namespace");
        if (property == null) {
            NAMESPACE = "org/mvel2/";
        } else {
            NAMESPACE = property;
        }
        String property2 = System.getProperty("mvel2.jit.list_impl");
        if (property2 == null) {
            LIST_IMPL = NAMESPACE + "util/FastList";
        } else {
            LIST_IMPL = property2;
        }
        EMPTYARG = new Object[0];
        EMPTYCLS = new Class[0];
    }

    public ASMAccessorOptimizer() {
        this.first = true;
        this.noinit = false;
        this.deferFinish = false;
        this.literal = false;
        this.propNull = false;
        this.methNull = false;
        this.stacksize = 1;
        this.maxlocals = 1;
        this.compileDepth = 0;
        new ClassWriter(1);
    }

    private ASMAccessorOptimizer(ClassWriter classWriter, MethodVisitor methodVisitor, ArrayList<ExecutableStatement> arrayList, String str, StringAppender stringAppender, int i) {
        this.first = true;
        this.literal = false;
        this.propNull = false;
        this.methNull = false;
        this.stacksize = 1;
        this.maxlocals = 1;
        this.f1421cw = classWriter;
        this.f1422mv = methodVisitor;
        this.compiledInputs = arrayList;
        this.className = str;
        this.buildLog = stringAppender;
        this.compileDepth = i + 1;
        this.noinit = true;
        this.deferFinish = true;
    }

    private void _initJIT() {
        if (MVEL.isAdvancedDebugging()) {
            this.buildLog = new StringAppender();
        }
        this.f1421cw = new ClassWriter(3);
        synchronized (Runtime.getRuntime()) {
            ClassWriter classWriter = this.f1421cw;
            int i = OPCODES_VERSION;
            String str = "ASMAccessorImpl_" + String.valueOf(this.f1421cw.hashCode()).replaceAll("\\-", "_") + (System.currentTimeMillis() / 10) + ((int) (Math.random() * 100.0d));
            this.className = str;
            classWriter.visit(i, 33, str, null, "java/lang/Object", new String[]{NAMESPACE + "compiler/Accessor"});
        }
        MethodVisitor methodVisitorVisitMethod = this.f1421cw.visitMethod(1, "<init>", "()V", null, null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        methodVisitorVisitMethod.visitInsn(Opcodes.RETURN);
        methodVisitorVisitMethod.visitMaxs(1, 1);
        methodVisitorVisitMethod.visitEnd();
        MethodVisitor methodVisitorVisitMethod2 = this.f1421cw.visitMethod(1, "getValue", "(Ljava/lang/Object;Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;)Ljava/lang/Object;", null, null);
        this.f1422mv = methodVisitorVisitMethod2;
        methodVisitorVisitMethod2.visitCode();
    }

    private void _initJIT2() {
        if (MVEL.isAdvancedDebugging()) {
            this.buildLog = new StringAppender();
        }
        this.f1421cw = new ClassWriter(3);
        synchronized (Runtime.getRuntime()) {
            ClassWriter classWriter = this.f1421cw;
            int i = OPCODES_VERSION;
            String str = "ASMAccessorImpl_" + String.valueOf(this.f1421cw.hashCode()).replaceAll("\\-", "_") + (System.currentTimeMillis() / 10) + ((int) (Math.random() * 100.0d));
            this.className = str;
            classWriter.visit(i, 33, str, null, "java/lang/Object", new String[]{NAMESPACE + "compiler/Accessor"});
        }
        MethodVisitor methodVisitorVisitMethod = this.f1421cw.visitMethod(1, "<init>", "()V", null, null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        methodVisitorVisitMethod.visitInsn(Opcodes.RETURN);
        methodVisitorVisitMethod.visitMaxs(1, 1);
        methodVisitorVisitMethod.visitEnd();
        MethodVisitor methodVisitorVisitMethod2 = this.f1421cw.visitMethod(1, "setValue", "(Ljava/lang/Object;Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;Ljava/lang/Object;)Ljava/lang/Object;", null, null);
        this.f1422mv = methodVisitorVisitMethod2;
        methodVisitorVisitMethod2.visitCode();
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Accessor optimizeAccessor(ParserContext parserContext, char[] cArr, int i, int i2, Object obj, Object obj2, VariableResolverFactory variableResolverFactory, boolean z, Class cls) {
        this.time = System.currentTimeMillis();
        if (this.compiledInputs == null) {
            this.compiledInputs = new ArrayList<>();
        }
        this.cursor = i;
        this.start = i;
        int i3 = i2 + i;
        this.end = i3;
        this.length = i3 - i;
        this.first = true;
        this.val = null;
        this.pCtx = parserContext;
        this.expr = cArr;
        this.ctx = obj;
        this.thisRef = obj2;
        this.variableFactory = variableResolverFactory;
        this.ingressType = cls;
        if (!this.noinit) {
            _initJIT();
        }
        return compileAccessor();
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Accessor optimizeSetAccessor(ParserContext parserContext, char[] cArr, int i, int i2, Object obj, Object obj2, VariableResolverFactory variableResolverFactory, boolean z, Object obj3, Class cls) throws IllegalAccessException, IOException, SecurityException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        Label label;
        Object primitiveInitialValue = obj3;
        this.expr = cArr;
        this.cursor = i;
        this.start = i;
        int i3 = i + i2;
        this.end = i3;
        this.length = i3;
        this.first = true;
        this.ingressType = cls;
        this.compiledInputs = new ArrayList<>();
        Object obj4 = obj;
        this.ctx = obj4;
        this.thisRef = obj2;
        this.variableFactory = variableResolverFactory;
        this.pCtx = parserContext;
        PropertyVerifier propertyVerifier = new PropertyVerifier(cArr, parserContext);
        int iFindLastUnion = findLastUnion();
        char[] cArrSubset = iFindLastUnion != -1 ? ParseTools.subset(cArr, 0, iFindLastUnion) : null;
        _initJIT2();
        if (cArrSubset != null) {
            int i4 = this.length;
            char[] cArr2 = this.expr;
            this.expr = cArrSubset;
            int length = cArrSubset.length;
            this.end = length;
            this.length = length;
            this.deferFinish = true;
            this.noinit = true;
            compileAccessor();
            Object obj5 = this.val;
            this.expr = cArr2;
            int length2 = cArrSubset.length + i + 1;
            this.cursor = length2;
            int length3 = (i4 - cArrSubset.length) - 1;
            this.length = length3;
            this.end = length2 + length3;
            obj4 = obj5;
        } else {
            this.f1422mv.visitVarInsn(25, 1);
        }
        try {
            skipWhitespace();
            if (this.collection) {
                int i5 = this.cursor;
                whiteSpaceSkip();
                if (i5 == this.end) {
                    throw new PropertyAccessException("unterminated '['", this.expr, i, parserContext);
                }
                if (scanTo(']')) {
                    throw new PropertyAccessException("unterminated '['", this.expr, i, parserContext);
                }
                String strTrim = new String(this.expr, i5, this.cursor - i5).trim();
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(obj4.getClass()));
                if (obj4 instanceof Map) {
                    if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING && PropertyHandlerFactory.hasPropertyHandler(Map.class)) {
                        propHandlerByteCodePut(strTrim, obj4, Map.class, primitiveInitialValue);
                    } else {
                        Object objEval = MVEL.eval(strTrim, obj4, this.variableFactory);
                        Class clsAnalyze = propertyVerifier.analyze();
                        this.returnType = clsAnalyze;
                        ((Map) obj4).put(objEval, DataConversion.convert(primitiveInitialValue, clsAnalyze));
                        writeLiteralOrSubexpression(ParseTools.subCompileExpression(strTrim.toCharArray(), parserContext));
                        this.f1422mv.visitVarInsn(25, 4);
                        if (primitiveInitialValue != null && this.returnType != primitiveInitialValue.getClass()) {
                            dataConversion(this.returnType);
                            checkcast(this.returnType);
                        }
                        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
                        this.f1422mv.visitInsn(87);
                        this.f1422mv.visitVarInsn(25, 4);
                    }
                } else {
                    boolean z2 = obj4 instanceof List;
                    Class cls2 = Integer.TYPE;
                    if (z2) {
                        if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING && PropertyHandlerFactory.hasPropertyHandler(List.class)) {
                            propHandlerByteCodePut(strTrim, obj4, List.class, primitiveInitialValue);
                        } else {
                            int iIntValue = ((Integer) MVEL.eval(strTrim, obj4, this.variableFactory, Integer.class)).intValue();
                            Class clsAnalyze2 = propertyVerifier.analyze();
                            this.returnType = clsAnalyze2;
                            ((List) obj4).set(iIntValue, DataConversion.convert(primitiveInitialValue, clsAnalyze2));
                            writeLiteralOrSubexpression(ParseTools.subCompileExpression(strTrim.toCharArray(), parserContext));
                            unwrapPrimitive(cls2);
                            this.f1422mv.visitVarInsn(25, 4);
                            if (primitiveInitialValue != null && !primitiveInitialValue.getClass().isAssignableFrom(this.returnType)) {
                                dataConversion(this.returnType);
                                checkcast(this.returnType);
                            }
                            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "set", "(ILjava/lang/Object;)Ljava/lang/Object;");
                            this.f1422mv.visitVarInsn(25, 4);
                        }
                    } else if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING && PropertyHandlerFactory.hasPropertyHandler(obj4.getClass())) {
                        propHandlerByteCodePut(strTrim, obj4, obj4.getClass(), primitiveInitialValue);
                    } else if (obj4.getClass().isArray()) {
                        if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING && PropertyHandlerFactory.hasPropertyHandler(Array.class)) {
                            propHandlerByteCodePut(strTrim, obj4, Array.class, primitiveInitialValue);
                        } else {
                            Class baseComponentType = ParseTools.getBaseComponentType(obj4.getClass());
                            Object objEval2 = MVEL.eval(strTrim, obj4, this.variableFactory);
                            writeLiteralOrSubexpression(ParseTools.subCompileExpression(strTrim.toCharArray(), parserContext), cls2);
                            if (!(objEval2 instanceof Integer)) {
                                dataConversion(Integer.class);
                                objEval2 = DataConversion.convert(objEval2, Integer.class);
                                unwrapPrimitive(cls2);
                            }
                            this.f1422mv.visitVarInsn(25, 4);
                            if (baseComponentType.isPrimitive()) {
                                unwrapPrimitive(baseComponentType);
                            } else if (!baseComponentType.equals(primitiveInitialValue.getClass())) {
                                dataConversion(baseComponentType);
                            }
                            arrayStore(baseComponentType);
                            Array.set(obj4, ((Integer) objEval2).intValue(), DataConversion.convert(primitiveInitialValue, baseComponentType));
                            this.f1422mv.visitVarInsn(25, 4);
                        }
                    } else {
                        throw new PropertyAccessException("cannot bind to collection property: " + new String(this.expr) + ": not a recognized collection type: " + obj4.getClass(), this.expr, i, parserContext);
                    }
                }
                this.deferFinish = false;
                this.noinit = false;
                _finishJIT();
                try {
                    this.deferFinish = false;
                    return _initializeAccessor();
                } catch (Exception e) {
                    throw new CompileException("could not generate accessor", this.expr, i, e);
                }
            }
            char[] cArr3 = this.expr;
            int i6 = this.cursor;
            String str = new String(cArr3, i6, this.end - i6);
            Member fieldOrWriteAccessor = PropertyTools.getFieldOrWriteAccessor(obj4.getClass(), str, primitiveInitialValue == null ? null : cls);
            if (GlobalListenerFactory.hasSetListeners()) {
                this.f1422mv.visitVarInsn(25, 1);
                this.f1422mv.visitLdcInsn(str);
                this.f1422mv.visitVarInsn(25, 3);
                this.f1422mv.visitVarInsn(25, 4);
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, NAMESPACE + "integration/GlobalListenerFactory", "notifySetListeners", "(Ljava/lang/Object;Ljava/lang/String;L" + NAMESPACE + "integration/VariableResolverFactory;Ljava/lang/Object;)V");
                GlobalListenerFactory.notifySetListeners(obj4, str, this.variableFactory, primitiveInitialValue);
            }
            if (fieldOrWriteAccessor instanceof Field) {
                checkcast(obj4.getClass());
                Field field = (Field) fieldOrWriteAccessor;
                Label label2 = new Label();
                if (field.getType().isPrimitive()) {
                    this.f1422mv.visitVarInsn(58, 5);
                    this.f1422mv.visitVarInsn(25, 4);
                    if (primitiveInitialValue == null) {
                        primitiveInitialValue = PropertyTools.getPrimitiveInitialValue(field.getType());
                    }
                    label = new Label();
                    this.f1422mv.visitJumpInsn(Opcodes.IFNONNULL, label);
                    this.f1422mv.visitVarInsn(25, 5);
                    this.f1422mv.visitInsn(3);
                    this.f1422mv.visitFieldInsn(Opcodes.PUTFIELD, Type.getInternalName(field.getDeclaringClass()), str, Type.getDescriptor(field.getType()));
                    this.f1422mv.visitJumpInsn(Opcodes.GOTO, label2);
                    this.f1422mv.visitLabel(label);
                    this.f1422mv.visitVarInsn(25, 5);
                    this.f1422mv.visitVarInsn(25, 4);
                    unwrapPrimitive(field.getType());
                } else {
                    this.f1422mv.visitVarInsn(25, 4);
                    checkcast(field.getType());
                    label = null;
                }
                if (label == null && primitiveInitialValue != null && !field.getType().isAssignableFrom(primitiveInitialValue.getClass())) {
                    if (!DataConversion.canConvert(field.getType(), primitiveInitialValue.getClass())) {
                        throw new CompileException("cannot convert type: " + primitiveInitialValue.getClass() + ": to " + field.getType(), this.expr, i);
                    }
                    dataConversion(field.getType());
                    field.set(obj4, DataConversion.convert(primitiveInitialValue, field.getType()));
                } else {
                    field.set(obj4, primitiveInitialValue);
                }
                this.f1422mv.visitFieldInsn(Opcodes.PUTFIELD, Type.getInternalName(field.getDeclaringClass()), str, Type.getDescriptor(field.getType()));
                this.f1422mv.visitLabel(label2);
                this.f1422mv.visitVarInsn(25, 4);
            } else if (fieldOrWriteAccessor != null) {
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(obj4.getClass()));
                Method method = (Method) fieldOrWriteAccessor;
                this.f1422mv.visitVarInsn(25, 4);
                Class<?> cls3 = method.getParameterTypes()[0];
                Label label3 = new Label();
                if (primitiveInitialValue != null && !cls3.isAssignableFrom(primitiveInitialValue.getClass())) {
                    if (!DataConversion.canConvert(cls3, primitiveInitialValue.getClass())) {
                        throw new CompileException("cannot convert type: " + primitiveInitialValue.getClass() + ": to " + method.getParameterTypes()[0], this.expr, i);
                    }
                    dataConversion(getWrapperClass(cls3));
                    if (cls3.isPrimitive()) {
                        unwrapPrimitive(cls3);
                    } else {
                        checkcast(cls3);
                    }
                    method.invoke(obj4, DataConversion.convert(primitiveInitialValue, method.getParameterTypes()[0]));
                } else {
                    if (cls3.isPrimitive()) {
                        if (primitiveInitialValue == null) {
                            primitiveInitialValue = PropertyTools.getPrimitiveInitialValue(cls3);
                        }
                        Label label4 = new Label();
                        this.f1422mv.visitJumpInsn(Opcodes.IFNONNULL, label4);
                        this.f1422mv.visitInsn(3);
                        this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(method.getDeclaringClass()), method.getName(), Type.getMethodDescriptor(method));
                        this.f1422mv.visitJumpInsn(Opcodes.GOTO, label3);
                        this.f1422mv.visitLabel(label4);
                        this.f1422mv.visitVarInsn(25, 4);
                        unwrapPrimitive(cls3);
                    } else {
                        checkcast(cls3);
                    }
                    method.invoke(obj4, primitiveInitialValue);
                }
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(method.getDeclaringClass()), method.getName(), Type.getMethodDescriptor(method));
                this.f1422mv.visitLabel(label3);
                this.f1422mv.visitVarInsn(25, 4);
            } else if (obj4 instanceof Map) {
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(obj4.getClass()));
                this.f1422mv.visitLdcInsn(str);
                this.f1422mv.visitVarInsn(25, 4);
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
                this.f1422mv.visitVarInsn(25, 4);
                ((Map) obj4).put(str, primitiveInitialValue);
            } else {
                throw new PropertyAccessException("could not access property (" + str + ") in: " + cls.getName(), this.expr, i, parserContext);
            }
            try {
                this.deferFinish = false;
                this.noinit = false;
                _finishJIT();
                return _initializeAccessor();
            } catch (Exception e2) {
                throw new CompileException("could not generate accessor", this.expr, i, e2);
            }
        } catch (IllegalAccessException e3) {
            throw new PropertyAccessException("could not access property", this.expr, i, e3, parserContext);
        } catch (InvocationTargetException e4) {
            throw new PropertyAccessException("could not access property", this.expr, i, e4, parserContext);
        }
    }

    private void _finishJIT() throws IOException {
        if (this.deferFinish) {
            return;
        }
        Class cls = this.returnType;
        if (cls != null && cls.isPrimitive()) {
            wrapPrimitive(this.returnType);
        }
        if (this.returnType == Void.TYPE) {
            this.f1422mv.visitInsn(1);
        }
        this.f1422mv.visitInsn(Opcodes.ARETURN);
        dumpAdvancedDebugging();
        this.f1422mv.visitMaxs(this.stacksize, this.maxlocals);
        this.f1422mv.visitEnd();
        MethodVisitor methodVisitorVisitMethod = this.f1421cw.visitMethod(1, "getKnownEgressType", "()Ljava/lang/Class;", null, null);
        this.f1422mv = methodVisitorVisitMethod;
        methodVisitorVisitMethod.visitCode();
        visitConstantClass(this.returnType);
        this.f1422mv.visitInsn(Opcodes.ARETURN);
        this.f1422mv.visitMaxs(1, 1);
        this.f1422mv.visitEnd();
        if (this.propNull) {
            this.f1421cw.visitField(1, "nullPropertyHandler", "L" + NAMESPACE + "integration/PropertyHandler;", null, null).visitEnd();
        }
        if (this.methNull) {
            this.f1421cw.visitField(1, "nullMethodHandler", "L" + NAMESPACE + "integration/PropertyHandler;", null, null).visitEnd();
        }
        buildInputs();
        StringAppender stringAppender = this.buildLog;
        if (stringAppender != null && stringAppender.length() != 0 && this.expr != null) {
            MethodVisitor methodVisitorVisitMethod2 = this.f1421cw.visitMethod(1, "toString", "()Ljava/lang/String;", null, null);
            this.f1422mv = methodVisitorVisitMethod2;
            methodVisitorVisitMethod2.visitCode();
            this.f1422mv.visitLabel(new Label());
            this.f1422mv.visitLdcInsn(this.buildLog.toString() + "\n\n## { " + new String(this.expr) + " }");
            this.f1422mv.visitInsn(Opcodes.ARETURN);
            this.f1422mv.visitLabel(new Label());
            this.f1422mv.visitMaxs(1, 1);
            this.f1422mv.visitEnd();
        }
        this.f1421cw.visitEnd();
    }

    private void visitConstantClass(Class<?> cls) {
        if (cls == null) {
            cls = Object.class;
        }
        if (cls.isPrimitive()) {
            this.f1422mv.visitFieldInsn(Opcodes.GETSTATIC, ReflectionUtil.toNonPrimitiveType(cls).getName().replace(".", "/"), "TYPE", "Ljava/lang/Class;");
        } else {
            this.f1422mv.visitLdcInsn(Type.getType(cls));
        }
    }

    private Accessor _initializeAccessor() throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Object objNewInstance;
        if (this.deferFinish) {
            return null;
        }
        Class clsLoadClass = loadClass(this.className, this.f1421cw.toByteArray());
        try {
            if (this.compiledInputs.size() == 0) {
                objNewInstance = clsLoadClass.newInstance();
            } else {
                Class<?>[] clsArr = new Class[this.compiledInputs.size()];
                for (int i = 0; i < this.compiledInputs.size(); i++) {
                    clsArr[i] = ExecutableStatement.class;
                }
                Constructor constructor = clsLoadClass.getConstructor(clsArr);
                ArrayList<ExecutableStatement> arrayList = this.compiledInputs;
                objNewInstance = constructor.newInstance(arrayList.toArray(new ExecutableStatement[arrayList.size()]));
            }
            if (this.propNull) {
                clsLoadClass.getField("nullPropertyHandler").set(objNewInstance, PropertyHandlerFactory.getNullPropertyHandler());
            }
            if (this.methNull) {
                clsLoadClass.getField("nullMethodHandler").set(objNewInstance, PropertyHandlerFactory.getNullMethodHandler());
            }
            return (Accessor) objNewInstance;
        } catch (VerifyError e) {
            System.out.println("**** COMPILER BUG! REPORT THIS IMMEDIATELY AT http://jira.codehaus.org/browse/MVEL");
            PrintStream printStream = System.out;
            StringBuilder sb = new StringBuilder();
            sb.append("Expression: ");
            char[] cArr = this.expr;
            sb.append(cArr != null ? new String(cArr) : null);
            printStream.println(sb.toString());
            throw e;
        }
    }

    private Accessor compileAccessor() {
        Object beanPropertyAO = this.ctx;
        try {
            if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING) {
                while (true) {
                    if (this.cursor >= this.end) {
                        break;
                    }
                    int iNextSubToken = nextSubToken();
                    if (iNextSubToken == 0) {
                        beanPropertyAO = getBeanPropertyAO(beanPropertyAO, capture());
                    } else if (iNextSubToken == 1) {
                        beanPropertyAO = getMethod(beanPropertyAO, capture());
                    } else if (iNextSubToken == 2) {
                        beanPropertyAO = getCollectionPropertyAO(beanPropertyAO, capture());
                    } else if (iNextSubToken == 3) {
                        beanPropertyAO = getWithProperty(beanPropertyAO);
                    }
                    if (this.fields == -1) {
                        if (beanPropertyAO == null) {
                            if (this.nullSafe) {
                                throw new OptimizationNotSupported();
                            }
                        } else {
                            this.fields = 0;
                        }
                    }
                    this.first = false;
                    if (this.nullSafe && this.cursor < this.end) {
                        this.f1422mv.visitInsn(89);
                        Label label = new Label();
                        this.f1422mv.visitJumpInsn(Opcodes.IFNONNULL, label);
                        this.f1422mv.visitInsn(Opcodes.ARETURN);
                        this.f1422mv.visitLabel(label);
                    }
                }
            } else {
                while (true) {
                    if (this.cursor >= this.end) {
                        break;
                    }
                    int iNextSubToken2 = nextSubToken();
                    if (iNextSubToken2 == 0) {
                        beanPropertyAO = getBeanProperty(beanPropertyAO, capture());
                    } else if (iNextSubToken2 == 1) {
                        beanPropertyAO = getMethod(beanPropertyAO, capture());
                    } else if (iNextSubToken2 == 2) {
                        beanPropertyAO = getCollectionProperty(beanPropertyAO, capture());
                    } else if (iNextSubToken2 == 3) {
                        beanPropertyAO = getWithProperty(beanPropertyAO);
                    }
                    if (this.fields == -1) {
                        if (beanPropertyAO == null) {
                            if (this.nullSafe) {
                                throw new OptimizationNotSupported();
                            }
                        } else {
                            this.fields = 0;
                        }
                    }
                    this.first = false;
                    if (this.nullSafe && this.cursor < this.end) {
                        this.f1422mv.visitInsn(89);
                        Label label2 = new Label();
                        this.f1422mv.visitJumpInsn(Opcodes.IFNONNULL, label2);
                        this.f1422mv.visitInsn(Opcodes.ARETURN);
                        this.f1422mv.visitLabel(label2);
                    }
                }
            }
            this.val = beanPropertyAO;
            _finishJIT();
            return _initializeAccessor();
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException(new String(this.expr), this.expr, this.f1420st, e, this.pCtx);
        } catch (IndexOutOfBoundsException e2) {
            throw new PropertyAccessException(new String(this.expr), this.expr, this.f1420st, e2, this.pCtx);
        } catch (NullPointerException e3) {
            throw new PropertyAccessException(new String(this.expr), this.expr, this.f1420st, e3, this.pCtx);
        } catch (InvocationTargetException e4) {
            throw new PropertyAccessException(new String(this.expr), this.expr, this.f1420st, e4, this.pCtx);
        } catch (PropertyAccessException e5) {
            throw new CompileException(e5.getMessage(), this.expr, this.f1420st, e5);
        } catch (CompileException e6) {
            throw e6;
        } catch (OptimizationNotSupported e7) {
            throw e7;
        } catch (Exception e8) {
            throw new CompileException(e8.getMessage(), this.expr, this.f1420st, e8);
        }
    }

    private Object getWithProperty(Object obj) {
        if (this.first) {
            this.f1422mv.visitVarInsn(25, 1);
            this.first = false;
        }
        String strTrim = new String(this.expr, 0, this.cursor - 1).trim();
        int i = this.cursor;
        int i2 = i + 1;
        this.cursor = ParseTools.balancedCaptureWithLineAccounting(this.expr, i, this.end, '{', this.pCtx);
        this.returnType = obj != null ? obj.getClass() : null;
        char[] cArr = this.expr;
        int i3 = this.cursor;
        this.cursor = i3 + 1;
        for (WithNode.ParmValuePair parmValuePair : WithNode.compileWithExpressions(cArr, i2, i3 - i2, strTrim, this.ingressType, this.pCtx)) {
            this.f1422mv.visitInsn(89);
            this.f1422mv.visitVarInsn(58, this.compileDepth + 5);
            parmValuePair.eval(obj, this.variableFactory);
            if (parmValuePair.getSetExpression() == null) {
                addSubstatement(parmValuePair.getStatement());
            } else {
                this.compiledInputs.add((ExecutableStatement) parmValuePair.getSetExpression());
                this.f1422mv.visitVarInsn(25, 0);
                this.f1422mv.visitFieldInsn(Opcodes.GETFIELD, this.className, "p" + (this.compiledInputs.size() - 1), "L" + NAMESPACE + "compiler/ExecutableStatement;");
                this.f1422mv.visitVarInsn(25, this.compileDepth + 5);
                this.f1422mv.visitVarInsn(25, 2);
                this.f1422mv.visitVarInsn(25, 3);
                addSubstatement(parmValuePair.getStatement());
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, NAMESPACE + "compiler/ExecutableStatement", "setValue", "(Ljava/lang/Object;Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;Ljava/lang/Object;)Ljava/lang/Object;");
                this.f1422mv.visitInsn(87);
            }
        }
        return obj;
    }

    private Object getBeanPropertyAO(Object obj, String str) {
        if (obj != null && PropertyHandlerFactory.hasPropertyHandler(obj.getClass())) {
            return propHandlerByteCode(str, obj, obj.getClass());
        }
        return getBeanProperty(obj, str);
    }

    private Object getBeanProperty(Object obj, String str) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls;
        boolean z;
        Object objInvoke;
        ParserContext parserContext = this.pCtx;
        if ((parserContext == null ? this.currType : parserContext.getVarOrInputTypeOrNull(str)) == Object.class && !this.pCtx.isStrongTyping()) {
            this.currType = null;
        }
        Class cls2 = this.returnType;
        if (cls2 != null && cls2.isPrimitive()) {
            wrapPrimitive(this.returnType);
        }
        boolean z2 = obj instanceof Class;
        if (z2) {
            if (MVEL.COMPILER_OPT_SUPPORT_JAVA_STYLE_CLASS_LITERALS && "class".equals(str)) {
                ldcClassConstant((Class) obj);
                return obj;
            }
            cls = (Class) obj;
            z = true;
        } else {
            cls = obj != null ? obj.getClass() : null;
            z = false;
        }
        if (PropertyHandlerFactory.hasPropertyHandler(cls)) {
            PropertyHandler propertyHandler = PropertyHandlerFactory.getPropertyHandler(cls);
            if (propertyHandler instanceof ProducesBytecode) {
                ((ProducesBytecode) propertyHandler).produceBytecodeGet(this.f1422mv, str, this.variableFactory);
                return propertyHandler.getProperty(str, obj, this.variableFactory);
            }
            throw new RuntimeException("unable to compileShared: custom accessor does not support producing bytecode: " + propertyHandler.getClass().getName());
        }
        Member fieldOrAccessor = cls != null ? PropertyTools.getFieldOrAccessor(cls, str) : null;
        if (fieldOrAccessor != null && z && (fieldOrAccessor.getModifiers() & 8) == 0) {
            fieldOrAccessor = null;
        }
        if (fieldOrAccessor != null && GlobalListenerFactory.hasGetListeners()) {
            this.f1422mv.visitVarInsn(25, 1);
            this.f1422mv.visitLdcInsn(fieldOrAccessor.getName());
            this.f1422mv.visitVarInsn(25, 3);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, NAMESPACE + "integration/GlobalListenerFactory", "notifyGetListeners", "(Ljava/lang/Object;Ljava/lang/String;L" + NAMESPACE + "integration/VariableResolverFactory;)V");
            GlobalListenerFactory.notifyGetListeners(obj, fieldOrAccessor.getName(), this.variableFactory);
        }
        if (this.first) {
            if ("this".equals(str)) {
                this.f1422mv.visitVarInsn(25, 2);
                return this.thisRef;
            }
            VariableResolverFactory variableResolverFactory = this.variableFactory;
            if (variableResolverFactory != null && variableResolverFactory.isResolveable(str)) {
                if (this.variableFactory.isIndexedFactory() && this.variableFactory.isTarget(str)) {
                    try {
                        int iVariableIndexOf = this.variableFactory.variableIndexOf(str);
                        loadVariableByIndex(iVariableIndexOf);
                        return this.variableFactory.getIndexedVariableResolver(iVariableIndexOf).getValue();
                    } catch (Exception unused) {
                        throw new OptimizationFailure(str);
                    }
                }
                try {
                    loadVariableByName(str);
                    return this.variableFactory.getVariableResolver(str).getValue();
                } catch (Exception e) {
                    throw new OptimizationFailure("critical error in JIT", e);
                }
            }
            this.f1422mv.visitVarInsn(25, 1);
        }
        if (fieldOrAccessor instanceof Field) {
            return optimizeFieldMethodProperty(obj, str, cls, fieldOrAccessor);
        }
        if (fieldOrAccessor != null) {
            if (this.first) {
                this.f1422mv.visitVarInsn(25, 1);
            }
            try {
                objInvoke = ((Method) fieldOrAccessor).invoke(obj, EMPTYARG);
                if (this.returnType != fieldOrAccessor.getDeclaringClass()) {
                    this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(fieldOrAccessor.getDeclaringClass()));
                }
                this.returnType = ((Method) fieldOrAccessor).getReturnType();
                if (fieldOrAccessor.getDeclaringClass().isInterface()) {
                    this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(fieldOrAccessor.getDeclaringClass()), fieldOrAccessor.getName(), Type.getMethodDescriptor((Method) fieldOrAccessor));
                } else {
                    this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(fieldOrAccessor.getDeclaringClass()), fieldOrAccessor.getName(), Type.getMethodDescriptor((Method) fieldOrAccessor));
                }
            } catch (IllegalAccessException e2) {
                Method method = (Method) fieldOrAccessor;
                Method methodDetermineActualTargetMethod = ParseTools.determineActualTargetMethod(method);
                if (methodDetermineActualTargetMethod == null) {
                    throw new PropertyAccessException("could not access field: " + cls.getName() + "." + str, this.expr, this.f1420st, e2, this.pCtx);
                }
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(methodDetermineActualTargetMethod.getDeclaringClass()));
                this.returnType = methodDetermineActualTargetMethod.getReturnType();
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(methodDetermineActualTargetMethod.getDeclaringClass()), fieldOrAccessor.getName(), Type.getMethodDescriptor(method));
                objInvoke = methodDetermineActualTargetMethod.invoke(obj, EMPTYARG);
            } catch (IllegalArgumentException e3) {
                if (fieldOrAccessor.getDeclaringClass().equals(obj)) {
                    try {
                        throw new CompileException("name collision between innerclass: " + Class.forName(fieldOrAccessor.getDeclaringClass().getName() + "$" + str).getCanonicalName() + "; and bean accessor: " + str + " (" + fieldOrAccessor.toString() + ")", this.expr, this.tkStart);
                    } catch (ClassNotFoundException unused2) {
                        throw e3;
                    }
                }
                throw e3;
            }
            if (PropertyHandlerFactory.hasNullPropertyHandler()) {
                if (objInvoke == null) {
                    objInvoke = PropertyHandlerFactory.getNullPropertyHandler().getProperty(fieldOrAccessor.getName(), obj, this.variableFactory);
                }
                writeOutNullHandler(fieldOrAccessor, 0);
            }
            this.currType = ReflectionUtil.toNonPrimitiveType(this.returnType);
            return objInvoke;
        }
        if (obj instanceof Map) {
            Map map = (Map) obj;
            if (map.containsKey(str) || this.nullSafe) {
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/Map");
                this.f1422mv.visitLdcInsn(str);
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
                return map.get(str);
            }
        }
        if (this.first && "this".equals(str)) {
            this.f1422mv.visitVarInsn(25, 2);
            return this.thisRef;
        }
        if ("length".equals(str) && obj.getClass().isArray()) {
            anyArrayCheck(obj.getClass());
            this.f1422mv.visitInsn(Opcodes.ARRAYLENGTH);
            wrapPrimitive(Integer.TYPE);
            return Integer.valueOf(Array.getLength(obj));
        }
        if (AbstractParser.LITERALS.containsKey(str)) {
            Object obj2 = AbstractParser.LITERALS.get(str);
            if (obj2 instanceof Class) {
                ldcClassConstant((Class) obj2);
            }
            return obj2;
        }
        Object objTryStaticAccess = tryStaticAccess();
        if (objTryStaticAccess != null) {
            if (objTryStaticAccess instanceof Class) {
                ldcClassConstant((Class) objTryStaticAccess);
                return objTryStaticAccess;
            }
            if (objTryStaticAccess instanceof Method) {
                Method method2 = (Method) objTryStaticAccess;
                writeFunctionPointerStub(method2.getDeclaringClass(), method2);
                return objTryStaticAccess;
            }
            return optimizeFieldMethodProperty(obj, str, cls, (Field) objTryStaticAccess);
        }
        if (z2) {
            Class cls3 = (Class) obj;
            for (Method method3 : cls3.getMethods()) {
                if (str.equals(method3.getName())) {
                    ParserContext parserContext2 = this.pCtx;
                    if (parserContext2 == null || parserContext2.getParserConfiguration() == null ? MVEL.COMPILER_OPT_ALLOW_NAKED_METH_CALL : this.pCtx.getParserConfiguration().isAllowNakedMethCall()) {
                        this.f1422mv.visitInsn(87);
                        this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(method3.getDeclaringClass()), method3.getName(), Type.getMethodDescriptor(method3));
                        this.returnType = method3.getReturnType();
                        return method3.invoke(null, ParseTools.EMPTY_OBJ_ARR);
                    }
                    writeFunctionPointerStub(cls3, method3);
                    return method3;
                }
            }
            try {
                Class clsFindClass = ParseTools.findClass(this.variableFactory, cls3.getName() + "$" + str, this.pCtx);
                ldcClassConstant(clsFindClass);
                return clsFindClass;
            } catch (ClassNotFoundException unused3) {
            }
        } else {
            ParserContext parserContext3 = this.pCtx;
            if (parserContext3 == null || parserContext3.getParserConfiguration() == null ? MVEL.COMPILER_OPT_ALLOW_NAKED_METH_CALL : this.pCtx.getParserConfiguration().isAllowNakedMethCall()) {
                return getMethod(obj, str);
            }
        }
        if (obj == null) {
            throw new PropertyAccessException("unresolvable property or identifier: " + str, this.expr, this.f1420st, this.pCtx);
        }
        throw new PropertyAccessException("could not access: " + str + "; in class: " + obj.getClass().getName(), this.expr, this.f1420st, this.pCtx);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Object optimizeFieldMethodProperty(Object obj, String str, Class<?> cls, Member member) throws IllegalAccessException, IllegalArgumentException {
        Object property = ((Field) member).get(obj);
        if ((member.getModifiers() & 8) != 0) {
            if ((member.getModifiers() & 16) != 0 && ((property instanceof String) || ((Field) member).getType().isPrimitive())) {
                Object obj2 = ((Field) member).get(null);
                this.f1422mv.visitLdcInsn(obj2);
                wrapPrimitive(obj2.getClass());
                if (PropertyHandlerFactory.hasNullPropertyHandler()) {
                    writeOutNullHandler(member, 0);
                }
                return obj2;
            }
            MethodVisitor methodVisitor = this.f1422mv;
            String internalName = Type.getInternalName(member.getDeclaringClass());
            String name = member.getName();
            Class<?> type = ((Field) member).getType();
            this.returnType = type;
            methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, internalName, name, Type.getDescriptor(type));
        } else {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(cls));
            MethodVisitor methodVisitor2 = this.f1422mv;
            String internalName2 = Type.getInternalName(cls);
            Class<?> type2 = ((Field) member).getType();
            this.returnType = type2;
            methodVisitor2.visitFieldInsn(Opcodes.GETFIELD, internalName2, str, Type.getDescriptor(type2));
        }
        this.returnType = ((Field) member).getType();
        if (PropertyHandlerFactory.hasNullPropertyHandler()) {
            if (property == null) {
                property = PropertyHandlerFactory.getNullPropertyHandler().getProperty(member.getName(), obj, this.variableFactory);
            }
            writeOutNullHandler(member, 0);
        }
        this.currType = ReflectionUtil.toNonPrimitiveType(this.returnType);
        return property;
    }

    private void writeFunctionPointerStub(Class cls, Method method) {
        ldcClassConstant(cls);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Class", "getMethods", "()[Ljava/lang/reflect/Method;");
        this.f1422mv.visitVarInsn(58, 7);
        this.f1422mv.visitInsn(3);
        this.f1422mv.visitVarInsn(54, 5);
        this.f1422mv.visitVarInsn(25, 7);
        this.f1422mv.visitInsn(Opcodes.ARRAYLENGTH);
        this.f1422mv.visitVarInsn(54, 6);
        Label label = new Label();
        this.f1422mv.visitJumpInsn(Opcodes.GOTO, label);
        Label label2 = new Label();
        this.f1422mv.visitLabel(label2);
        this.f1422mv.visitVarInsn(25, 7);
        this.f1422mv.visitVarInsn(21, 5);
        this.f1422mv.visitInsn(50);
        this.f1422mv.visitVarInsn(58, 4);
        this.f1422mv.visitLabel(new Label());
        this.f1422mv.visitLdcInsn(method.getName());
        this.f1422mv.visitVarInsn(25, 4);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/reflect/Method", "getName", "()Ljava/lang/String;");
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z");
        Label label3 = new Label();
        this.f1422mv.visitJumpInsn(Opcodes.IFEQ, label3);
        this.f1422mv.visitLabel(new Label());
        this.f1422mv.visitVarInsn(25, 4);
        this.f1422mv.visitInsn(Opcodes.ARETURN);
        this.f1422mv.visitLabel(label3);
        this.f1422mv.visitIincInsn(5, 1);
        this.f1422mv.visitLabel(label);
        this.f1422mv.visitVarInsn(21, 5);
        this.f1422mv.visitVarInsn(21, 6);
        this.f1422mv.visitJumpInsn(Opcodes.IF_ICMPLT, label2);
        this.f1422mv.visitLabel(new Label());
        this.f1422mv.visitInsn(1);
        this.f1422mv.visitInsn(Opcodes.ARETURN);
    }

    private Object getCollectionProperty(Object obj, String str) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (str.trim().length() > 0) {
            obj = getBeanProperty(obj, str);
            this.first = false;
        }
        this.currType = null;
        int i = this.cursor + 1;
        this.cursor = i;
        skipWhitespace();
        if (this.cursor == this.end) {
            throw new CompileException("unterminated '['", this.expr, this.f1420st);
        }
        if (scanTo(']')) {
            throw new CompileException("unterminated '['", this.expr, this.f1420st);
        }
        String str2 = new String(this.expr, i, this.cursor - i);
        if (obj == null) {
            return null;
        }
        if (this.first) {
            this.f1422mv.visitVarInsn(25, 1);
        }
        ExecutableStatement executableStatement = (ExecutableStatement) ParseTools.subCompileExpression(str2.toCharArray(), this.pCtx);
        Object value = executableStatement.getValue(this.ctx, this.variableFactory);
        this.cursor++;
        if (obj instanceof Map) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/Map");
            Class clsWriteLiteralOrSubexpression = writeLiteralOrSubexpression(executableStatement);
            if (clsWriteLiteralOrSubexpression != null && clsWriteLiteralOrSubexpression.isPrimitive()) {
                wrapPrimitive(clsWriteLiteralOrSubexpression);
            }
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
            return ((Map) obj).get(value);
        }
        boolean z = obj instanceof List;
        Class cls = Integer.TYPE;
        if (z) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
            writeLiteralOrSubexpression(executableStatement, cls);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
            return ((List) obj).get(((Integer) DataConversion.convert(value, Integer.class)).intValue());
        }
        boolean zIsArray = obj.getClass().isArray();
        Class cls2 = Character.TYPE;
        if (zIsArray) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getDescriptor(obj.getClass()));
            writeLiteralOrSubexpression(executableStatement, cls, value.getClass());
            Class baseComponentType = ParseTools.getBaseComponentType(obj.getClass());
            if (baseComponentType.isPrimitive()) {
                if (baseComponentType == cls) {
                    this.f1422mv.visitInsn(46);
                } else if (baseComponentType == cls2) {
                    this.f1422mv.visitInsn(52);
                } else if (baseComponentType == Boolean.TYPE) {
                    this.f1422mv.visitInsn(51);
                } else if (baseComponentType == Double.TYPE) {
                    this.f1422mv.visitInsn(49);
                } else if (baseComponentType == Float.TYPE) {
                    this.f1422mv.visitInsn(48);
                } else if (baseComponentType == Short.TYPE) {
                    this.f1422mv.visitInsn(53);
                } else if (baseComponentType == Long.TYPE) {
                    this.f1422mv.visitInsn(47);
                } else if (baseComponentType == Byte.TYPE) {
                    this.f1422mv.visitInsn(51);
                }
                wrapPrimitive(baseComponentType);
            } else {
                this.f1422mv.visitInsn(50);
            }
            return Array.get(obj, ((Integer) DataConversion.convert(value, Integer.class)).intValue());
        }
        if (obj instanceof CharSequence) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/CharSequence");
            if (value instanceof Integer) {
                Integer num = (Integer) value;
                intPush(num.intValue());
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/CharSequence", "charAt", "(I)C");
                wrapPrimitive(cls2);
                return Character.valueOf(((CharSequence) obj).charAt(num.intValue()));
            }
            writeLiteralOrSubexpression(executableStatement, Integer.class);
            unwrapPrimitive(cls);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/CharSequence", "charAt", "(I)C");
            wrapPrimitive(cls2);
            return Character.valueOf(((CharSequence) obj).charAt(((Integer) DataConversion.convert(value, Integer.class)).intValue()));
        }
        TypeDescriptor typeDescriptor = new TypeDescriptor(this.expr, this.start, this.length, 0);
        if (typeDescriptor.isArray()) {
            try {
                Class classReference = TypeDescriptor.getClassReference((Class) obj, typeDescriptor, this.variableFactory, this.pCtx);
                ldcClassConstant(classReference);
                return classReference;
            } catch (Exception unused) {
            }
        }
        throw new CompileException("illegal use of []: unknown type: " + obj.getClass().getName(), this.expr, this.f1420st);
    }

    private Object getCollectionPropertyAO(Object obj, String str) throws IllegalAccessException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (str.length() > 0) {
            obj = getBeanProperty(obj, str);
            this.first = false;
        }
        this.currType = null;
        int i = this.cursor + 1;
        this.cursor = i;
        skipWhitespace();
        if (this.cursor == this.end) {
            throw new CompileException("unterminated '['", this.expr, this.f1420st);
        }
        if (scanTo(']')) {
            throw new CompileException("unterminated '['", this.expr, this.f1420st);
        }
        String str2 = new String(this.expr, i, this.cursor - i);
        if (obj == null) {
            return null;
        }
        ExecutableStatement executableStatement = (ExecutableStatement) ParseTools.subCompileExpression(str2.toCharArray());
        Object value = executableStatement.getValue(this.ctx, this.variableFactory);
        this.cursor++;
        if (obj instanceof Map) {
            if (PropertyHandlerFactory.hasPropertyHandler(Map.class)) {
                return propHandlerByteCode(str2, obj, Map.class);
            }
            if (this.first) {
                this.f1422mv.visitVarInsn(25, 1);
            }
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/Map");
            Class clsWriteLiteralOrSubexpression = writeLiteralOrSubexpression(executableStatement);
            if (clsWriteLiteralOrSubexpression != null && clsWriteLiteralOrSubexpression.isPrimitive()) {
                wrapPrimitive(clsWriteLiteralOrSubexpression);
            }
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
            return ((Map) obj).get(value);
        }
        boolean z = obj instanceof List;
        Class cls = Integer.TYPE;
        if (z) {
            if (PropertyHandlerFactory.hasPropertyHandler(List.class)) {
                return propHandlerByteCode(str2, obj, List.class);
            }
            if (this.first) {
                this.f1422mv.visitVarInsn(25, 1);
            }
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/List");
            writeLiteralOrSubexpression(executableStatement, cls);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
            return ((List) obj).get(((Integer) DataConversion.convert(value, Integer.class)).intValue());
        }
        boolean zIsArray = obj.getClass().isArray();
        Class cls2 = Character.TYPE;
        if (zIsArray) {
            if (PropertyHandlerFactory.hasPropertyHandler(Array.class)) {
                return propHandlerByteCode(str2, obj, Array.class);
            }
            if (this.first) {
                this.f1422mv.visitVarInsn(25, 1);
            }
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getDescriptor(obj.getClass()));
            writeLiteralOrSubexpression(executableStatement, cls, value.getClass());
            Class baseComponentType = ParseTools.getBaseComponentType(obj.getClass());
            if (baseComponentType.isPrimitive()) {
                if (baseComponentType == cls) {
                    this.f1422mv.visitInsn(46);
                } else if (baseComponentType == cls2) {
                    this.f1422mv.visitInsn(52);
                } else if (baseComponentType == Boolean.TYPE) {
                    this.f1422mv.visitInsn(51);
                } else if (baseComponentType == Double.TYPE) {
                    this.f1422mv.visitInsn(49);
                } else if (baseComponentType == Float.TYPE) {
                    this.f1422mv.visitInsn(48);
                } else if (baseComponentType == Short.TYPE) {
                    this.f1422mv.visitInsn(53);
                } else if (baseComponentType == Long.TYPE) {
                    this.f1422mv.visitInsn(47);
                } else if (baseComponentType == Byte.TYPE) {
                    this.f1422mv.visitInsn(51);
                }
                wrapPrimitive(baseComponentType);
            } else {
                this.f1422mv.visitInsn(50);
            }
            return Array.get(obj, ((Integer) DataConversion.convert(value, Integer.class)).intValue());
        }
        if (obj instanceof CharSequence) {
            if (PropertyHandlerFactory.hasPropertyHandler(CharSequence.class)) {
                return propHandlerByteCode(str2, obj, CharSequence.class);
            }
            if (this.first) {
                this.f1422mv.visitVarInsn(25, 1);
            }
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/CharSequence");
            if (value instanceof Integer) {
                Integer num = (Integer) value;
                intPush(num.intValue());
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/CharSequence", "charAt", "(I)C");
                wrapPrimitive(cls2);
                return Character.valueOf(((CharSequence) obj).charAt(num.intValue()));
            }
            writeLiteralOrSubexpression(executableStatement, Integer.class);
            unwrapPrimitive(cls);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/lang/CharSequence", "charAt", "(I)C");
            wrapPrimitive(cls2);
            return Character.valueOf(((CharSequence) obj).charAt(((Integer) DataConversion.convert(value, Integer.class)).intValue()));
        }
        char[] cArr = this.expr;
        int i2 = this.start;
        TypeDescriptor typeDescriptor = new TypeDescriptor(cArr, i2, this.end - i2, 0);
        if (typeDescriptor.isArray()) {
            try {
                Class classReference = TypeDescriptor.getClassReference((Class) obj, typeDescriptor, this.variableFactory, this.pCtx);
                ldcClassConstant(classReference);
                return classReference;
            } catch (Exception unused) {
            }
        }
        throw new CompileException("illegal use of []: unknown type: " + obj.getClass().getName(), this.expr, this.f1420st);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002b  */
    /* JADX WARN: Type inference failed for: r11v0, types: [org.mvel2.compiler.ExecutableStatement[]] */
    /* JADX WARN: Type inference failed for: r25v0, types: [org.mvel2.compiler.AbstractParser, org.mvel2.optimizers.AbstractOptimizer, org.mvel2.optimizers.impl.asm.ASMAccessorOptimizer] */
    /* JADX WARN: Type inference failed for: r2v96, types: [org.mvel2.compiler.ExecutableStatement[]] */
    /* JADX WARN: Type inference failed for: r5v13, types: [org.mvel2.compiler.ExecutableStatement] */
    /* JADX WARN: Type inference failed for: r5v16, types: [org.mvel2.compiler.ExecutableStatement] */
    /* JADX WARN: Type inference failed for: r8v11, types: [org.mvel2.compiler.ExecutableStatement] */
    /* JADX WARN: Type inference failed for: r8v13 */
    /* JADX WARN: Type inference failed for: r8v2, types: [org.mvel2.compiler.ExecutableStatement] */
    /* JADX WARN: Type inference failed for: r8v4, types: [org.mvel2.compiler.ExecutableStatement] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object getMethod(java.lang.Object r26, java.lang.String r27) throws java.lang.IllegalAccessException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 2126
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.optimizers.impl.asm.ASMAccessorOptimizer.getMethod(java.lang.Object, java.lang.String):java.lang.Object");
    }

    private void dataConversion(Class cls) {
        if (cls.equals(Object.class)) {
            return;
        }
        ldcClassConstant(cls);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, NAMESPACE + "DataConversion", "convert", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
    }

    public static void setMVELClassLoader(MVELClassLoader mVELClassLoader) {
        classLoader = mVELClassLoader;
    }

    public static MVELClassLoader getMVELClassLoader() {
        return classLoader;
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public void init() {
        try {
            classLoader = new JITClassLoader(Thread.currentThread().getContextClassLoader());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ContextClassLoader getContextClassLoader() {
        if (this.pCtx == null) {
            return null;
        }
        return new ContextClassLoader(this.pCtx.getClassLoader());
    }

    private static class ContextClassLoader extends ClassLoader {
        ContextClassLoader(ClassLoader classLoader) {
            super(classLoader);
        }

        Class<?> defineClass(String str, byte[] bArr) {
            return defineClass(str, bArr, 0, bArr.length);
        }
    }

    private Class loadClass(String str, byte[] bArr) {
        ContextClassLoader contextClassLoader = getContextClassLoader();
        if (contextClassLoader == null) {
            return classLoader.defineClassX(str, bArr, 0, bArr.length);
        }
        return contextClassLoader.defineClass(str, bArr);
    }

    private boolean debug(String str) {
        StringAppender stringAppender = this.buildLog;
        if (stringAppender == null) {
            return true;
        }
        stringAppender.append(str).append("\n");
        return true;
    }

    public String getName() {
        return "ASM";
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Object getResultOptPass() {
        return this.val;
    }

    private Class getWrapperClass(Class cls) {
        if (cls == Boolean.TYPE) {
            return Boolean.class;
        }
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Float.TYPE) {
            return Float.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        if (cls == Short.TYPE) {
            return Short.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Byte.TYPE) {
            return Byte.class;
        }
        return cls == Character.TYPE ? Character.class : cls;
    }

    private void unwrapPrimitive(Class cls) {
        if (cls == Boolean.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
            return;
        }
        if (cls == Integer.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
            return;
        }
        if (cls == Float.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Float");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
            return;
        }
        if (cls == Double.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
            return;
        }
        if (cls == Short.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Short");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
            return;
        }
        if (cls == Long.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Long");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
        } else if (cls == Byte.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Byte");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
        } else if (cls == Character.TYPE) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Character");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
        }
    }

    private void wrapPrimitive(Class<? extends Object> cls) {
        int i = OPCODES_VERSION;
        Class<? extends Object> cls2 = Float.TYPE;
        Class<? extends Object> cls3 = Integer.TYPE;
        Class<? extends Object> cls4 = Boolean.TYPE;
        if (i != 48) {
            if (cls == cls4 || cls == Boolean.class) {
                debug("INVOKESTATIC java/lang/Boolean.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
                return;
            }
            if (cls == cls3 || cls == Integer.class) {
                debug("INVOKESTATIC java/lang/Integer.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
                return;
            }
            if (cls == cls2 || cls == Float.class) {
                debug("INVOKESTATIC java/lang/Float.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
                return;
            }
            if (cls == Double.TYPE || cls == Double.class) {
                debug("INVOKESTATIC java/lang/Double.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
                return;
            }
            if (cls == Short.TYPE || cls == Short.class) {
                debug("INVOKESTATIC java/lang/Short.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
                return;
            }
            if (cls == Long.TYPE || cls == Long.class) {
                debug("INVOKESTATIC java/lang/Long.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
                return;
            } else if (cls == Byte.TYPE || cls == Byte.class) {
                debug("INVOKESTATIC java/lang/Byte.valueOf");
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
                return;
            } else {
                if (cls == Character.TYPE || cls == Character.class) {
                    debug("INVOKESTATIC java/lang/Character.valueOf");
                    this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
                    return;
                }
                return;
            }
        }
        debug("** Using 1.4 Bytecode **");
        if (cls == cls4 || cls == Boolean.class) {
            debug("NEW java/lang/Boolean");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Boolean");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Boolean.<init>::(Z)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Boolean", "<init>", "(Z)V");
            return;
        }
        if (cls == cls3 || cls == Integer.class) {
            debug("NEW java/lang/Integer");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Integer");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Integer.<init>::(I)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Integer", "<init>", "(I)V");
            return;
        }
        if (cls == cls2 || cls == Float.class) {
            debug("NEW java/lang/Float");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Float");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Float.<init>::(F)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Float", "<init>", "(F)V");
            return;
        }
        if (cls == Double.TYPE || cls == Double.class) {
            debug("NEW java/lang/Double");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Double");
            debug("DUP X2");
            this.f1422mv.visitInsn(91);
            debug("DUP X2");
            this.f1422mv.visitInsn(91);
            debug("POP");
            this.f1422mv.visitInsn(87);
            debug("INVOKESPECIAL java/lang/Double.<init>::(D)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Double", "<init>", "(D)V");
            return;
        }
        if (cls == Short.TYPE || cls == Short.class) {
            debug("NEW java/lang/Short");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Short");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Short.<init>::(S)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Short", "<init>", "(S)V");
            return;
        }
        if (cls == Long.TYPE || cls == Long.class) {
            debug("NEW java/lang/Long");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Long");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Long.<init>::(L)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Float", "<init>", "(L)V");
            return;
        }
        if (cls == Byte.TYPE || cls == Byte.class) {
            debug("NEW java/lang/Byte");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Byte");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Byte.<init>::(B)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Byte", "<init>", "(B)V");
            return;
        }
        if (cls == Character.TYPE || cls == Character.class) {
            debug("NEW java/lang/Character");
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/Character");
            debug("DUP X1");
            this.f1422mv.visitInsn(90);
            debug("SWAP");
            this.f1422mv.visitInsn(95);
            debug("INVOKESPECIAL java/lang/Character.<init>::(C)V");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Character", "<init>", "(C)V");
        }
    }

    private void anyArrayCheck(Class cls) {
        if (cls == boolean[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[Z");
            return;
        }
        if (cls == int[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[I");
            return;
        }
        if (cls == float[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[F");
            return;
        }
        if (cls == double[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[D");
            return;
        }
        if (cls == short[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[S");
            return;
        }
        if (cls == long[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[J");
            return;
        }
        if (cls == byte[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[B");
        } else if (cls == char[].class) {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[C");
        } else {
            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "[Ljava/lang/Object;");
        }
    }

    private void writeOutLiteralWrapped(Object obj) {
        if (obj instanceof Integer) {
            intPush(((Integer) obj).intValue());
            wrapPrimitive(Integer.TYPE);
            return;
        }
        if (obj instanceof String) {
            this.f1422mv.visitLdcInsn(obj);
            return;
        }
        if (obj instanceof Long) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Long.TYPE);
            return;
        }
        if (obj instanceof Float) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Float.TYPE);
            return;
        }
        if (obj instanceof Double) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Double.TYPE);
            return;
        }
        if (obj instanceof Short) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Short.TYPE);
            return;
        }
        if (obj instanceof Character) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Character.TYPE);
        } else if (obj instanceof Boolean) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Boolean.TYPE);
        } else if (obj instanceof Byte) {
            this.f1422mv.visitLdcInsn(obj);
            wrapPrimitive(Byte.TYPE);
        }
    }

    public static int toPrimitiveTypeOperand(Class<?> cls) {
        if (cls == Integer.TYPE) {
            return 10;
        }
        if (cls == Long.TYPE) {
            return 11;
        }
        if (cls == Double.TYPE) {
            return 7;
        }
        if (cls == Float.TYPE) {
            return 6;
        }
        if (cls == Short.TYPE) {
            return 9;
        }
        if (cls == Byte.TYPE) {
            return 8;
        }
        if (cls == Character.TYPE) {
            return 5;
        }
        if (cls == Boolean.TYPE) {
            return 4;
        }
        throw new IllegalStateException("Non-primitive type passed to toPrimitiveTypeOperand: " + cls);
    }

    private void createArray(Class cls, int i) {
        intPush(i);
        if (cls.isPrimitive()) {
            this.f1422mv.visitIntInsn(Opcodes.NEWARRAY, toPrimitiveTypeOperand(cls));
        } else {
            this.f1422mv.visitTypeInsn(Opcodes.ANEWARRAY, Type.getInternalName(cls));
        }
    }

    public void arrayStore(Class cls) {
        if (cls.isPrimitive()) {
            if (cls == Integer.TYPE) {
                this.f1422mv.visitInsn(79);
                return;
            }
            if (cls == Character.TYPE) {
                this.f1422mv.visitInsn(85);
                return;
            }
            if (cls == Boolean.TYPE) {
                this.f1422mv.visitInsn(84);
                return;
            }
            if (cls == Double.TYPE) {
                this.f1422mv.visitInsn(82);
                return;
            }
            if (cls == Float.TYPE) {
                this.f1422mv.visitInsn(81);
                return;
            }
            if (cls == Short.TYPE) {
                this.f1422mv.visitInsn(86);
                return;
            } else if (cls == Long.TYPE) {
                this.f1422mv.visitInsn(80);
                return;
            } else {
                if (cls == Byte.TYPE) {
                    this.f1422mv.visitInsn(84);
                    return;
                }
                return;
            }
        }
        this.f1422mv.visitInsn(83);
    }

    public void wrapRuntimeConverstion(Class cls) {
        ldcClassConstant(getWrapperClass(cls));
        this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "" + NAMESPACE + "DataConversion", "convert", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
    }

    private Object addSubstatement(ExecutableStatement executableStatement) {
        this.compiledInputs.add(executableStatement);
        this.f1422mv.visitVarInsn(25, 0);
        MethodVisitor methodVisitor = this.f1422mv;
        String str = this.className;
        StringBuilder sb = new StringBuilder();
        sb.append("p");
        sb.append(this.compiledInputs.size() - 1);
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str, sb.toString(), "L" + NAMESPACE + "compiler/ExecutableStatement;");
        this.f1422mv.visitVarInsn(25, 2);
        this.f1422mv.visitVarInsn(25, 3);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, Type.getInternalName(ExecutableStatement.class), "getValue", "(Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;)Ljava/lang/Object;");
        return null;
    }

    private void loadVariableByName(String str) {
        this.f1422mv.visitVarInsn(25, 3);
        this.f1422mv.visitLdcInsn(str);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "" + NAMESPACE + "integration/VariableResolverFactory", "getVariableResolver", "(Ljava/lang/String;)L" + NAMESPACE + "integration/VariableResolver;");
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "" + NAMESPACE + "integration/VariableResolver", "getValue", "()Ljava/lang/Object;");
        this.returnType = Object.class;
    }

    private void loadVariableByIndex(int i) {
        this.f1422mv.visitVarInsn(25, 3);
        intPush(i);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "" + NAMESPACE + "integration/VariableResolverFactory", "getIndexedVariableResolver", "(I)L" + NAMESPACE + "integration/VariableResolver;");
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "" + NAMESPACE + "integration/VariableResolver", "getValue", "()Ljava/lang/Object;");
        this.returnType = Object.class;
    }

    private void loadField(int i) {
        this.f1422mv.visitVarInsn(25, 0);
        this.f1422mv.visitFieldInsn(Opcodes.GETFIELD, this.className, "p" + i, "L" + NAMESPACE + "compiler/ExecutableStatement;");
    }

    private void ldcClassConstant(Class cls) {
        if (OPCODES_VERSION == 48) {
            this.f1422mv.visitLdcInsn(cls.getName());
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;");
            Label label = new Label();
            this.f1422mv.visitJumpInsn(Opcodes.GOTO, label);
            this.f1422mv.visitTypeInsn(Opcodes.NEW, "java/lang/NoClassDefFoundError");
            this.f1422mv.visitInsn(90);
            this.f1422mv.visitInsn(95);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Throwable", "getMessage", "()Ljava/lang/String;");
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/NoClassDefFoundError", "<init>", "(Ljava/lang/String;)V");
            this.f1422mv.visitInsn(Opcodes.ATHROW);
            this.f1422mv.visitLabel(label);
            return;
        }
        this.f1422mv.visitLdcInsn(Type.getType((Class<?>) cls));
    }

    private void buildInputs() {
        if (this.compiledInputs.size() == 0) {
            return;
        }
        StringAppender stringAppender = new StringAppender("(");
        int size = this.compiledInputs.size();
        for (int i = 0; i < size; i++) {
            this.f1421cw.visitField(2, "p" + i, "L" + NAMESPACE + "compiler/ExecutableStatement;", null, null).visitEnd();
            stringAppender.append("L" + NAMESPACE + "compiler/ExecutableStatement;");
        }
        stringAppender.append(")V");
        MethodVisitor methodVisitorVisitMethod = this.f1421cw.visitMethod(1, "<init>", stringAppender.toString(), null, null);
        methodVisitorVisitMethod.visitCode();
        methodVisitorVisitMethod.visitVarInsn(25, 0);
        methodVisitorVisitMethod.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        int i2 = 0;
        while (i2 < size) {
            methodVisitorVisitMethod.visitVarInsn(25, 0);
            int i3 = i2 + 1;
            methodVisitorVisitMethod.visitVarInsn(25, i3);
            methodVisitorVisitMethod.visitFieldInsn(Opcodes.PUTFIELD, this.className, "p" + i2, "L" + NAMESPACE + "compiler/ExecutableStatement;");
            i2 = i3;
        }
        methodVisitorVisitMethod.visitInsn(Opcodes.RETURN);
        methodVisitorVisitMethod.visitMaxs(0, 0);
        methodVisitorVisitMethod.visitEnd();
    }

    private int _getAccessor(Object obj, Class cls) {
        int i;
        Class<?> nonPrimitiveArray;
        if (obj instanceof List) {
            this.f1422mv.visitTypeInsn(Opcodes.NEW, LIST_IMPL);
            this.f1422mv.visitInsn(89);
            this.f1422mv.visitInsn(89);
            List list = (List) obj;
            intPush(list.size());
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, LIST_IMPL, "<init>", "(I)V");
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (_getAccessor(it.next(), cls) != 3) {
                    this.f1422mv.visitInsn(87);
                }
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z");
                this.f1422mv.visitInsn(87);
                this.f1422mv.visitInsn(89);
            }
            this.returnType = List.class;
            return 1;
        }
        if (obj instanceof Map) {
            this.f1422mv.visitTypeInsn(Opcodes.NEW, MAP_IMPL);
            this.f1422mv.visitInsn(89);
            this.f1422mv.visitInsn(89);
            Map map = (Map) obj;
            intPush(map.size());
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, MAP_IMPL, "<init>", "(I)V");
            for (Object obj2 : map.keySet()) {
                this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, "java/util/Map");
                if (_getAccessor(obj2, cls) != 3) {
                    this.f1422mv.visitInsn(87);
                }
                if (_getAccessor(map.get(obj2), cls) != 3) {
                    this.f1422mv.visitInsn(87);
                }
                this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
                this.f1422mv.visitInsn(87);
                this.f1422mv.visitInsn(89);
            }
            this.returnType = Map.class;
            return 2;
        }
        if (obj instanceof Object[]) {
            Accessor[] accessorArr = new Accessor[((Object[]) obj).length];
            if (cls != null) {
                i = 0;
                while (cls.getName().charAt(i) == '[') {
                    i++;
                }
            } else {
                cls = Object[].class;
                i = 1;
            }
            try {
                Class subComponentType = ParseTools.getSubComponentType(cls);
                createArray(subComponentType, ((Object[]) obj).length);
                if (i > 1) {
                    nonPrimitiveArray = ParseTools.findClass(null, ParseTools.repeatChar('[', i - 1) + "L" + ParseTools.getBaseComponentType(cls).getName() + ";", this.pCtx);
                } else {
                    nonPrimitiveArray = ReflectionUtil.toNonPrimitiveArray(cls);
                }
                this.f1422mv.visitInsn(89);
                int i2 = 0;
                for (Object obj3 : (Object[]) obj) {
                    intPush(i2);
                    if (_getAccessor(obj3, nonPrimitiveArray) != 3) {
                        this.f1422mv.visitInsn(87);
                    }
                    if (subComponentType.isPrimitive()) {
                        unwrapPrimitive(subComponentType);
                    }
                    arrayStore(subComponentType);
                    this.f1422mv.visitInsn(89);
                    i2++;
                }
                return 0;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("this error should never throw:" + ParseTools.getBaseComponentType(cls).getName(), e);
            }
        }
        if (cls.isArray()) {
            writeLiteralOrSubexpression(ParseTools.subCompileExpression(((String) obj).toCharArray(), this.pCtx), ParseTools.getSubComponentType(cls));
        } else {
            writeLiteralOrSubexpression(ParseTools.subCompileExpression(((String) obj).toCharArray(), this.pCtx));
        }
        return 3;
    }

    private Class writeLiteralOrSubexpression(Object obj) {
        return writeLiteralOrSubexpression(obj, null, null);
    }

    private Class writeLiteralOrSubexpression(Object obj, Class cls) {
        return writeLiteralOrSubexpression(obj, cls, null);
    }

    private Class writeLiteralOrSubexpression(Object obj, Class cls, Class cls2) {
        Class cls3;
        if (obj instanceof ExecutableLiteral) {
            ExecutableLiteral executableLiteral = (ExecutableLiteral) obj;
            Object literal = executableLiteral.getLiteral();
            if (literal == null) {
                this.f1422mv.visitInsn(1);
                return null;
            }
            Class<?> cls4 = literal.getClass();
            if (cls4 == Integer.class && cls == (cls3 = Integer.TYPE)) {
                intPush(executableLiteral.getInteger32());
                return cls3;
            }
            if (cls != null && cls != cls4) {
                if (!DataConversion.canConvert(cls4, cls)) {
                    throw new CompileException("was expecting type: " + cls.getName() + "; but found type: " + cls4.getName(), this.expr, this.f1420st);
                }
                writeOutLiteralWrapped(DataConversion.convert(literal, cls));
                return cls4;
            }
            writeOutLiteralWrapped(literal);
            return cls4;
        }
        this.literal = false;
        ExecutableStatement executableStatement = (ExecutableStatement) obj;
        addSubstatement(executableStatement);
        if (cls2 == null) {
            cls2 = executableStatement.getKnownEgressType();
        }
        if (cls == null || cls2 == cls || !cls.isPrimitive()) {
            return cls2;
        }
        if (cls2 == null) {
            throw new OptimizationFailure("cannot optimize expression: " + new String(this.expr) + ": cannot determine ingress type for primitive output");
        }
        checkcast(cls2);
        unwrapPrimitive(cls);
        return cls2;
    }

    private void addPrintOut(String str) {
        this.f1422mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        this.f1422mv.visitLdcInsn(str);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Accessor optimizeCollection(ParserContext parserContext, Object obj, Class cls, char[] cArr, int i, int i2, Object obj2, Object obj3, VariableResolverFactory variableResolverFactory) throws IOException {
        int i3;
        this.expr = cArr;
        this.start = i;
        this.cursor = i;
        this.end = i + i2;
        this.length = i2;
        this.returnType = cls;
        this.compiledInputs = new ArrayList<>();
        this.ctx = obj2;
        this.thisRef = obj3;
        this.variableFactory = variableResolverFactory;
        this.pCtx = parserContext;
        _initJIT();
        this.literal = true;
        _getAccessor(obj, cls);
        _finishJIT();
        try {
            Accessor accessor_initializeAccessor = _initializeAccessor();
            return (cArr == null || (i3 = this.length) <= i) ? accessor_initializeAccessor : new Union(parserContext, accessor_initializeAccessor, cArr, i, i3);
        } catch (Exception e) {
            throw new OptimizationFailure("could not optimize collection", e);
        }
    }

    private void checkcast(Class cls) {
        this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(cls));
    }

    private void intPush(int i) {
        if (i < 0 || i >= 6) {
            if (i > -127 && i < 128) {
                this.f1422mv.visitIntInsn(16, i);
                return;
            } else if (i > 32767) {
                this.f1422mv.visitLdcInsn(Integer.valueOf(i));
                return;
            } else {
                this.f1422mv.visitIntInsn(17, i);
                return;
            }
        }
        if (i == 0) {
            this.f1422mv.visitInsn(3);
            return;
        }
        if (i == 1) {
            this.f1422mv.visitInsn(4);
            return;
        }
        if (i == 2) {
            this.f1422mv.visitInsn(5);
            return;
        }
        if (i == 3) {
            this.f1422mv.visitInsn(6);
        } else if (i == 4) {
            this.f1422mv.visitInsn(7);
        } else {
            if (i != 5) {
                return;
            }
            this.f1422mv.visitInsn(8);
        }
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Accessor optimizeObjectCreation(ParserContext parserContext, char[] cArr, int i, int i2, Object obj, Object obj2, VariableResolverFactory variableResolverFactory) {
        String str;
        String str2;
        _initJIT();
        this.compiledInputs = new ArrayList<>();
        this.cursor = i;
        this.start = i;
        int i3 = i + i2;
        this.end = i3;
        this.length = i3 - i;
        this.ctx = obj;
        this.thisRef = obj2;
        this.variableFactory = variableResolverFactory;
        this.pCtx = parserContext;
        String[] strArrCaptureContructorAndResidual = ParseTools.captureContructorAndResidual(cArr, i, i2);
        int i4 = 0;
        List<char[]> methodOrConstructor = ParseTools.parseMethodOrConstructor(strArrCaptureContructorAndResidual[0].toCharArray());
        try {
            if (methodOrConstructor != null) {
                Iterator<char[]> it = methodOrConstructor.iterator();
                while (it.hasNext()) {
                    this.compiledInputs.add((ExecutableStatement) ParseTools.subCompileExpression(it.next(), parserContext));
                }
                Class clsFindClass = ParseTools.findClass(variableResolverFactory, new String(ParseTools.subset(cArr, 0, ArrayTools.findFirst('(', i, this.length, cArr))), parserContext);
                this.f1422mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(clsFindClass));
                this.f1422mv.visitInsn(89);
                int size = methodOrConstructor.size();
                Object[] objArr = new Object[size];
                ArrayList<ExecutableStatement> arrayList = this.compiledInputs;
                int size2 = arrayList.size();
                int i5 = 0;
                while (i4 < size2) {
                    ExecutableStatement executableStatement = arrayList.get(i4);
                    i4++;
                    objArr[i5] = executableStatement.getValue(obj, variableResolverFactory);
                    i5++;
                }
                Constructor bestConstructorCandidate = ParseTools.getBestConstructorCandidate(objArr, clsFindClass, parserContext.isStrongTyping());
                if (bestConstructorCandidate == null) {
                    StringBuilder sb = new StringBuilder();
                    int i6 = 0;
                    while (i6 < size) {
                        sb.append(objArr[i6].getClass().getName());
                        i6++;
                        if (i6 < size) {
                            sb.append(", ");
                        }
                    }
                    throw new CompileException("unable to find constructor: " + clsFindClass.getName() + "(" + sb.toString() + ")", this.expr, this.f1420st);
                }
                this.returnType = bestConstructorCandidate.getDeclaringClass();
                Class<?>[] parameterTypes = bestConstructorCandidate.getParameterTypes();
                Class<?> baseComponentType = null;
                int i7 = -1;
                int i8 = 0;
                while (i8 < methodOrConstructor.size()) {
                    if (i8 < parameterTypes.length) {
                        baseComponentType = parameterTypes[i8];
                        if (bestConstructorCandidate.isVarArgs() && i8 == parameterTypes.length - 1) {
                            baseComponentType = ParseTools.getBaseComponentType(baseComponentType);
                            createArray(baseComponentType, methodOrConstructor.size() - i8);
                            i7 = i8;
                        }
                    } else if (i7 < 0 || baseComponentType == null) {
                        throw new IllegalStateException("Incorrect argument count " + i8);
                    }
                    if (i7 >= 0) {
                        this.f1422mv.visitInsn(89);
                        intPush(i8 - i7);
                    }
                    Class cls = clsFindClass;
                    this.f1422mv.visitVarInsn(25, 0);
                    MethodVisitor methodVisitor = this.f1422mv;
                    String str3 = this.className;
                    StringBuilder sb2 = new StringBuilder();
                    Constructor constructor = bestConstructorCandidate;
                    sb2.append("p");
                    sb2.append(i8);
                    methodVisitor.visitFieldInsn(Opcodes.GETFIELD, str3, sb2.toString(), "L" + NAMESPACE + "compiler/ExecutableStatement;");
                    this.f1422mv.visitVarInsn(25, 2);
                    this.f1422mv.visitVarInsn(25, 3);
                    this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "" + NAMESPACE + "compiler/ExecutableStatement", "getValue", "(Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;)Ljava/lang/Object;");
                    Class<?> wrapperClass = baseComponentType.isPrimitive() ? getWrapperClass(baseComponentType) : baseComponentType;
                    Object obj3 = objArr[i8];
                    if (obj3 != null && !obj3.getClass().isAssignableFrom(baseComponentType)) {
                        ldcClassConstant(wrapperClass);
                        Class<?> cls2 = wrapperClass;
                        this.f1422mv.visitMethodInsn(Opcodes.INVOKESTATIC, "" + NAMESPACE + "DataConversion", "convert", "(Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;");
                        if (baseComponentType.isPrimitive()) {
                            unwrapPrimitive(baseComponentType);
                        } else {
                            this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(cls2));
                        }
                    } else {
                        this.f1422mv.visitTypeInsn(Opcodes.CHECKCAST, Type.getInternalName(baseComponentType));
                    }
                    if (i7 >= 0) {
                        arrayStore(baseComponentType);
                    }
                    i8++;
                    clsFindClass = cls;
                    bestConstructorCandidate = constructor;
                }
                Class cls3 = clsFindClass;
                Constructor constructor2 = bestConstructorCandidate;
                if (i8 < parameterTypes.length && constructor2.isVarArgs()) {
                    createArray(ParseTools.getBaseComponentType(parameterTypes[i8]), 0);
                }
                this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(cls3), "<init>", Type.getConstructorDescriptor(constructor2));
                _finishJIT();
                Accessor accessor_initializeAccessor = _initializeAccessor();
                return (strArrCaptureContructorAndResidual.length <= 1 || (str2 = strArrCaptureContructorAndResidual[1]) == null || str2.trim().equals("")) ? accessor_initializeAccessor : new Union(parserContext, accessor_initializeAccessor, strArrCaptureContructorAndResidual[1].toCharArray(), 0, strArrCaptureContructorAndResidual[1].length());
            }
            Class clsFindClass2 = ParseTools.findClass(variableResolverFactory, new String(cArr), parserContext);
            this.f1422mv.visitTypeInsn(Opcodes.NEW, Type.getInternalName(clsFindClass2));
            this.f1422mv.visitInsn(89);
            this.f1422mv.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(clsFindClass2), "<init>", Type.getConstructorDescriptor(clsFindClass2.getConstructor(EMPTYCLS)));
            _finishJIT();
            Accessor accessor_initializeAccessor2 = _initializeAccessor();
            return (strArrCaptureContructorAndResidual.length <= 1 || (str = strArrCaptureContructorAndResidual[1]) == null || str.trim().equals("")) ? accessor_initializeAccessor2 : new Union(parserContext, accessor_initializeAccessor2, strArrCaptureContructorAndResidual[1].toCharArray(), 0, strArrCaptureContructorAndResidual[1].length());
        } catch (ClassNotFoundException unused) {
            throw new CompileException("class or class reference not found: " + new String(cArr), cArr, this.f1420st);
        } catch (Exception e) {
            throw new OptimizationFailure("could not optimize construtor: " + new String(cArr), e);
        }
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public Class getEgressType() {
        return this.returnType;
    }

    private void dumpAdvancedDebugging() throws IOException {
        if (this.buildLog == null) {
            return;
        }
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("JIT Compiler Dump for: <<");
        char[] cArr = this.expr;
        sb.append(cArr == null ? null : new String(cArr));
        sb.append(">>\n-------------------------------\n");
        printStream.println(sb.toString());
        System.out.println(this.buildLog.toString());
        System.out.println("\n<END OF DUMP>\n");
        if (MVEL.isFileDebugging()) {
            try {
                FileWriter debugFileWriter = ParseTools.getDebugFileWriter();
                debugFileWriter.write(this.buildLog.toString());
                debugFileWriter.flush();
                debugFileWriter.close();
            } catch (IOException unused) {
            }
        }
    }

    private Object propHandlerByteCode(String str, Object obj, Class cls) {
        PropertyHandler propertyHandler = PropertyHandlerFactory.getPropertyHandler(cls);
        if (propertyHandler instanceof ProducesBytecode) {
            ((ProducesBytecode) propertyHandler).produceBytecodeGet(this.f1422mv, str, this.variableFactory);
            return propertyHandler.getProperty(str, obj, this.variableFactory);
        }
        throw new RuntimeException("unable to compileShared: custom accessor does not support producing bytecode: " + propertyHandler.getClass().getName());
    }

    private void propHandlerByteCodePut(String str, Object obj, Class cls, Object obj2) {
        PropertyHandler propertyHandler = PropertyHandlerFactory.getPropertyHandler(cls);
        if (propertyHandler instanceof ProducesBytecode) {
            ((ProducesBytecode) propertyHandler).produceBytecodePut(this.f1422mv, str, this.variableFactory);
            propertyHandler.setProperty(str, obj, this.variableFactory, obj2);
        } else {
            throw new RuntimeException("unable to compileShared: custom accessor does not support producing bytecode: " + propertyHandler.getClass().getName());
        }
    }

    private void writeOutNullHandler(Member member, int i) {
        this.f1422mv.visitInsn(89);
        Label label = new Label();
        this.f1422mv.visitJumpInsn(Opcodes.IFNONNULL, label);
        this.f1422mv.visitInsn(87);
        this.f1422mv.visitVarInsn(25, 0);
        if (i == 0) {
            this.propNull = true;
            this.f1422mv.visitFieldInsn(Opcodes.GETFIELD, this.className, "nullPropertyHandler", "L" + NAMESPACE + "integration/PropertyHandler;");
        } else {
            this.methNull = true;
            this.f1422mv.visitFieldInsn(Opcodes.GETFIELD, this.className, "nullMethodHandler", "L" + NAMESPACE + "integration/PropertyHandler;");
        }
        this.f1422mv.visitLdcInsn(member.getName());
        this.f1422mv.visitVarInsn(25, 1);
        this.f1422mv.visitVarInsn(25, 3);
        this.f1422mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, NAMESPACE + "integration/PropertyHandler", "getProperty", "(Ljava/lang/String;Ljava/lang/Object;L" + NAMESPACE + "integration/VariableResolverFactory;)Ljava/lang/Object;");
        this.f1422mv.visitLabel(label);
    }

    @Override // org.mvel2.optimizers.AccessorOptimizer
    public boolean isLiteralOnly() {
        return this.literal;
    }
}
