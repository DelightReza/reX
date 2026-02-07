package com.google.android.gms.internal.clearcut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
final class zzda extends zzcy {
    private static final Class zzlv = DesugarCollections.unmodifiableList(Collections.EMPTY_LIST).getClass();

    private zzda() {
        super();
    }

    private static List zzb(Object obj, long j) {
        return (List) zzfd.zzo(obj, j);
    }

    @Override // com.google.android.gms.internal.clearcut.zzcy
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zzfd.zzo(obj, j);
        if (list instanceof zzcx) {
            objUnmodifiableList = ((zzcx) list).zzbu();
        } else if (zzlv.isAssignableFrom(list.getClass())) {
            return;
        } else {
            objUnmodifiableList = DesugarCollections.unmodifiableList(list);
        }
        zzfd.zza(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.clearcut.zzcy
    final void zza(Object obj, Object obj2, long j) {
        zzcw zzcwVar;
        List listZzb = zzb(obj2, j);
        int size = listZzb.size();
        List listZzb2 = zzb(obj, j);
        if (listZzb2.isEmpty()) {
            listZzb2 = listZzb2 instanceof zzcx ? new zzcw(size) : new ArrayList(size);
            zzfd.zza(obj, j, listZzb2);
        } else {
            if (zzlv.isAssignableFrom(listZzb2.getClass())) {
                ArrayList arrayList = new ArrayList(listZzb2.size() + size);
                arrayList.addAll(listZzb2);
                zzcwVar = arrayList;
            } else if (listZzb2 instanceof zzfa) {
                zzcw zzcwVar2 = new zzcw(listZzb2.size() + size);
                zzcwVar2.addAll((zzfa) listZzb2);
                zzcwVar = zzcwVar2;
            }
            zzfd.zza(obj, j, zzcwVar);
            listZzb2 = zzcwVar;
        }
        int size2 = listZzb2.size();
        int size3 = listZzb.size();
        if (size2 > 0 && size3 > 0) {
            listZzb2.addAll(listZzb);
        }
        if (size2 > 0) {
            listZzb = listZzb2;
        }
        zzfd.zza(obj, j, listZzb);
    }
}
