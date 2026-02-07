package p017j$.time.format;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.InterfaceC1744r;

/* renamed from: j$.time.format.a */
/* loaded from: classes2.dex */
public final class C1687a extends C1681B {

    /* renamed from: d */
    public final /* synthetic */ C1680A f565d;

    public C1687a(C1680A c1680a) {
        this.f565d = c1680a;
    }

    @Override // p017j$.time.format.C1681B
    /* renamed from: b */
    public final String mo707b(InterfaceC1661k interfaceC1661k, InterfaceC1744r interfaceC1744r, long j, TextStyle textStyle, Locale locale) {
        return this.f565d.m705a(j, textStyle);
    }

    @Override // p017j$.time.format.C1681B
    /* renamed from: c */
    public final String mo708c(InterfaceC1744r interfaceC1744r, long j, TextStyle textStyle, Locale locale) {
        return this.f565d.m705a(j, textStyle);
    }

    @Override // p017j$.time.format.C1681B
    /* renamed from: d */
    public final Iterator mo709d(InterfaceC1661k interfaceC1661k, InterfaceC1744r interfaceC1744r, TextStyle textStyle, Locale locale) {
        List list = (List) ((HashMap) this.f565d.f535b).get(textStyle);
        if (list != null) {
            return list.iterator();
        }
        return null;
    }

    @Override // p017j$.time.format.C1681B
    /* renamed from: e */
    public final Iterator mo710e(InterfaceC1744r interfaceC1744r, TextStyle textStyle, Locale locale) {
        List list = (List) ((HashMap) this.f565d.f535b).get(textStyle);
        if (list != null) {
            return list.iterator();
        }
        return null;
    }
}
