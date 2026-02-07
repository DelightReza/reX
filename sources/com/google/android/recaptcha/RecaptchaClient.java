package com.google.android.recaptcha;

import kotlin.Result;
import kotlin.coroutines.Continuation;

/* loaded from: classes4.dex */
public interface RecaptchaClient {
    /* renamed from: execute-0E7RQCE, reason: not valid java name */
    Object mo2713execute0E7RQCE(RecaptchaAction recaptchaAction, long j, Continuation<? super Result> continuation);

    /* renamed from: execute-gIAlu-s, reason: not valid java name */
    Object mo2714executegIAlus(RecaptchaAction recaptchaAction, Continuation<? super Result> continuation);
}
