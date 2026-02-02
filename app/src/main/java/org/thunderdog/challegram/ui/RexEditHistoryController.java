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
import org.thunderdog.challegram.rex.db.EditHistory;
import org.thunderdog.challegram.rex.db.RexDatabase;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RexEditHistoryController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;
  private List<EditHistory> allEdits;
  private final long messageId;

  public RexEditHistoryController(Context context, Tdlib tdlib, long messageId) {
    super(context, tdlib);
    this.messageId = messageId;
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexEditHistory);
  }

  @Override
  public int getId() {
    return R.id.controller_rexEditHistory;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        // No valued settings in this controller
      }
    };

    // Load edit history from database
    loadEditHistory();

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexEditHistory));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));

    if (allEdits.isEmpty()) {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexEditHistoryDescription));
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault());
      
      for (int i = 0; i < allEdits.size(); i++) {
        EditHistory edit = allEdits.get(i);
        String timeStr = sdf.format(new Date(edit.getTimestamp() * 1000L));
        String editText = edit.getOldText();
        
        // Edit version header
        items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Version " + (allEdits.size() - i)));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        
        // Timestamp
        items.add(new ListItem(ListItem.TYPE_INFO, 0, 0, "Edited: " + timeStr));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        
        // Previous text content
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, editText));
        
        if (i < allEdits.size() - 1) {
          items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
        }
      }
    }

    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    
    if (!allEdits.isEmpty()) {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, 
        allEdits.size() + " edit(s) recorded for message ID: " + messageId));
    }

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  private void loadEditHistory() {
    try {
      allEdits = RexDatabase.get(UI.getContext()).rexDao().getEdits(messageId);
    } catch (Exception e) {
      allEdits = new ArrayList<>();
      UI.showToast("Error loading edit history: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT);
    }
  }

  @Override
  public void onClick(View v) {
    // Handle clicks if needed
  }
}
