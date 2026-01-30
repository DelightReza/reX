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
import org.thunderdog.challegram.rex.RexConfig;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class RexGhostSettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexGhostSettingsController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return "Ghost Mode";
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
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.isGhostMode(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoRead) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoRead(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoTyping) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoTyping(), isUpdate);
        } else if (itemId == R.id.btn_rexGhostNoOnline) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getGhostNoOnline(), isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Ghost Mode Settings"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostMode, 0, "Enable Ghost Mode", R.id.btn_rexGhostMode, RexConfig.INSTANCE.isGhostMode()));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoRead, 0, "No Read Receipts", R.id.btn_rexGhostNoRead, RexConfig.INSTANCE.getGhostNoRead()));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoTyping, 0, "No Typing Indicator", R.id.btn_rexGhostNoTyping, RexConfig.INSTANCE.getGhostNoTyping()));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexGhostNoOnline, 0, "No Online Status", R.id.btn_rexGhostNoOnline, RexConfig.INSTANCE.getGhostNoOnline()));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Hide your online status, read receipts, and typing indicators from others."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexGhostMode) {
      RexConfig.INSTANCE.setGhostMode(!RexConfig.INSTANCE.isGhostMode());
      adapter.updateValuedSettingById(R.id.btn_rexGhostMode);
    } else if (viewId == R.id.btn_rexGhostNoRead) {
      RexConfig.INSTANCE.setGhostNoRead(!RexConfig.INSTANCE.getGhostNoRead());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoRead);
    } else if (viewId == R.id.btn_rexGhostNoTyping) {
      RexConfig.INSTANCE.setGhostNoTyping(!RexConfig.INSTANCE.getGhostNoTyping());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoTyping);
    } else if (viewId == R.id.btn_rexGhostNoOnline) {
      RexConfig.INSTANCE.setGhostNoOnline(!RexConfig.INSTANCE.getGhostNoOnline());
      adapter.updateValuedSettingById(R.id.btn_rexGhostNoOnline);
    }
  }
}
