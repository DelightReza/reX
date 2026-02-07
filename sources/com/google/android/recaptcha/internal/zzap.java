package com.google.android.recaptcha.internal;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.telegram.tgnet.TLObject;

/* loaded from: classes4.dex */
final class zzap extends ContinuationImpl {
    /* synthetic */ Object zza;
    final /* synthetic */ zzaw zzb;
    int zzc;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzap(zzaw zzawVar, Continuation continuation) {
        super(continuation);
        this.zzb = zzawVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        this.zza = obj;
        this.zzc |= TLObject.FLAG_31;
        Object objMo2713execute0E7RQCE = this.zzb.mo2713execute0E7RQCE(null, 0L, this);
        return objMo2713execute0E7RQCE == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objMo2713execute0E7RQCE : Result.m2960boximpl(objMo2713execute0E7RQCE);
    }
}
