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
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.theme.ColorId;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsReXController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public SettingsReXController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.ReXSettings);
  }

  @Override
  public int getId () {
    return R.id.controller_rexSettings;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    ArrayList<ListItem> items = new ArrayList<>();
    
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXSettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexGhostMode, R.drawable.baseline_visibility_off_24, R.string.ReXGhostMode).setTextColorId(ColorId.textNeutral));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSpyCustomization, R.drawable.baseline_extension_24, R.string.ReXSpyCustomization).setTextColorId(ColorId.textNeutral));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXSettingsDesc));

    adapter.setItems(items, false);
  }

  @Override
  public void onClick (View v) {
    int viewId = v.getId();
    if (viewId == R.id.btn_rexGhostMode) {
      navigateTo(new SettingsReXGhostController(context, tdlib));
    } else if (viewId == R.id.btn_rexSpyCustomization) {
      navigateTo(new SettingsReXSpyController(context, tdlib));
    }
  }

  @Override
  protected void onAttachToRecyclerView (CustomRecyclerView recyclerView) {
    super.onAttachToRecyclerView(recyclerView);
    adapter = new SettingsAdapter(this);
  }
}
