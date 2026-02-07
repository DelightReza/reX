package p017j$.time.format;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* renamed from: j$.time.format.d */
/* loaded from: classes2.dex */
public final class C1690d implements InterfaceC1691e {

    /* renamed from: a */
    public final InterfaceC1691e[] f568a;

    /* renamed from: b */
    public final boolean f569b;

    /* JADX WARN: Illegal instructions before constructor call */
    public C1690d(List list, boolean z) {
        ArrayList arrayList = (ArrayList) list;
        this((InterfaceC1691e[]) arrayList.toArray(new InterfaceC1691e[arrayList.size()]), z);
    }

    public C1690d(InterfaceC1691e[] interfaceC1691eArr, boolean z) {
        this.f568a = interfaceC1691eArr;
        this.f569b = z;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0026, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002c, code lost:
    
        if (r2 != false) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:
    
        return true;
     */
    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: i */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean mo721i(p017j$.time.format.C1711y r8, java.lang.StringBuilder r9) {
        /*
            r7 = this;
            int r0 = r9.length()
            r1 = 1
            boolean r2 = r7.f569b
            if (r2 == 0) goto Le
            int r3 = r8.f636c
            int r3 = r3 + r1
            r8.f636c = r3
        Le:
            j$.time.format.e[] r3 = r7.f568a     // Catch: java.lang.Throwable -> L27
            int r4 = r3.length     // Catch: java.lang.Throwable -> L27
            r5 = 0
        L12:
            if (r5 >= r4) goto L2c
            r6 = r3[r5]     // Catch: java.lang.Throwable -> L27
            boolean r6 = r6.mo721i(r8, r9)     // Catch: java.lang.Throwable -> L27
            if (r6 != 0) goto L29
            r9.setLength(r0)     // Catch: java.lang.Throwable -> L27
            if (r2 == 0) goto L2f
        L21:
            int r9 = r8.f636c
            int r9 = r9 - r1
            r8.f636c = r9
            return r1
        L27:
            r9 = move-exception
            goto L30
        L29:
            int r5 = r5 + 1
            goto L12
        L2c:
            if (r2 == 0) goto L2f
            goto L21
        L2f:
            return r1
        L30:
            if (r2 == 0) goto L37
            int r0 = r8.f636c
            int r0 = r0 - r1
            r8.f636c = r0
        L37:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.time.format.C1690d.mo721i(j$.time.format.y, java.lang.StringBuilder):boolean");
    }

    @Override // p017j$.time.format.InterfaceC1691e
    /* renamed from: j */
    public final int mo722j(C1708v c1708v, CharSequence charSequence, int i) {
        boolean z = this.f569b;
        InterfaceC1691e[] interfaceC1691eArr = this.f568a;
        int i2 = 0;
        if (z) {
            ArrayList arrayList = c1708v.f628d;
            C1683D c1683dM762c = c1708v.m762c();
            c1683dM762c.getClass();
            C1683D c1683d = new C1683D();
            ((HashMap) c1683d.f540a).putAll(c1683dM762c.f540a);
            c1683d.f541b = c1683dM762c.f541b;
            c1683d.f542c = c1683dM762c.f542c;
            c1683d.f543d = c1683dM762c.f543d;
            arrayList.add(c1683d);
            int length = interfaceC1691eArr.length;
            int iMo722j = i;
            while (i2 < length) {
                iMo722j = interfaceC1691eArr[i2].mo722j(c1708v, charSequence, iMo722j);
                if (iMo722j < 0) {
                    c1708v.f628d.remove(r8.size() - 1);
                    return i;
                }
                i2++;
            }
            c1708v.f628d.remove(r8.size() - 2);
            return iMo722j;
        }
        int length2 = interfaceC1691eArr.length;
        while (i2 < length2) {
            i = interfaceC1691eArr[i2].mo722j(c1708v, charSequence, i);
            if (i < 0) {
                return i;
            }
            i2++;
        }
        return i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        InterfaceC1691e[] interfaceC1691eArr = this.f568a;
        if (interfaceC1691eArr != null) {
            boolean z = this.f569b;
            sb.append(z ? "[" : "(");
            for (InterfaceC1691e interfaceC1691e : interfaceC1691eArr) {
                sb.append(interfaceC1691e);
            }
            sb.append(z ? "]" : ")");
        }
        return sb.toString();
    }
}
