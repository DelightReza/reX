package com.yandex.runtime.attestation_storage.internal;

/* loaded from: classes4.dex */
public interface AttestationListener {
    void onAttestationFailed(String str);

    void onAttestationReceived(byte[] bArr);
}
