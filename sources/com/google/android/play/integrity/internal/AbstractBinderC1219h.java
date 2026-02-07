package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.integrity.internal.h */
/* loaded from: classes4.dex */
public abstract class AbstractBinderC1219h extends AbstractBinderC1213b implements InterfaceC1220i {
    /* renamed from: b */
    public static InterfaceC1220i m417b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IExpressIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1220i ? (InterfaceC1220i) iInterfaceQueryLocalInterface : new C1218g(iBinder);
    }
}
