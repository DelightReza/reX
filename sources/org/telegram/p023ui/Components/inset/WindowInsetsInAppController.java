package org.telegram.p023ui.Components.inset;

import org.telegram.messenger.AndroidUtilities;

/* loaded from: classes3.dex */
public interface WindowInsetsInAppController {
    void requestInAppKeyboardHeight(int i);

    void requestInAppKeyboardHeightIncludeNavbar(int i);

    void resetInAppKeyboardHeight(boolean z);

    /* renamed from: org.telegram.ui.Components.inset.WindowInsetsInAppController$-CC, reason: invalid class name */
    /* loaded from: classes6.dex */
    public abstract /* synthetic */ class CC {
        public static void $default$requestInAppKeyboardHeightIncludeNavbar(WindowInsetsInAppController windowInsetsInAppController, int i) {
            if (i > 0) {
                windowInsetsInAppController.requestInAppKeyboardHeight(i + AndroidUtilities.navigationBarHeight);
            } else {
                windowInsetsInAppController.resetInAppKeyboardHeight(true);
            }
        }
    }
}
