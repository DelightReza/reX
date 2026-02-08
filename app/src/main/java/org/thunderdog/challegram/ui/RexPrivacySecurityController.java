/*
 * This file is a part of reX
 * Copyright © 2024
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.ui;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.navigation.SettingsWrapBuilder;
import org.thunderdog.challegram.security.RexSecurityManager;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RexPrivacySecurityController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexPrivacySecurityController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return "reX Privacy & Security";
  }

  @Override
  public int getId () {
    return R.id.controller_rexPrivacySecurity;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        RexSecurityManager sec = RexSecurityManager.getInstance();
        if (itemId == R.id.btn_rexLockScreenshots) {
          v.getToggler().setRadioEnabled(sec.isLockScreenshots(), isUpdate);
        } else if (itemId == R.id.btn_rexHideMyPhone) {
          v.getToggler().setRadioEnabled(sec.isPhoneMasked(), isUpdate);
        } else if (itemId == R.id.btn_rexAuthTimeout) {
          v.setData(getAuthTimeoutLabel(sec.getAuthTimeout()));
        }
      }
    };

    final List<ListItem> items = new ArrayList<>();
    RexSecurityManager sec = RexSecurityManager.getInstance();

    // Category 1: Locked Chats
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Locked Chats"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexEditLockedChats, R.drawable.baseline_lock_24, "Edit Locked Chats List"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockScreenshots, 0, "Lock Screenshots"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Locked chats require biometric authentication to open. Lock screenshots applies FLAG_SECURE to prevent screen capture."));

    // Category 2: Biometric
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Biometric"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexAuthTimeout, R.drawable.baseline_schedule_24, "Ask for authentication every..."));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Controls how long the session remains unlocked after successful biometric verification."));

    // Category 3: Phone Numbers
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Phone Numbers"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideMyPhone, 0, "Hide phone numbers"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "When enabled, all phone numbers will be masked as +** *** throughout the app."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    final int viewId = v.getId();
    RexSecurityManager sec = RexSecurityManager.getInstance();

    if (viewId == R.id.btn_rexLockScreenshots) {
      boolean newValue = !sec.isLockScreenshots();
      sec.setLockScreenshots(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexLockScreenshots);
      applyScreenshotLock(newValue);
    } else if (viewId == R.id.btn_rexHideMyPhone) {
      boolean newValue = !sec.isPhoneMasked();
      sec.setPhoneMasked(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexHideMyPhone);
    } else if (viewId == R.id.btn_rexAuthTimeout) {
      showAuthTimeoutSelector();
    } else if (viewId == R.id.btn_rexEditLockedChats) {
      UI.showToast("Locked chats can be managed via long-press on any chat in the chat list.", android.widget.Toast.LENGTH_LONG);
    }
  }

  private void showAuthTimeoutSelector () {
    final String[] options = {"Always", "1 minute", "5 minutes"};
    final int[] values = {0, 60, 300};
    int currentTimeout = RexSecurityManager.getInstance().getAuthTimeout();
    int currentIndex = 0;
    for (int i = 0; i < values.length; i++) {
      if (values[i] == currentTimeout) {
        currentIndex = i;
        break;
      }
    }
    SettingsWrapBuilder b = new SettingsWrapBuilder(R.id.btn_rexAuthTimeout)
      .setRawItems(options)
      .setIntDelegate((id, result) -> {
        int index = result.get(R.id.btn_rexAuthTimeout);
        if (index >= 0 && index < values.length) {
          RexSecurityManager.getInstance().setAuthTimeout(values[index]);
          adapter.updateValuedSettingById(R.id.btn_rexAuthTimeout);
        }
      });
    b.setSaveStr("OK");
    b.setAllowResize(false);
    showSettings(b);
  }

  private static String getAuthTimeoutLabel (int seconds) {
    if (seconds <= 0) return "Always";
    if (seconds == 60) return "1 minute";
    if (seconds == 300) return "5 minutes";
    return seconds + "s";
  }

  private void applyScreenshotLock (boolean lock) {
    if (context() != null && context().getWindow() != null) {
      if (lock) {
        context().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
      } else {
        context().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
      }
    }
  }
}
