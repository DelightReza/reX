package com.google.android.gms.internal.measurement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import p017j$.util.DesugarCollections;

/* loaded from: classes4.dex */
final class zzls extends zzlw {
    private static final Class zza = DesugarCollections.unmodifiableList(Collections.EMPTY_LIST).getClass();

    /* synthetic */ zzls(zzlr zzlrVar) {
        super(null);
    }

    @Override // com.google.android.gms.internal.measurement.zzlw
    final void zza(Object obj, long j) {
        Object objUnmodifiableList;
        List list = (List) zznu.zzf(obj, j);
        if (list instanceof zzlq) {
            objUnmodifiableList = ((zzlq) list).zze();
        } else {
            if (zza.isAssignableFrom(list.getClass())) {
                return;
            }
            if ((list instanceof zzmp) && (list instanceof zzli)) {
                zzli zzliVar = (zzli) list;
                if (zzliVar.zzc()) {
                    zzliVar.zzb();
                    return;
                }
                return;
            }
            objUnmodifiableList = DesugarCollections.unmodifiableList(list);
        }
        zznu.zzs(obj, j, objUnmodifiableList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.google.android.gms.internal.measurement.zzlw
    final void zzb(Object obj, Object obj2, long j) {
        zzlp zzlpVar;
        List list = (List) zznu.zzf(obj2, j);
        int size = list.size();
        List listZzd = (List) zznu.zzf(obj, j);
        if (listZzd.isEmpty()) {
            listZzd = listZzd instanceof zzlq ? new zzlp(size) : ((listZzd instanceof zzmp) && (listZzd instanceof zzli)) ? ((zzli) listZzd).zzd(size) : new ArrayList(size);
            zznu.zzs(obj, j, listZzd);
        } else {
            if (zza.isAssignableFrom(listZzd.getClass())) {
                ArrayList arrayList = new ArrayList(listZzd.size() + size);
                arrayList.addAll(listZzd);
                zznu.zzs(obj, j, arrayList);
                zzlpVar = arrayList;
            } else if (listZzd instanceof zznp) {
                zzlp zzlpVar2 = new zzlp(listZzd.size() + size);
                zzlpVar2.addAll(zzlpVar2.size(), (zznp) listZzd);
                zznu.zzs(obj, j, zzlpVar2);
                zzlpVar = zzlpVar2;
            } else if ((listZzd instanceof zzmp) && (listZzd instanceof zzli)) {
                zzli zzliVar = (zzli) listZzd;
                if (!zzliVar.zzc()) {
                    listZzd = zzliVar.zzd(listZzd.size() + size);
                    zznu.zzs(obj, j, listZzd);
                }
            }
            listZzd = zzlpVar;
        }
        int size2 = listZzd.size();
        int size3 = list.size();
        if (size2 > 0 && size3 > 0) {
            listZzd.addAll(list);
        }
        if (size2 > 0) {
            list = listZzd;
        }
        zznu.zzs(obj, j, list);
    }
}
