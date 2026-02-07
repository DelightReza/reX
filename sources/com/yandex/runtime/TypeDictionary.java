package com.yandex.runtime;

import java.util.Map;

/* loaded from: classes4.dex */
public interface TypeDictionary<T> {
    Map<String, T> getAllItems();

    <U extends T> U getItem(Class<U> cls);
}
