package p017j$.time.zone;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/* renamed from: j$.time.zone.g */
/* loaded from: classes2.dex */
public final class C1759g implements PrivilegedAction {

    /* renamed from: a */
    public final /* synthetic */ List f752a;

    public C1759g(List list) {
        this.f752a = list;
    }

    @Override // java.security.PrivilegedAction
    public final Object run() {
        String property = System.getProperty("java.time.zone.DefaultZoneRulesProvider");
        if (property != null) {
            try {
                C1760h c1760h = (C1760h) C1760h.class.cast(Class.forName(property, true, C1760h.class.getClassLoader()).newInstance());
                C1760h.m851b(c1760h);
                ((ArrayList) this.f752a).add(c1760h);
                return null;
            } catch (Exception e) {
                throw new Error(e);
            }
        }
        C1760h.m851b(new C1760h());
        return null;
    }
}
