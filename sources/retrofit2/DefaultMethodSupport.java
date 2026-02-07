package retrofit2;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
abstract class DefaultMethodSupport {
    private static Constructor lookupConstructor;

    static Object invoke(Method method, Class cls, Object obj, Object[] objArr) throws NoSuchMethodException, SecurityException {
        Constructor declaredConstructor = lookupConstructor;
        if (declaredConstructor == null) {
            declaredConstructor = DefaultMethodSupport$$ExternalSyntheticApiModelOutline0.m1383m().getDeclaredConstructor(Class.class, Integer.TYPE);
            declaredConstructor.setAccessible(true);
            lookupConstructor = declaredConstructor;
        }
        return DefaultMethodSupport$$ExternalSyntheticApiModelOutline1.m1384m(declaredConstructor.newInstance(cls, -1)).unreflectSpecial(method, cls).bindTo(obj).invokeWithArguments(objArr);
    }
}
