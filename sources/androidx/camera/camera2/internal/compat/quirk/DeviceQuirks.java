package androidx.camera.camera2.internal.compat.quirk;

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
        QuirkSettingsHolder.instance().observe(CameraXExecutors.directExecutor(), new Consumer() { // from class: androidx.camera.camera2.internal.compat.quirk.DeviceQuirks$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                DeviceQuirks.$r8$lambda$tU6StsICq527ZoYmR0dXjWUBMvg((QuirkSettings) obj);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$tU6StsICq527ZoYmR0dXjWUBMvg(QuirkSettings quirkSettings) {
        sQuirks = new Quirks(DeviceQuirksLoader.loadQuirks(quirkSettings));
        Logger.m43d("DeviceQuirks", "camera2 DeviceQuirks = " + Quirks.toString(sQuirks));
    }

    public static Quirks getAll() {
        return sQuirks;
    }

    public static Quirk get(Class cls) {
        return sQuirks.get(cls);
    }
}
