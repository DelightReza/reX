/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.ui;

import android.content.Context;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.rex.RexConfig;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class RexGhostSettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexGhostSettingsController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexGhostMode);
  }

  @Override
  public int getId() {
    return R.id.controller_rexGhostSettings;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexGhostMode) {
          // Master toggle shows count of enabled options
          int count = getEnabledGhostOptionsCount();
          String title = Lang.getString(R.string.RexEnableGhostMode) + " " + count + "/5";
          view.setName(title);
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.isGhostMode(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoRead) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoRead(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoStories) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoStories(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoTyping) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoTyping(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoOnline) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoOnline(), isUpdate);
        } else if (itemId == R.id.btn_rexGoOfflineAuto) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGoOfflineAuto(), isUpdate);
        } else if (itemId == R.id.btn_rexReadOnInteract) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getReadOnInteract(), isUpdate);
        } else if (itemId == R.id.btn_rexScheduleMessages) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getScheduleMessages(), isUpdate);
        } else if (itemId == R.id.btn_rexSendWithoutSound) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSendWithoutSound(), isUpdate);
        } else if (itemId == R.id.btn_rexStoryGhostAlert) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getStoryGhostAlert(), isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexGhostModeSettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostMode, 0, R.string.RexEnableGhostMode));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoRead, 0, R.string.RexNoReadReceipts));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoStories, 0, R.string.RexNoReadStories));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoOnline, 0, R.string.RexNoOnlineStatus));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoTyping, 0, R.string.RexNoTypingIndicator));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGoOfflineAuto, 0, R.string.RexGoOfflineAuto));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexGhostModeDescription));

    // Additional toggles section
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexReadOnInteract, 0, R.string.RexReadOnInteract));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexScheduleMessages, 0, R.string.RexScheduleMessages));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSendWithoutSound, 0, R.string.RexSendWithoutSound));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexStoryGhostAlert, 0, R.string.RexStoryGhostAlert));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  private int getEnabledGhostOptionsCount() {
    int count = 0;
    if (RexConfig.INSTANCE.getGhostNoRead()) count++;
    if (RexConfig.INSTANCE.getGhostNoStories()) count++;
    if (RexConfig.INSTANCE.getGhostNoOnline()) count++;
    if (RexConfig.INSTANCE.getGhostNoTyping()) count++;
    if (RexConfig.INSTANCE.getGoOfflineAuto()) count++;
    return count;
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexGhostMode) {
      boolean newState = !RexConfig.INSTANCE.isGhostMode();
      RexConfig.INSTANCE.setGhostMode(newState);
      
      // Master toggle: enable/disable all sub-options (unless locked)
      Set<String> locked = RexConfig.INSTANCE.getLockedGhostOptions();
      if (!locked.contains("noRead")) {
        RexConfig.INSTANCE.setGhostNoRead(newState);
        adapter.updateValuedSettingById(R.id.btn_rexGhostNoRead);
      }
      if (!locked.contains("noStories")) {
        RexConfig.INSTANCE.setGhostNoStories(newState);
        adapter.updateValuedSettingById(R.id.btn_rexGhostNoStories);
      }
      if (!locked.contains("noOnline")) {
        RexConfig.INSTANCE.setGhostNoOnline(newState);
        adapter.updateValuedSettingById(R.id.btn_rexGhostNoOnline);
      }
      if (!locked.contains("noTyping")) {
        RexConfig.INSTANCE.setGhostNoTyping(newState);
        adapter.updateValuedSettingById(R.id.btn_rexGhostNoTyping);
      }
      if (!locked.contains("offlineAuto")) {
        RexConfig.INSTANCE.setGoOfflineAuto(newState);
        adapter.updateValuedSettingById(R.id.btn_rexGoOfflineAuto);
      }
      
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode);
    } else if (viewId == R.id.btn_rexGhostNoRead) {
      RexConfig.INSTANCE.setGhostNoRead(!RexConfig.INSTANCE.getGhostNoRead());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoRead);
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode); // Update count
    } else if (viewId == R.id.btn_rexGhostNoStories) {
      RexConfig.INSTANCE.setGhostNoStories(!RexConfig.INSTANCE.getGhostNoStories());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoStories);
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode); // Update count
    } else if (viewId == R.id.btn_rexGhostNoTyping) {
      RexConfig.INSTANCE.setGhostNoTyping(!RexConfig.INSTANCE.getGhostNoTyping());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoTyping);
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode); // Update count
    } else if (viewId == R.id.btn_rexGhostNoOnline) {
      RexConfig.INSTANCE.setGhostNoOnline(!RexConfig.INSTANCE.getGhostNoOnline());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoOnline);
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode); // Update count
    } else if (viewId == R.id.btn_rexGoOfflineAuto) {
      RexConfig.INSTANCE.setGoOfflineAuto(!RexConfig.INSTANCE.getGoOfflineAuto());
      adapter.updateValuedSettingById(R.id.btn_rexGoOfflineAuto);
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode); // Update count
    } else if (viewId == R.id.btn_rexReadOnInteract) {
      RexConfig.INSTANCE.setReadOnInteract(!RexConfig.INSTANCE.getReadOnInteract());
      adapter.updateValuedSettingById(R.id.btn_rexReadOnInteract);
    } else if (viewId == R.id.btn_rexScheduleMessages) {
      RexConfig.INSTANCE.setScheduleMessages(!RexConfig.INSTANCE.getScheduleMessages());
      adapter.updateValuedSettingById(R.id.btn_rexScheduleMessages);
    } else if (viewId == R.id.btn_rexSendWithoutSound) {
      RexConfig.INSTANCE.setSendWithoutSound(!RexConfig.INSTANCE.getSendWithoutSound());
      adapter.updateValuedSettingById(R.id.btn_rexSendWithoutSound);
    } else if (viewId == R.id.btn_rexStoryGhostAlert) {
      RexConfig.INSTANCE.setStoryGhostAlert(!RexConfig.INSTANCE.getStoryGhostAlert());
      adapter.updateValuedSettingById(R.id.btn_rexStoryGhostAlert);
    }
  }
}
