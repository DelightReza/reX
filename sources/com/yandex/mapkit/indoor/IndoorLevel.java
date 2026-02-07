package com.yandex.mapkit.indoor;

import com.yandex.runtime.bindings.Archive;
import com.yandex.runtime.bindings.Serializable;

/* loaded from: classes4.dex */
public class IndoorLevel implements Serializable {

    /* renamed from: id */
    private String f416id;
    private boolean isUnderground;
    private String name;

    public IndoorLevel(String str, String str2, boolean z) {
        if (str == null) {
            throw new IllegalArgumentException("Required field \"id\" cannot be null");
        }
        if (str2 == null) {
            throw new IllegalArgumentException("Required field \"name\" cannot be null");
        }
        this.f416id = str;
        this.name = str2;
        this.isUnderground = z;
    }

    public IndoorLevel() {
    }

    public String getId() {
        return this.f416id;
    }

    public String getName() {
        return this.name;
    }

    public boolean getIsUnderground() {
        return this.isUnderground;
    }

    @Override // com.yandex.runtime.bindings.Serializable
    public void serialize(Archive archive) {
        this.f416id = archive.add(this.f416id, false);
        this.name = archive.add(this.name, false);
        this.isUnderground = archive.add(this.isUnderground);
    }
}
