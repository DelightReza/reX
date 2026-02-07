package org.mvel2;

import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.mvel2.ast.FunctionInstance;
import org.mvel2.ast.PrototypalFunctionInstance;
import org.mvel2.ast.TypeDescriptor;
import org.mvel2.integration.GlobalListenerFactory;
import org.mvel2.integration.PropertyHandlerFactory;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.ImmutableDefaultFactory;
import org.mvel2.util.ErrorUtil;
import org.mvel2.util.ParseTools;
import org.mvel2.util.PropertyTools;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
public class PropertyAccessor {
    private static final int COL = 2;
    private static final int METH = 1;
    private static final int NORM = 0;
    private static final int WITH = 3;
    private Object ctx;
    private Object curr;
    private Class currType;
    private int cursor;
    private int end;
    private boolean first;
    private int length;
    private boolean nullHandle;
    private ParserContext pCtx;
    private char[] property;

    /* renamed from: st */
    private int f1410st;
    private int start;
    private Object thisReference;
    private VariableResolverFactory variableFactory;
    private static final Object[] EMPTYARG = new Object[0];
    private static final Map<Class, WeakHashMap<Integer, WeakReference<Member>>> READ_PROPERTY_RESOLVER_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));
    private static final Map<Class, WeakHashMap<Integer, WeakReference<Member>>> WRITE_PROPERTY_RESOLVER_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));
    private static final Map<Class, WeakHashMap<Integer, WeakReference<Object[]>>> METHOD_RESOLVER_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));
    private static final Map<Member, WeakReference<Class[]>> METHOD_PARMTYPES_CACHE = DesugarCollections.synchronizedMap(new WeakHashMap(10));

    public PropertyAccessor(String str, Object obj) {
        this.start = 0;
        this.cursor = 0;
        this.currType = null;
        this.first = true;
        this.nullHandle = false;
        char[] charArray = str.toCharArray();
        this.property = charArray;
        int length = charArray.length;
        this.end = length;
        this.length = length;
        this.ctx = obj;
        this.variableFactory = new ImmutableDefaultFactory();
    }

    public PropertyAccessor(char[] cArr, Object obj, VariableResolverFactory variableResolverFactory, Object obj2, ParserContext parserContext) {
        this.start = 0;
        this.cursor = 0;
        this.currType = null;
        this.first = true;
        this.nullHandle = false;
        this.property = cArr;
        int length = cArr.length;
        this.end = length;
        this.length = length;
        this.ctx = obj;
        this.variableFactory = variableResolverFactory;
        this.thisReference = obj2;
        this.pCtx = parserContext;
    }

    public PropertyAccessor(char[] cArr, int i, int i2, Object obj, VariableResolverFactory variableResolverFactory, Object obj2, ParserContext parserContext) {
        this.currType = null;
        this.first = true;
        this.nullHandle = false;
        this.property = cArr;
        this.start = i;
        this.f1410st = i;
        this.cursor = i;
        this.length = i2;
        this.end = i + i2;
        this.ctx = obj;
        this.variableFactory = variableResolverFactory;
        this.thisReference = obj2;
        this.pCtx = parserContext;
    }

    public static Object get(String str, Object obj) {
        return new PropertyAccessor(str, obj).get();
    }

    public static Object get(char[] cArr, int i, int i2, Object obj, VariableResolverFactory variableResolverFactory, Object obj2, ParserContext parserContext) {
        return new PropertyAccessor(cArr, i, i2, obj, variableResolverFactory, obj2, parserContext).get();
    }

    public static Object get(String str, Object obj, VariableResolverFactory variableResolverFactory, Object obj2, ParserContext parserContext) {
        return new PropertyAccessor(str.toCharArray(), obj, variableResolverFactory, obj2, parserContext).get();
    }

    public static void set(Object obj, String str, Object obj2) throws IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        new PropertyAccessor(str, obj).set(obj2);
    }

    public static void set(Object obj, VariableResolverFactory variableResolverFactory, String str, Object obj2, ParserContext parserContext) throws IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        new PropertyAccessor(str.toCharArray(), obj, variableResolverFactory, null, parserContext).set(obj2);
    }

    private Object get() {
        this.curr = this.ctx;
        try {
            if (!MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING) {
                return getNormal();
            }
            return getAllowOverride();
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException("could not access property", this.property, this.cursor, e, this.pCtx);
        } catch (IndexOutOfBoundsException e2) {
            int i = this.cursor;
            int i2 = this.length;
            if (i >= i2) {
                this.cursor = i2 - 1;
            }
            throw new PropertyAccessException("array or collections index out of bounds in property: " + new String(this.property, this.cursor, this.length), this.property, this.cursor, e2, this.pCtx);
        } catch (NullPointerException e3) {
            throw new PropertyAccessException("null pointer exception in property: " + new String(this.property), this.property, this.cursor, e3, this.pCtx);
        } catch (InvocationTargetException e4) {
            throw new PropertyAccessException("could not access property", this.property, this.cursor, e4, this.pCtx);
        } catch (CompileException e5) {
            throw ErrorUtil.rewriteIfNeeded(e5, this.property, this.f1410st);
        } catch (Exception e6) {
            throw new PropertyAccessException("unknown exception in expression: " + new String(this.property), this.property, this.cursor, e6, this.pCtx);
        }
    }

    private Object getNormal() {
        while (this.cursor < this.end) {
            int iNextToken = nextToken();
            if (iNextToken == 0) {
                this.curr = getBeanProperty(this.curr, capture());
            } else if (iNextToken == 1) {
                this.curr = getMethod(this.curr, capture());
            } else if (iNextToken == 2) {
                this.curr = getCollectionProperty(this.curr, capture());
            } else if (iNextToken == 3) {
                this.curr = getWithProperty(this.curr);
            }
            if (this.nullHandle) {
                if (this.curr == null) {
                    return null;
                }
                this.nullHandle = false;
            }
            this.first = false;
        }
        return this.curr;
    }

    private Object getAllowOverride() {
        while (this.cursor < this.end) {
            int iNextToken = nextToken();
            if (iNextToken == 0) {
                Object beanPropertyAO = getBeanPropertyAO(this.curr, capture());
                this.curr = beanPropertyAO;
                if (beanPropertyAO == null && PropertyHandlerFactory.hasNullPropertyHandler()) {
                    this.curr = PropertyHandlerFactory.getNullPropertyHandler().getProperty(capture(), this.ctx, this.variableFactory);
                }
            } else if (iNextToken == 1) {
                Object method = getMethod(this.curr, capture());
                this.curr = method;
                if (method == null && PropertyHandlerFactory.hasNullMethodHandler()) {
                    this.curr = PropertyHandlerFactory.getNullMethodHandler().getProperty(capture(), this.ctx, this.variableFactory);
                }
            } else if (iNextToken == 2) {
                this.curr = getCollectionPropertyAO(this.curr, capture());
            } else if (iNextToken == 3) {
                this.curr = getWithProperty(this.curr);
            }
            if (this.nullHandle) {
                if (this.curr == null) {
                    return null;
                }
                this.nullHandle = false;
            } else if (this.curr == null && this.cursor < this.end) {
                throw null;
            }
            this.first = false;
        }
        return this.curr;
    }

    private void set(Object obj) throws IllegalAccessException, ArrayIndexOutOfBoundsException, IllegalArgumentException, InvocationTargetException {
        this.curr = this.ctx;
        try {
            int i = this.end;
            this.end = ParseTools.findAbsoluteLast(this.property);
            Object obj2 = get();
            this.curr = obj2;
            if (obj2 == null) {
                throw new PropertyAccessException("cannot bind to null context: " + new String(this.property, this.cursor, this.length), this.property, this.cursor, this.pCtx);
            }
            this.end = i;
            if (nextToken() == 2) {
                int i2 = this.cursor + 1;
                this.cursor = i2;
                whiteSpaceSkip();
                if (this.cursor == this.length || scanTo(']')) {
                    throw new PropertyAccessException("unterminated '['", this.property, this.cursor, this.pCtx);
                }
                String str = new String(this.property, i2, this.cursor - i2);
                if (!MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING) {
                    Object obj3 = this.curr;
                    if (obj3 instanceof Map) {
                        ((Map) obj3).put(MVEL.eval(str, this.ctx, this.variableFactory), obj);
                        return;
                    }
                    if (obj3 instanceof List) {
                        ((List) obj3).set(((Integer) MVEL.eval(str, this.ctx, this.variableFactory, Integer.class)).intValue(), obj);
                        return;
                    }
                    if (PropertyHandlerFactory.hasPropertyHandler(obj3.getClass())) {
                        PropertyHandlerFactory.getPropertyHandler(this.curr.getClass()).setProperty(str, this.ctx, this.variableFactory, obj);
                        return;
                    }
                    if (this.curr.getClass().isArray()) {
                        Array.set(this.curr, ((Integer) MVEL.eval(str, this.ctx, this.variableFactory, Integer.class)).intValue(), DataConversion.convert(obj, ParseTools.getBaseComponentType(this.curr.getClass())));
                        return;
                    }
                    throw new PropertyAccessException("cannot bind to collection property: " + new String(this.property) + ": not a recognized collection type: " + this.ctx.getClass(), this.property, this.cursor, this.pCtx);
                }
                GlobalListenerFactory.notifySetListeners(this.ctx, str, this.variableFactory, obj);
                Object obj4 = this.curr;
                if (obj4 instanceof Map) {
                    if (PropertyHandlerFactory.hasPropertyHandler(Map.class)) {
                        PropertyHandlerFactory.getPropertyHandler(Map.class).setProperty(str, this.curr, this.variableFactory, obj);
                        return;
                    } else {
                        ((Map) this.curr).put(MVEL.eval(str, this.ctx, this.variableFactory), obj);
                        return;
                    }
                }
                if (obj4 instanceof List) {
                    if (PropertyHandlerFactory.hasPropertyHandler(List.class)) {
                        PropertyHandlerFactory.getPropertyHandler(List.class).setProperty(str, this.curr, this.variableFactory, obj);
                        return;
                    } else {
                        ((List) this.curr).set(((Integer) MVEL.eval(str, this.ctx, this.variableFactory, Integer.class)).intValue(), obj);
                        return;
                    }
                }
                if (obj4.getClass().isArray()) {
                    if (PropertyHandlerFactory.hasPropertyHandler(Array.class)) {
                        PropertyHandlerFactory.getPropertyHandler(Array.class).setProperty(str, this.curr, this.variableFactory, obj);
                        return;
                    } else {
                        Array.set(this.curr, ((Integer) MVEL.eval(str, this.ctx, this.variableFactory, Integer.class)).intValue(), DataConversion.convert(obj, ParseTools.getBaseComponentType(this.curr.getClass())));
                        return;
                    }
                }
                if (PropertyHandlerFactory.hasPropertyHandler(this.curr.getClass())) {
                    PropertyHandlerFactory.getPropertyHandler(this.curr.getClass()).setProperty(str, this.curr, this.variableFactory, obj);
                    return;
                }
                throw new PropertyAccessException("cannot bind to collection property: " + new String(this.property) + ": not a recognized collection type: " + this.ctx.getClass(), this.property, this.cursor, this.pCtx);
            }
            if (MVEL.COMPILER_OPT_ALLOW_OVERRIDE_ALL_PROPHANDLING && PropertyHandlerFactory.hasPropertyHandler(this.curr.getClass())) {
                PropertyHandlerFactory.getPropertyHandler(this.curr.getClass()).setProperty(capture(), this.curr, this.variableFactory, obj);
                return;
            }
            String strCapture = capture();
            Member memberCheckWriteCache = checkWriteCache(this.curr.getClass(), Integer.valueOf(strCapture == null ? 0 : strCapture.hashCode()));
            if (memberCheckWriteCache == null) {
                Class<?> cls = this.curr.getClass();
                Integer numValueOf = Integer.valueOf(strCapture != null ? strCapture.hashCode() : -1);
                Member fieldOrWriteAccessor = obj != null ? PropertyTools.getFieldOrWriteAccessor(this.curr.getClass(), strCapture, obj.getClass()) : PropertyTools.getFieldOrWriteAccessor(this.curr.getClass(), strCapture);
                addWriteCache(cls, numValueOf, fieldOrWriteAccessor);
                memberCheckWriteCache = fieldOrWriteAccessor;
            }
            if (memberCheckWriteCache instanceof Method) {
                Method method = (Method) memberCheckWriteCache;
                Class[] clsArrCheckParmTypesCache = checkParmTypesCache(method);
                if (obj != null && !clsArrCheckParmTypesCache[0].isAssignableFrom(obj.getClass())) {
                    if (!DataConversion.canConvert(clsArrCheckParmTypesCache[0], obj.getClass())) {
                        throw new CompileException("cannot convert type: " + obj.getClass() + ": to " + method.getParameterTypes()[0], this.property, this.cursor);
                    }
                    method.invoke(this.curr, DataConversion.convert(obj, clsArrCheckParmTypesCache[0]));
                    return;
                }
                method.invoke(this.curr, obj);
                return;
            }
            if (memberCheckWriteCache != null) {
                Field field = (Field) memberCheckWriteCache;
                if (obj != null && !field.getType().isAssignableFrom(obj.getClass())) {
                    if (!DataConversion.canConvert(field.getType(), obj.getClass())) {
                        throw new CompileException("cannot convert type: " + obj.getClass() + ": to " + field.getType(), this.property, this.cursor);
                    }
                    field.set(this.curr, DataConversion.convert(obj, field.getType()));
                    return;
                }
                field.set(this.curr, obj);
                return;
            }
            Object obj5 = this.curr;
            if (obj5 instanceof Map) {
                ((Map) obj5).put(MVEL.eval(strCapture, this.ctx, this.variableFactory), obj);
                return;
            }
            if (obj5 instanceof FunctionInstance) {
                ((PrototypalFunctionInstance) obj5).getResolverFactory().getVariableResolver(strCapture).setValue(obj);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("could not access/write property (");
            sb.append(strCapture);
            sb.append(") in: ");
            Object obj6 = this.curr;
            sb.append(obj6 == null ? "Unknown" : obj6.getClass().getName());
            throw new PropertyAccessException(sb.toString(), this.property, this.cursor, this.pCtx);
        } catch (IllegalAccessException e) {
            throw new PropertyAccessException("could not access property", this.property, this.f1410st, e, this.pCtx);
        } catch (InvocationTargetException e2) {
            throw new PropertyAccessException("could not access property", this.property, this.f1410st, e2, this.pCtx);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ab A[LOOP:2: B:46:0x00ab->B:48:0x00b7, LOOP_START] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00cc A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00a4 A[EDGE_INSN: B:65:0x00a4->B:44:0x00a4 BREAK  A[LOOP:1: B:39:0x0090->B:67:?], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int nextToken() {
        /*
            Method dump skipped, instructions count: 205
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.PropertyAccessor.nextToken():int");
    }

    private String capture() {
        return new String(this.property, this.f1410st, trimLeft(this.cursor) - this.f1410st);
    }

    protected int trimLeft(int i) {
        while (i > 0 && ParseTools.isWhitespace(this.property[i - 1])) {
            i--;
        }
        return i;
    }

    public static void clearPropertyResolverCache() {
        READ_PROPERTY_RESOLVER_CACHE.clear();
        WRITE_PROPERTY_RESOLVER_CACHE.clear();
        METHOD_RESOLVER_CACHE.clear();
    }

    public static void reportCacheSizes() {
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder();
        sb.append("read property cache: ");
        Map<Class, WeakHashMap<Integer, WeakReference<Member>>> map = READ_PROPERTY_RESOLVER_CACHE;
        sb.append(map.size());
        printStream.println(sb.toString());
        for (Class cls : map.keySet()) {
            System.out.println(" [" + cls.getName() + "]: " + READ_PROPERTY_RESOLVER_CACHE.get(cls).size() + " entries.");
        }
        PrintStream printStream2 = System.out;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("write property cache: ");
        Map<Class, WeakHashMap<Integer, WeakReference<Member>>> map2 = WRITE_PROPERTY_RESOLVER_CACHE;
        sb2.append(map2.size());
        printStream2.println(sb2.toString());
        for (Class cls2 : map2.keySet()) {
            System.out.println(" [" + cls2.getName() + "]: " + WRITE_PROPERTY_RESOLVER_CACHE.get(cls2).size() + " entries.");
        }
        PrintStream printStream3 = System.out;
        StringBuilder sb3 = new StringBuilder();
        sb3.append("method cache: ");
        Map<Class, WeakHashMap<Integer, WeakReference<Object[]>>> map3 = METHOD_RESOLVER_CACHE;
        sb3.append(map3.size());
        printStream3.println(sb3.toString());
        for (Class cls3 : map3.keySet()) {
            System.out.println(" [" + cls3.getName() + "]: " + METHOD_RESOLVER_CACHE.get(cls3).size() + " entries.");
        }
    }

    private static void addReadCache(Class cls, Integer num, Member member) {
        Map<Class, WeakHashMap<Integer, WeakReference<Member>>> map = READ_PROPERTY_RESOLVER_CACHE;
        synchronized (map) {
            try {
                WeakHashMap<Integer, WeakReference<Member>> weakHashMap = map.get(cls);
                if (weakHashMap == null) {
                    weakHashMap = new WeakHashMap<>();
                    map.put(cls, weakHashMap);
                }
                weakHashMap.put(num, new WeakReference<>(member));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Member checkReadCache(Class cls, Integer num) {
        WeakReference<Member> weakReference;
        WeakHashMap<Integer, WeakReference<Member>> weakHashMap = READ_PROPERTY_RESOLVER_CACHE.get(cls);
        if (weakHashMap == null || (weakReference = weakHashMap.get(num)) == null) {
            return null;
        }
        return weakReference.get();
    }

    private static void addWriteCache(Class cls, Integer num, Member member) {
        Map<Class, WeakHashMap<Integer, WeakReference<Member>>> map = WRITE_PROPERTY_RESOLVER_CACHE;
        synchronized (map) {
            try {
                WeakHashMap<Integer, WeakReference<Member>> weakHashMap = map.get(cls);
                if (weakHashMap == null) {
                    weakHashMap = new WeakHashMap<>();
                    map.put(cls, weakHashMap);
                }
                weakHashMap.put(num, new WeakReference<>(member));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Member checkWriteCache(Class cls, Integer num) {
        WeakReference<Member> weakReference;
        WeakHashMap<Integer, WeakReference<Member>> weakHashMap = WRITE_PROPERTY_RESOLVER_CACHE.get(cls);
        if (weakHashMap == null || (weakReference = weakHashMap.get(num)) == null) {
            return null;
        }
        return weakReference.get();
    }

    public static Class[] checkParmTypesCache(Method method) {
        Class[] clsArr;
        Map<Member, WeakReference<Class[]>> map = METHOD_PARMTYPES_CACHE;
        WeakReference<Class[]> weakReference = map.get(method);
        if (weakReference != null && (clsArr = weakReference.get()) != null) {
            return clsArr;
        }
        Class<?>[] parameterTypes = method.getParameterTypes();
        map.put(method, new WeakReference<>(parameterTypes));
        return parameterTypes;
    }

    private static void addMethodCache(Class cls, Integer num, Method method) {
        Map<Class, WeakHashMap<Integer, WeakReference<Object[]>>> map = METHOD_RESOLVER_CACHE;
        synchronized (map) {
            try {
                WeakHashMap<Integer, WeakReference<Object[]>> weakHashMap = map.get(cls);
                if (weakHashMap == null) {
                    weakHashMap = new WeakHashMap<>();
                    map.put(cls, weakHashMap);
                }
                weakHashMap.put(num, new WeakReference<>(new Object[]{method, method.getParameterTypes()}));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static Object[] checkMethodCache(Class cls, Integer num) {
        WeakReference<Object[]> weakReference;
        WeakHashMap<Integer, WeakReference<Object[]>> weakHashMap = METHOD_RESOLVER_CACHE.get(cls);
        if (weakHashMap == null || (weakReference = weakHashMap.get(num)) == null) {
            return null;
        }
        return weakReference.get();
    }

    private Object getBeanPropertyAO(Object obj, String str) {
        if (obj != null && PropertyHandlerFactory.hasPropertyHandler(obj.getClass())) {
            return PropertyHandlerFactory.getPropertyHandler(obj.getClass()).getProperty(str, obj, this.variableFactory);
        }
        GlobalListenerFactory.notifyGetListeners(obj, str, this.variableFactory);
        return getBeanProperty(obj, str);
    }

    /* JADX WARN: Code restructure failed: missing block: B:132:0x021e, code lost:
    
        return getMethod(r8, r9);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object getBeanProperty(java.lang.Object r8, java.lang.String r9) throws java.lang.ClassNotFoundException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 619
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.PropertyAccessor.getBeanProperty(java.lang.Object, java.lang.String):java.lang.Object");
    }

    private void whiteSpaceSkip() {
        if (this.cursor < this.end) {
            while (ParseTools.isWhitespace(this.property[this.cursor])) {
                int i = this.cursor + 1;
                this.cursor = i;
                if (i >= this.end) {
                    return;
                }
            }
        }
    }

    private boolean scanTo(char c) {
        while (true) {
            int i = this.cursor;
            int i2 = this.end;
            if (i >= i2) {
                return true;
            }
            char[] cArr = this.property;
            char c2 = cArr[i];
            if (c2 == '\"' || c2 == '\'') {
                this.cursor = ParseTools.captureStringLiteral(c2, cArr, i, i2);
            }
            char[] cArr2 = this.property;
            int i3 = this.cursor;
            if (cArr2[i3] == c) {
                return false;
            }
            this.cursor = i3 + 1;
        }
    }

    private Object getWithProperty(Object obj) {
        int i = this.start;
        String strTrim = i == this.cursor ? null : new String(this.property, i, (r1 - i) - 1).trim();
        char[] cArr = this.property;
        int i2 = this.cursor;
        int i3 = i2 + 1;
        int iBalancedCaptureWithLineAccounting = ParseTools.balancedCaptureWithLineAccounting(cArr, i2, this.end, '{', this.pCtx);
        this.cursor = iBalancedCaptureWithLineAccounting;
        ParseTools.parseWithExpressions(strTrim, cArr, i3, iBalancedCaptureWithLineAccounting - i3, obj, this.variableFactory);
        this.cursor++;
        return obj;
    }

    private Object getCollectionProperty(Object obj, String str) {
        if (str.length() != 0 && (obj = getBeanProperty(obj, str)) == null) {
            throw new NullPointerException("null pointer on indexed access for: " + str);
        }
        this.currType = null;
        int i = this.cursor + 1;
        this.cursor = i;
        whiteSpaceSkip();
        if (this.cursor == this.end || scanTo(']')) {
            throw new PropertyAccessException("unterminated '['", this.property, this.cursor, this.pCtx);
        }
        char[] cArr = this.property;
        int i2 = this.cursor;
        this.cursor = i2 + 1;
        String str2 = new String(cArr, i, i2 - i);
        if (obj instanceof Map) {
            return ((Map) obj).get(MVEL.eval(str2, obj, this.variableFactory));
        }
        if (obj instanceof List) {
            return ((List) obj).get(((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue());
        }
        if (obj instanceof Collection) {
            int iIntValue = ((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue();
            Collection collection = (Collection) obj;
            if (iIntValue > collection.size()) {
                throw new PropertyAccessException("index [" + iIntValue + "] out of bounds on collections", this.property, this.cursor, this.pCtx);
            }
            Iterator it = collection.iterator();
            for (int i3 = 0; i3 < iIntValue; i3++) {
                it.next();
            }
            return it.next();
        }
        if (obj.getClass().isArray()) {
            return Array.get(obj, ((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue());
        }
        if (obj instanceof CharSequence) {
            return Character.valueOf(((CharSequence) obj).charAt(((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue()));
        }
        try {
            return TypeDescriptor.getClassReference(this.pCtx, (Class) obj, new TypeDescriptor(this.property, this.start, this.length, 0));
        } catch (Exception e) {
            throw new PropertyAccessException("illegal use of []: unknown type: " + obj.getClass().getName(), this.property, this.f1410st, e, this.pCtx);
        }
    }

    private Object getCollectionPropertyAO(Object obj, String str) throws ClassNotFoundException, SecurityException {
        if (str.length() != 0) {
            obj = getBeanProperty(obj, str);
        }
        this.currType = null;
        if (obj == null) {
            return null;
        }
        int i = this.cursor + 1;
        this.cursor = i;
        whiteSpaceSkip();
        if (this.cursor == this.end || scanTo(']')) {
            throw new PropertyAccessException("unterminated '['", this.property, this.cursor, this.pCtx);
        }
        char[] cArr = this.property;
        int i2 = this.cursor;
        this.cursor = i2 + 1;
        String str2 = new String(cArr, i, i2 - i);
        if (obj instanceof Map) {
            if (PropertyHandlerFactory.hasPropertyHandler(Map.class)) {
                return PropertyHandlerFactory.getPropertyHandler(Map.class).getProperty(str2, obj, this.variableFactory);
            }
            return ((Map) obj).get(MVEL.eval(str2, obj, this.variableFactory));
        }
        if (obj instanceof List) {
            if (PropertyHandlerFactory.hasPropertyHandler(List.class)) {
                return PropertyHandlerFactory.getPropertyHandler(List.class).getProperty(str2, obj, this.variableFactory);
            }
            return ((List) obj).get(((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue());
        }
        if (obj instanceof Collection) {
            if (PropertyHandlerFactory.hasPropertyHandler(Collection.class)) {
                return PropertyHandlerFactory.getPropertyHandler(Collection.class).getProperty(str2, obj, this.variableFactory);
            }
            int iIntValue = ((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue();
            Collection collection = (Collection) obj;
            if (iIntValue > collection.size()) {
                throw new PropertyAccessException("index [" + iIntValue + "] out of bounds on collections", this.property, this.cursor, this.pCtx);
            }
            Iterator it = collection.iterator();
            for (int i3 = 0; i3 < iIntValue; i3++) {
                it.next();
            }
            return it.next();
        }
        if (obj.getClass().isArray()) {
            if (PropertyHandlerFactory.hasPropertyHandler(Array.class)) {
                return PropertyHandlerFactory.getPropertyHandler(Array.class).getProperty(str2, obj, this.variableFactory);
            }
            return Array.get(obj, ((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue());
        }
        if (obj instanceof CharSequence) {
            if (PropertyHandlerFactory.hasPropertyHandler(CharSequence.class)) {
                return PropertyHandlerFactory.getPropertyHandler(CharSequence.class).getProperty(str2, obj, this.variableFactory);
            }
            return Character.valueOf(((CharSequence) obj).charAt(((Integer) MVEL.eval(str2, obj, this.variableFactory)).intValue()));
        }
        try {
            char[] cArr2 = this.property;
            int i4 = this.start;
            return TypeDescriptor.getClassReference(this.pCtx, (Class) obj, new TypeDescriptor(cArr2, i4, this.end - i4, 0));
        } catch (Exception unused) {
            throw new PropertyAccessException("illegal use of []: unknown type: " + obj.getClass().getName(), this.property, this.f1410st, this.pCtx);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:9:0x0027  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object getMethod(java.lang.Object r13, java.lang.String r14) {
        /*
            Method dump skipped, instructions count: 712
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.PropertyAccessor.getMethod(java.lang.Object, java.lang.String):java.lang.Object");
    }

    private static int createSignature(String str, String str2) {
        return str.hashCode() + str2.hashCode();
    }

    private ClassLoader getClassLoader() {
        ParserContext parserContext = this.pCtx;
        return parserContext != null ? parserContext.getClassLoader() : Thread.currentThread().getContextClassLoader();
    }

    /* JADX WARN: Removed duplicated region for block: B:128:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected java.lang.Object tryStaticAccess() throws java.lang.ClassNotFoundException, java.lang.SecurityException {
        /*
            Method dump skipped, instructions count: 300
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.mvel2.PropertyAccessor.tryStaticAccess():java.lang.Object");
    }
}
