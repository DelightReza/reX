package com.yandex.mapkit.logo;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class Alignment implements Serializable {
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;

    public Alignment(HorizontalAlignment horizontalAlignment, VerticalAlignment verticalAlignment) {
        if (horizontalAlignment == null) {
            throw new IllegalArgumentException("Required field \"horizontalAlignment\" cannot be null");
        }
        if (verticalAlignment == null) {
            throw new IllegalArgumentException("Required field \"verticalAlignment\" cannot be null");
        }
        this.horizontalAlignment = horizontalAlignment;
        this.verticalAlignment = verticalAlignment;
    }

    public Alignment() {
    }

    public HorizontalAlignment getHorizontalAlignment() {
        return this.horizontalAlignment;
    }

    public VerticalAlignment getVerticalAlignment() {
        return this.verticalAlignment;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.horizontalAlignment = (HorizontalAlignment) archive.add((Archive) this.horizontalAlignment, false, (Class<Archive>) HorizontalAlignment.class);
        this.verticalAlignment = (VerticalAlignment) archive.add((Archive) this.verticalAlignment, false, (Class<Archive>) VerticalAlignment.class);
    }
}
