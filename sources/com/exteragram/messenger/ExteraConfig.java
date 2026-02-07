package com.exteragram.messenger;

import android.content.SharedPreferences;
import android.util.Pair;
import com.exteragram.messenger.adblock.backend.AdBlockManager;
import com.exteragram.messenger.api.ApiController;
import com.exteragram.messenger.backup.PreferencesUtils;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.utils.ChatUtils;
import com.exteragram.messenger.utils.network.BankingUtils;
import com.exteragram.messenger.utils.network.RemoteUtils;
import com.exteragram.messenger.utils.text.TranslatorUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.radolyn.ayugram.AyuConfig;
import com.radolyn.ayugram.AyuInfra;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import org.mvel2.DataTypes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildVars;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.SharedConfig;
import org.telegram.p023ui.web.SearchEngine;
import p017j$.util.function.Consumer$CC;

/* loaded from: classes.dex */
public abstract class ExteraConfig {
    public static boolean addCommaAfterMention;
    public static boolean alwaysSendInHD;
    public static boolean archiveOnPull;
    public static float avatarCorners;
    public static int bottomButton;
    public static boolean cameraMirrorMode;
    public static boolean cameraStabilization;
    public static int cameraType;
    public static boolean centerTitle;
    public static boolean checkUpdatesOnLaunch;
    private static boolean configLoaded;
    private static volatile Pair currentApiBot;
    public static boolean customThemes;
    public static boolean disableNumberRounding;
    public static boolean disableUnarchiveSwipe;
    public static int dividerType;
    public static int doubleTapAction;
    public static int doubleTapActionOutOwner;
    public static int doubleTapSeekDuration;
    public static int downloadSpeedBoost;
    public static String editingIconPackId;
    public static SharedPreferences.Editor editor;
    public static boolean enableAdBlock;
    public static int eventType;
    public static boolean extendedFramesPerSecond;
    public static boolean filterZalgo;
    public static float flashIntensity;
    public static float flashWarmth;
    public static boolean forceBlur;
    public static boolean forceSnow;
    public static boolean formatTimeWithSeconds;
    public static boolean glareOnElements;
    public static boolean gooeyAvatarAnimation;
    public static boolean groupMessageMenu;
    public static boolean hideActionBarStatus;
    public static boolean hideAllChats;
    public static boolean hideCameraTile;
    public static boolean hideKeyboardOnScroll;
    public static boolean hidePhoneNumber;
    public static boolean hidePhotoCounter;
    public static boolean hideReactions;
    public static boolean hideSendAsPeer;
    public static boolean hideShareButton;
    public static boolean hideStickerTime;
    public static boolean hideStories;
    public static int iconPack;
    public static boolean immersiveDrawerAnimation;
    public static boolean inAppVibration;
    public static boolean md3Containers;
    public static boolean md3SeparatedHeaders;
    public static boolean newSwitchStyle;
    public static boolean pauseOnMinimizeRound;
    public static boolean pauseOnMinimizeVideo;
    public static boolean pauseOnMinimizeVoice;
    public static Set pinnedPlugins;
    public static boolean pluginsCompactView;
    public static boolean pluginsDevMode;
    public static boolean pluginsEngine;
    public static boolean pluginsSafeMode;
    public static boolean postprocessingWithAi;
    public static boolean preferOriginalQuality;
    public static SharedPreferences preferences;
    public static boolean quickAdminShortcuts;
    public static boolean quickTransitionForChannels;
    public static boolean quickTransitionForTopics;
    public static String recognitionLanguage;
    public static boolean rememberLastUsedCamera;
    public static boolean removeMessageTail;
    public static boolean replaceEditedWithIcon;
    public static boolean replyBackground;
    public static boolean replyColors;
    public static boolean replyEmoji;
    public static boolean senderMiniAvatars;
    public static boolean showClearButton;
    public static boolean showCopyPhotoButton;
    public static boolean showDetailsButton;
    public static boolean showGenerateButton;
    public static boolean showHistoryButton;
    public static int showIdAndDc;
    public static boolean showReportButton;
    public static boolean showSaveMessageButton;
    public static boolean singleCornerRadius;
    public static boolean springAnimations;
    public static boolean squareFab;
    public static boolean staticZoom;
    public static int stickerShape;
    public static float stickerSize;
    public static boolean swipeToPip;
    public static boolean tabCounter;
    public static int tabIcons;
    public static int tabStyle;
    public static int tabletMode;
    public static String targetLang;
    public static int titleText;
    public static int translationFormality;
    public static int translationProvider;
    public static boolean unlimitedRecentStickers;
    public static boolean unmuteWithVolumeButtons;
    public static long updateScheduleTimestamp;
    public static boolean uploadSpeedBoost;
    public static boolean useGoogleAnalytics;
    public static boolean useGoogleCrashlytics;
    public static boolean useSystemFonts;
    public static boolean useSystemIconShape;
    public static boolean useYandexMaps;
    public static int videoMessagesCamera;
    public static int voiceHintShowcases;
    private static final Object sync = new Object();
    public static ArrayList drawerLayout = new ArrayList();
    public static ArrayList drawerHiddenItems = new ArrayList();
    public static ArrayList iconPacksLayout = new ArrayList();
    public static ArrayList iconPacksHidden = new ArrayList();
    public static SearchEngine YANDEX_SEARCH_ENGINE = new SearchEngine("Yandex", "https://mini.ya.ru/", "https://ya.ru/search/?text=", "https://suggestqueries.google.com/complete/search?client=chrome&q=", "https://yandex.ru/legal/confidential");

