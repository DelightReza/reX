package androidx.work.impl.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public abstract class WorkSpecKt {
    public static final WorkGenerationalId generationalId(WorkSpec workSpec) {
        Intrinsics.checkNotNullParameter(workSpec, "<this>");
        return new WorkGenerationalId(workSpec.f51id, workSpec.getGeneration());
    }
}
