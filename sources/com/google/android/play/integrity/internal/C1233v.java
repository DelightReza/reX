package com.google.android.play.integrity.internal;

/* renamed from: com.google.android.play.integrity.internal.v */
/* loaded from: classes4.dex */
final class C1233v extends AbstractRunnableC1229r {

    /* renamed from: a */
    final /* synthetic */ C1203ac f367a;

    C1233v(C1203ac c1203ac) {
        this.f367a = c1203ac;
    }

    @Override // com.google.android.play.integrity.internal.AbstractRunnableC1229r
    /* renamed from: b */
    public final void mo339b() {
        synchronized (this.f367a.f341g) {
            try {
                if (this.f367a.f347m.get() > 0 && this.f367a.f347m.decrementAndGet() > 0) {
                    this.f367a.f337c.m423c("Leaving the connection open for other ongoing calls.", new Object[0]);
                    return;
                }
                C1203ac c1203ac = this.f367a;
                if (c1203ac.f349o != null) {
                    c1203ac.f337c.m423c("Unbind from service.", new Object[0]);
                    C1203ac c1203ac2 = this.f367a;
                    c1203ac2.f336b.unbindService(c1203ac2.f348n);
                    this.f367a.f342h = false;
                    this.f367a.f349o = null;
                    this.f367a.f348n = null;
                }
                this.f367a.m394x();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
