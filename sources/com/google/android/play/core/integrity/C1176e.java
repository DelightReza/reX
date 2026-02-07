package com.google.android.play.core.integrity;

import android.net.Network;

/* renamed from: com.google.android.play.core.integrity.e */
/* loaded from: classes4.dex */
final class C1176e extends IntegrityTokenRequest {

    /* renamed from: a */
    private final String f302a;

    /* renamed from: b */
    private final Long f303b;

    /* synthetic */ C1176e(String str, Long l, Network network, C1175d c1175d) {
        this.f302a = str;
        this.f303b = l;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    /* renamed from: a */
    public final Network mo330a() {
        return null;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final Long cloudProjectNumber() {
        return this.f303b;
    }

    public final boolean equals(Object obj) {
        Long l;
        if (obj == this) {
            return true;
        }
        if (obj instanceof IntegrityTokenRequest) {
            IntegrityTokenRequest integrityTokenRequest = (IntegrityTokenRequest) obj;
            if (this.f302a.equals(integrityTokenRequest.nonce()) && ((l = this.f303b) != null ? l.equals(integrityTokenRequest.cloudProjectNumber()) : integrityTokenRequest.cloudProjectNumber() == null)) {
                integrityTokenRequest.mo330a();
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = this.f302a.hashCode() ^ 1000003;
        Long l = this.f303b;
        return ((iHashCode * 1000003) ^ (l == null ? 0 : l.hashCode())) * 1000003;
    }

    @Override // com.google.android.play.core.integrity.IntegrityTokenRequest
    public final String nonce() {
        return this.f302a;
    }

    public final String toString() {
        return "IntegrityTokenRequest{nonce=" + this.f302a + ", cloudProjectNumber=" + this.f303b + ", network=null}";
    }
}