    public static Pair getApiBotInfo() {
        if (currentApiBot != null) {
            return currentApiBot;
        }
        synchronized (ExteraConfig.class) {
            try {
                if (currentApiBot != null) {
                    return currentApiBot;
                }
                String stringConfigValue = RemoteUtils.getStringConfigValue("extera_api_bot", "8083294286:exteraAuthBot");
                if (stringConfigValue != null) {
                    try {
                        String[] strArrSplit = stringConfigValue.split(":", 2);
                        if (strArrSplit.length == 2) {
                            Pair pair = new Pair(Long.valueOf(Long.parseLong(strArrSplit[0])), strArrSplit[1]);
                            currentApiBot = pair;
                            return pair;
                        }
                    } catch (Exception e) {
                        FileLog.m1160e(e);
                    }
                }
                Pair pair2 = new Pair(8083294286L, "exteraAuthBot");
                currentApiBot = pair2;
                return pair2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    static {
        loadConfig();
    }

    public enum DrawerItem {
        GHOST_MODE(DataTypes.EMPTY),
        KILL_APP(201),
        DIVIDER(-1),
        PROFILE(18),
        STATUS(15),
        ARCHIVE(14),
        BOTS(105),
        NEW_GROUP(2),
        NEW_SECRET(3),
        NEW_CHANNEL(4),
        CONTACTS(6),
        CALLS(10),
        SAVED(11),
        SETTINGS(8),
        PLUGINS(102),
        BROWSER(101),
        QR(17);


        /* renamed from: id */
        public final int f145id;

        DrawerItem(int i) {
            this.f145id = i;
        }

        public static DrawerItem getById(int i) {
            for (DrawerItem drawerItem : values()) {
                if (drawerItem.f145id == i) {
                    return drawerItem;
                }
            }
            return null;
        }
    }

    public static ArrayList getDefaultDrawerLayout() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(DrawerItem.GHOST_MODE.f145id));
        DrawerItem drawerItem = DrawerItem.DIVIDER;
        arrayList.add(Integer.valueOf(drawerItem.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.PROFILE.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.STATUS.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.ARCHIVE.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.BOTS.f145id));
        arrayList.add(Integer.valueOf(drawerItem.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.NEW_GROUP.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.CONTACTS.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.CALLS.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.SAVED.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.SETTINGS.f145id));
        PluginsController.isPluginEngineSupported();
        arrayList.add(Integer.valueOf(drawerItem.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.BROWSER.f145id));
        arrayList.add(Integer.valueOf(DrawerItem.QR.f145id));
        return arrayList;
    }

    public static void loadConfig() {
        boolean z;
        ArrayList arrayList;
        int i;
        ArrayList arrayList2;
        int i2;
        ArrayList arrayList3;
        int i3;
        ArrayList arrayList4;
        int i4;
        ArrayList arrayList5;
        int i5;
        ArrayList arrayList6;
        int i6;
        ArrayList arrayList7;
        int i7;
        ArrayList arrayList8;
        int i8;
        ArrayList arrayList9;
        int i9;
        ArrayList arrayList10;
        int i10;
        ArrayList arrayList11;
        int i11;
        ArrayList arrayList12;
        int i12;
        ArrayList arrayList13;
        int i13;
        ArrayList arrayList14;
        int i14;
        ArrayList arrayList15;
        int i15;
        synchronized (sync) {
            try {
                if (configLoaded) {
                    return;
                }
                SharedPreferences preferences2 = PreferencesUtils.getPreferences("exteraconfig");
                preferences = preferences2;
                editor = preferences2.edit();
                cameraType = preferences.getInt("cameraType", getPerfomanceClassFast() == 2 ? 2 : 0);
                translationProvider = preferences.getInt("translationProvider", 0);
                translationFormality = preferences.getInt("translationFormality", 0);
                disableNumberRounding = preferences.getBoolean("disableNumberRounding", false);
                formatTimeWithSeconds = preferences.getBoolean("formatTimeWithSeconds", false);
                inAppVibration = preferences.getBoolean("inAppVibration", true);
                filterZalgo = preferences.getBoolean("filterZalgo", true);
                tabletMode = preferences.getInt("tabletMode", 0);
                downloadSpeedBoost = preferences.getInt("downloadSpeedBoost", 0);
                uploadSpeedBoost = preferences.getBoolean("uploadSpeedBoost", false);
                hidePhoneNumber = preferences.getBoolean("hidePhoneNumber", false);
                showIdAndDc = preferences.getInt("showIdAndDc", 2);
                archiveOnPull = preferences.getBoolean("archiveOnPull", false);
                disableUnarchiveSwipe = preferences.getBoolean("disableUnarchiveSwipe", true);
                useYandexMaps = preferences.getBoolean("useYandexMaps", false);
                enableAdBlock = preferences.getBoolean("enableAdBlock", true);
                avatarCorners = preferences.getFloat("avatarCorners", 28.0f);
                singleCornerRadius = preferences.getBoolean("singleCornerRadius", false);
                hideActionBarStatus = preferences.getBoolean("hideActionBarStatus", false);
                hideStories = preferences.getBoolean("hideStories", false);
                centerTitle = preferences.getBoolean("centerTitle", false);
                senderMiniAvatars = preferences.getBoolean("senderMiniAvatars", false);
                titleText = preferences.getInt("titleText", 2);
                tabCounter = preferences.getBoolean("tabCounter", true);
                tabIcons = preferences.getInt("tabIcons", 1);
                tabStyle = preferences.getInt("tabStyle", 4);
                hideAllChats = preferences.getBoolean("hideAllChats", false);
                iconPack = preferences.getInt("iconPack", 1);
                editingIconPackId = preferences.getString("editingIconPackId", null);
                squareFab = preferences.getBoolean("squareFab", true);
                md3Containers = preferences.getBoolean("md3Containers", true);
                md3SeparatedHeaders = preferences.getBoolean("md3SeparatedHeaders", true);
                dividerType = preferences.getInt("dividerType", 1);
                forceSnow = preferences.getBoolean("forceSnow", false);
                useSystemFonts = preferences.getBoolean("useSystemFonts", true);
                newSwitchStyle = preferences.getBoolean("newSwitchStyle", true);
                removeMessageTail = preferences.getBoolean("removeMessageTail", true);
                gooeyAvatarAnimation = preferences.getBoolean("gooeyAvatarAnimation", true);
                springAnimations = preferences.getBoolean("springAnimations", true);
                glareOnElements = preferences.getBoolean("glareOnElements", true);
                forceBlur = preferences.getBoolean("forceBlur", false);
                eventType = preferences.getInt("eventType", 0);
                immersiveDrawerAnimation = preferences.getBoolean("immersiveDrawerAnimation", false);
                String string = preferences.getString("iconPacksLayout", null);
                String string2 = preferences.getString("iconPacksHidden", null);
                if (string != null) {
                    iconPacksLayout = (ArrayList) new Gson().fromJson(string, new TypeToken<ArrayList<String>>() { // from class: com.exteragram.messenger.ExteraConfig.1
                    }.getType());
                    if (string2 != null) {
                        iconPacksHidden = (ArrayList) new Gson().fromJson(string2, new TypeToken<ArrayList<String>>() { // from class: com.exteragram.messenger.ExteraConfig.2
                        }.getType());
                    } else {
                        iconPacksHidden = new ArrayList();
                    }
                } else {
                    iconPacksLayout = new ArrayList();
                    iconPacksHidden = new ArrayList();
                    String[] strArr = {"base.default", "base.solar", "base.remix"};
                    for (int i16 = 0; i16 < 3; i16++) {
                        if (i16 == iconPack) {
                            iconPacksLayout.add(strArr[i16]);
                        } else {
                            iconPacksHidden.add(strArr[i16]);
                        }
                    }
                    saveIconPacksLayout();
                }
                String[] strArr2 = {"base.default", "base.solar", "base.remix"};
                boolean z2 = false;
                for (int i17 = 0; i17 < 3; i17++) {
                    String str = strArr2[i17];
                    if (!iconPacksLayout.contains(str) && !iconPacksHidden.contains(str)) {
                        iconPacksHidden.add(str);
                        z2 = true;
                    }
                }
                if (z2) {
                    saveIconPacksLayout();
                }
                String string3 = preferences.getString("drawerLayout", null);
                String string4 = preferences.getString("drawerHiddenItems", null);
                if (string3 != null) {
                    drawerLayout = (ArrayList) new Gson().fromJson(string3, new TypeToken<ArrayList<Integer>>() { // from class: com.exteragram.messenger.ExteraConfig.3
                    }.getType());
                    if (string4 != null) {
                        drawerHiddenItems = (ArrayList) new Gson().fromJson(string4, new TypeToken<ArrayList<Integer>>() { // from class: com.exteragram.messenger.ExteraConfig.4
                        }.getType());
                    } else {
                        drawerHiddenItems = new ArrayList();
                    }
                    z = false;
                } else {
                    drawerLayout = new ArrayList();
                    drawerHiddenItems = new ArrayList();
                    boolean z3 = AyuConfig.preferences.getBoolean("showGhostToggleInDrawer", true);
                    boolean z4 = AyuConfig.preferences.getBoolean("showKillButtonInDrawer", false);
                    boolean z5 = preferences.getBoolean("myProfile", true);
                    boolean z6 = preferences.getBoolean("changeStatus", true);
                    boolean z7 = preferences.getBoolean("archivedChats", true);
                    boolean z8 = preferences.getBoolean("menuBots", true);
                    boolean z9 = preferences.getBoolean("newGroup", true);
                    boolean z10 = preferences.getBoolean("newSecretChat", false);
                    boolean z11 = preferences.getBoolean("newChannel", false);
                    boolean z12 = preferences.getBoolean("contacts", true);
                    boolean z13 = preferences.getBoolean("calls", true);
                    boolean z14 = preferences.getBoolean("savedMessages", true);
                    boolean z15 = preferences.getBoolean("pluginsItem", false);
                    z = false;
                    boolean z16 = preferences.getBoolean("browser", true);
                    boolean z17 = preferences.getBoolean("scanQr", true);
                    if (z3) {
                        arrayList = drawerLayout;
                        i = DrawerItem.GHOST_MODE.f145id;
                    } else {
                        arrayList = drawerHiddenItems;
                        i = DrawerItem.GHOST_MODE.f145id;
                    }
                    arrayList.add(Integer.valueOf(i));
                    if (z4) {
                        arrayList2 = drawerLayout;
                        i2 = DrawerItem.KILL_APP.f145id;
                    } else {
                        arrayList2 = drawerHiddenItems;
                        i2 = DrawerItem.KILL_APP.f145id;
                    }
                    arrayList2.add(Integer.valueOf(i2));
                    if (z4 || z3) {
                        drawerLayout.add(Integer.valueOf(DrawerItem.DIVIDER.f145id));
                    }
                    if (z5) {
                        arrayList3 = drawerLayout;
                        i3 = DrawerItem.PROFILE.f145id;
                    } else {
                        arrayList3 = drawerHiddenItems;
                        i3 = DrawerItem.PROFILE.f145id;
                    }
                    arrayList3.add(Integer.valueOf(i3));
                    if (z6) {
                        arrayList4 = drawerLayout;
                        i4 = DrawerItem.STATUS.f145id;
                    } else {
                        arrayList4 = drawerHiddenItems;
                        i4 = DrawerItem.STATUS.f145id;
                    }
                    arrayList4.add(Integer.valueOf(i4));
                    if (z7) {
                        arrayList5 = drawerLayout;
                        i5 = DrawerItem.ARCHIVE.f145id;
                    } else {
                        arrayList5 = drawerHiddenItems;
                        i5 = DrawerItem.ARCHIVE.f145id;
                    }
                    arrayList5.add(Integer.valueOf(i5));
                    if (z8) {
                        arrayList6 = drawerLayout;
                        i6 = DrawerItem.BOTS.f145id;
                    } else {
                        arrayList6 = drawerHiddenItems;
                        i6 = DrawerItem.BOTS.f145id;
                    }
                    arrayList6.add(Integer.valueOf(i6));
                    if (!drawerLayout.isEmpty()) {
                        drawerLayout.add(Integer.valueOf(DrawerItem.DIVIDER.f145id));
                    }
                    if (z9) {
                        arrayList7 = drawerLayout;
                        i7 = DrawerItem.NEW_GROUP.f145id;
                    } else {
                        arrayList7 = drawerHiddenItems;
                        i7 = DrawerItem.NEW_GROUP.f145id;
                    }
                    arrayList7.add(Integer.valueOf(i7));
                    if (z10) {
                        arrayList8 = drawerLayout;
                        i8 = DrawerItem.NEW_SECRET.f145id;
                    } else {
                        arrayList8 = drawerHiddenItems;
                        i8 = DrawerItem.NEW_SECRET.f145id;
                    }
                    arrayList8.add(Integer.valueOf(i8));
                    if (z11) {
                        arrayList9 = drawerLayout;
                        i9 = DrawerItem.NEW_CHANNEL.f145id;
                    } else {
                        arrayList9 = drawerHiddenItems;
                        i9 = DrawerItem.NEW_CHANNEL.f145id;
                    }
                    arrayList9.add(Integer.valueOf(i9));
                    if (z12) {
                        arrayList10 = drawerLayout;
                        i10 = DrawerItem.CONTACTS.f145id;
                    } else {
                        arrayList10 = drawerHiddenItems;
                        i10 = DrawerItem.CONTACTS.f145id;
                    }
                    arrayList10.add(Integer.valueOf(i10));
                    if (z13) {
                        arrayList11 = drawerLayout;
                        i11 = DrawerItem.CALLS.f145id;
                    } else {
                        arrayList11 = drawerHiddenItems;
                        i11 = DrawerItem.CALLS.f145id;
                    }
                    arrayList11.add(Integer.valueOf(i11));
                    if (z14) {
                        arrayList12 = drawerLayout;
                        i12 = DrawerItem.SAVED.f145id;
                    } else {
                        arrayList12 = drawerHiddenItems;
                        i12 = DrawerItem.SAVED.f145id;
                    }
                    arrayList12.add(Integer.valueOf(i12));
                    drawerLayout.add(Integer.valueOf(DrawerItem.SETTINGS.f145id));
                    if (PluginsController.isPluginEngineSupported()) {
                        if (z15) {
                            arrayList15 = drawerLayout;
                            i15 = DrawerItem.PLUGINS.f145id;
                        } else {
                            arrayList15 = drawerHiddenItems;
                            i15 = DrawerItem.PLUGINS.f145id;
                        }
                        arrayList15.add(Integer.valueOf(i15));
                    }
                    drawerLayout.add(Integer.valueOf(DrawerItem.DIVIDER.f145id));
                    if (z16) {
                        arrayList13 = drawerLayout;
                        i13 = DrawerItem.BROWSER.f145id;
                    } else {
                        arrayList13 = drawerHiddenItems;
                        i13 = DrawerItem.BROWSER.f145id;
                    }
                    arrayList13.add(Integer.valueOf(i13));
                    if (z17) {
                        arrayList14 = drawerLayout;
                        i14 = DrawerItem.QR.f145id;
                    } else {
                        arrayList14 = drawerHiddenItems;
                        i14 = DrawerItem.QR.f145id;
                    }
                    arrayList14.add(Integer.valueOf(i14));
                    saveDrawerLayout();
                }
                if (!PluginsController.isPluginEngineSupported()) {
                    pluginsEngine = z;
                    if (preferences.getBoolean("pluginsEngine", false)) {
                        editor.putBoolean("pluginsEngine", false).apply();
                    }
                    Integer numValueOf = Integer.valueOf(DrawerItem.PLUGINS.f145id);
                    drawerLayout.remove(numValueOf);
                    drawerHiddenItems.remove(numValueOf);
                } else {
                    pluginsEngine = preferences.getBoolean("pluginsEngine", false);
                }
                drawerLayout.removeAll(drawerHiddenItems);
                stickerSize = preferences.getFloat("stickerSize", 12.0f);
                stickerShape = preferences.getInt("stickerShape", 1);
                replyColors = preferences.getBoolean("replyColors", true);
                replyEmoji = preferences.getBoolean("replyEmoji", true);
                replyBackground = preferences.getBoolean("replyBackground", true);
                hideStickerTime = preferences.getBoolean("hideStickerTime", false);
                unlimitedRecentStickers = preferences.getBoolean("unlimitedRecentStickers", false);
                hideSendAsPeer = preferences.getBoolean("hideSendAsPeer", false);
                hideReactions = preferences.getBoolean("hideReactions", false);
                doubleTapAction = preferences.getInt("doubleTapAction", 1);
                doubleTapActionOutOwner = preferences.getInt("doubleTapActionOutOwner", 1);
                bottomButton = preferences.getInt("bottomButton", 2);
                hideKeyboardOnScroll = preferences.getBoolean("hideKeyboardOnScroll", true);
                quickAdminShortcuts = preferences.getBoolean("quickAdminShortcuts", true);
                quickTransitionForChannels = preferences.getBoolean("quickTransitionForChannels", true);
                quickTransitionForTopics = preferences.getBoolean("quickTransitionForTopics", true);
                hideShareButton = preferences.getBoolean("hideShareButton", true);
                replaceEditedWithIcon = preferences.getBoolean("replaceEditedWithIcon", true);
                showDetailsButton = preferences.getBoolean("showDetailsButton", true);
                showGenerateButton = preferences.getBoolean("showGenerateButton", true);
                showSaveMessageButton = preferences.getBoolean("showSaveMessageButton", false);
                showCopyPhotoButton = preferences.getBoolean("showCopyPhotoButton", true);
                showClearButton = preferences.getBoolean("showClearButton", true);
                showReportButton = preferences.getBoolean("showReportButton", true);
                showHistoryButton = preferences.getBoolean("showHistoryButton", false);
                groupMessageMenu = preferences.getBoolean("groupMessageMenu", true);
                customThemes = preferences.getBoolean("customThemes", true);
                addCommaAfterMention = preferences.getBoolean("addCommaAfterMention", true);
                hidePhotoCounter = preferences.getBoolean("hidePhotoCounter", true);
                alwaysSendInHD = preferences.getBoolean("alwaysSendInHD", true);
                hideCameraTile = preferences.getBoolean("hideCameraTile", true);
                recognitionLanguage = preferences.getString("recognitionLanguage", "none");
                postprocessingWithAi = preferences.getBoolean("postprocessingWithAi", false);
                extendedFramesPerSecond = preferences.getBoolean("extendedFramesPerSecond", false);
                cameraStabilization = preferences.getBoolean("cameraStabilization", false);
                cameraMirrorMode = preferences.getBoolean("cameraMirrorMode", true);
                staticZoom = preferences.getBoolean("staticZoom", false);
                videoMessagesCamera = preferences.getInt("videoMessagesCamera", 0);
                rememberLastUsedCamera = preferences.getBoolean("rememberLastUsedCamera", false);
                pauseOnMinimizeVideo = preferences.getBoolean("pauseOnMinimizeVideo", true);
                pauseOnMinimizeVoice = preferences.getBoolean("pauseOnMinimizeVoice", false);
                pauseOnMinimizeRound = preferences.getBoolean("pauseOnMinimizeRound", false);
                doubleTapSeekDuration = preferences.getInt("doubleTapSeekDuration", 1);
                preferOriginalQuality = preferences.getBoolean("preferOriginalQuality", false);
                swipeToPip = preferences.getBoolean("swipeToPip", false);
                unmuteWithVolumeButtons = preferences.getBoolean("unmuteWithVolumeButtons", false);
                updateScheduleTimestamp = preferences.getLong("updateScheduleTimestamp", 0L);
                checkUpdatesOnLaunch = preferences.getBoolean("checkUpdatesOnLaunch", true);
                targetLang = preferences.getString("targetLang", "en");
                voiceHintShowcases = preferences.getInt("voiceHintShowcases", 0);
                useGoogleCrashlytics = preferences.getBoolean("useGoogleCrashlytics", !AyuInfra.isModified());
                useGoogleAnalytics = preferences.getBoolean("useGoogleAnalytics", false);
                flashWarmth = preferences.getFloat("flashWarmth", 0.5f);
                flashIntensity = preferences.getFloat("flashIntensity", 1.0f);
                pluginsDevMode = preferences.getBoolean("pluginsDevMode", false);
                pluginsSafeMode = preferences.getBoolean("pluginsSafeMode", false);
                pluginsCompactView = preferences.getBoolean("pluginsCompactView", false);
                pinnedPlugins = preferences.getStringSet("pinnedPlugins", Collections.EMPTY_SET);
                useSystemIconShape = preferences.getBoolean("useSystemIconShape", true);
                configLoaded = true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private static int getPerfomanceClassFast() {
        return ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getInt("devicePerformanceClass", SharedConfig.getDevicePerformanceClass());
    }

    public static int getAvatarCorners(float f) {
        return getAvatarCorners(f, false, false, false);
    }

    public static int getAvatarCorners(float f, boolean z) {
        return getAvatarCorners(f, z, false, false);
    }

    public static int getAvatarCorners(float f, boolean z, boolean z2) {
        return getAvatarCorners(f, z, z2, false);
    }

    public static int getAvatarCorners(float f, boolean z, boolean z2, boolean z3) {
        float f2 = avatarCorners;
        if (f2 == 0.0f) {
            return 0;
        }
        float fM1146dp = (f2 * f) / 56.0f;
        if (z3) {
            fM1146dp -= 2.5f;
        }
        if (!z) {
            fM1146dp = AndroidUtilities.m1146dp(fM1146dp);
        }
        if (z2 && !singleCornerRadius) {
            fM1146dp = (((int) fM1146dp) * 42) >> 6;
        }
        return (int) Math.ceil(fM1146dp);
    }

    public static void toggleLogging() {
        SharedPreferences.Editor editorEdit = ApplicationLoader.applicationContext.getSharedPreferences("systemConfig", 0).edit();
        boolean z = !BuildVars.LOGS_ENABLED;
        BuildVars.LOGS_ENABLED = z;
        editorEdit.putBoolean("logsEnabled", z).apply();
        if (BuildVars.LOGS_ENABLED) {
            return;
        }
        FileLog.cleanupLogs();
    }

    public static boolean getLogging() {
        return ApplicationLoader.applicationContext.getSharedPreferences("systemConfig", 0).getBoolean("logsEnabled", false);
    }

    public static void setLogging(boolean z) {
        SharedPreferences.Editor editorEdit = ApplicationLoader.applicationContext.getSharedPreferences("systemConfig", 0).edit();
        BuildVars.LOGS_ENABLED = z;
        editorEdit.putBoolean("logsEnabled", z).apply();
        if (BuildVars.LOGS_ENABLED) {
            return;
        }
        FileLog.cleanupLogs();
    }

    public static String getCurrentLangName() {
        return TranslatorUtils.getLanguageTitleSystem(targetLang);
    }

    public static int getDoubleTapSeekDuration() {
        int i = doubleTapSeekDuration;
        if (i == 0 || i == 1 || i == 2) {
            return (i + 1) * 5000;
        }
        return 30000;
    }

    public static void reloadConfig() {
        synchronized (sync) {
            configLoaded = false;
            loadConfig();
        }
    }

    public static void saveExchangeRates(BankingUtils.ExchangeRates exchangeRates) {
        editor.putString("exchangeRates", new Gson().toJson(exchangeRates)).apply();
    }

    public static BankingUtils.ExchangeRates getExchangeRates() {
        Type type = new TypeToken<BankingUtils.ExchangeRates>() { // from class: com.exteragram.messenger.ExteraConfig.5
        }.getType();
        String string = preferences.getString("exchangeRates", null);
        if (string == null) {
            return null;
        }
        return (BankingUtils.ExchangeRates) new Gson().fromJson(string, type);
    }

    public static void init() {
        ApiController.sync();
        RemoteUtils.init();
        PluginsController.getInstance().init(new Runnable() { // from class: com.exteragram.messenger.ExteraConfig$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                PluginsController.getInstance().executeOnAppEvent(PluginsConstants.APP_START);
            }
        });
        ChatUtils.utilsQueue.postRunnable(new Runnable() { // from class: com.exteragram.messenger.ExteraConfig$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AdBlockManager.initialize();
            }
        });
        ApiController.getExchangeRates("usd", new Consumer() { // from class: com.exteragram.messenger.ExteraConfig$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ExteraConfig.saveExchangeRates(new BankingUtils.ExchangeRates(new HashMap((Map) obj)));
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }
        });
    }

    public static void saveDrawerLayout() {
        editor.putString("drawerLayout", new Gson().toJson(drawerLayout));
        editor.putString("drawerHiddenItems", new Gson().toJson(drawerHiddenItems));
        editor.apply();
    }

    public static void saveIconPacksLayout() {
        editor.putString("iconPacksLayout", new Gson().toJson(iconPacksLayout));
        editor.putString("iconPacksHidden", new Gson().toJson(iconPacksHidden));
        editor.apply();
    }

    public static boolean canUseYandexMaps() {
        return useYandexMaps && ApplicationLoader.applicationLoaderInstance.allowToUseYandexMaps();
    }
}
