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
    return "Privacy and Security";
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
        if (itemId == R.id.btn_rexLockArchived) {
          v.getToggler().setRadioEnabled(sec.isLockArchived(), isUpdate);
        } else if (itemId == R.id.btn_rexLockCalls) {
          v.getToggler().setRadioEnabled(sec.isLockCalls(), isUpdate);
        } else if (itemId == R.id.btn_rexLockSecret) {
          v.getToggler().setRadioEnabled(sec.isLockSecret(), isUpdate);
        } else if (itemId == R.id.btn_rexLockSettings) {
          v.getToggler().setRadioEnabled(sec.isLockSettings(), isUpdate);
        } else if (itemId == R.id.btn_rexHideLockedChats) {
          v.getToggler().setRadioEnabled(sec.isHideLockedChats(), isUpdate);
        } else if (itemId == R.id.btn_rexLockScreenshots) {
          v.getToggler().setRadioEnabled(sec.isLockScreenshots(), isUpdate);
        } else if (itemId == R.id.btn_rexShowNotifications) {
          v.getToggler().setRadioEnabled(sec.isShowNotificationsLocked(), isUpdate);
        } else if (itemId == R.id.btn_rexAllowDevicePin) {
          v.getToggler().setRadioEnabled(sec.isAllowDevicePin(), isUpdate);
        } else if (itemId == R.id.btn_rexAuthTimeout) {
          v.setData(getAuthTimeoutLabel(sec.getAuthTimeout()));
        } else if (itemId == R.id.btn_rexHideMyPhone) {
          v.getToggler().setRadioEnabled(sec.isPhoneMasked(), isUpdate);
        } else if (itemId == R.id.btn_rexHideOthersPhone) {
          v.getToggler().setRadioEnabled(sec.isOthersPhoneMasked(), isUpdate);
        } else if (itemId == R.id.btn_rexLockedAccounts) {
          int count = sec.getHiddenAccountIds().size();
          v.setData("Locked accounts " + count + "/2");
        }
      }
    };

    final List<ListItem> items = new ArrayList<>();

    // Locked actions
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Locked actions"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockArchived, 0, "Lock archived chats"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockCalls, 0, "Lock calls history"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockSecret, 0, "Lock secret chats"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockSettings, 0, "Lock settings"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    // Locked chats
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Locked chats"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexHowItWorks, R.drawable.baseline_help_24, "How does it work?"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexEditLockedChats, R.drawable.baseline_edit_24, "Edit list"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideLockedChats, 0, "Hide locked chats"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexLockScreenshots, 0, "Lock screenshots"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexShowNotifications, 0, "Show notifications"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Hide locked chats from the list."));

    // Actions
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Actions"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexLockedAccounts, 0, "Locked accounts"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    // Biometric
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Biometric"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexAllowDevicePin, 0, "Allow using device PIN"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexAuthTimeout, R.drawable.baseline_schedule_24, "Ask for authentication every..."));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Biometric authentication will only be required once every selected interval."));

    // Phone numbers
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Phone numbers"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideMyPhone, 0, "Hide phone number"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Hide your phone number in the app interfaces."));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideOthersPhone, 0, "Hide other people's phone number"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Hide other people's phone number from their profile."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    final int viewId = v.getId();
    RexSecurityManager sec = RexSecurityManager.getInstance();

    if (viewId == R.id.btn_rexLockArchived) {
      sec.setLockArchived(!sec.isLockArchived());
      adapter.updateValuedSettingById(R.id.btn_rexLockArchived);
    } else if (viewId == R.id.btn_rexLockCalls) {
      sec.setLockCalls(!sec.isLockCalls());
      adapter.updateValuedSettingById(R.id.btn_rexLockCalls);
    } else if (viewId == R.id.btn_rexLockSecret) {
      sec.setLockSecret(!sec.isLockSecret());
      adapter.updateValuedSettingById(R.id.btn_rexLockSecret);
    } else if (viewId == R.id.btn_rexLockSettings) {
      sec.setLockSettings(!sec.isLockSettings());
      adapter.updateValuedSettingById(R.id.btn_rexLockSettings);
    } else if (viewId == R.id.btn_rexHideLockedChats) {
      sec.setHideLockedChats(!sec.isHideLockedChats());
      adapter.updateValuedSettingById(R.id.btn_rexHideLockedChats);
    } else if (viewId == R.id.btn_rexLockScreenshots) {
      boolean newValue = !sec.isLockScreenshots();
      sec.setLockScreenshots(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexLockScreenshots);
      applyScreenshotLock(newValue);
    } else if (viewId == R.id.btn_rexShowNotifications) {
      sec.setShowNotificationsLocked(!sec.isShowNotificationsLocked());
      adapter.updateValuedSettingById(R.id.btn_rexShowNotifications);
    } else if (viewId == R.id.btn_rexAllowDevicePin) {
      sec.setAllowDevicePin(!sec.isAllowDevicePin());
      adapter.updateValuedSettingById(R.id.btn_rexAllowDevicePin);
    } else if (viewId == R.id.btn_rexAuthTimeout) {
      showAuthTimeoutSelector();
    } else if (viewId == R.id.btn_rexHideMyPhone) {
      sec.setPhoneMasked(!sec.isPhoneMasked());
      adapter.updateValuedSettingById(R.id.btn_rexHideMyPhone);
    } else if (viewId == R.id.btn_rexHideOthersPhone) {
      sec.setOthersPhoneMasked(!sec.isOthersPhoneMasked());
      adapter.updateValuedSettingById(R.id.btn_rexHideOthersPhone);
    } else if (viewId == R.id.btn_rexHowItWorks) {
      UI.showToast("Hidden chats are invisible until you long-press the search icon and authenticate. Long-press any chat to hide it.", android.widget.Toast.LENGTH_LONG);
    } else if (viewId == R.id.btn_rexEditLockedChats) {
      RexHiddenChatsController c = new RexHiddenChatsController(context, tdlib);
      navigateTo(c);
    } else if (viewId == R.id.btn_rexLockedAccounts) {
      UI.showToast("Account locking — coming soon", android.widget.Toast.LENGTH_SHORT);
    }
  }

  private void showAuthTimeoutSelector () {
    int current = RexSecurityManager.getInstance().getAuthTimeout();
    showSettings(new SettingsWrapBuilder(R.id.btn_rexAuthTimeout)
      .setRawItems(new ListItem[] {
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeoutAlways, 0, "Always", R.id.btn_rexAuthTimeout, current == 0),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeout10s, 0, "10 seconds", R.id.btn_rexAuthTimeout, current == 10),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeout30s, 0, "30 seconds", R.id.btn_rexAuthTimeout, current == 30),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeout1m, 0, "1 minute", R.id.btn_rexAuthTimeout, current == 60),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeout2m, 0, "2 minutes", R.id.btn_rexAuthTimeout, current == 120),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexAuthTimeout5m, 0, "5 minutes", R.id.btn_rexAuthTimeout, current == 300),
      })
      .setIntDelegate((id, result) -> {
        int selected = result.get(R.id.btn_rexAuthTimeout);
        int timeout = 0;
        if (selected == R.id.btn_rexAuthTimeout10s) timeout = 10;
        else if (selected == R.id.btn_rexAuthTimeout30s) timeout = 30;
        else if (selected == R.id.btn_rexAuthTimeout1m) timeout = 60;
        else if (selected == R.id.btn_rexAuthTimeout2m) timeout = 120;
        else if (selected == R.id.btn_rexAuthTimeout5m) timeout = 300;
        RexSecurityManager.getInstance().setAuthTimeout(timeout);
        adapter.updateValuedSettingById(R.id.btn_rexAuthTimeout);
      })
      .setAllowResize(false));
  }

  private static String getAuthTimeoutLabel (int seconds) {
    if (seconds <= 0) return "Always";
    if (seconds == 10) return "10 seconds";
    if (seconds == 30) return "30 seconds";
    if (seconds == 60) return "1 minute";
    if (seconds == 120) return "2 minutes";
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
