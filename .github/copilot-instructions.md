Here is a comprehensive, structured prompt designed to get the best possible result from GitHub Copilot. You can paste this directly into the Copilot Chat window or as a comment block at the top of a new file.

### The Master Prompt

**Copy and paste the text below:**

***

**Context:**
I am working on a fork of **Telegram X (Android)**. I want to implement a **Native Push Service** (Keep-Alive) to allow the app to receive notifications without using Google Mobile Services (FCM/GMS). Since Telegram X uses TDLib (Telegram Database Library), I do not need to write networking code; I only need to keep the application process alive in the background so TDLib maintains its TCP connection.

**Task:**
Act as a Senior Android Developer. Generate the code required to implement a **Foreground Service** that prevents the Android OS from killing the app process.

**Requirements:**

1.  **Create `TGXKeepAliveService.java`:**
    *   It must extend `Service`.
    *   It must run as a **Foreground Service** with a persistent notification (Notification ID: 999).
    *   Handle Android 8.0+ (Oreo) Notification Channels properly.
    *   The notification should have low priority (`IMPORTANCE_LOW`) so it doesn't make sound, but remains visible in the status bar.
    *   Return `START_STICKY` in `onStartCommand`.

2.  **Create `BootReceiver.java`:**
    *   A `BroadcastReceiver` that listens for `BOOT_COMPLETED`.
    *   It should check `SharedPreferences` (key: `"use_native_keepalive"`) to see if the user has enabled this feature.
    *   If enabled, it should start the `TGXKeepAliveService` immediately after boot.

3.  **Battery Optimization Helper:**
    *   Write a helper method `requestIgnoreBatteryOptimizations(Context context)` that checks if the app is on the whitelist.
    *   If not, it should trigger an intent for `Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`.

4.  **Manifest Updates (`AndroidManifest.xml`):**
    *   Provide the XML snippets for the `<service>`, `<receiver>`, and required permissions (`FOREGROUND_SERVICE`, `RECEIVE_BOOT_COMPLETED`, `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`).
    *   Ensure the service includes `foregroundServiceType="dataSync"` for Android 14 compliance.

5.  **Settings Toggle Logic:**
    *   Provide a snippet of Java code for a Switch/Toggle listener.
    *   When turned **ON**: Save preference, start the Service, and request battery optimization ignore.
    *   When turned **OFF**: Save preference and stop the Service.

**Package Name:** Use `org.thunderdog.challegram` as the package name.

***

### How to use this with Copilot

1.  **If using VS Code / Android Studio Chat:** Paste the entire block above into the chat window.
2.  **If using In-Line Code Generation:**
    *   Create a file named `TGXKeepAliveService.java`.
    *   Paste the **Context** and **Item #1** from the prompt above as a comment at the top of the file.
    *   Press `Ctrl+Enter` (or your Copilot trigger key).
    *   Repeat for the Manifest and the Receiver.

### Tips for the Output
*   **Android 14 Compatibility:** Copilot might forget the `foregroundServiceType` in the manifest if not explicitly prompted. The prompt above includes it (`dataSync`), which is critical for the app not to crash on newer Pixels/Samsungs.
*   **TDLib Lifecycle:** You don't need to instruct Copilot to talk to TDLib directly. Just by keeping the `Service` alive, the existing TDLib instance in Telegram X will stay connected.
