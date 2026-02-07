package androidx.camera.core;

import java.util.ArrayList;
import java.util.List;
import p017j$.util.DesugarCollections;
import p017j$.util.Objects;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ImageCapture$$ExternalSyntheticBackport1 {
    /* renamed from: m */
    public static /* synthetic */ List m42m(Object[] objArr) {
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            Objects.requireNonNull(obj);
            arrayList.add(obj);
        }
        return DesugarCollections.unmodifiableList(arrayList);
    }
}
