# reX Project Architecture & Guidelines

You are the lead architect for "reX", a modular modification for Telegram X (Android/TDLib).
All code generation must adhere strictly to the following UI structure and Logic architecture.

## 1. UI Hierarchy & Navigation

### A. Main Entry Point (Settings)
*   **Location:** Inside `ConfigActivity` (or Main Settings Fragment).
*   **Position:** Insert after "Language" and before "Ask a Question".
*   **Visual:** Divider line + "reX" Item + Settings Gear (⚙️) Icon.

### B. reX Main Menu (`RexSettingsActivity`)
Display three main categories with specific icons:
1.  **Ghost Mode** (Ghost 👻 Icon)
2.  **Spy** (Bot 👾 Icon)
3.  **Customization** (Palette 🎨 Icon)

### C. Ghost Mode Screen (`GhostSettingsFragment`)
*   **Header:** "Ghost essentials"
*   **Master Toggle:** "Ghost Mode 5/5" (Toggles all sub-options).
*   **Sub-Options (Selectable):**
    *   Don't Read Messages
    *   Don't Read Stories
    *   Don't Send Online
    *   Don't Send Typing
    *   Go Offline Automatically
    *   *Logic:* Long-press any option to "Lock" it (prevent Master Toggle from changing it).
*   **Additional Toggles:**
    *   **Read on Interact:** Mark as read when sending a message/reaction.
    *   **Schedule Messages:** Delay outgoing msgs by ~12s (Hide online status).
    *   **Send without Sound:** Default silent messages.
    *   **Story Ghost Mode Alert:** Popup before opening stories.

### D. Spy Screen (`SpySettingsFragment`)
*   **Header:** "Spy essentials"
*   **Toggles:**
    *   Save Deleted Messages
    *   Save Edits History
    *   Save in Bot Dialogs
    *   Save Read Date (Local storage)
    *   Save Last Seen Date (Approximate logging)
*   **Attachments Section:**
    *   Toggle: "Save Attachments"
    *   Selector: "Attachments Folder"
    *   Slider: "Max folder size" (Steps: 300MB, 1GB, 2GB, 5GB, 16GB, No Limit). Logic: Delete oldest if exceeded.
*   **Database Actions:** Buttons for "Export Database", "Import Database", "Clear".

### E. Customization Screen (`CustomizationFragment`)
*   **Visuals:**
    *   Disable Colorful Replies (Toggle).
    *   Translucent Deleted Messages (Toggle).
    *   **Deleted Mark:** Icon Selector (Trash bin, Cross, Ghost, etc.) + Color Picker.
*   **Drawer Options:**
    *   List of elements: Contact, Calls, Saved Messages, Settings, Invite Friends, Help, Night Mode.
    *   Hidden List: Enable Ghost.
    *   *Logic:* Drag-and-drop to reorder. Tap to hide/unhide.

---

## 2. Logic & Data Architecture

### A. Configuration (`.core.RexConfig`)
*   **Preferences:** Store all UI toggles here.
*   **Locked States:** `Set<String>` to store which Ghost options are locked via long-press.
*   **Drawer Order:** `List<String>` to persist sidebar item order.

### B. Database (`.core.spy.database`)
*   **Room Database:** `SavedMessage` (Deleted), `EditHistory`, `UserLog` (Last Seen/Read Date).
*   **DAO:** Methods to query history by ChatID.

### C. Utilities (`.core.utils`)
*   **`RexCloneSender`:** 
    *   **Text:** Extract text -> Create `SendMessage` (Bypass Forward Restriction).
    *   **Media:** Check Download -> Get Path -> Create `SendMessage` (Bypass Forward Restriction).
*   **`RexScheduler`:** Implementation for "Schedule Messages" (Delay queue).
*   **`RexAttachmentManager`:** Monitor folder size and delete old files if limit exceeded.

---

## 3. Integration Hooks (The "How")

### A. Message Context Menu (`MessagePopup.java`)
Inject the following items dynamically:
1.  **"Read Message":** (Ghost Mode ON) -> Force `ViewMessages`.
2.  **"Burn":** (One-View Media) -> Force `ViewMessages` + Close Viewer + Delete Local (Optional).
3.  **"Forward":** (Restricted Content) -> Trigger `RexCloneSender` (Copy-Paste Logic).
4.  **"Save":** (Restricted/TTL) -> Export file to Downloads.
5.  **"View Edit History":** (If edits exist) -> Show Dialog.

### B. Chat Menu (3-Dots in Chat Activity)
Inject "reX" Submenu containing:
*   View Deleted (Open Viewer for this chat).
*   Read Exclusion (Toggle Ghost for this specific chat).
*   Type Exclusion (Toggle Typing for this specific chat).
*   Clear Deleted (Wipe DB for this chat).

### C. Rendering (`MessageView.onDraw`)
*   **Ghost Messages:** 
    *   Do NOT remove from list if Spy is ON.
    *   Apply `0.5f` Alpha (or configured Translucency).
    *   Draw configured **Deleted Mark Icon** in configured **Color**.
*   **Edit Indicator:** Draw "✎ Edited" badge.

### D. Network Interceptor (`TdClient.send`)
*   **Read on Interact:** If sending `SendMessage` -> Force send `ViewMessages` for current chat first.
*   **Ghost:** Block `ViewMessages`/`ViewStory`/`SetChatAction` based on flags.

## 4. Coding Rules
1.  **Imports:** Use `org.drinkless.td.libcore.telegram.TdApi`.
2.  **UI Components:** Use Telegram X standard cells (`TextCell`, `SwitchCell`, `HeaderCell`) to match the native look.
3.  **Performance:** Database IO must be on `Dispatchers.IO`.
4.  **Markers:** `// --- REX MOD ---` ... `// --- END REX ---`.
