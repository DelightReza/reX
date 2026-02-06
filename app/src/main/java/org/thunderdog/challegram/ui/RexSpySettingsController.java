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
import android.widget.Toast;

import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.core.Background;
import org.thunderdog.challegram.core.Lang;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.rex.RexConfig;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.tool.UI;
import org.thunderdog.challegram.v.CustomRecyclerView;

import java.util.ArrayList;

public class RexSpySettingsController extends RecyclerViewController<Void> implements View.OnClickListener {

  private SettingsAdapter adapter;

  public RexSpySettingsController(Context context, Tdlib tdlib) {
    super(context, tdlib);
  }

  @Override
  public CharSequence getName() {
    return Lang.getString(R.string.RexSpyAntiDelete);
  }

  @Override
  public int getId() {
    return R.id.controller_rexSpySettings;
  }

  @Override
  protected void onCreateView(Context context, CustomRecyclerView recyclerView) {
    adapter = new SettingsAdapter(this) {
      @Override
      protected void setValuedSetting(ListItem item, SettingView view, boolean isUpdate) {
        final int itemId = item.getId();
        if (itemId == R.id.btn_rexSaveDeleted) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveDeletedMessages(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveEdits) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveEditsHistory(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveBotDialogs) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveInBotDialogs(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveReadDate) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveReadDate(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveLastSeen) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveLastSeenDate(), isUpdate);
        } else if (itemId == R.id.btn_rexSaveAttachments) {
          view.getToggler().setRadioEnabled(RexConfig.INSTANCE.getSaveAttachments(), isUpdate);
        } else if (itemId == R.id.btn_rexMaxFolderSize) {
          int size = RexConfig.INSTANCE.getMaxFolderSize();
          String sizeText = size == 0 ? Lang.getString(R.string.RexNoLimit) : 
                           size < 1024 ? size + " " + Lang.getString(R.string.RexMB) : 
                           (size / 1024) + " " + Lang.getString(R.string.RexGB);
          view.setData(sizeText);
        }
      }
    };

    ArrayList<ListItem> items = new ArrayList<>();
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexSpySettings));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveDeleted, 0, R.string.RexSaveDeletedMessages));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveEdits, 0, R.string.RexSaveEditsHistory));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveBotDialogs, 0, R.string.RexSaveInBotDialogs));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveReadDate, 0, R.string.RexSaveReadDate));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveLastSeen, 0, R.string.RexSaveLastSeenDate));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexSpyFeaturesDescription));
    
    // Attachments section
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexAttachments));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, R.id.btn_rexSaveAttachments, 0, R.string.RexSaveAttachments));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexAttachmentsFolder, 0, R.string.RexAttachmentsFolder));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_VALUED_SETTING_COMPACT, R.id.btn_rexMaxFolderSize, 0, R.string.RexMaxFolderSize));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexSaveAttachmentsDescription));
    
    // View saved messages button
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexSavedData));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexViewSaved, R.drawable.baseline_archive_24, R.string.RexViewDeletedMessages));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
    items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, R.string.RexViewDeletedDescription));

    // Database actions
    items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
    items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, R.string.RexDatabase));
    items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexExportDb, 0, R.string.RexExportDatabase));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexImportDb, 0, R.string.RexImportDatabase));
    items.add(new ListItem(ListItem.TYPE_SEPARATOR_FULL));
    items.add(new ListItem(ListItem.TYPE_SETTING, R.id.btn_rexClearDb, 0, R.string.RexClearDatabase));
    items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

    adapter.setItems(items, false);
    recyclerView.setAdapter(adapter);
  }

  @Override
  public void onClick(View v) {
    final int viewId = v.getId();
    if (viewId == R.id.btn_rexSaveDeleted) {
      RexConfig.INSTANCE.setSaveDeletedMessages(!RexConfig.INSTANCE.getSaveDeletedMessages());
      adapter.updateValuedSettingById(R.id.btn_rexSaveDeleted);
    } else if (viewId == R.id.btn_rexSaveEdits) {
      RexConfig.INSTANCE.setSaveEditsHistory(!RexConfig.INSTANCE.getSaveEditsHistory());
      adapter.updateValuedSettingById(R.id.btn_rexSaveEdits);
    } else if (viewId == R.id.btn_rexSaveBotDialogs) {
      RexConfig.INSTANCE.setSaveInBotDialogs(!RexConfig.INSTANCE.getSaveInBotDialogs());
      adapter.updateValuedSettingById(R.id.btn_rexSaveBotDialogs);
    } else if (viewId == R.id.btn_rexSaveReadDate) {
      RexConfig.INSTANCE.setSaveReadDate(!RexConfig.INSTANCE.getSaveReadDate());
      adapter.updateValuedSettingById(R.id.btn_rexSaveReadDate);
    } else if (viewId == R.id.btn_rexSaveLastSeen) {
      RexConfig.INSTANCE.setSaveLastSeenDate(!RexConfig.INSTANCE.getSaveLastSeenDate());
      adapter.updateValuedSettingById(R.id.btn_rexSaveLastSeen);
    } else if (viewId == R.id.btn_rexSaveAttachments) {
      RexConfig.INSTANCE.setSaveAttachments(!RexConfig.INSTANCE.getSaveAttachments());
      adapter.updateValuedSettingById(R.id.btn_rexSaveAttachments);
    } else if (viewId == R.id.btn_rexAttachmentsFolder) {
      showAttachmentsFolderOptions();
    } else if (viewId == R.id.btn_rexMaxFolderSize) {
      showMaxFolderSizeOptions();
    } else if (viewId == R.id.btn_rexViewSaved) {
      navigateTo(new RexDeletedMessagesController(context, tdlib));
    } else if (viewId == R.id.btn_rexExportDb) {
      UI.showToast(R.string.RexTodoNotImplemented, Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexImportDb) {
      UI.showToast(R.string.RexTodoNotImplemented, Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexClearDb) {
      showConfirmClearDatabase();
    }
  }
  
  private void showConfirmClearDatabase() {
    showOptions("Clear Database", new int[]{R.id.btn_clearAllDeleted, R.id.btn_cancel}, new String[]{
      Lang.getString(R.string.RexClearAllDeletedMessages), Lang.getString(R.string.Cancel)
    }, new int[]{OptionColor.RED, OptionColor.NORMAL}, new int[]{
      R.drawable.baseline_delete_forever_24, R.drawable.baseline_cancel_24
    }, (itemView, id) -> {
      if (id == R.id.btn_clearAllDeleted) {
        clearDatabase();
      }
      return true;
    });
  }
  
  private void clearDatabase() {
    Background.instance().post(() -> {
      try {
        org.thunderdog.challegram.rex.db.RexDatabase db = org.thunderdog.challegram.rex.db.RexDatabase.get(context);
        db.rexDao().clearAllDeletedMessages();
        tdlib.ui().post(() -> {
          UI.showToast("All deleted messages cleared", Toast.LENGTH_SHORT);
        });
      } catch (Exception e) {
        tdlib.ui().post(() -> {
          UI.showToast("Failed to clear database", Toast.LENGTH_SHORT);
        });
      }
    });
  }
  
  private void showAttachmentsFolderOptions() {
    showOptions(Lang.getString(R.string.RexAttachmentsFolder), new int[]{
      R.id.btn_rexFolderDownload, 
      R.id.btn_rexFolderCustom, 
      R.id.btn_cancel
    }, new String[]{
      "Download/reX",
      Lang.getString(R.string.RexCustomPath),
      Lang.getString(R.string.Cancel)
    }, new int[]{
      OptionColor.NORMAL, 
      OptionColor.NORMAL, 
      OptionColor.NORMAL
    }, new int[]{
      R.drawable.baseline_folder_24,
      R.drawable.baseline_create_new_folder_24,
      R.drawable.baseline_cancel_24
    }, (itemView, id) -> {
      if (id == R.id.btn_rexFolderDownload) {
        // Use default Download/reX path
        String defaultPath = android.os.Environment.getExternalStoragePublicDirectory(
          android.os.Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/reX";
        // Create .nomedia file
        java.io.File folder = new java.io.File(defaultPath);
        if (!folder.exists()) {
          folder.mkdirs();
        }
        java.io.File nomedia = new java.io.File(folder, ".nomedia");
        try {
          nomedia.createNewFile();
        } catch (java.io.IOException e) {
          // Ignore
        }
        RexConfig.INSTANCE.setAttachmentsFolder(defaultPath);
        UI.showToast("Folder set to: Download/reX", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexFolderCustom) {
        // TODO: Show file picker dialog for custom path
        // For now, just show a toast
        UI.showToast(R.string.RexTodoNotImplemented, Toast.LENGTH_SHORT);
      }
      return true;
    });
  }
  
  private void showMaxFolderSizeOptions() {
    showOptions(Lang.getString(R.string.RexMaxFolderSize), new int[]{
      R.id.btn_rexSize300MB,
      R.id.btn_rexSize1GB,
      R.id.btn_rexSize2GB,
      R.id.btn_rexSize5GB,
      R.id.btn_rexSize16GB,
      R.id.btn_rexSizeNoLimit,
      R.id.btn_cancel
    }, new String[]{
      "300 " + Lang.getString(R.string.RexMB),
      "1 " + Lang.getString(R.string.RexGB),
      "2 " + Lang.getString(R.string.RexGB),
      "5 " + Lang.getString(R.string.RexGB),
      "16 " + Lang.getString(R.string.RexGB),
      Lang.getString(R.string.RexNoLimit),
      Lang.getString(R.string.Cancel)
    }, new int[]{
      OptionColor.NORMAL,
      OptionColor.NORMAL,
      OptionColor.NORMAL,
      OptionColor.NORMAL,
      OptionColor.NORMAL,
      OptionColor.NORMAL,
      OptionColor.NORMAL
    }, new int[]{
      R.drawable.baseline_data_usage_24,
      R.drawable.baseline_data_usage_24,
      R.drawable.baseline_data_usage_24,
      R.drawable.baseline_data_usage_24,
      R.drawable.baseline_data_usage_24,
      R.drawable.baseline_all_inclusive_24,
      R.drawable.baseline_cancel_24
    }, (itemView, id) -> {
      if (id == R.id.btn_rexSize300MB) {
        RexConfig.INSTANCE.setMaxFolderSize(300);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("Max size set to 300 MB", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexSize1GB) {
        RexConfig.INSTANCE.setMaxFolderSize(1024);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("Max size set to 1 GB", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexSize2GB) {
        RexConfig.INSTANCE.setMaxFolderSize(2048);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("Max size set to 2 GB", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexSize5GB) {
        RexConfig.INSTANCE.setMaxFolderSize(5120);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("Max size set to 5 GB", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexSize16GB) {
        RexConfig.INSTANCE.setMaxFolderSize(16384);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("Max size set to 16 GB", Toast.LENGTH_SHORT);
      } else if (id == R.id.btn_rexSizeNoLimit) {
        RexConfig.INSTANCE.setMaxFolderSize(0);
        adapter.updateValuedSettingById(R.id.btn_rexMaxFolderSize);
        UI.showToast("No size limit", Toast.LENGTH_SHORT);
      }
      return true;
    });
  }
}
