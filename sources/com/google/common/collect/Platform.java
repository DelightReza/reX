package com.google.common.collect;

import java.util.Arrays;
import java.util.Map;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
abstract class Platform {
    private static final Logger logger = Logger.getLogger(Platform.class.getName());

    static Map newHashMapWithExpectedSize(int i) {
        return Maps.newHashMapWithExpectedSize(i);
    }

    static Object[] newArray(Object[] objArr, int i) {
        if (objArr.length != 0) {
            objArr = Arrays.copyOf(objArr, 0);
        }
        return Arrays.copyOf(objArr, i);
    }

    static Object[] copy(Object[] objArr, int i, int i2, Object[] objArr2) {
        return Arrays.copyOfRange(objArr, i, i2, objArr2.getClass());
    }
}
