package com.yandex.runtime.bindings;

import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class ClassHandler<T extends Serializable> implements ArchivingHandler<T> {
    private final boolean isOptional;
    private Class<T> itemClass;

    public ClassHandler(Class<T> cls) {
        this(false, cls);
    }

    public ClassHandler(boolean z, Class<T> cls) {
        this.isOptional = z;
        this.itemClass = cls;
    }

    @Override // com.yandex.runtime.bindings.ArchivingHandler
    public T add(T t, Archive archive) {
        return (T) archive.add((Archive) t, this.isOptional, (Class<Archive>) this.itemClass);
    }
}
