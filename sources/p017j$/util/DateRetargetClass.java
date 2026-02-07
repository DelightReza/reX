package p017j$.util;

import java.util.Date;
import org.telegram.messenger.MediaDataController;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.Instant;

/* loaded from: classes2.dex */
public final /* synthetic */ class DateRetargetClass {
    public static Instant toInstant(Date date) {
        long time = date.getTime();
        Instant instant = Instant.f433c;
        long j = MediaDataController.MAX_STYLE_RUNS_COUNT;
        return Instant.m551Q(AbstractC1636a.m499R(time, j), ((int) AbstractC1636a.m498Q(time, j)) * 1000000);
    }
}
