package p017j$.util.stream;

/* renamed from: j$.util.stream.H3 */
/* loaded from: classes2.dex */
public final class RunnableC1895H3 implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f1018a;

    /* renamed from: b */
    public final /* synthetic */ Object f1019b;

    /* renamed from: c */
    public final /* synthetic */ Object f1020c;

    public /* synthetic */ RunnableC1895H3(int i, Object obj, Object obj2) {
        this.f1018a = i;
        this.f1019b = obj;
        this.f1020c = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f1018a) {
            case 0:
                try {
                    ((Runnable) this.f1019b).run();
                    ((Runnable) this.f1020c).run();
                    return;
                } catch (Throwable th) {
                    try {
                        ((Runnable) this.f1020c).run();
                    } catch (Throwable th2) {
                        try {
                            th.addSuppressed(th2);
                        } catch (Throwable unused) {
                        }
                    }
                    throw th;
                }
            default:
                try {
                    ((BaseStream) this.f1019b).close();
                    ((BaseStream) this.f1020c).close();
                    return;
                } catch (Throwable th3) {
                    try {
                        ((BaseStream) this.f1020c).close();
                    } catch (Throwable th4) {
                        try {
                            th3.addSuppressed(th4);
                        } catch (Throwable unused2) {
                        }
                    }
                    throw th3;
                }
        }
    }
}
