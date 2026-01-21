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
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsReXSpyController extends RecyclerViewController<Void> implements View.OnClickListener {

  public SettingsReXSpyController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.ReXSpyCustomization);
  }

  @Override
  public int getId () {
    return R.id.controller_rexSpyCustomization;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    ArrayList<ListItem> items = new ArrayList<>();
    
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXSpyCustomization));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXSpyCustomizationDesc));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, false);
  }

  private SettingsAdapter adapter;

  @Override
  public void onClick (View v) {
    // Future implementation for spy customization features
  }

  @Override
  protected void onAttachToRecyclerView (CustomRecyclerView recyclerView) {
    super.onAttachToRecyclerView(recyclerView);
    adapter = new SettingsAdapter(this);
  }
}
