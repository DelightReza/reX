package org.thunderdog.challegram.ui;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.data.DeletedMessagesManager;
import org.thunderdog.challegram.data.GhostModeManager;
import org.thunderdog.challegram.navigation.BackHeaderButton;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.theme.ColorId;
import org.thunderdog.challegram.support.ViewSupport;
import org.thunderdog.challegram.tool.UI;
import java.util.ArrayList;
import me.vkryl.android.widget.FrameLayoutFix;

public class GhostSettingsController extends ViewController<Void> implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SettingsAdapter adapter;

    // --- IDs matching Ayugram Layout ---
    
    // Ghost Essentials
    private static final int ID_GHOST_MODE_MASTER = 1001;
    private static final int ID_DONT_READ = 1002;
    // Stories skipped (not in TGX)
    private static final int ID_DONT_ONLINE = 1003;
    private static final int ID_DONT_TYPE = 1004;
    private static final int ID_OFFLINE_AUTO = 1005;
    
    private static final int ID_READ_ON_INTERACT = 1010;
    private static final int ID_SCHEDULE_MESSAGES = 1011;
    private static final int ID_SEND_NO_SOUND = 1012;

    // Spy Essentials
    private static final int ID_SAVE_DELETED = 2001;
    private static final int ID_SAVE_EDITS = 2002;
    private static final int ID_SAVE_BOTS = 2003; 
    private static final int ID_SAVE_READ_DATE = 2004; 
    private static final int ID_SAVE_LAST_SEEN = 2005; 
    private static final int ID_SAVE_ATTACHMENTS = 2006; 
    
    // Database
    private static final int ID_DB_EXPORT = 3001;
    private static final int ID_DB_IMPORT = 3002;
    private static final int ID_DB_CLEAR = 3003;

    public GhostSettingsController (Context context, Tdlib tdlib) {
        super(context, tdlib);
    }

    @Override
    public int getId() { return 1948192; }
    
    @Override
    public CharSequence getName() { return "Ghost Mode"; }

    @Override
    protected int getBackButton () { return BackHeaderButton.TYPE_BACK; }

    @Override
    protected View onCreateView(Context context) {
        recyclerView = new RecyclerView(context);
        recyclerView.setLayoutParams(FrameLayoutFix.newParams(FrameLayoutFix.LayoutParams.MATCH_PARENT, FrameLayoutFix.LayoutParams.MATCH_PARENT));
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        ViewSupport.setThemedBackground(recyclerView, ColorId.background, this);
        
        adapter = new SettingsAdapter(this) {
            @Override
            protected void setValuedSetting (ListItem item, SettingView view, boolean isUpdate) {
                final int id = item.getId();
                GhostModeManager gm = GhostModeManager.getInstance();
                DeletedMessagesManager dm = DeletedMessagesManager.getInstance();
                boolean isGhost = gm.isGhostModeEnabled();

                if (id == ID_GHOST_MODE_MASTER) view.getToggler().setRadioEnabled(isGhost, isUpdate);
                else if (id == ID_DONT_READ) view.getToggler().setRadioEnabled(isGhost && gm.isDontReadEnabled(), isUpdate);
                else if (id == ID_DONT_ONLINE) view.getToggler().setRadioEnabled(isGhost && gm.isDontOnlineEnabled(), isUpdate);
                else if (id == ID_DONT_TYPE) view.getToggler().setRadioEnabled(isGhost && gm.isDontTypeEnabled(), isUpdate);
                else if (id == ID_OFFLINE_AUTO) view.getToggler().setRadioEnabled(isGhost && gm.isOfflineAutoEnabled(), isUpdate);
                
                else if (id == ID_READ_ON_INTERACT) view.getToggler().setRadioEnabled(gm.isReadOnInteractEnabled(), isUpdate);
                else if (id == ID_SCHEDULE_MESSAGES) view.getToggler().setRadioEnabled(gm.isScheduleMessagesEnabled(), isUpdate);
                else if (id == ID_SEND_NO_SOUND) view.getToggler().setRadioEnabled(gm.isSendNoSoundEnabled(), isUpdate);
                
                else if (id == ID_SAVE_DELETED) view.getToggler().setRadioEnabled(dm.isGhostEnabled(), isUpdate);
                else if (id == ID_SAVE_EDITS) view.getToggler().setRadioEnabled(dm.isEditHistoryEnabled(), isUpdate);
                else if (id == ID_SAVE_BOTS) view.getToggler().setRadioEnabled(gm.isSaveBotsEnabled(), isUpdate);
                
                if (id == ID_DONT_READ || id == ID_DONT_ONLINE || id == ID_DONT_TYPE || id == ID_OFFLINE_AUTO) {
                    view.setEnabled(isGhost);
                    view.setAlpha(isGhost ? 1.0f : 0.5f);
                }
            }
        };
        
        ArrayList<ListItem> items = new ArrayList<>();
        
        // --- 1. GHOST ESSENTIALS ---
        items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
        items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Ghost essentials").setColor(0xFF29B6F6));
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_GHOST_MODE_MASTER, R.drawable.baseline_visibility_24, "Ghost Mode"));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_DONT_READ, R.drawable.baseline_done_all_24, "Don't Read Messages"));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_DONT_ONLINE, R.drawable.baseline_eye_off_24, "Don't Send Online"));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_DONT_TYPE, R.drawable.baseline_keyboard_24, "Don't Send Typing"));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_OFFLINE_AUTO, R.drawable.baseline_timer_24, "Go Offline Automatically"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Long-press any option to prevent it from changing when toggling Ghost Mode."));

        // Interact
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_READ_ON_INTERACT, 0, "Read on Interact"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Automatically marks a message as read when you send a new one or tap a reaction."));

        // Schedule
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SCHEDULE_MESSAGES, 0, "Schedule Messages"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Automatically schedules outgoing messages to send after ~12 seconds. Using this, you won't appear online."));

        // Sound
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SEND_NO_SOUND, 0, "Send without Sound"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Sends outgoing messages without sound by default."));

        // --- 2. SPY ESSENTIALS ---
        items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "Spy essentials").setColor(0xFF29B6F6));
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_DELETED, 0, "Save Deleted Messages"));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_EDITS, 0, "Save Edits History"));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_BOTS, 0, "Save in Bot Dialogs"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        
        // Read Date
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_READ_DATE, 0, "Save Read Date"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Locally saves data about reading messages."));

        // Last Seen
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_LAST_SEEN, 0, "Save Last Seen Date"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Saves the last online date for users with hidden status."));

        // Attachments
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_RADIO_SETTING, ID_SAVE_ATTACHMENTS, 0, "Save Attachments"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

        // Database
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_DB_EXPORT, R.drawable.baseline_cloud_upload_24, "Export Database"));
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_DB_IMPORT, R.drawable.baseline_cloud_download_24, "Import Database"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_DB_CLEAR, R.drawable.baseline_delete_forever_24, "Clear"));
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));

        adapter.setItems(items, false);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    private void refreshGhostSubs() {
        adapter.updateValuedSettingById(ID_DONT_READ);
        adapter.updateValuedSettingById(ID_DONT_ONLINE);
        adapter.updateValuedSettingById(ID_DONT_TYPE);
        adapter.updateValuedSettingById(ID_OFFLINE_AUTO);
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        GhostModeManager gm = GhostModeManager.getInstance();
        DeletedMessagesManager dm = DeletedMessagesManager.getInstance();
        boolean isGhost = gm.isGhostModeEnabled();

        if (id == ID_GHOST_MODE_MASTER) {
            boolean newState = !isGhost;
            gm.setGhostModeEnabled(newState);
            if (newState) {
                gm.setDontReadEnabled(true);
                gm.setDontOnlineEnabled(true);
                gm.setDontTypeEnabled(true);
                gm.setOfflineAutoEnabled(true);
                UI.showToast("Ghost Mode Enabled", Toast.LENGTH_SHORT);
            }
            adapter.updateValuedSettingById(ID_GHOST_MODE_MASTER);
            refreshGhostSubs();
        }
        else if (id == ID_DONT_READ) { if (isGhost) { gm.setDontReadEnabled(!gm.isDontReadEnabled()); adapter.updateValuedSettingById(id); } }
        else if (id == ID_DONT_ONLINE) { if (isGhost) { gm.setDontOnlineEnabled(!gm.isDontOnlineEnabled()); adapter.updateValuedSettingById(id); } }
        else if (id == ID_DONT_TYPE) { if (isGhost) { gm.setDontTypeEnabled(!gm.isDontTypeEnabled()); adapter.updateValuedSettingById(id); } }
        else if (id == ID_OFFLINE_AUTO) { if (isGhost) { gm.setOfflineAutoEnabled(!gm.isOfflineAutoEnabled()); adapter.updateValuedSettingById(id); } }
        
        else if (id == ID_READ_ON_INTERACT) { gm.setReadOnInteractEnabled(!gm.isReadOnInteractEnabled()); adapter.updateValuedSettingById(id); }
        else if (id == ID_SCHEDULE_MESSAGES) { gm.setScheduleMessagesEnabled(!gm.isScheduleMessagesEnabled()); adapter.updateValuedSettingById(id); }
        else if (id == ID_SEND_NO_SOUND) { gm.setSendNoSoundEnabled(!gm.isSendNoSoundEnabled()); adapter.updateValuedSettingById(id); }

        else if (id == ID_SAVE_DELETED) { dm.setGhostEnabled(!dm.isGhostEnabled()); adapter.updateValuedSettingById(id); }
        else if (id == ID_SAVE_EDITS) { dm.setEditHistoryEnabled(!dm.isEditHistoryEnabled()); adapter.updateValuedSettingById(id); }
        else if (id == ID_SAVE_BOTS) { gm.setSaveBotsEnabled(!gm.isSaveBotsEnabled()); adapter.updateValuedSettingById(id); }
        
        else if (id == ID_SAVE_READ_DATE || id == ID_SAVE_LAST_SEEN || id == ID_SAVE_ATTACHMENTS) { 
            UI.showToast("Feature enabled (Visual Only)", Toast.LENGTH_SHORT); 
        }

        else if (id == ID_DB_CLEAR) {
            dm.clearAllGhosts();
            UI.showToast("Database Cleared", Toast.LENGTH_SHORT);
        }
        else if (id == ID_DB_EXPORT) {
            dm.exportDatabase();
        }
        else if (id == ID_DB_IMPORT) {
            UI.showToast("Import not yet implemented", Toast.LENGTH_SHORT);
        }
    }
}
