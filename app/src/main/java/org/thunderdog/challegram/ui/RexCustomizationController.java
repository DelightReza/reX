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
import org.thunderdog.challegram.helper.BatteryOptimizationHelper;
import org.thunderdog.challegram.rex.RexConfig;
import org.thunderdog.challegram.service.TGXKeepAliveService;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class RexCustomizationController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexCustomizationController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexCustomization);
  }

  @Override
  public int getId() {
    return R.id.controller_rexCustomization;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexSaveRestricted) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.isSaveRestricted(), isUpdate);
        } else if (itemId == R.id.btn_rexNativeKeepAlive) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getUseNativeKeepAlive(), isUpdate);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexCustomizationSettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveRestricted, 0, R.string.RexSaveRestrictedContent));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexNativeKeepAlive, 0, R.string.NativeKeepAlive));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.NativeKeepAliveDescription));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexSaveRestricted) {
      RexConfig.INSTANCE.setSaveRestricted(!RexConfig.INSTANCE.isSaveRestricted());
      adapter.updateValuedSettingById(R.id.btn_rexSaveRestricted);
    } else if (viewId == R.id.btn_rexNativeKeepAlive) {
      toggleNativeKeepAlive();
    }
  }

  private void toggleNativeKeepAlive() {
    boolean currentValue = RexConfig.INSTANCE.getUseNativeKeepAlive();
    boolean newValue = !currentValue;

    if (newValue) {
      // Enabling Native Keep-Alive
      // Check if battery optimization is disabled
      if (!BatteryOptimizationHelper.isIgnoringBatteryOptimizations(context)) {
        // Show confirmation dialog
        showConfirm(
          Lang.getString(R.string.BatteryOptimizationRequired),
          Lang.getString(R.string.BatteryOptimizationRequiredDescription),
          () -> {
            // User confirmed, request battery optimization exemption
            BatteryOptimizationHelper.requestIgnoreBatteryOptimizations(context);
            
            // Enable the service
            RexConfig.INSTANCE.setUseNativeKeepAlive(true);
            TGXKeepAliveService.start(context);
            adapter.updateValuedSettingById(R.id.btn_rexNativeKeepAlive);
            
            UI.showToast(R.string.NativeKeepAliveEnabled, 1);
          },
          () -> {
            // User cancelled, do nothing
          }
        );
      } else {
        // Battery optimization already disabled, just enable the service
        RexConfig.INSTANCE.setUseNativeKeepAlive(true);
        TGXKeepAliveService.start(context);
        adapter.updateValuedSettingById(R.id.btn_rexNativeKeepAlive);
        UI.showToast(R.string.NativeKeepAliveEnabled, 1);
      }
    } else {
      // Disabling Native Keep-Alive
      RexConfig.INSTANCE.setUseNativeKeepAlive(false);
      TGXKeepAliveService.stop(context);
      adapter.updateValuedSettingById(R.id.btn_rexNativeKeepAlive);
      UI.showToast(R.string.NativeKeepAliveDisabled, 1);
    }
  }
}
