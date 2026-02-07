package com.google.android.recaptcha;

import com.google.android.gms.tasks.Task;

/* loaded from: classes4.dex */
public interface RecaptchaTasksClient {
    Task<String> executeTask(RecaptchaAction recaptchaAction);

    Task<String> executeTask(RecaptchaAction recaptchaAction, long j);
}
