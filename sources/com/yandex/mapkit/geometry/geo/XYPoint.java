package com.yandex.mapkit.geometry.geo;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class XYPoint implements Serializable {

    /* renamed from: x */
    private double f414x;

    /* renamed from: y */
    private double f415y;

    public XYPoint(double d, double d2) {
        this.f414x = d;
        this.f415y = d2;
    }

    public XYPoint() {
    }

    public double getX() {
        return this.f414x;
    }

    public double getY() {
        return this.f415y;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f414x = archive.add(this.f414x);
        this.f415y = archive.add(this.f415y);
    }
}
