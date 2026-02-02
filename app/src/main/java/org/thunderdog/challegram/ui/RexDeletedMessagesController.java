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
import org.thunderdog.challegram.rex.db.RexDatabase;
import org.thunderdog.challegram.rex.db.SavedMessage;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RexDeletedMessagesController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;
  private List<SavedMessage> deletedMessages;

  public RexDeletedMessagesController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexDeletedMessages);
  }

  @Override
  public int getId() {
    return R.id.controller_rexDeletedMessages;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        // No valued settings in this controller
      }
    };

    // Load deleted messages from database
    loadDeletedMessages();

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexDeletedMessages));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));

    if (deletedMessages.isEmpty()) {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexDeletedMessagesDescription));
    } else {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
      
      for (int i = 0; i < deletedMessages.size(); i++) {
        SavedMessage msg = deletedMessages.get(i);
        String timeStr = sdf.format(new Date(msg.getTimestamp() * 1000L));
        String chatInfo = "Chat ID: " + msg.getChatId();
        String messageText = msg.getText() != null ? msg.getText() : "(No text content)";
        
        // Message preview item
        items.add(new ListItem(ListItem.TYPE_SETTING, i, 0, messageText)
          .setData(timeStr + " • " + chatInfo));
        
        if (i < deletedMessages.size() - 1) {
          items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
        }
      }
    }

    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    
    if (!deletedMessages.isEmpty()) {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, 
        deletedMessages.size() + " deleted message(s) saved"));
    }

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  private void loadDeletedMessages() {
    try {
      deletedMessages = RexDatabase.get(UI.getContext()).rexDao().getAllDeletedMessages();
    } catch (Exception e) {
      deletedMessages = new ArrayList<>();
      UI.showToast("Error loading deleted messages: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT);
    }
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    // Handle clicks on individual messages if needed
    // Can navigate to edit history viewer or show full message details
  }
}
