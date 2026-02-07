package org.telegram.p023ui.Adapters;

import android.location.Location;
import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.Components.ListView.AdapterWithDiffUtils;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes5.dex */
public abstract class BaseLocationAdapter extends AdapterWithDiffUtils {
    public final boolean biz;
    private int currentRequestNum;
    private BaseLocationAdapterDelegate delegate;
    private long dialogId;
    private String lastFoundQuery;
    private Location lastSearchLocation;
    private String lastSearchQuery;
    protected boolean searchInProgress;
    private Runnable searchRunnable;
    protected boolean searching;
    protected boolean searchingLocations;
    private boolean searchingUser;
    public final boolean stories;
    protected boolean searched = false;
    protected ArrayList locations = new ArrayList();
    protected ArrayList places = new ArrayList();
    private int currentAccount = UserConfig.selectedAccount;

    public interface BaseLocationAdapterDelegate {
        void didLoadSearchResult(ArrayList arrayList);
    }

    public BaseLocationAdapter(boolean z, boolean z2) {
        this.stories = z;
        this.biz = z2;
    }

    public void destroy() {
        if (this.currentRequestNum != 0) {
            ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.currentRequestNum, true);
            this.currentRequestNum = 0;
        }
    }

    public void setDelegate(long j, BaseLocationAdapterDelegate baseLocationAdapterDelegate) {
        this.dialogId = j;
        this.delegate = baseLocationAdapterDelegate;
    }

    public void searchDelayed(final String str, final Location location) {
        if (str == null || str.length() == 0) {
            this.places.clear();
            this.locations.clear();
            this.searchInProgress = false;
            update(true);
            return;
        }
        if (this.searchRunnable != null) {
            Utilities.searchQueue.cancelRunnable(this.searchRunnable);
            this.searchRunnable = null;
        }
        this.searchInProgress = true;
        DispatchQueue dispatchQueue = Utilities.searchQueue;
        Runnable runnable = new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$searchDelayed$1(str, location);
            }
        };
        this.searchRunnable = runnable;
        dispatchQueue.postRunnable(runnable, 400L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchDelayed$1(final String str, final Location location) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$searchDelayed$0(str, location);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchDelayed$0(String str, Location location) {
        this.searchRunnable = null;
        this.lastSearchLocation = null;
        searchPlacesWithQuery(str, location, true);
    }

    private void searchBotUser() {
        String str;
        if (this.searchingUser) {
            return;
        }
        this.searchingUser = true;
        TLRPC.TL_contacts_resolveUsername tL_contacts_resolveUsername = new TLRPC.TL_contacts_resolveUsername();
        if (this.stories) {
            str = MessagesController.getInstance(this.currentAccount).storyVenueSearchBot;
        } else {
            str = MessagesController.getInstance(this.currentAccount).venueSearchBot;
        }
        tL_contacts_resolveUsername.username = str;
        ConnectionsManager.getInstance(this.currentAccount).sendRequest(tL_contacts_resolveUsername, new RequestDelegate() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda2
            @Override // org.telegram.tgnet.RequestDelegate
            public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                this.f$0.lambda$searchBotUser$3(tLObject, tL_error);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchBotUser$3(final TLObject tLObject, TLRPC.TL_error tL_error) {
        if (tLObject != null) {
            AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$searchBotUser$2(tLObject);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchBotUser$2(TLObject tLObject) {
        TLRPC.TL_contacts_resolvedPeer tL_contacts_resolvedPeer = (TLRPC.TL_contacts_resolvedPeer) tLObject;
        MessagesController.getInstance(this.currentAccount).putUsers(tL_contacts_resolvedPeer.users, false);
        MessagesController.getInstance(this.currentAccount).putChats(tL_contacts_resolvedPeer.chats, false);
        MessagesStorage.getInstance(this.currentAccount).putUsersAndChats(tL_contacts_resolvedPeer.users, tL_contacts_resolvedPeer.chats, true, true);
        Location location = this.lastSearchLocation;
        this.lastSearchLocation = null;
        searchPlacesWithQuery(this.lastSearchQuery, location, false);
    }

    public boolean isSearching() {
        return this.searchInProgress;
    }

    public String getLastSearchString() {
        return this.lastFoundQuery;
    }

    public void searchPlacesWithQuery(String str, Location location, boolean z) {
        searchPlacesWithQuery(str, location, z, false);
    }

    public void searchPlacesWithQuery(final String str, Location location, boolean z, boolean z2) {
        Location location2;
        String str2;
        final BaseLocationAdapter baseLocationAdapter;
        final String str3;
        final Location location3;
        final Locale locale;
        if ((location != null || this.stories) && ((location2 = this.lastSearchLocation) == null || location == null || location.distanceTo(location2) >= 200.0f)) {
            Locale locale2 = null;
            this.lastSearchLocation = location == null ? null : new Location(location);
            this.lastSearchQuery = str;
            if (this.searching) {
                this.searching = false;
                if (this.currentRequestNum != 0) {
                    ConnectionsManager.getInstance(this.currentAccount).cancelRequest(this.currentRequestNum, true);
                    this.currentRequestNum = 0;
                }
            }
            getItemCount();
            this.searching = true;
            this.searched = true;
            MessagesController messagesController = MessagesController.getInstance(this.currentAccount);
            if (this.stories) {
                str2 = MessagesController.getInstance(this.currentAccount).storyVenueSearchBot;
            } else {
                str2 = MessagesController.getInstance(this.currentAccount).venueSearchBot;
            }
            TLObject userOrChat = messagesController.getUserOrChat(str2);
            if (userOrChat instanceof TLRPC.User) {
                TLRPC.User user = (TLRPC.User) userOrChat;
                TLRPC.TL_messages_getInlineBotResults tL_messages_getInlineBotResults = new TLRPC.TL_messages_getInlineBotResults();
                tL_messages_getInlineBotResults.query = str == null ? "" : str;
                tL_messages_getInlineBotResults.bot = MessagesController.getInstance(this.currentAccount).getInputUser(user);
                tL_messages_getInlineBotResults.offset = "";
                if (location != null) {
                    TLRPC.TL_inputGeoPoint tL_inputGeoPoint = new TLRPC.TL_inputGeoPoint();
                    tL_messages_getInlineBotResults.geo_point = tL_inputGeoPoint;
                    tL_inputGeoPoint.lat = AndroidUtilities.fixLocationCoord(location.getLatitude());
                    tL_messages_getInlineBotResults.geo_point._long = AndroidUtilities.fixLocationCoord(location.getLongitude());
                    tL_messages_getInlineBotResults.flags |= 1;
                }
                if (DialogObject.isEncryptedDialog(this.dialogId)) {
                    tL_messages_getInlineBotResults.peer = new TLRPC.TL_inputPeerEmpty();
                } else {
                    tL_messages_getInlineBotResults.peer = MessagesController.getInstance(this.currentAccount).getInputPeer(this.dialogId);
                }
                if (!TextUtils.isEmpty(str) && (this.stories || this.biz)) {
                    this.searchingLocations = true;
                    final Locale currentLocale = LocaleController.getInstance().getCurrentLocale();
                    if (!this.stories) {
                        locale = locale2;
                        baseLocationAdapter = this;
                        str3 = str;
                        location3 = location;
                        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() throws IOException {
                                this.f$0.lambda$searchPlacesWithQuery$5(currentLocale, str3, locale, location3, str);
                            }
                        });
                    } else if (currentLocale.getLanguage().contains("en")) {
                        locale = currentLocale;
                        baseLocationAdapter = this;
                        str3 = str;
                        location3 = location;
                        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() throws IOException {
                                this.f$0.lambda$searchPlacesWithQuery$5(currentLocale, str3, locale, location3, str);
                            }
                        });
                    } else {
                        locale2 = Locale.US;
                        locale = locale2;
                        baseLocationAdapter = this;
                        str3 = str;
                        location3 = location;
                        Utilities.globalQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() throws IOException {
                                this.f$0.lambda$searchPlacesWithQuery$5(currentLocale, str3, locale, location3, str);
                            }
                        });
                    }
                } else {
                    baseLocationAdapter = this;
                    str3 = str;
                    location3 = location;
                    baseLocationAdapter.searchingLocations = false;
                }
                if (location3 == null) {
                    return;
                }
                baseLocationAdapter.currentRequestNum = ConnectionsManager.getInstance(baseLocationAdapter.currentAccount).sendRequest(tL_messages_getInlineBotResults, new RequestDelegate() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda1
                    @Override // org.telegram.tgnet.RequestDelegate
                    public final void run(TLObject tLObject, TLRPC.TL_error tL_error) {
                        this.f$0.lambda$searchPlacesWithQuery$7(str3, tLObject, tL_error);
                    }
                });
                update(true);
                return;
            }
            if (z) {
                searchBotUser();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01e0 A[Catch: Exception -> 0x049b, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x049b, blocks: (B:3:0x0009, B:7:0x0012, B:9:0x0023, B:11:0x0032, B:12:0x003d, B:14:0x0043, B:16:0x004b, B:18:0x0051, B:20:0x0059, B:22:0x005f, B:25:0x0070, B:27:0x0098, B:30:0x00a0, B:32:0x00aa, B:33:0x00ad, B:37:0x00c1, B:39:0x00cb, B:41:0x00d1, B:42:0x00d4, B:60:0x0112, B:62:0x0118, B:64:0x011e, B:65:0x0121, B:67:0x0126, B:69:0x012c, B:70:0x012f, B:74:0x0138, B:76:0x0142, B:78:0x0152, B:80:0x015e, B:82:0x016a, B:93:0x01b8, B:95:0x01be, B:96:0x01c1, B:98:0x01ca, B:99:0x01cd, B:101:0x01d7, B:104:0x01e0, B:109:0x01f3, B:111:0x01f9, B:115:0x0227, B:117:0x022d, B:119:0x0249, B:120:0x024b, B:122:0x0253, B:124:0x0257, B:126:0x026a, B:128:0x0271, B:130:0x0277, B:131:0x027b, B:133:0x0281, B:134:0x0285, B:136:0x0294, B:138:0x02a3, B:140:0x02a9, B:142:0x02b5, B:144:0x02bb, B:146:0x02c5, B:148:0x02d3, B:150:0x02da, B:152:0x02e0, B:154:0x02ea, B:156:0x02f8, B:157:0x02fc, B:159:0x0302, B:161:0x030c, B:163:0x031a, B:164:0x031e, B:166:0x0324, B:168:0x032a, B:170:0x0334, B:172:0x033a, B:173:0x033d, B:175:0x0343, B:178:0x034a, B:180:0x034f, B:185:0x0361, B:187:0x0367, B:191:0x037b, B:196:0x038a, B:198:0x0396, B:200:0x03cd, B:202:0x03e0, B:204:0x03e7, B:206:0x03ed, B:207:0x03f1, B:209:0x03f7, B:210:0x03fb, B:212:0x0405, B:213:0x040f, B:215:0x0415, B:216:0x041f, B:220:0x042c, B:222:0x0432, B:224:0x043e, B:226:0x0475, B:227:0x0482, B:183:0x035d, B:121:0x0250, B:86:0x017b, B:88:0x018f, B:90:0x0199, B:92:0x01b3, B:45:0x00db, B:47:0x00e5, B:49:0x00eb, B:50:0x00ee, B:51:0x00f2, B:53:0x00fc, B:55:0x0102, B:57:0x0108, B:58:0x010b), top: B:236:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0224  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0367 A[Catch: Exception -> 0x049b, TryCatch #0 {Exception -> 0x049b, blocks: (B:3:0x0009, B:7:0x0012, B:9:0x0023, B:11:0x0032, B:12:0x003d, B:14:0x0043, B:16:0x004b, B:18:0x0051, B:20:0x0059, B:22:0x005f, B:25:0x0070, B:27:0x0098, B:30:0x00a0, B:32:0x00aa, B:33:0x00ad, B:37:0x00c1, B:39:0x00cb, B:41:0x00d1, B:42:0x00d4, B:60:0x0112, B:62:0x0118, B:64:0x011e, B:65:0x0121, B:67:0x0126, B:69:0x012c, B:70:0x012f, B:74:0x0138, B:76:0x0142, B:78:0x0152, B:80:0x015e, B:82:0x016a, B:93:0x01b8, B:95:0x01be, B:96:0x01c1, B:98:0x01ca, B:99:0x01cd, B:101:0x01d7, B:104:0x01e0, B:109:0x01f3, B:111:0x01f9, B:115:0x0227, B:117:0x022d, B:119:0x0249, B:120:0x024b, B:122:0x0253, B:124:0x0257, B:126:0x026a, B:128:0x0271, B:130:0x0277, B:131:0x027b, B:133:0x0281, B:134:0x0285, B:136:0x0294, B:138:0x02a3, B:140:0x02a9, B:142:0x02b5, B:144:0x02bb, B:146:0x02c5, B:148:0x02d3, B:150:0x02da, B:152:0x02e0, B:154:0x02ea, B:156:0x02f8, B:157:0x02fc, B:159:0x0302, B:161:0x030c, B:163:0x031a, B:164:0x031e, B:166:0x0324, B:168:0x032a, B:170:0x0334, B:172:0x033a, B:173:0x033d, B:175:0x0343, B:178:0x034a, B:180:0x034f, B:185:0x0361, B:187:0x0367, B:191:0x037b, B:196:0x038a, B:198:0x0396, B:200:0x03cd, B:202:0x03e0, B:204:0x03e7, B:206:0x03ed, B:207:0x03f1, B:209:0x03f7, B:210:0x03fb, B:212:0x0405, B:213:0x040f, B:215:0x0415, B:216:0x041f, B:220:0x042c, B:222:0x0432, B:224:0x043e, B:226:0x0475, B:227:0x0482, B:183:0x035d, B:121:0x0250, B:86:0x017b, B:88:0x018f, B:90:0x0199, B:92:0x01b3, B:45:0x00db, B:47:0x00e5, B:49:0x00eb, B:50:0x00ee, B:51:0x00f2, B:53:0x00fc, B:55:0x0102, B:57:0x0108, B:58:0x010b), top: B:236:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:191:0x037b A[Catch: Exception -> 0x049b, TryCatch #0 {Exception -> 0x049b, blocks: (B:3:0x0009, B:7:0x0012, B:9:0x0023, B:11:0x0032, B:12:0x003d, B:14:0x0043, B:16:0x004b, B:18:0x0051, B:20:0x0059, B:22:0x005f, B:25:0x0070, B:27:0x0098, B:30:0x00a0, B:32:0x00aa, B:33:0x00ad, B:37:0x00c1, B:39:0x00cb, B:41:0x00d1, B:42:0x00d4, B:60:0x0112, B:62:0x0118, B:64:0x011e, B:65:0x0121, B:67:0x0126, B:69:0x012c, B:70:0x012f, B:74:0x0138, B:76:0x0142, B:78:0x0152, B:80:0x015e, B:82:0x016a, B:93:0x01b8, B:95:0x01be, B:96:0x01c1, B:98:0x01ca, B:99:0x01cd, B:101:0x01d7, B:104:0x01e0, B:109:0x01f3, B:111:0x01f9, B:115:0x0227, B:117:0x022d, B:119:0x0249, B:120:0x024b, B:122:0x0253, B:124:0x0257, B:126:0x026a, B:128:0x0271, B:130:0x0277, B:131:0x027b, B:133:0x0281, B:134:0x0285, B:136:0x0294, B:138:0x02a3, B:140:0x02a9, B:142:0x02b5, B:144:0x02bb, B:146:0x02c5, B:148:0x02d3, B:150:0x02da, B:152:0x02e0, B:154:0x02ea, B:156:0x02f8, B:157:0x02fc, B:159:0x0302, B:161:0x030c, B:163:0x031a, B:164:0x031e, B:166:0x0324, B:168:0x032a, B:170:0x0334, B:172:0x033a, B:173:0x033d, B:175:0x0343, B:178:0x034a, B:180:0x034f, B:185:0x0361, B:187:0x0367, B:191:0x037b, B:196:0x038a, B:198:0x0396, B:200:0x03cd, B:202:0x03e0, B:204:0x03e7, B:206:0x03ed, B:207:0x03f1, B:209:0x03f7, B:210:0x03fb, B:212:0x0405, B:213:0x040f, B:215:0x0415, B:216:0x041f, B:220:0x042c, B:222:0x0432, B:224:0x043e, B:226:0x0475, B:227:0x0482, B:183:0x035d, B:121:0x0250, B:86:0x017b, B:88:0x018f, B:90:0x0199, B:92:0x01b3, B:45:0x00db, B:47:0x00e5, B:49:0x00eb, B:50:0x00ee, B:51:0x00f2, B:53:0x00fc, B:55:0x0102, B:57:0x0108, B:58:0x010b), top: B:236:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0118 A[Catch: Exception -> 0x049b, TryCatch #0 {Exception -> 0x049b, blocks: (B:3:0x0009, B:7:0x0012, B:9:0x0023, B:11:0x0032, B:12:0x003d, B:14:0x0043, B:16:0x004b, B:18:0x0051, B:20:0x0059, B:22:0x005f, B:25:0x0070, B:27:0x0098, B:30:0x00a0, B:32:0x00aa, B:33:0x00ad, B:37:0x00c1, B:39:0x00cb, B:41:0x00d1, B:42:0x00d4, B:60:0x0112, B:62:0x0118, B:64:0x011e, B:65:0x0121, B:67:0x0126, B:69:0x012c, B:70:0x012f, B:74:0x0138, B:76:0x0142, B:78:0x0152, B:80:0x015e, B:82:0x016a, B:93:0x01b8, B:95:0x01be, B:96:0x01c1, B:98:0x01ca, B:99:0x01cd, B:101:0x01d7, B:104:0x01e0, B:109:0x01f3, B:111:0x01f9, B:115:0x0227, B:117:0x022d, B:119:0x0249, B:120:0x024b, B:122:0x0253, B:124:0x0257, B:126:0x026a, B:128:0x0271, B:130:0x0277, B:131:0x027b, B:133:0x0281, B:134:0x0285, B:136:0x0294, B:138:0x02a3, B:140:0x02a9, B:142:0x02b5, B:144:0x02bb, B:146:0x02c5, B:148:0x02d3, B:150:0x02da, B:152:0x02e0, B:154:0x02ea, B:156:0x02f8, B:157:0x02fc, B:159:0x0302, B:161:0x030c, B:163:0x031a, B:164:0x031e, B:166:0x0324, B:168:0x032a, B:170:0x0334, B:172:0x033a, B:173:0x033d, B:175:0x0343, B:178:0x034a, B:180:0x034f, B:185:0x0361, B:187:0x0367, B:191:0x037b, B:196:0x038a, B:198:0x0396, B:200:0x03cd, B:202:0x03e0, B:204:0x03e7, B:206:0x03ed, B:207:0x03f1, B:209:0x03f7, B:210:0x03fb, B:212:0x0405, B:213:0x040f, B:215:0x0415, B:216:0x041f, B:220:0x042c, B:222:0x0432, B:224:0x043e, B:226:0x0475, B:227:0x0482, B:183:0x035d, B:121:0x0250, B:86:0x017b, B:88:0x018f, B:90:0x0199, B:92:0x01b3, B:45:0x00db, B:47:0x00e5, B:49:0x00eb, B:50:0x00ee, B:51:0x00f2, B:53:0x00fc, B:55:0x0102, B:57:0x0108, B:58:0x010b), top: B:236:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0142 A[Catch: Exception -> 0x049b, TryCatch #0 {Exception -> 0x049b, blocks: (B:3:0x0009, B:7:0x0012, B:9:0x0023, B:11:0x0032, B:12:0x003d, B:14:0x0043, B:16:0x004b, B:18:0x0051, B:20:0x0059, B:22:0x005f, B:25:0x0070, B:27:0x0098, B:30:0x00a0, B:32:0x00aa, B:33:0x00ad, B:37:0x00c1, B:39:0x00cb, B:41:0x00d1, B:42:0x00d4, B:60:0x0112, B:62:0x0118, B:64:0x011e, B:65:0x0121, B:67:0x0126, B:69:0x012c, B:70:0x012f, B:74:0x0138, B:76:0x0142, B:78:0x0152, B:80:0x015e, B:82:0x016a, B:93:0x01b8, B:95:0x01be, B:96:0x01c1, B:98:0x01ca, B:99:0x01cd, B:101:0x01d7, B:104:0x01e0, B:109:0x01f3, B:111:0x01f9, B:115:0x0227, B:117:0x022d, B:119:0x0249, B:120:0x024b, B:122:0x0253, B:124:0x0257, B:126:0x026a, B:128:0x0271, B:130:0x0277, B:131:0x027b, B:133:0x0281, B:134:0x0285, B:136:0x0294, B:138:0x02a3, B:140:0x02a9, B:142:0x02b5, B:144:0x02bb, B:146:0x02c5, B:148:0x02d3, B:150:0x02da, B:152:0x02e0, B:154:0x02ea, B:156:0x02f8, B:157:0x02fc, B:159:0x0302, B:161:0x030c, B:163:0x031a, B:164:0x031e, B:166:0x0324, B:168:0x032a, B:170:0x0334, B:172:0x033a, B:173:0x033d, B:175:0x0343, B:178:0x034a, B:180:0x034f, B:185:0x0361, B:187:0x0367, B:191:0x037b, B:196:0x038a, B:198:0x0396, B:200:0x03cd, B:202:0x03e0, B:204:0x03e7, B:206:0x03ed, B:207:0x03f1, B:209:0x03f7, B:210:0x03fb, B:212:0x0405, B:213:0x040f, B:215:0x0415, B:216:0x041f, B:220:0x042c, B:222:0x0432, B:224:0x043e, B:226:0x0475, B:227:0x0482, B:183:0x035d, B:121:0x0250, B:86:0x017b, B:88:0x018f, B:90:0x0199, B:92:0x01b3, B:45:0x00db, B:47:0x00e5, B:49:0x00eb, B:50:0x00ee, B:51:0x00f2, B:53:0x00fc, B:55:0x0102, B:57:0x0108, B:58:0x010b), top: B:236:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public /* synthetic */ void lambda$searchPlacesWithQuery$5(java.util.Locale r30, java.lang.String r31, java.util.Locale r32, final android.location.Location r33, final java.lang.String r34) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1194
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Adapters.BaseLocationAdapter.lambda$searchPlacesWithQuery$5(java.util.Locale, java.lang.String, java.util.Locale, android.location.Location, java.lang.String):void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchPlacesWithQuery$4(Location location, String str, ArrayList arrayList) {
        this.searchingLocations = false;
        if (location == null) {
            this.currentRequestNum = 0;
            this.searching = false;
            this.places.clear();
            this.searchInProgress = false;
            this.lastFoundQuery = str;
        }
        this.locations.clear();
        this.locations.addAll(arrayList);
        update(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchPlacesWithQuery$7(final String str, final TLObject tLObject, final TLRPC.TL_error tL_error) {
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Adapters.BaseLocationAdapter$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$searchPlacesWithQuery$6(tL_error, str, tLObject);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$searchPlacesWithQuery$6(TLRPC.TL_error tL_error, String str, TLObject tLObject) {
        if (tL_error == null) {
            this.currentRequestNum = 0;
            this.searching = false;
            this.places.clear();
            this.searchInProgress = false;
            this.lastFoundQuery = str;
            TLRPC.messages_BotResults messages_botresults = (TLRPC.messages_BotResults) tLObject;
            int size = messages_botresults.results.size();
            for (int i = 0; i < size; i++) {
                TLRPC.BotInlineResult botInlineResult = (TLRPC.BotInlineResult) messages_botresults.results.get(i);
                if ("venue".equals(botInlineResult.type)) {
                    TLRPC.BotInlineMessage botInlineMessage = botInlineResult.send_message;
                    if (botInlineMessage instanceof TLRPC.TL_botInlineMessageMediaVenue) {
                        TLRPC.TL_botInlineMessageMediaVenue tL_botInlineMessageMediaVenue = (TLRPC.TL_botInlineMessageMediaVenue) botInlineMessage;
                        TLRPC.TL_messageMediaVenue tL_messageMediaVenue = new TLRPC.TL_messageMediaVenue();
                        tL_messageMediaVenue.geo = tL_botInlineMessageMediaVenue.geo;
                        tL_messageMediaVenue.address = tL_botInlineMessageMediaVenue.address;
                        tL_messageMediaVenue.title = tL_botInlineMessageMediaVenue.title;
                        tL_messageMediaVenue.icon = "https://ss3.4sqi.net/img/categories_v2/" + tL_botInlineMessageMediaVenue.venue_type + "_64.png";
                        tL_messageMediaVenue.venue_type = tL_botInlineMessageMediaVenue.venue_type;
                        tL_messageMediaVenue.venue_id = tL_botInlineMessageMediaVenue.venue_id;
                        tL_messageMediaVenue.provider = tL_botInlineMessageMediaVenue.provider;
                        tL_messageMediaVenue.query_id = messages_botresults.query_id;
                        tL_messageMediaVenue.result_id = botInlineResult.f1569id;
                        this.places.add(tL_messageMediaVenue);
                    }
                }
            }
        }
        BaseLocationAdapterDelegate baseLocationAdapterDelegate = this.delegate;
        if (baseLocationAdapterDelegate != null) {
            baseLocationAdapterDelegate.didLoadSearchResult(this.places);
        }
        update(true);
    }

    protected void update(boolean z) {
        notifyDataSetChanged();
    }
}
