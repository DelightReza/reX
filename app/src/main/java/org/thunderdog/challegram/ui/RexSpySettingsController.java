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

public class RexSpySettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexSpySettingsController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return "Spy/Anti-Delete";
  }

  @Override
  public int getId() {
    return R.id.controller_rexSpySettings;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexSpyMode) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.isSpyEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveDeleted) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveDeletedMessages(), isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Spy/Anti-Delete Settings"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpyMode, 0, "Enable Spy Mode", R.id.btn_rexSpyMode, RexConfig.INSTANCE.isSpyEnabled()));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveDeleted, 0, "Save Deleted Messages", R.id.btn_rexSaveDeleted, RexConfig.INSTANCE.getSaveDeletedMessages()));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Save deleted messages and edit history to local database."));
    
    // View saved messages button
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Saved Data"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexViewSaved, 0, "View Deleted Messages", R.drawable.baseline_message_24));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Browse all deleted messages saved by Spy Mode."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexSpyMode) {
      RexConfig.INSTANCE.setSpyEnabled(!RexConfig.INSTANCE.isSpyEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpyMode);
    } else if (viewId == R.id.btn_rexSaveDeleted) {
      RexConfig.INSTANCE.setSaveDeletedMessages(!RexConfig.INSTANCE.getSaveDeletedMessages());
      adapter.updateValuedSettingById(R.id.btn_rexSaveDeleted);
    } else if (viewId == R.id.btn_rexViewSaved) {
      navigateTo(new RexDeletedMessagesController(context, tdlib));
    }
  }
}
