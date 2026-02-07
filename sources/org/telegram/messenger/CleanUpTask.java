package org.telegram.messenger;

import android.os.Process;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes4.dex */
public class CleanUpTask {
    public static void resolve() {
        m1154p1();
        m1155p2();
    }

    /* renamed from: p1 */
    private static void m1154p1() {
        LaunchActivity.instance.finishAndRemoveTask();
    }

    /* renamed from: p2 */
    private static void m1155p2() {
        Process.killProcess(Process.myPid());
    }
}
