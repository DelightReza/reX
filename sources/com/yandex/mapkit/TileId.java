package com.yandex.mapkit;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class TileId implements Serializable {

    /* renamed from: x */
    private int f411x;

    /* renamed from: y */
    private int f412y;

    /* renamed from: z */
    private int f413z;

    public TileId(int i, int i2, int i3) {
        this.f411x = i;
        this.f412y = i2;
        this.f413z = i3;
    }

    public TileId() {
    }

    public int getX() {
        return this.f411x;
    }

    public int getY() {
        return this.f412y;
    }

    public int getZ() {
        return this.f413z;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f411x = archive.add(this.f411x);
        this.f412y = archive.add(this.f412y);
        this.f413z = archive.add(this.f413z);
    }
}
