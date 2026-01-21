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
import android.widget.Toast;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.unsorted.Settings;
import org.thunderdog.challegram.v.CustomRecyclerView;
import org.thunderdog.challegram.widget.SliderWrapView;

import java.util.ArrayList;

public class SettingsReXSpyController extends RecyclerViewController<Void> implements View.OnClickListener, ViewController.SettingsIntDelegate, SliderWrapView.RealTimeChangeListener {

  private SettingsAdapter adapter;

  public SettingsReXSpyController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return Lang.getString(R.string.ReXSpy);
  }

  @Override
  public int getId () {
    return R.id.controller_rexSpy;
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    ArrayList<ListItem> items = new ArrayList<>();
    
    // Spy essentials header
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXSpyEssentials));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    
    // Save Deleted Messages
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveDeletedMessages, 0, R.string.ReXSaveDeletedMessages));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    
    // Save Edits History
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveEditsHistory, 0, R.string.ReXSaveEditsHistory));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    
    // Save in Bot Dialogs
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveInBotDialogs, 0, R.string.ReXSaveInBotDialogs));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    
    // Save Read Date
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveReadDate, 0, R.string.ReXSaveReadDate));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXSaveReadDateDesc));
    
    // Save Last Seen Date
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveLastSeenDate, 0, R.string.ReXSaveLastSeenDate));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXSaveLastSeenDateDesc));
    
    // Save Attachments
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    ListItem attachItem = new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveAttachments, 0, R.string.ReXSaveAttachments);
    attachItem.setString(Lang.getString(R.string.ReXConfigureChatsAndLimits));
    items.add(attachItem);
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    
    // Attachments Folder link
    ListItem folderItem = new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexAttachmentsFolder, 0, R.string.ReXAttachmentsFolder);
    folderItem.setString(Lang.getString(R.string.ReXSavedAttachments));
    items.add(folderItem);
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    
    // Max folder size header and slider
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.ReXMaxFolderSize));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SLIDER, R.id.slider_rexMaxFolderSize, 0, 0));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.ReXMaxFolderSizeDesc));
    
    // Database action buttons
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexExportDatabase, R.drawable.baseline_upload_24, R.string.ReXExportDatabase));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexImportDatabase, R.drawable.baseline_download_24, R.string.ReXImportDatabase));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexClearDatabase, R.drawable.baseline_delete_24, R.string.ReXClearDatabase));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, false);
  }

  @Override
  public void onClick (View v) {
    int viewId = v.getId();
    if (viewId == R.id.btn_rexSaveDeletedMessages) {
      boolean newValue = !Settings.instance().getReXSaveDeletedMessages();
      Settings.instance().setReXSaveDeletedMessages(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveDeletedMessages);
    } else if (viewId == R.id.btn_rexSaveEditsHistory) {
      boolean newValue = !Settings.instance().getReXSaveEditsHistory();
      Settings.instance().setReXSaveEditsHistory(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveEditsHistory);
    } else if (viewId == R.id.btn_rexSaveInBotDialogs) {
      boolean newValue = !Settings.instance().getReXSaveInBotDialogs();
      Settings.instance().setReXSaveInBotDialogs(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveInBotDialogs);
    } else if (viewId == R.id.btn_rexSaveReadDate) {
      boolean newValue = !Settings.instance().getReXSaveReadDate();
      Settings.instance().setReXSaveReadDate(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveReadDate);
    } else if (viewId == R.id.btn_rexSaveLastSeenDate) {
      boolean newValue = !Settings.instance().getReXSaveLastSeenDate();
      Settings.instance().setReXSaveLastSeenDate(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveLastSeenDate);
    } else if (viewId == R.id.btn_rexSaveAttachments) {
      boolean newValue = !Settings.instance().getReXSaveAttachments();
      Settings.instance().setReXSaveAttachments(newValue);
      adapter.updateValuedSettingById(R.id.btn_rexSaveAttachments);
    } else if (viewId == R.id.btn_rexAttachmentsFolder) {
      UI.showToast("Attachments folder feature coming soon", Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexExportDatabase) {
      UI.showToast("Export database feature coming soon", Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexImportDatabase) {
      UI.showToast("Import database feature coming soon", Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexClearDatabase) {
      UI.showToast("Clear database feature coming soon", Toast.LENGTH_SHORT);
    }
  }

  @Override
  public void onSliderValueChanged (View view, float factor) {
    int sizeIndex = Math.round(factor * 5); // 0-5 for 6 options
    Settings.instance().setReXMaxFolderSize(sizeIndex);
  }

  @Override
  protected void onAttachToRecyclerView (CustomRecyclerView recyclerView) {
    super.onAttachToRecyclerView(recyclerView);
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexSaveDeletedMessages) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveDeletedMessages(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveEditsHistory) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveEditsHistory(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveInBotDialogs) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveInBotDialogs(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveReadDate) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveReadDate(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveLastSeenDate) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveLastSeenDate(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveAttachments) {
          v.getToggler().setRadioEnabled(Settings.instance().getReXSaveAttachments(), isUpdate);
        } else if (itemId == R.id.slider_rexMaxFolderSize) {
          String[] sizeOptions = {"300 MB", "1 GB", "2 GB", "5 GB", "16 GB", "No limit"};
          int currentIndex = Settings.instance().getReXMaxFolderSize();
          float factor = currentIndex / 5.0f;
          v.setSliderInfo(factor, sizeOptions[currentIndex]);
        }
      }
    };
  }

  @Override
  public void onApplySettings (int id, SparseIntArray modes) {
    // No-op for now
  }
}
