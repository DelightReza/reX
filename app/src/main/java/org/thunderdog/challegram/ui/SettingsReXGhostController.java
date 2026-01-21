/*
 * This file is a part of Telegram X
 * Copyright © 2014 (tgx-android@pm.me)
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
import android.util.SparseIntArray;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.unsorted.Settings;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsReXGhostController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {

  private SettingsAdapter adapter;

  public SettingsReXGhostController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.ReXGhostMode);
  }

  @Override
  public int getId () {
    return R.id.controller_rexGhostMode;
  }

  private void rebuildList () {
    ArrayList<ListItem> items = new ArrayList<>();
    
    // Ghost essentials header
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXGhostEssentials));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    
    // Master Ghost Mode toggle with counter
    boolean isExpanded = Settings.instance().isGhostModeExpanded();
    int enabledCount = Settings.instance().getReXGhostModeEnabledCount();
    ListItem masterItem = new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostMasterToggle, 0, R.string.ReXGhostMode);
    masterItem.setString(enabledCount + "/5 " + (isExpanded ? "▲" : "▼"));
    items.add(masterItem);
    
    // Expandable sub-options
    if (isExpanded) {
      items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
      items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexDontReadMessages, 0, R.string.ReXDontReadMessages));
      items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
      items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexDontReadStories, 0, R.string.ReXDontReadStories));
      items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
      items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexDontSendOnline, 0, R.string.ReXDontSendOnline));
      items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
      items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexDontSendTyping, 0, R.string.ReXDontSendTyping));
      items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
      items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGoOfflineAutomatically, 0, R.string.ReXGoOfflineAutomatically));
    }
    
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXGhostModeLongPressDesc));
    
    // Read on Interact
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexReadOnInteract, 0, R.string.ReXReadOnInteract));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXReadOnInteractDesc));
    
    // Schedule Messages
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexScheduleMessages, 0, R.string.ReXScheduleMessages));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXScheduleMessagesDesc));
    
    // Send without Sound
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    ListItem soundItem = new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSendWithoutSound, 0, R.string.ReXSendWithoutSound);
    int soundMode = Settings.instance().getReXSendWithoutSound();
    String[] soundOptions = {Lang.getString(R.string.ReXSendWithoutSoundNever), Lang.getString(R.string.ReXSendWithoutSoundAlways), Lang.getString(R.string.ReXSendWithoutSoundPMs)};
    soundItem.setString(soundOptions[soundMode]);
    items.add(soundItem);
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXSendWithoutSoundDesc));
    
    // Story Ghost Mode Alert - Hidden until TGX has story features
    // items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    // items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexStoryGhostModeAlert, 0, R.string.ReXStoryGhostModeAlert));
    // items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    // items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXStoryGhostModeAlertDesc));

    adapter.setItems(items, false);
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    rebuildList();
  }

  @Override
  public void onClick (View v) {
    int viewId = v.getId();
    if (viewId == R.id.btn_rexGhostMasterToggle) {
      // Toggle expansion
      boolean newExpanded = !Settings.instance().isGhostModeExpanded();
      Settings.instance().setGhostModeExpanded(newExpanded);
      rebuildList();
    } else if (viewId == R.id.btn_rexDontReadMessages) {
      boolean newValue = !Settings.instance().getReXDontReadMessages();
      Settings.instance().setReXDontReadMessages(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexDontReadMessages);
      rebuildList(); // Update counter
    } else if (viewId == R.id.btn_rexDontReadStories) {
      boolean newValue = !Settings.instance().getReXDontReadStories();
      Settings.instance().setReXDontReadStories(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexDontReadStories);
      rebuildList();
    } else if (viewId == R.id.btn_rexDontSendOnline) {
      boolean newValue = !Settings.instance().getReXDontSendOnline();
      Settings.instance().setReXDontSendOnline(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexDontSendOnline);
      rebuildList();
    } else if (viewId == R.id.btn_rexDontSendTyping) {
      boolean newValue = !Settings.instance().getReXDontSendTyping();
      Settings.instance().setReXDontSendTyping(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexDontSendTyping);
      rebuildList();
    } else if (viewId == R.id.btn_rexGoOfflineAutomatically) {
      boolean newValue = !Settings.instance().getReXGoOfflineAutomatically();
      Settings.instance().setReXGoOfflineAutomatically(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexGoOfflineAutomatically);
      rebuildList();
    } else if (viewId == R.id.btn_rexReadOnInteract) {
      boolean newValue = !Settings.instance().getReXReadOnInteract();
      Settings.instance().setReXReadOnInteract(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexReadOnInteract);
    } else if (viewId == R.id.btn_rexScheduleMessages) {
      boolean newValue = !Settings.instance().getReXScheduleMessages();
      Settings.instance().setReXScheduleMessages(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexScheduleMessages);
    } else if (viewId == R.id.btn_rexSendWithoutSound) {
      // Cycle through options: Never -> Always -> PMs only -> Never
      int currentMode = Settings.instance().getReXSendWithoutSound();
      int newMode = (currentMode + 1) % 3;
      Settings.instance().setReXSendWithoutSound(newMode);
      rebuildList();
    }
    // Story Ghost Mode Alert handler - Hidden until TGX has story features
    // else if (viewId == R.id.btn_rexStoryGhostModeAlert) {
    //   boolean newValue = !Settings.instance().getReXStoryGhostModeAlert();
    //   Settings.instance().setReXStoryGhostModeAlert(newValue);
    //   adapter.updateValuedSettingById(R.id.btn_rexStoryGhostModeAlert);
    // }
  }

  @Override
  protected void onAttachToRecyclerView (CustomRecyclerView recyclerView) {
    super.onAttachToRecyclerView(recyclerView);
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexGhostMasterToggle) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXGhostModeMaster(), isUpdate);
        } else if (itemId == R.id.btn_rexDontReadMessages) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXDontReadMessages(), isUpdate);
        } else if (itemId == R.id.btn_rexDontReadStories) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXDontReadStories(), isUpdate);
        } else if (itemId == R.id.btn_rexDontSendOnline) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXDontSendOnline(), isUpdate);
        } else if (itemId == R.id.btn_rexDontSendTyping) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXDontSendTyping(), isUpdate);
        } else if (itemId == R.id.btn_rexGoOfflineAutomatically) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXGoOfflineAutomatically(), isUpdate);
        } else if (itemId == R.id.btn_rexReadOnInteract) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXReadOnInteract(), isUpdate);
        } else if (itemId == R.id.btn_rexScheduleMessages) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXScheduleMessages(), isUpdate);
        }
        // Story Ghost Mode Alert - Hidden until TGX has story features
        // else if (itemId == R.id.btn_rexStoryGhostModeAlert) {
        //   v.getToggler().setRadioEnabled(Settings.instance().getReXStoryGhostModeAlert(), isUpdate);
        // }
      }
    };
  }

  @Override
  public void onApplySettings (int id, SparseIntArray modes) {
    // No-op for now
  }
}
