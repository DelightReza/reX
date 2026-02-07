package p017j$.util.concurrent;

import java.util.concurrent.locks.LockSupport;
import p017j$.sun.misc.C1638a;

/* renamed from: j$.util.concurrent.p */
/* loaded from: classes2.dex */
public final class C1807p extends C1802k {

    /* renamed from: h */
    public static final C1638a f853h;

    /* renamed from: i */
    public static final long f854i;

    /* renamed from: e */
    public C1808q f855e;

    /* renamed from: f */
    public volatile C1808q f856f;

    /* renamed from: g */
    public volatile Thread f857g;
    volatile int lockState;

    static {
        C1638a c1638a = C1638a.f426b;
        f853h = c1638a;
        f854i = c1638a.m538h(C1807p.class, "lockState");
    }

    /* renamed from: i */
    public static int m897i(Object obj, Object obj2) {
        int iCompareTo;
        return (obj == null || obj2 == null || (iCompareTo = obj.getClass().getName().compareTo(obj2.getClass().getName())) == 0) ? System.identityHashCode(obj) <= System.identityHashCode(obj2) ? -1 : 1 : iCompareTo;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004b A[PHI: r7
      0x004b: PHI (r7v3 java.lang.Class<?>) = (r7v2 java.lang.Class<?>), (r7v4 java.lang.Class<?>) binds: [B:24:0x0049, B:16:0x0033] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public C1807p(p017j$.util.concurrent.C1808q r11) {
        /*
            r10 = this;
            r0 = -2
            r1 = 0
            r10.<init>(r0, r1, r1)
            r10.f856f = r11
            r0 = r1
        L8:
            if (r11 == 0) goto L6c
            j$.util.concurrent.k r2 = r11.f840d
            j$.util.concurrent.q r2 = (p017j$.util.concurrent.C1808q) r2
            r11.f860g = r1
            r11.f859f = r1
            r3 = 0
            if (r0 != 0) goto L1b
            r11.f858e = r1
            r11.f862i = r3
        L19:
            r0 = r11
            goto L68
        L1b:
            java.lang.Object r4 = r11.f838b
            int r5 = r11.f837a
            r6 = r0
            r7 = r1
        L21:
            java.lang.Object r8 = r6.f838b
            int r9 = r6.f837a
            if (r9 <= r5) goto L29
            r8 = -1
            goto L51
        L29:
            if (r9 >= r5) goto L2d
            r8 = 1
            goto L51
        L2d:
            if (r7 != 0) goto L35
            java.lang.Class r7 = p017j$.util.concurrent.ConcurrentHashMap.m869c(r4)
            if (r7 == 0) goto L4b
        L35:
            int r9 = p017j$.util.concurrent.ConcurrentHashMap.f802g
            if (r8 == 0) goto L48
            java.lang.Class r9 = r8.getClass()
            if (r9 == r7) goto L40
            goto L48
        L40:
            r9 = r4
            java.lang.Comparable r9 = (java.lang.Comparable) r9
            int r9 = r9.compareTo(r8)
            goto L49
        L48:
            r9 = 0
        L49:
            if (r9 != 0) goto L50
        L4b:
            int r8 = m897i(r4, r8)
            goto L51
        L50:
            r8 = r9
        L51:
            if (r8 > 0) goto L56
            j$.util.concurrent.q r9 = r6.f859f
            goto L58
        L56:
            j$.util.concurrent.q r9 = r6.f860g
        L58:
            if (r9 != 0) goto L6a
            r11.f858e = r6
            if (r8 > 0) goto L61
            r6.f859f = r11
            goto L63
        L61:
            r6.f860g = r11
        L63:
            j$.util.concurrent.q r11 = m894c(r0, r11)
            goto L19
        L68:
            r11 = r2
            goto L8
        L6a:
            r6 = r9
            goto L21
        L6c:
            r10.f855e = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.C1807p.<init>(j$.util.concurrent.q):void");
    }

    /* renamed from: d */
    public final void m898d() {
        if (f853h.m534c(this, f854i, 0, 1)) {
            return;
        }
        boolean z = false;
        while (true) {
            int i = this.lockState;
            if ((i & (-3)) == 0) {
                if (f853h.m534c(this, f854i, i, 1)) {
                    break;
                }
            } else if ((i & 2) == 0) {
                if (f853h.m534c(this, f854i, i, i | 2)) {
                    this.f857g = Thread.currentThread();
                    z = true;
                }
            } else if (z) {
                LockSupport.park(this);
            }
        }
        if (z) {
            this.f857g = null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001b, code lost:
    
        return r0;
     */
    @Override // p017j$.util.concurrent.C1802k
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final p017j$.util.concurrent.C1802k mo891a(int r9, java.lang.Object r10) {
        /*
            r8 = this;
            j$.util.concurrent.q r0 = r8.f856f
        L2:
            r1 = 0
            if (r0 == 0) goto L59
            int r6 = r8.lockState
            r2 = r6 & 3
            if (r2 == 0) goto L20
            int r1 = r0.f837a
            if (r1 != r9) goto L1c
            java.lang.Object r1 = r0.f838b
            if (r1 == r10) goto L1b
            if (r1 == 0) goto L1c
            boolean r1 = r10.equals(r1)
            if (r1 == 0) goto L1c
        L1b:
            return r0
        L1c:
            j$.util.concurrent.k r0 = r0.f840d
            r3 = r8
            goto L2
        L20:
            j$.sun.misc.a r2 = p017j$.util.concurrent.C1807p.f853h
            long r4 = p017j$.util.concurrent.C1807p.f854i
            int r7 = r6 + 4
            r3 = r8
            boolean r6 = r2.m534c(r3, r4, r6, r7)
            if (r6 == 0) goto L2
            r6 = 6
            j$.util.concurrent.q r0 = r3.f855e     // Catch: java.lang.Throwable -> L45
            if (r0 != 0) goto L33
            goto L37
        L33:
            j$.util.concurrent.q r1 = r0.m901b(r9, r10, r1)     // Catch: java.lang.Throwable -> L45
        L37:
            int r9 = r2.m536e(r8, r4)
            if (r9 != r6) goto L44
            java.lang.Thread r9 = r3.f857g
            if (r9 == 0) goto L44
            java.util.concurrent.locks.LockSupport.unpark(r9)
        L44:
            return r1
        L45:
            r0 = move-exception
            r9 = r0
            j$.sun.misc.a r10 = p017j$.util.concurrent.C1807p.f853h
            long r0 = p017j$.util.concurrent.C1807p.f854i
            int r10 = r10.m536e(r8, r0)
            if (r10 != r6) goto L58
            java.lang.Thread r10 = r3.f857g
            if (r10 == 0) goto L58
            java.util.concurrent.locks.LockSupport.unpark(r10)
        L58:
            throw r9
        L59:
            r3 = r8
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.C1807p.mo891a(int, java.lang.Object):j$.util.concurrent.k");
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0050 A[PHI: r0
      0x0050: PHI (r0v4 java.lang.Class<?>) = (r0v3 java.lang.Class<?>), (r0v5 java.lang.Class<?>) binds: [B:27:0x004e, B:19:0x0038] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final p017j$.util.concurrent.C1808q m899e(int r12, java.lang.Object r13, java.lang.Object r14) {
        /*
            r11 = this;
            j$.util.concurrent.q r0 = r11.f855e
            r7 = 0
            r8 = 0
            r6 = r0
            r0 = r7
            r1 = 0
        L7:
            if (r6 != 0) goto L18
            j$.util.concurrent.q r1 = new j$.util.concurrent.q
            r5 = 0
            r6 = 0
            r2 = r12
            r3 = r13
            r4 = r14
            r1.<init>(r2, r3, r4, r5, r6)
            r11.f855e = r1
            r11.f856f = r1
            return r7
        L18:
            int r4 = r6.f837a
            r9 = 1
            if (r4 <= r12) goto L20
            r4 = -1
            r10 = -1
            goto L71
        L20:
            if (r4 >= r12) goto L24
            r10 = 1
            goto L71
        L24:
            java.lang.Object r4 = r6.f838b
            if (r4 == r13) goto Lad
            if (r4 == 0) goto L32
            boolean r5 = r13.equals(r4)
            if (r5 == 0) goto L32
            goto Lad
        L32:
            if (r0 != 0) goto L3a
            java.lang.Class r0 = p017j$.util.concurrent.ConcurrentHashMap.m869c(r13)
            if (r0 == 0) goto L50
        L3a:
            int r5 = p017j$.util.concurrent.ConcurrentHashMap.f802g
            if (r4 == 0) goto L4d
            java.lang.Class r5 = r4.getClass()
            if (r5 == r0) goto L45
            goto L4d
        L45:
            r5 = r13
            java.lang.Comparable r5 = (java.lang.Comparable) r5
            int r5 = r5.compareTo(r4)
            goto L4e
        L4d:
            r5 = 0
        L4e:
            if (r5 != 0) goto L70
        L50:
            if (r1 != 0) goto L6a
            j$.util.concurrent.q r1 = r6.f859f
            if (r1 == 0) goto L5e
            j$.util.concurrent.q r1 = r1.m901b(r12, r13, r0)
            if (r1 != 0) goto L5d
            goto L5e
        L5d:
            return r1
        L5e:
            j$.util.concurrent.q r1 = r6.f860g
            if (r1 == 0) goto L69
            j$.util.concurrent.q r1 = r1.m901b(r12, r13, r0)
            if (r1 == 0) goto L69
            return r1
        L69:
            r1 = 1
        L6a:
            int r4 = m897i(r13, r4)
            r10 = r4
            goto L71
        L70:
            r10 = r5
        L71:
            if (r10 > 0) goto L76
            j$.util.concurrent.q r4 = r6.f859f
            goto L78
        L76:
            j$.util.concurrent.q r4 = r6.f860g
        L78:
            if (r4 != 0) goto Laa
            j$.util.concurrent.q r5 = r11.f856f
            j$.util.concurrent.q r1 = new j$.util.concurrent.q
            r2 = r12
            r3 = r13
            r4 = r14
            r1.<init>(r2, r3, r4, r5, r6)
            r11.f856f = r1
            if (r5 == 0) goto L8a
            r5.f861h = r1
        L8a:
            if (r10 > 0) goto L8f
            r6.f859f = r1
            goto L91
        L8f:
            r6.f860g = r1
        L91:
            boolean r0 = r6.f862i
            if (r0 != 0) goto L98
            r1.f862i = r9
            return r7
        L98:
            r11.m898d()
            j$.util.concurrent.q r0 = r11.f855e     // Catch: java.lang.Throwable -> La6
            j$.util.concurrent.q r0 = m894c(r0, r1)     // Catch: java.lang.Throwable -> La6
            r11.f855e = r0     // Catch: java.lang.Throwable -> La6
            r11.lockState = r8
            return r7
        La6:
            r0 = move-exception
            r11.lockState = r8
            throw r0
        Laa:
            r6 = r4
            goto L7
        Lad:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.C1807p.m899e(int, java.lang.Object, java.lang.Object):j$.util.concurrent.q");
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x008e A[PHI: r0
      0x008e: PHI (r0v4 j$.util.concurrent.q) = (r0v3 j$.util.concurrent.q), (r0v12 j$.util.concurrent.q) binds: [B:55:0x008a, B:51:0x0083] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean m900f(p017j$.util.concurrent.C1808q r11) {
        /*
            Method dump skipped, instructions count: 207
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: p017j$.util.concurrent.C1807p.m900f(j$.util.concurrent.q):boolean");
    }

    /* renamed from: g */
    public static C1808q m895g(C1808q c1808q, C1808q c1808q2) {
        C1808q c1808q3;
        if (c1808q2 != null && (c1808q3 = c1808q2.f860g) != null) {
            C1808q c1808q4 = c1808q3.f859f;
            c1808q2.f860g = c1808q4;
            if (c1808q4 != null) {
                c1808q4.f858e = c1808q2;
            }
            C1808q c1808q5 = c1808q2.f858e;
            c1808q3.f858e = c1808q5;
            if (c1808q5 == null) {
                c1808q3.f862i = false;
                c1808q = c1808q3;
            } else if (c1808q5.f859f == c1808q2) {
                c1808q5.f859f = c1808q3;
            } else {
                c1808q5.f860g = c1808q3;
            }
            c1808q3.f859f = c1808q2;
            c1808q2.f858e = c1808q3;
        }
        return c1808q;
    }

    /* renamed from: h */
    public static C1808q m896h(C1808q c1808q, C1808q c1808q2) {
        C1808q c1808q3;
        if (c1808q2 != null && (c1808q3 = c1808q2.f859f) != null) {
            C1808q c1808q4 = c1808q3.f860g;
            c1808q2.f859f = c1808q4;
            if (c1808q4 != null) {
                c1808q4.f858e = c1808q2;
            }
            C1808q c1808q5 = c1808q2.f858e;
            c1808q3.f858e = c1808q5;
            if (c1808q5 == null) {
                c1808q3.f862i = false;
                c1808q = c1808q3;
            } else if (c1808q5.f860g == c1808q2) {
                c1808q5.f860g = c1808q3;
            } else {
                c1808q5.f859f = c1808q3;
            }
            c1808q3.f860g = c1808q2;
            c1808q2.f858e = c1808q3;
        }
        return c1808q;
    }

    /* renamed from: c */
    public static C1808q m894c(C1808q c1808q, C1808q c1808q2) {
        C1808q c1808q3;
        c1808q2.f862i = true;
        while (true) {
            C1808q c1808q4 = c1808q2.f858e;
            if (c1808q4 == null) {
                c1808q2.f862i = false;
                return c1808q2;
            }
            if (!c1808q4.f862i || (c1808q3 = c1808q4.f858e) == null) {
                break;
            }
            C1808q c1808q5 = c1808q3.f859f;
            if (c1808q4 == c1808q5) {
                C1808q c1808q6 = c1808q3.f860g;
                if (c1808q6 != null && c1808q6.f862i) {
                    c1808q6.f862i = false;
                    c1808q4.f862i = false;
                    c1808q3.f862i = true;
                    c1808q2 = c1808q3;
                } else {
                    if (c1808q2 == c1808q4.f860g) {
                        c1808q = m895g(c1808q, c1808q4);
                        C1808q c1808q7 = c1808q4.f858e;
                        c1808q3 = c1808q7 == null ? null : c1808q7.f858e;
                        c1808q4 = c1808q7;
                        c1808q2 = c1808q4;
                    }
                    if (c1808q4 != null) {
                        c1808q4.f862i = false;
                        if (c1808q3 != null) {
                            c1808q3.f862i = true;
                            c1808q = m896h(c1808q, c1808q3);
                        }
                    }
                }
            } else if (c1808q5 != null && c1808q5.f862i) {
                c1808q5.f862i = false;
                c1808q4.f862i = false;
                c1808q3.f862i = true;
                c1808q2 = c1808q3;
            } else {
                if (c1808q2 == c1808q4.f859f) {
                    c1808q = m896h(c1808q, c1808q4);
                    C1808q c1808q8 = c1808q4.f858e;
                    c1808q3 = c1808q8 == null ? null : c1808q8.f858e;
                    c1808q4 = c1808q8;
                    c1808q2 = c1808q4;
                }
                if (c1808q4 != null) {
                    c1808q4.f862i = false;
                    if (c1808q3 != null) {
                        c1808q3.f862i = true;
                        c1808q = m895g(c1808q, c1808q3);
                    }
                }
            }
        }
        return c1808q;
    }

    /* renamed from: b */
    public static C1808q m893b(C1808q c1808q, C1808q c1808q2) {
        while (c1808q2 != null && c1808q2 != c1808q) {
            C1808q c1808q3 = c1808q2.f858e;
            if (c1808q3 == null) {
                c1808q2.f862i = false;
                return c1808q2;
            }
            if (c1808q2.f862i) {
                c1808q2.f862i = false;
                return c1808q;
            }
            C1808q c1808q4 = c1808q3.f859f;
            if (c1808q4 == c1808q2) {
                C1808q c1808q5 = c1808q3.f860g;
                if (c1808q5 != null && c1808q5.f862i) {
                    c1808q5.f862i = false;
                    c1808q3.f862i = true;
                    c1808q = m895g(c1808q, c1808q3);
                    c1808q3 = c1808q2.f858e;
                    c1808q5 = c1808q3 == null ? null : c1808q3.f860g;
                }
                if (c1808q5 != null) {
                    C1808q c1808q6 = c1808q5.f859f;
                    C1808q c1808q7 = c1808q5.f860g;
                    if ((c1808q7 == null || !c1808q7.f862i) && (c1808q6 == null || !c1808q6.f862i)) {
                        c1808q5.f862i = true;
                    } else {
                        if (c1808q7 == null || !c1808q7.f862i) {
                            if (c1808q6 != null) {
                                c1808q6.f862i = false;
                            }
                            c1808q5.f862i = true;
                            c1808q = m896h(c1808q, c1808q5);
                            c1808q3 = c1808q2.f858e;
                            c1808q5 = c1808q3 != null ? c1808q3.f860g : null;
                        }
                        if (c1808q5 != null) {
                            c1808q5.f862i = c1808q3 == null ? false : c1808q3.f862i;
                            C1808q c1808q8 = c1808q5.f860g;
                            if (c1808q8 != null) {
                                c1808q8.f862i = false;
                            }
                        }
                        if (c1808q3 != null) {
                            c1808q3.f862i = false;
                            c1808q = m895g(c1808q, c1808q3);
                        }
                        c1808q2 = c1808q;
                    }
                }
                c1808q2 = c1808q3;
            } else {
                if (c1808q4 != null && c1808q4.f862i) {
                    c1808q4.f862i = false;
                    c1808q3.f862i = true;
                    c1808q = m896h(c1808q, c1808q3);
                    c1808q3 = c1808q2.f858e;
                    c1808q4 = c1808q3 == null ? null : c1808q3.f859f;
                }
                if (c1808q4 != null) {
                    C1808q c1808q9 = c1808q4.f859f;
                    C1808q c1808q10 = c1808q4.f860g;
                    if ((c1808q9 == null || !c1808q9.f862i) && (c1808q10 == null || !c1808q10.f862i)) {
                        c1808q4.f862i = true;
                    } else {
                        if (c1808q9 == null || !c1808q9.f862i) {
                            if (c1808q10 != null) {
                                c1808q10.f862i = false;
                            }
                            c1808q4.f862i = true;
                            c1808q = m895g(c1808q, c1808q4);
                            c1808q3 = c1808q2.f858e;
                            c1808q4 = c1808q3 != null ? c1808q3.f859f : null;
                        }
                        if (c1808q4 != null) {
                            c1808q4.f862i = c1808q3 == null ? false : c1808q3.f862i;
                            C1808q c1808q11 = c1808q4.f859f;
                            if (c1808q11 != null) {
                                c1808q11.f862i = false;
                            }
                        }
                        if (c1808q3 != null) {
                            c1808q3.f862i = false;
                            c1808q = m896h(c1808q, c1808q3);
                        }
                        c1808q2 = c1808q;
                    }
                }
                c1808q2 = c1808q3;
            }
        }
        return c1808q;
    }
}
