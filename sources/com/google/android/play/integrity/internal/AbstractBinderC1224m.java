package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;

/* renamed from: com.google.android.play.integrity.internal.m */
/* loaded from: classes4.dex */
public abstract class AbstractBinderC1224m extends AbstractBinderC1213b implements InterfaceC1225n {
    /* renamed from: b */
    public static InterfaceC1225n m419b(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.play.core.integrity.protocol.IIntegrityService");
        return iInterfaceQueryLocalInterface instanceof InterfaceC1225n ? (InterfaceC1225n) iInterfaceQueryLocalInterface : new C1223l(iBinder);
    }
}
