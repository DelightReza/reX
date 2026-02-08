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

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.config.RexConfig;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RexGhostSettingsController extends RecyclerViewController<Void> implements View.OnClickListener, View.OnLongClickListener {

  private SettingsAdapter adapter;

  public RexGhostSettingsController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return "Ghost Mode";
  }

  @Override
  public int getId () {
    return R.id.controller_rexGhostSettings;
  }

  private String getGhostToggleLabel () {
    RexConfig config = RexConfig.getInstance();
    int count = config.getEnabledGhostOptionCount();
    return "Ghost Mode " + count + "/5";
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        RexConfig config = RexConfig.getInstance();
        if (itemId == R.id.btn_rexGhostToggle) {
          v.getToggler().setRadioEnabled(config.isGhostEnabled(), isUpdate);
          v.setName(getGhostToggleLabel());
        } else if (itemId == R.id.btn_rexGhostNoRead) {
          v.getToggler().setRadioEnabled(config.isGhostOptionRawEnabled(RexConfig.GHOST_NO_READ), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoStories) {
          v.getToggler().setRadioEnabled(config.isGhostOptionRawEnabled(RexConfig.GHOST_NO_STORIES), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoOnline) {
          v.getToggler().setRadioEnabled(config.isGhostOptionRawEnabled(RexConfig.GHOST_NO_ONLINE), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoTyping) {
          v.getToggler().setRadioEnabled(config.isGhostOptionRawEnabled(RexConfig.GHOST_NO_TYPING), isUpdate);
        } else if (itemId == R.id.btn_rexGhostGoOffline) {
          v.getToggler().setRadioEnabled(config.isGoOfflineAuto(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostReadOnInteract) {
          v.getToggler().setRadioEnabled(config.isReadOnInteract(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostSchedule) {
          v.getToggler().setRadioEnabled(config.isScheduleMessages(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostSendNoSound) {
          v.getToggler().setRadioEnabled(config.isSendWithoutSound(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostStoryAlert) {
          v.getToggler().setRadioEnabled(config.isStoryGhostAlert(), isUpdate);
        }
      }
    };

    final List<ListItem> items = new ArrayList<>();

    // Ghost essentials header
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Ghost essentials"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostToggle, 0, getGhostToggleLabel()));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoRead, 0, "Don't Read Messages"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoStories, 0, "Don't Read Stories"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoOnline, 0, "Don't Send Online"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoTyping, 0, "Don't Send Typing"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostGoOffline, 0, "Go Offline Automatically"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Long-press any option to prevent it from changing when toggling Ghost Mode."));

    // Additional toggles
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostReadOnInteract, 0, "Read on Interact"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Automatically marks a message as read when you send a new one or a reaction."));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostSchedule, 0, "Schedule Messages"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Automatically schedules outgoing messages to send after ~12 seconds (longer for media). Using this feature, you won't appear online. Avoid using on unreliable networks."));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostSendNoSound, 0, "Send without Sound"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Sends outgoing messages without sound by default."));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostStoryAlert, 0, "Story Ghost Mode Alert"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Displays an alert before opening a story, suggesting you enable Ghost Mode."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    final int viewId = v.getId();
    RexConfig config = RexConfig.getInstance();

    if (viewId == R.id.btn_rexGhostToggle) {
      boolean newEnabled = !config.isGhostEnabled();
      config.setGhostEnabled(newEnabled);
      if (newEnabled) {
        // Enable all non-locked options
        String[] allOptions = {RexConfig.GHOST_NO_READ, RexConfig.GHOST_NO_STORIES, RexConfig.GHOST_NO_ONLINE, RexConfig.GHOST_NO_TYPING};
        Set<String> locked = config.getLockedGhostOptions();
        for (String option : allOptions) {
          if (!locked.contains(option)) {
            config.toggleGhostOption(option, true);
          }
        }
        config.setGoOfflineAuto(true);
      } else {
        // Disable all non-locked options
        String[] allOptions = {RexConfig.GHOST_NO_READ, RexConfig.GHOST_NO_STORIES, RexConfig.GHOST_NO_ONLINE, RexConfig.GHOST_NO_TYPING};
        Set<String> locked = config.getLockedGhostOptions();
        for (String option : allOptions) {
          if (!locked.contains(option)) {
            config.toggleGhostOption(option, false);
          }
        }
        if (!locked.contains("GoOffline")) {
          config.setGoOfflineAuto(false);
        }
      }
      refreshAllGhostItems();
    } else if (viewId == R.id.btn_rexGhostNoRead) {
      toggleGhostSubOption(RexConfig.GHOST_NO_READ, R.id.btn_rexGhostNoRead);
    } else if (viewId == R.id.btn_rexGhostNoStories) {
      toggleGhostSubOption(RexConfig.GHOST_NO_STORIES, R.id.btn_rexGhostNoStories);
    } else if (viewId == R.id.btn_rexGhostNoOnline) {
      toggleGhostSubOption(RexConfig.GHOST_NO_ONLINE, R.id.btn_rexGhostNoOnline);
    } else if (viewId == R.id.btn_rexGhostNoTyping) {
      toggleGhostSubOption(RexConfig.GHOST_NO_TYPING, R.id.btn_rexGhostNoTyping);
    } else if (viewId == R.id.btn_rexGhostGoOffline) {
      config.setGoOfflineAuto(!config.isGoOfflineAuto());
      adapter.updateValuedSettingById(R.id.btn_rexGhostGoOffline);
      adapter.updateValuedSettingById(R.id.btn_rexGhostToggle);
    } else if (viewId == R.id.btn_rexGhostReadOnInteract) {
      config.setReadOnInteract(!config.isReadOnInteract());
      adapter.updateValuedSettingById(R.id.btn_rexGhostReadOnInteract);
    } else if (viewId == R.id.btn_rexGhostSchedule) {
      config.setScheduleMessages(!config.isScheduleMessages());
      adapter.updateValuedSettingById(R.id.btn_rexGhostSchedule);
    } else if (viewId == R.id.btn_rexGhostSendNoSound) {
      config.setSendWithoutSound(!config.isSendWithoutSound());
      adapter.updateValuedSettingById(R.id.btn_rexGhostSendNoSound);
    } else if (viewId == R.id.btn_rexGhostStoryAlert) {
      config.setStoryGhostAlert(!config.isStoryGhostAlert());
      adapter.updateValuedSettingById(R.id.btn_rexGhostStoryAlert);
    }
  }

  @Override
  public boolean onLongClick (View v) {
    final int viewId = v.getId();
    String option = null;
    if (viewId == R.id.btn_rexGhostNoRead) option = RexConfig.GHOST_NO_READ;
    else if (viewId == R.id.btn_rexGhostNoStories) option = RexConfig.GHOST_NO_STORIES;
    else if (viewId == R.id.btn_rexGhostNoOnline) option = RexConfig.GHOST_NO_ONLINE;
    else if (viewId == R.id.btn_rexGhostNoTyping) option = RexConfig.GHOST_NO_TYPING;

    if (option != null) {
      RexConfig config = RexConfig.getInstance();
      config.toggleGhostOptionLock(option);
      boolean locked = config.isGhostOptionLocked(option);
      UI.showToast(locked ? "Option locked — immune to master toggle" : "Option unlocked", android.widget.Toast.LENGTH_SHORT);
      return true;
    }
    return false;
  }

  private void toggleGhostSubOption (String option, int btnId) {
    RexConfig config = RexConfig.getInstance();
    boolean current = config.isGhostOptionRawEnabled(option);
    config.toggleGhostOption(option, !current);
    adapter.updateValuedSettingById(btnId);
    adapter.updateValuedSettingById(R.id.btn_rexGhostToggle);
  }

  private void refreshAllGhostItems () {
    adapter.updateValuedSettingById(R.id.btn_rexGhostToggle);
    adapter.updateValuedSettingById(R.id.btn_rexGhostNoRead);
    adapter.updateValuedSettingById(R.id.btn_rexGhostNoStories);
    adapter.updateValuedSettingById(R.id.btn_rexGhostNoOnline);
    adapter.updateValuedSettingById(R.id.btn_rexGhostNoTyping);
    adapter.updateValuedSettingById(R.id.btn_rexGhostGoOffline);
  }
}
