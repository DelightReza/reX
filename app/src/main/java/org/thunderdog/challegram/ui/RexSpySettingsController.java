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
import org.thunderdog.challegram.navigation.SettingsWrapBuilder;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RexSpySettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexSpySettingsController (Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName () {
    return "Spy";
  }

  @Override
  public int getId () {
    return R.id.controller_rexSpySettings;
  }

  private static String getFolderSizeLabel (int sizeMb) {
    if (sizeMb <= 0) return "No limit";
    if (sizeMb < 1024) return sizeMb + " MB";
    return (sizeMb / 1024) + " GB";
  }

  @Override
  protected void onCreateView (Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      public void setValuedSetting (ListItem item, SettingView v, boolean isUpdate) {
        final int itemId = item.getId();
        RexConfig config = RexConfig.getInstance();
        if (itemId == R.id.btn_rexSpySaveDeleted) {
          v.getToggler().setRadioEnabled(config.isSaveDeletedEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpySaveEdits) {
          v.getToggler().setRadioEnabled(config.isSaveEditsEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpySaveBotDialogs) {
          v.getToggler().setRadioEnabled(config.isSaveBotDialogsEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpySaveReadDate) {
          v.getToggler().setRadioEnabled(config.isSaveReadDateEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpySaveLastSeen) {
          v.getToggler().setRadioEnabled(config.isSaveLastSeenEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpySaveAttachments) {
          v.getToggler().setRadioEnabled(config.isSaveAttachmentsEnabled(), isUpdate);
        } else if (itemId == R.id.btn_rexSpyAttachmentsFolder) {
          v.setData(config.getAttachmentsFolder());
        } else if (itemId == R.id.btn_rexSpyMaxFolderSize) {
          v.setData(getFolderSizeLabel(config.getMaxFolderSizeMb()));
        }
      }
    };

    final List<ListItem> items = new ArrayList<>();

    // Spy essentials
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Spy essentials"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveDeleted, 0, "Save Deleted Messages"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveEdits, 0, "Save Edits History"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveBotDialogs, 0, "Save in Bot Dialogs"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveReadDate, 0, "Save Read Date"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Locally saves data about reading messages. This will be shown if Telegram does not provide a read date."));

    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveLastSeen, 0, "Save Last Seen Date"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Saves the last online date for users with hidden online status based on their actions. You'll be able to see very approximately when they were last online."));

    // Attachments
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSpySaveAttachments, 0, "Save Attachments"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexSpyAttachmentsFolder, 0, "Attachments Folder"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexSpyMaxFolderSize, 0, "Max folder size"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "If folder size exceeds this limit, the oldest attachments will be removed from the device."));

    // Database actions
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSpyExportDb, R.drawable.baseline_import_export_24, "Export Database"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSpyImportDb, R.drawable.baseline_file_download_24, "Import Database"));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexSpyClearDb, R.drawable.baseline_delete_24, "Clear"));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick (View v) {
    final int viewId = v.getId();
    RexConfig config = RexConfig.getInstance();

    if (viewId == R.id.btn_rexSpySaveDeleted) {
      config.setSaveDeletedEnabled(!config.isSaveDeletedEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveDeleted);
    } else if (viewId == R.id.btn_rexSpySaveEdits) {
      config.setSaveEditsEnabled(!config.isSaveEditsEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveEdits);
    } else if (viewId == R.id.btn_rexSpySaveBotDialogs) {
      config.setSaveBotDialogsEnabled(!config.isSaveBotDialogsEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveBotDialogs);
    } else if (viewId == R.id.btn_rexSpySaveReadDate) {
      config.setSaveReadDateEnabled(!config.isSaveReadDateEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveReadDate);
    } else if (viewId == R.id.btn_rexSpySaveLastSeen) {
      config.setSaveLastSeenEnabled(!config.isSaveLastSeenEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveLastSeen);
    } else if (viewId == R.id.btn_rexSpySaveAttachments) {
      config.setSaveAttachmentsEnabled(!config.isSaveAttachmentsEnabled());
      adapter.updateValuedSettingById(R.id.btn_rexSpySaveAttachments);
    } else if (viewId == R.id.btn_rexSpyMaxFolderSize) {
      showMaxFolderSizeSelector();
    } else if (viewId == R.id.btn_rexSpyAttachmentsFolder) {
      UI.showToast("Attachments folder: " + config.getAttachmentsFolder(), android.widget.Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexSpyExportDb) {
      UI.showToast("Export database — coming soon", android.widget.Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexSpyImportDb) {
      UI.showToast("Import database — coming soon", android.widget.Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexSpyClearDb) {
      UI.showToast("Clear database — coming soon", android.widget.Toast.LENGTH_SHORT);
    }
  }

  private void showMaxFolderSizeSelector () {
    int current = RexConfig.getInstance().getMaxFolderSizeMb();
    showSettings(new SettingsWrapBuilder(R.id.btn_rexSpyMaxFolderSize)
      .setRawItems(new ListItem[] {
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSize300, 0, "300 MB", R.id.btn_rexSpyMaxFolderSize, current == 300),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSize1g, 0, "1 GB", R.id.btn_rexSpyMaxFolderSize, current == 1024),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSize2g, 0, "2 GB", R.id.btn_rexSpyMaxFolderSize, current == 2048),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSize5g, 0, "5 GB", R.id.btn_rexSpyMaxFolderSize, current == 5120),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSize16g, 0, "16 GB", R.id.btn_rexSpyMaxFolderSize, current == 16384),
        new ListItem(ListItem.TYPE_RADIO_OPTION, R.id.btn_rexFolderSizeNone, 0, "No limit", R.id.btn_rexSpyMaxFolderSize, current == 0),
      })
      .setIntDelegate((id, result) -> {
        int selected = result.get(R.id.btn_rexSpyMaxFolderSize);
        int size = 0;
        if (selected == R.id.btn_rexFolderSize300) size = 300;
        else if (selected == R.id.btn_rexFolderSize1g) size = 1024;
        else if (selected == R.id.btn_rexFolderSize2g) size = 2048;
        else if (selected == R.id.btn_rexFolderSize5g) size = 5120;
        else if (selected == R.id.btn_rexFolderSize16g) size = 16384;
        RexConfig.getInstance().setMaxFolderSizeMb(size);
        adapter.updateValuedSettingById(R.id.btn_rexSpyMaxFolderSize);
      })
      .setAllowResize(false));
  }
}
