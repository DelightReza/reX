package com.google.android.play.core.integrity;

import com.google.android.play.core.integrity.StandardIntegrityManager;

/* renamed from: com.google.android.play.core.integrity.h */
/* loaded from: classes4.dex */
final class C1179h extends StandardIntegrityManager.PrepareIntegrityTokenRequest {

    /* renamed from: a */
    private final long f306a;

    /* synthetic */ C1179h(long j, C1178g c1178g) {
        this.f306a = j;
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager.PrepareIntegrityTokenRequest
    /* renamed from: a */
    public final long mo331a() {
        return this.f306a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof StandardIntegrityManager.PrepareIntegrityTokenRequest) && this.f306a == ((StandardIntegrityManager.PrepareIntegrityTokenRequest) obj).mo331a();
    }

    public final int hashCode() {
        long j = this.f306a;
        return 1000003 ^ ((int) (j ^ (j >>> 32)));
    }

    public final String toString() {
        return "PrepareIntegrityTokenRequest{cloudProjectNumber=" + this.f306a + "}";
    }
}
