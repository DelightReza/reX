package com.exteragram.messenger.icons.p007ui.picker;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.exteragram.messenger.ExteraConfig;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes.dex */
public abstract class IconPickerController {
    private static IconPickerView pickerView;

    public static void onDestroy() {
        IconPickerView iconPickerView = pickerView;
        if (iconPickerView != null) {
            iconPickerView.saveConfig();
            if (pickerView.getParent() instanceof ViewGroup) {
                ((ViewGroup) pickerView.getParent()).removeView(pickerView);
            }
        }
        pickerView = null;
    }

    public static boolean onBackPressed() {
        IconPickerView iconPickerView = pickerView;
        return iconPickerView != null && iconPickerView.onBackPressed();
    }

    public static void setActive(LaunchActivity launchActivity, boolean z) {
        if (launchActivity == null || launchActivity.isFinishing() || launchActivity.isDestroyed()) {
            return;
        }
        if (z == (pickerView != null)) {
            return;
        }
        if (z) {
            showPicker(launchActivity);
        } else {
            hidePicker(launchActivity);
        }
    }

    private static void showPicker(LaunchActivity launchActivity) {
        if (ExteraConfig.editingIconPackId != null && pickerView == null) {
            pickerView = new IconPickerView(launchActivity);
            FrameLayout mainContainerFrameLayout = launchActivity.getMainContainerFrameLayout();
            if (mainContainerFrameLayout != null) {
                mainContainerFrameLayout.addView(pickerView, new FrameLayout.LayoutParams(-1, -1));
                pickerView.showFab();
            }
        }
    }

    private static void hidePicker(final LaunchActivity launchActivity) {
        if (pickerView == null) {
            return;
        }
        IconObserver.INSTANCE.clear();
        final IconPickerView iconPickerView = pickerView;
        pickerView = null;
        iconPickerView.dismiss(new Runnable() { // from class: com.exteragram.messenger.icons.ui.picker.IconPickerController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                IconPickerController.m1837$r8$lambda$OfRV3mAQKhYExDNLrcksaoqPBI(launchActivity, iconPickerView);
            }
        });
    }

    /* renamed from: $r8$lambda$OfRV3mAQKhYExDNLrcksaoq-PBI, reason: not valid java name */
    public static /* synthetic */ void m1837$r8$lambda$OfRV3mAQKhYExDNLrcksaoqPBI(LaunchActivity launchActivity, IconPickerView iconPickerView) {
        FrameLayout mainContainerFrameLayout = launchActivity.getMainContainerFrameLayout();
        if (mainContainerFrameLayout != null) {
            mainContainerFrameLayout.removeView(iconPickerView);
        } else if (iconPickerView.getParent() instanceof ViewGroup) {
            ((ViewGroup) iconPickerView.getParent()).removeView(iconPickerView);
        }
    }
}
