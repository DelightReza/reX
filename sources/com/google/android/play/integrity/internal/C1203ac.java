package com.google.android.play.integrity.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import androidx.appcompat.app.WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.google.android.play.integrity.internal.ac */
/* loaded from: classes4.dex */
public final class C1203ac {

    /* renamed from: a */
    private static final Map f335a = new HashMap();

    /* renamed from: b */
    private final Context f336b;

    /* renamed from: c */
    private final C1228q f337c;

    /* renamed from: d */
    private final String f338d;

    /* renamed from: h */
    private boolean f342h;

    /* renamed from: i */
    private final Intent f343i;

    /* renamed from: j */
    private final InterfaceC1235x f344j;

    /* renamed from: n */
    private ServiceConnection f348n;

    /* renamed from: o */
    private IInterface f349o;

    /* renamed from: e */
    private final List f339e = new ArrayList();

    /* renamed from: f */
    private final Set f340f = new HashSet();

    /* renamed from: g */
    private final Object f341g = new Object();

    /* renamed from: l */
    private final IBinder.DeathRecipient f346l = new IBinder.DeathRecipient() { // from class: com.google.android.play.integrity.internal.t
        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            C1203ac.m384k(this.f363a);
        }
    };

    /* renamed from: m */
    private final AtomicInteger f347m = new AtomicInteger(0);

    /* renamed from: k */
    private final WeakReference f345k = new WeakReference(null);

    public C1203ac(Context context, C1228q c1228q, String str, Intent intent, InterfaceC1235x interfaceC1235x, InterfaceC1234w interfaceC1234w) {
        this.f336b = context;
        this.f337c = c1228q;
        this.f338d = str;
        this.f343i = intent;
        this.f344j = interfaceC1235x;
    }

    /* renamed from: k */
    public static /* synthetic */ void m384k(C1203ac c1203ac) {
        c1203ac.f337c.m423c("reportBinderDeath", new Object[0]);
        WindowDecorActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m4m(c1203ac.f345k.get());
        c1203ac.f337c.m423c("%s : Binder has died.", c1203ac.f338d);
        Iterator it = c1203ac.f339e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1229r) it.next()).mo338a(c1203ac.m393w());
        }
        c1203ac.f339e.clear();
        synchronized (c1203ac.f341g) {
            c1203ac.m394x();
        }
    }

    /* renamed from: o */
    static /* bridge */ /* synthetic */ void m388o(final C1203ac c1203ac, final TaskCompletionSource taskCompletionSource) {
        c1203ac.f340f.add(taskCompletionSource);
        taskCompletionSource.getTask().addOnCompleteListener(new OnCompleteListener() { // from class: com.google.android.play.integrity.internal.s
            @Override // com.google.android.gms.tasks.OnCompleteListener
            public final void onComplete(Task task) {
                this.f361a.m398u(taskCompletionSource, task);
            }
        });
    }

    /* renamed from: r */
    static /* bridge */ /* synthetic */ void m391r(C1203ac c1203ac) throws RemoteException {
        c1203ac.f337c.m423c("linkToDeath", new Object[0]);
        try {
            c1203ac.f349o.asBinder().linkToDeath(c1203ac.f346l, 0);
        } catch (RemoteException e) {
            c1203ac.f337c.m422b(e, "linkToDeath failed", new Object[0]);
        }
    }

    /* renamed from: s */
    static /* bridge */ /* synthetic */ void m392s(C1203ac c1203ac) {
        c1203ac.f337c.m423c("unlinkToDeath", new Object[0]);
        c1203ac.f349o.asBinder().unlinkToDeath(c1203ac.f346l, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: x */
    public final void m394x() {
        Iterator it = this.f340f.iterator();
        while (it.hasNext()) {
            ((TaskCompletionSource) it.next()).trySetException(m393w());
        }
        this.f340f.clear();
    }

    /* renamed from: c */
    public final Handler m395c() {
        Handler handler;
        Map map = f335a;
        synchronized (map) {
            try {
                if (!map.containsKey(this.f338d)) {
                    HandlerThread handlerThread = new HandlerThread(this.f338d, 10);
                    handlerThread.start();
                    map.put(this.f338d, new Handler(handlerThread.getLooper()));
                }
                handler = (Handler) map.get(this.f338d);
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    /* renamed from: e */
    public final IInterface m396e() {
        return this.f349o;
    }

    /* renamed from: t */
    public final void m397t(AbstractRunnableC1229r abstractRunnableC1229r, TaskCompletionSource taskCompletionSource) {
        m395c().post(new C1232u(this, abstractRunnableC1229r.m425c(), taskCompletionSource, abstractRunnableC1229r));
    }

    /* renamed from: u */
    final /* synthetic */ void m398u(TaskCompletionSource taskCompletionSource, Task task) {
        synchronized (this.f341g) {
            this.f340f.remove(taskCompletionSource);
        }
    }

    /* renamed from: v */
    public final void m399v(TaskCompletionSource taskCompletionSource) {
        synchronized (this.f341g) {
            this.f340f.remove(taskCompletionSource);
        }
        m395c().post(new C1233v(this));
    }

    /* renamed from: w */
    private final RemoteException m393w() {
        return new RemoteException(String.valueOf(this.f338d).concat(" : Binder has died."));
    }

    /* renamed from: q */
    static /* bridge */ /* synthetic */ void m390q(C1203ac c1203ac, AbstractRunnableC1229r abstractRunnableC1229r) {
        if (c1203ac.f349o != null || c1203ac.f342h) {
            if (!c1203ac.f342h) {
                abstractRunnableC1229r.run();
                return;
            } else {
                c1203ac.f337c.m423c("Waiting to bind to the service.", new Object[0]);
                c1203ac.f339e.add(abstractRunnableC1229r);
                return;
            }
        }
        c1203ac.f337c.m423c("Initiate binding to the service.", new Object[0]);
        c1203ac.f339e.add(abstractRunnableC1229r);
        ServiceConnectionC1202ab serviceConnectionC1202ab = new ServiceConnectionC1202ab(c1203ac, null);
        c1203ac.f348n = serviceConnectionC1202ab;
        c1203ac.f342h = true;
        if (c1203ac.f336b.bindService(c1203ac.f343i, serviceConnectionC1202ab, 1)) {
            return;
        }
        c1203ac.f337c.m423c("Failed to bind to the service.", new Object[0]);
        c1203ac.f342h = false;
        Iterator it = c1203ac.f339e.iterator();
        while (it.hasNext()) {
            ((AbstractRunnableC1229r) it.next()).mo338a(new C1204ad());
        }
        c1203ac.f339e.clear();
    }
}
