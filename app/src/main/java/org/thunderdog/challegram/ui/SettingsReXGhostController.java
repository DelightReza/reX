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
import android.util.SparseIntArray;
import android.view.View;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.unsorted.Settings;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class SettingsReXGhostController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate {

  public SettingsReXGhostController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.ReXGhostMode);
  }

  @Override
  public int getId () {
    return R.id.controller_rexGhostMode;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    ArrayList<ListItem> items = new ArrayList<>();
    
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXGhostMode));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideReadStatus, 0, R.string.ReXHideReadStatus));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideTyping, 0, R.string.ReXHideTyping));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideOnlineStatus, 0, R.string.ReXHideOnlineStatus));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexHideRecording, 0, R.string.ReXHideRecording));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXGhostModeDesc));

    adapter.setItems(items, false);
  }

  private SettingsAdapter adapter;

  @Override
  public void onClick (View v) {
    int viewId = v.getId();
    if (viewId == R.id.btn_rexHideReadStatus) {
      boolean newValue = !Settings.instance().getReXHideReadStatus();
      Settings.instance().setReXHideReadStatus(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexHideReadStatus);
    } else if (viewId == R.id.btn_rexHideTyping) {
      boolean newValue = !Settings.instance().getReXHideTyping();
      Settings.instance().setReXHideTyping(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexHideTyping);
    } else if (viewId == R.id.btn_rexHideOnlineStatus) {
      boolean newValue = !Settings.instance().getReXHideOnlineStatus();
      Settings.instance().setReXHideOnlineStatus(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexHideOnlineStatus);
    } else if (viewId == R.id.btn_rexHideRecording) {
      boolean newValue = !Settings.instance().getReXHideRecording();
      Settings.instance().setReXHideRecording(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexHideRecording);
    }
  }

  @Override
  protected void onAttachToRecyclerView (CustomRecyclerView recyclerView) {
    super.onAttachToRecyclerView(recyclerView);
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexHideReadStatus) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXHideReadStatus(), isUpdate);
        } else if (itemId == R.id.btn_rexHideTyping) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXHideTyping(), isUpdate);
        } else if (itemId == R.id.btn_rexHideOnlineStatus) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXHideOnlineStatus(), isUpdate);
        } else if (itemId == R.id.btn_rexHideRecording) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXHideRecording(), isUpdate);
        }
      }
    };
  }

  @Override
  public void onApplySettings (int id, SparseIntArray modes) {
    // No-op for now
  }
}
