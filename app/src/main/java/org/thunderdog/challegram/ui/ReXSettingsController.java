package org.thunderdog.challegram.ui;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.thunderdog.challegram.R;
import org.thunderdog.challegram.component.base.SettingView;
import org.thunderdog.challegram.navigation.BackHeaderButton;
import org.thunderdog.challegram.navigation.ViewController;
import org.thunderdog.challegram.telegram.Tdlib;
import org.thunderdog.challegram.theme.ColorId;
import org.thunderdog.challegram.support.ViewSupport;
import org.thunderdog.challegram.tool.UI;
import java.util.ArrayList;
import me.vkryl.android.widget.FrameLayoutFix;

public class ReXSettingsController extends ViewController<Void> implements View.OnClickListener {

    private RecyclerView recyclerView;
    private SettingsAdapter adapter;
    
    private static final int ID_GHOST_MODE = 3001;
    private static final int ID_SPY = 3002;
    private static final int ID_CUSTOMISATION = 3003;

    public ReXSettingsController (Context context, Tdlib tdlib) {
        super(context, tdlib);
    }

    @Override
    public int getId() { return 1948194; }
    
    @Override
    public CharSequence getName() { return "reX"; }

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
                // No toggles in this menu, just navigation items
            }
        };
        
        ArrayList<ListItem> items = new ArrayList<>();
        
        items.add(new ListItem(ListItem.TYPE_EMPTY_OFFSET_SMALL));
        items.add(new ListItem(ListItem.TYPE_HEADER, 0, 0, "reX Settings").setColor(0xFF29B6F6));
        items.add(new ListItem(ListItem.TYPE_SHADOW_TOP));
        
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_GHOST_MODE, R.drawable.baseline_visibility_24, "Ghost Mode"));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_SPY, R.drawable.baseline_remove_red_eye_24, "Spy"));
        items.add(new ListItem(ListItem.TYPE_SEPARATOR));
        items.add(new ListItem(ListItem.TYPE_SETTING, ID_CUSTOMISATION, R.drawable.baseline_settings_24, "Customisation"));
        
        items.add(new ListItem(ListItem.TYPE_SHADOW_BOTTOM));
        items.add(new ListItem(ListItem.TYPE_DESCRIPTION, 0, 0, "Advanced features for Ghost Mode, Spy essentials, and Drawer customisation."));

        adapter.setItems(items, false);
        recyclerView.setAdapter(adapter);

        return recyclerView;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();
        
        if (id == ID_GHOST_MODE) {
            // Navigate to Ghost Mode settings (Ghost essentials section)
            GhostSettingsController controller = new GhostSettingsController(context, tdlib);
            UI.navigateTo(controller);
        }
        else if (id == ID_SPY) {
            // Navigate to Spy settings (Spy essentials section - same as Ghost Mode but scrolled/focused on Spy)
            // For now, navigate to the same Ghost Mode controller which contains Spy essentials
            GhostSettingsController controller = new GhostSettingsController(context, tdlib);
            UI.navigateTo(controller);
            // TODO: Could add a scroll parameter to focus on Spy essentials section
        }
        else if (id == ID_CUSTOMISATION) {
            // Navigate to Drawer Preferences (Customisation)
            DrawerPreferencesController controller = new DrawerPreferencesController(context, tdlib);
            UI.navigateTo(controller);
        }
    }
}
