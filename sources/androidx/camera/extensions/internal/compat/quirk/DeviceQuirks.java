package androidx.camera.extensions.internal.compat.quirk;

import androidx.camera.core.Logger;
import androidx.camera.core.impl.Quirk;
import androidx.camera.core.impl.QuirkSettings;
import androidx.camera.core.impl.QuirkSettingsHolder;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.core.util.Consumer;

/* loaded from: classes3.dex */
public abstract class DeviceQuirks {
    private static volatile Quirks sQuirks;

    static {
        QuirkSettingsHolder.instance().observe(CameraXExecutors.directExecutor(), new Consumer() { // from class: androidx.camera.extensions.internal.compat.quirk.DeviceQuirks$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                DeviceQuirks.m1472$r8$lambda$iZ8ZTP6LGoN8Q8XODlRyqvD2_0((QuirkSettings) obj);
            }
        });
    }

    /* renamed from: $r8$lambda$iZ8ZTP6LGoN8Q8XODlR-yqvD2_0, reason: not valid java name */
    public static /* synthetic */ void m1472$r8$lambda$iZ8ZTP6LGoN8Q8XODlRyqvD2_0(QuirkSettings quirkSettings) {
        sQuirks = new Quirks(DeviceQuirksLoader.loadQuirks(quirkSettings));
        Logger.m43d("DeviceQuirks", "extensions DeviceQuirks = " + Quirks.toString(sQuirks));
    }

    public static Quirk get(Class cls) {
        return sQuirks.get(cls);
    }
}
