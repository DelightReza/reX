package com.google.android.play.integrity.internal;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.google.android.play.integrity.internal.d */
/* loaded from: classes4.dex */
public abstract class AbstractC1215d {
    /* renamed from: a */
    public static final List m410a(List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            AbstractC1217f abstractC1217f = (AbstractC1217f) it.next();
            Bundle bundle = new Bundle();
            bundle.putInt("event_type", abstractC1217f.mo412a());
            bundle.putLong("event_timestamp", abstractC1217f.mo413b());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* renamed from: b */
    public static final void m411b(int i, List list) {
        list.add(AbstractC1217f.m414c(i, System.currentTimeMillis()));
    }
}
