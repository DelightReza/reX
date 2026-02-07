package com.google.android.play.core.integrity;

import com.google.android.play.core.integrity.StandardIntegrityManager;

/* renamed from: com.google.android.play.core.integrity.k */
/* loaded from: classes4.dex */
final class C1182k extends StandardIntegrityManager.StandardIntegrityTokenRequest {

    /* renamed from: a */
    private final String f308a;

    /* synthetic */ C1182k(String str, C1181j c1181j) {
        this.f308a = str;
    }

    @Override // com.google.android.play.core.integrity.StandardIntegrityManager.StandardIntegrityTokenRequest
    /* renamed from: a */
    public final String mo332a() {
        return this.f308a;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StandardIntegrityManager.StandardIntegrityTokenRequest)) {
            return false;
        }
        StandardIntegrityManager.StandardIntegrityTokenRequest standardIntegrityTokenRequest = (StandardIntegrityManager.StandardIntegrityTokenRequest) obj;
        String str = this.f308a;
        return str == null ? standardIntegrityTokenRequest.mo332a() == null : str.equals(standardIntegrityTokenRequest.mo332a());
    }

    public final int hashCode() {
        String str = this.f308a;
        return (str == null ? 0 : str.hashCode()) ^ 1000003;
    }

    public final String toString() {
        return "StandardIntegrityTokenRequest{requestHash=" + this.f308a + "}";
    }
}
