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
import org.thunderdog.challegram.core.Lang;
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
      UI.showToast(R.string.RexTodoNotImplemented, Toast.LENGTH_SHORT);
    } else if (viewId == R.id.btn_rexMaxFolderSize) {
      UI.showToast(R.string.RexTodoNotImplemented, Toast.LENGTH_SHORT);
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
    }, new int[]{ViewController.OPTION_COLOR_RED, ViewController.OPTION_COLOR_NORMAL}, new int[]{
      R.drawable.baseline_delete_forever_24, R.drawable.baseline_cancel_24
    }, (itemView, id) -> {
      if (id == R.id.btn_clearAllDeleted) {
        clearDatabase();
      }
      return true;
    });
  }
  
  private void clearDatabase() {
    Background.execute(() -> {
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
}
