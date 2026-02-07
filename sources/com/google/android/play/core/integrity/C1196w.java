package com.google.android.play.core.integrity;

import com.google.android.gms.tasks.Task;

/* renamed from: com.google.android.play.core.integrity.w */
/* loaded from: classes4.dex */
final class C1196w implements IntegrityManager {

    /* renamed from: a */
    private final C1144ad f329a;

    C1196w(C1144ad c1144ad) {
        this.f329a = c1144ad;
    }

    @Override // com.google.android.play.core.integrity.IntegrityManager
    public final Task<IntegrityTokenResponse> requestIntegrityToken(IntegrityTokenRequest integrityTokenRequest) {
        return this.f329a.m343b(integrityTokenRequest);
    }
}
