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

public class RexCustomizationController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexCustomizationController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return "Customisation";
  }

  @Override
  public int getId () {
    return R.id.controller_rexCustomization;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        RexConfig config = RexConfig.getInstance();
        if (itemId == R.id.btn_rexDisableColorfulReplies) {
          v.getToggler().setRadioEnabled(config.isDisableColorfulReplies(), isUpdate);
        } else if (itemId == R.id.btn_rexTranslucentDeleted) {
          v.getToggler().setRadioEnabled(config.isTranslucentDeleted(), isUpdate);
        }
      }
    };

    final List<ListItem> items = new ArrayList<>();

    // Customization
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Customization"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexDisableColorfulReplies, 0, "Disable Colorful Replies"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexTranslucentDeleted, 0, "Translucent Deleted Messages"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexDeletedMark, R.drawable.baseline_delete_24, "Deleted Mark"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    // Drawer Options
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexDrawerOptions, R.drawable.baseline_drag_handle_24, "Drawer Options"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Change the order of elements in the navigation drawer."));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    final int viewId = v.getId();
    RexConfig config = RexConfig.getInstance();

    if (viewId == R.id.btn_rexDisableColorfulReplies) {
      config.setDisableColorfulReplies(!config.isDisableColorfulReplies());
      adapter.updateValuedSettingById(R.id.btn_rexDisableColorfulReplies);
    } else if (viewId == R.id.btn_rexTranslucentDeleted) {
      config.setTranslucentDeleted(!config.isTranslucentDeleted());
      adapter.updateValuedSettingById(R.id.btn_rexTranslucentDeleted);
    } else if (viewId == R.id.btn_rexDeletedMark) {
      UI.showToast("Deleted mark icon selector — coming soon", android.widget.Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexDrawerOptions) {
      UI.showToast("Drawer options — coming soon", android.widget.Toast.LENGTH_SHORT);
    }
  }
}
