package com.google.android.play.integrity.internal;

import com.google.android.gms.tasks.TaskCompletionSource;

/* renamed from: com.google.android.play.integrity.internal.r */
/* loaded from: classes4.dex */
public abstract class AbstractRunnableC1229r implements Runnable {

    /* renamed from: a */
    private final TaskCompletionSource f360a;

    AbstractRunnableC1229r() {
        this.f360a = null;
    }

    public AbstractRunnableC1229r(TaskCompletionSource taskCompletionSource) {
        this.f360a = taskCompletionSource;
    }

    /* renamed from: a */
    public void mo338a(Exception exc) {
        TaskCompletionSource taskCompletionSource = this.f360a;
        if (taskCompletionSource != null) {
            taskCompletionSource.trySetException(exc);
        }
    }

    /* renamed from: b */
    protected abstract void mo339b();

    /* renamed from: c */
    final TaskCompletionSource m425c() {
        return this.f360a;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            mo339b();
        } catch (Exception e) {
            mo338a(e);
        }
    }
}
