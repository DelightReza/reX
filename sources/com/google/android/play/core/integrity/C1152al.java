package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.SuccessContinuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.play.core.integrity.StandardIntegrityManager;

/* renamed from: com.google.android.play.core.integrity.al */
/* loaded from: classes4.dex */
final class C1152al implements StandardIntegrityManager {

    /* renamed from: a */
    private final C1164ax f262a;

    /* renamed from: b */
    private final C1171bd f263b;

    C1152al(C1164ax c1164ax, C1171bd c1171bd) {
        this.f262a = c1164ax;
        this.f263b = c1171bd;
    }

    /* renamed from: a */
    final /* synthetic */ Task m347a(StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest, Long l) {
        final C1171bd c1171bd = this.f263b;
        final long jMo331a = prepareIntegrityTokenRequest.mo331a();
        final long jLongValue = l.longValue();
        return Tasks.forResult(new StandardIntegrityManager.StandardIntegrityTokenProvider() { // from class: com.google.android.play.core.integrity.bc
            @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenProvider
            public final Task request(StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest) {
                return c1171bd.m364a(jMo331a, jLongValue, standardIntegrityTokenRequest);
            }
        });
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager
    public final Task<StandardIntegrityManager.StandardIntegrityTokenProvider> prepareIntegrityToken(final StandardIntegrityManager.PrepareIntegrityTokenRequest prepareIntegrityTokenRequest) {
        return this.f262a.m359d(prepareIntegrityTokenRequest.mo331a()).onSuccessTask(new SuccessContinuation() { // from class: com.google.android.play.core.integrity.ak
            @Override // com.google.android.gms.tasks.SuccessContinuation
            public final Task then(Object obj) {
                return this.f260a.m347a(prepareIntegrityTokenRequest, (Long) obj);
            }
        });
    }
}
