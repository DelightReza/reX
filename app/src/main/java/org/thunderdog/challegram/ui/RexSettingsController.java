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
import org.thunderdog.challegram.helper.KeepAliveHelper;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class RexSettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexSettingsController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexSettings);
  }

  @Override
  public int getId() {
    return R.id.controller_rexSettings;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexKeepAlive) {
          view.getToggler().setRadioEnabled(KeepAliveHelper.isKeepAliveEnabled(context), isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexSettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexGhostSettings, R.drawable.baseline_eye_off_24, R.string.RexGhostMode));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSpySettings, R.drawable.baseline_visibility_24, R.string.RexSpyAntiDelete));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexCustomization, R.drawable.baseline_tune_24, R.string.RexCustomization));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexSettingsDescription));
    
    // Keep Alive Section
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Background Service"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexKeepAlive, R.drawable.baseline_sync_24, R.string.RexKeepAlive));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexKeepAliveDescription));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexGhostSettings) {
      navigateTo(new RexGhostSettingsController(context, tdlib));
    } else if (viewId == R.id.btn_rexSpySettings) {
      navigateTo(new RexSpySettingsController(context, tdlib));
    } else if (viewId == R.id.btn_rexCustomization) {
      navigateTo(new RexCustomizationController(context, tdlib));
    } else if (viewId == R.id.btn_rexKeepAlive) {
      boolean currentlyEnabled = KeepAliveHelper.isKeepAliveEnabled(context);
      KeepAliveHelper.setKeepAliveEnabled(context, !currentlyEnabled);
      adapter.updateValuedSettingById(R.id.btn_rexKeepAlive);
    }
  }
}
