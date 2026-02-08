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

import org.drinkless.tdlib.TdApi;
import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.security.RexSecurityManager;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RexHiddenChatsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexHiddenChatsController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return "Hidden Chats";
  }

  @Override
  public int getId () {
    return R.id.controller_rexHiddenChats;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
        // No valued settings in this controller
      }
    };

    buildItems();
    recyclerView.setAdapter(adapter);
  }

  private void buildItems () {
    List<ListItem> items = new ArrayList<>();
    RexSecurityManager sec = RexSecurityManager.getInstance();
    Set<String> hiddenIds = sec.getHiddenChatIds();

    if (hiddenIds.isEmpty()) {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "No hidden chats. Long-press any chat in the chat list and tap \"Hide Chat\" to hide it."));
    } else {
      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Tap on a chat to unhide it."));
      items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));

      boolean first = true;
      for (String idStr : hiddenIds) {
        try {
          long chatId = Long.parseLong(idStr);
          String chatTitle = getChatTitle(chatId);

          if (!first) {
            items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
          }
          first = false;

          items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexHideChat, R.drawable.baseline_visibility_24, chatTitle).setLongId(chatId));
        } catch (NumberFormatException ignored) { }
      }

      items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

      items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, hiddenIds.size() + " hidden chat(s). Hidden chats are invisible until you long-press the search icon and authenticate."));
    }

    adapter.setItems(items, false);
  }

  private String getChatTitle (long chatId) {
    TdApi.Chat chat = tdlib().chat(chatId);
    if (chat != null && chat.title != null && !chat.title.isEmpty()) {
      return chat.title;
    }
    // For private chats, try to get user name
    if (chat != null && chat.type instanceof TdApi.ChatTypePrivate) {
      long userId = ((TdApi.ChatTypePrivate) chat.type).userId;
      String name = tdlib().cache().userFirstName(userId);
      if (name != null && !name.isEmpty()) {
        return name;
      }
    }
    return "Chat " + chatId;
  }

  @Override
  public void onClick (View v) {
    if (v.getId() == R.id.btn_rexHideChat) {
      ListItem item = (ListItem) v.getTag();
      if (item != null) {
        long chatId = item.getLongId();
        String chatTitle = getChatTitle(chatId);
        showOptions(
          "Unhide \"" + chatTitle + "\"?",
          new int[] {R.id.btn_rexUnhideChat, R.id.btn_cancel},
          new String[] {"Unhide", "Cancel"},
          new int[] {ViewController.OptionColor.RED, ViewController.OptionColor.NORMAL},
          new int[] {R.drawable.baseline_visibility_24, R.drawable.baseline_cancel_24},
          (itemView, id) -> {
            if (id == R.id.btn_rexUnhideChat) {
              RexSecurityManager.getInstance().removeHiddenChat(chatId);
              UI.showToast("Chat unhidden", android.widget.Toast.LENGTH_SHORT);
              buildItems();
            }
            return true;
          }
        );
      }
    }
  }
}
