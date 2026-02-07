package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class ScreenPoint implements Serializable {

    /* renamed from: x */
    private float f409x;

    /* renamed from: y */
    private float f410y;

    public ScreenPoint(float f, float f2) {
        this.f409x = f;
        this.f410y = f2;
    }

    public ScreenPoint() {
    }

    public float getX() {
        return this.f409x;
    }

    public float getY() {
        return this.f410y;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f409x = archive.add(this.f409x);
        this.f410y = archive.add(this.f410y);
    }
}
