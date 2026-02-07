package com.exteragram.messenger.preferences.utils;

import android.text.TextUtils;
import android.view.View;
import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import com.exteragram.messenger.p003ai.p004ui.activities.AiPreferencesActivity;
import com.exteragram.messenger.plugins.PluginsController;
import com.exteragram.messenger.plugins.PluginsController$$ExternalSyntheticBackport1;
import com.exteragram.messenger.plugins.p009ui.PluginsInfoActivity;
import com.exteragram.messenger.preferences.AppearancePreferencesActivity;
import com.exteragram.messenger.preferences.BasePreferencesActivity;
import com.exteragram.messenger.preferences.ChatsPreferencesActivity;
import com.exteragram.messenger.preferences.DrawerPreferencesActivity;
import com.exteragram.messenger.preferences.GeneralPreferencesActivity;
import com.exteragram.messenger.preferences.MainPreferencesActivity;
import com.exteragram.messenger.preferences.OtherPreferencesActivity;
import com.exteragram.messenger.preferences.utils.SettingsRegistry;
import com.radolyn.ayugram.preferences.AyuMainPreferencesActivity;
import com.radolyn.ayugram.preferences.CustomizationPreferencesActivity;
import com.radolyn.ayugram.preferences.FiltersPreferencesActivity;
import com.radolyn.ayugram.preferences.GhostModePreferencesActivity;
import com.radolyn.ayugram.preferences.SpyPreferencesActivity;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.Components.BulletinFactory;
import org.telegram.p023ui.Components.UItem;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.ProfileActivity;
import p017j$.lang.Iterable$EL;
import p017j$.util.Collection;
import p017j$.util.Objects;
import p017j$.util.concurrent.ConcurrentHashMap;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.Function$CC;
import p017j$.util.function.Predicate$CC;

/* loaded from: classes.dex */
public class SettingsRegistry {
    private static final Map ayuCategories;
    private static final Map categoriesIcons;
    private boolean entriesFetched;
    private final ConcurrentHashMap preparedEntries = new ConcurrentHashMap();
    private final ConcurrentHashMap entriesStringAlias = new ConcurrentHashMap();

    private static class SingletonHolder {
        private static final SettingsRegistry INSTANCE = new SettingsRegistry();
    }

    static {
        HashMap map = new HashMap(PluginsController$$ExternalSyntheticBackport1.m212m(new Map.Entry[]{new AbstractMap.SimpleEntry(MainPreferencesActivity.class, Integer.valueOf(C2369R.drawable.etg_settings)), new AbstractMap.SimpleEntry(GeneralPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_media)), new AbstractMap.SimpleEntry(AppearancePreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_theme)), new AbstractMap.SimpleEntry(ChatsPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_discussion)), new AbstractMap.SimpleEntry(PluginsInfoActivity.class, Integer.valueOf(C2369R.drawable.msg_plugins)), new AbstractMap.SimpleEntry(OtherPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_fave)), new AbstractMap.SimpleEntry(AiPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_bot)), new AbstractMap.SimpleEntry(DrawerPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_list))}));
        categoriesIcons = map;
        Map mapM212m = PluginsController$$ExternalSyntheticBackport1.m212m(new Map.Entry[]{new AbstractMap.SimpleEntry(AyuMainPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg2_reactions2)), new AbstractMap.SimpleEntry(GhostModePreferencesActivity.class, Integer.valueOf(C2369R.drawable.ayu_ghost)), new AbstractMap.SimpleEntry(SpyPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_bots)), new AbstractMap.SimpleEntry(FiltersPreferencesActivity.class, Integer.valueOf(C2369R.drawable.menu_tag_filter)), new AbstractMap.SimpleEntry(CustomizationPreferencesActivity.class, Integer.valueOf(C2369R.drawable.msg_theme))});
        ayuCategories = mapM212m;
        map.putAll(mapM212m);
    }

    public static SettingsRegistry getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static boolean isValidForSearch(UItem uItem) {
        if (uItem.f2017id != 0 && !TextUtils.isEmpty(uItem.text)) {
            return true;
        }
        Integer numValueOf = Integer.valueOf(uItem.f2017id);
        Integer numValueOf2 = Integer.valueOf(uItem.viewType);
        View view = uItem.view;
        FileLog.m1158e(String.format("[Extera] UItems with ID 0 or empty text cannot be added as search result. (UItem ID: %s; View type: %s; View: %s; Text: %s; Subtext: %s)", numValueOf, numValueOf2, view == null ? null : view.getClass().getName(), uItem.text, TextUtils.concat(uItem.subtext, uItem.animatedText)));
        return false;
    }

    public static boolean isValidForLinkAliases(UItem uItem) {
        int i = uItem.f2017id;
        if (i != 0) {
            return true;
        }
        Integer numValueOf = Integer.valueOf(i);
        Integer numValueOf2 = Integer.valueOf(uItem.viewType);
        View view = uItem.view;
        FileLog.m1158e(String.format("[Extera] Cannot set link aliases for UItems with ID 0. (UItem ID: %s; View type: %s; View: %s; Text: %s; Subtext: %s)", numValueOf, numValueOf2, view == null ? null : view.getClass().getName(), uItem.text, TextUtils.concat(uItem.subtext, uItem.animatedText)));
        return false;
    }

    public void addSearchEntry(BaseFragment baseFragment, UItem uItem) {
        if (isValidForSearch(uItem)) {
            Entry entryFromUItem = Entry.fromUItem(baseFragment, uItem);
            if (!this.preparedEntries.containsKey(Integer.valueOf(generateGUIDForUItem(baseFragment.getClass(), uItem)))) {
                FileLog.m1157d("[Extera] Added an entry: " + entryFromUItem);
            }
            this.preparedEntries.putIfAbsent(Integer.valueOf(entryFromUItem.guid), entryFromUItem);
        }
    }

    public void addLinkAliasForOption(String str, BaseFragment baseFragment, UItem uItem) {
        if (isValidForLinkAliases(uItem)) {
            if (this.entriesStringAlias.containsKey(str)) {
                FileLog.m1157d("[Extera] Key '" + str + "' already linked to an entry.");
                return;
            }
            Entry entryFromUItem = (Entry) this.preparedEntries.get(Integer.valueOf(generateGUIDForUItem(baseFragment.getClass(), uItem)));
            if (entryFromUItem == null) {
                entryFromUItem = Entry.fromUItem(baseFragment, uItem);
            }
            FileLog.m1157d(String.format("[Extera] Added link alias %s for an entry %s", str, entryFromUItem));
            this.entriesStringAlias.put(str, entryFromUItem);
        }
    }

    public void handleLink(String str, String str2) {
        FileLog.m1157d("[Extera] Setting link handler called with alias " + str);
        if (str2 != null && !TextUtils.isEmpty(str2)) {
            PluginsController.openPluginSetting(str2, str);
            return;
        }
        createEntriesIfNeeded();
        Entry entry = (Entry) this.entriesStringAlias.get(str);
        if (entry == null) {
            onSettingNotFound();
            return;
        }
        FileLog.m1157d("[Extera] Found entry for alias: " + entry);
        FileLog.m1157d("[Extera] Opening fragment...");
        openActivity(entry.fragmentClass, Integer.valueOf(entry.itemId));
    }

    public void onSettingNotFound() {
        onSettingNotFound(LaunchActivity.getLastFragment());
    }

    public void onSettingNotFound(BaseFragment baseFragment) {
        BulletinFactory.m1267of(baseFragment).createEmojiBulletin("🤷\u200d♂️", LocaleController.getString(C2369R.string.NoSuchSetting)).show();
    }

    public String getFirstSettingLink(Class cls, UItem uItem) {
        final int iGenerateGUIDForUItem = generateGUIDForUItem(cls, uItem);
        Map.Entry entry = (Map.Entry) Collection.EL.stream(this.entriesStringAlias.entrySet()).filter(new Predicate() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda2
            public /* synthetic */ Predicate and(Predicate predicate) {
                return Predicate$CC.$default$and(this, predicate);
            }

            public /* synthetic */ Predicate negate() {
                return Predicate$CC.$default$negate(this);
            }

            /* renamed from: or */
            public /* synthetic */ Predicate m231or(Predicate predicate) {
                return Predicate$CC.$default$or(this, predicate);
            }

            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SettingsRegistry.$r8$lambda$p4BQB7CEiJF3IYJpUIO4QjqPKlE(iGenerateGUIDForUItem, (Map.Entry) obj);
            }
        }).findFirst().orElse(null);
        if (entry == null) {
            return null;
        }
        return "https://t.me/" + (ayuCategories.containsKey(cls) ? "ayuSettings" : "exteraSettings") + "?s=" + ((String) entry.getKey());
    }

    public static /* synthetic */ boolean $r8$lambda$p4BQB7CEiJF3IYJpUIO4QjqPKlE(int i, Map.Entry entry) {
        return ((Entry) entry.getValue()).guid == i;
    }

    public ProfileActivity.SearchAdapter.SearchResult[] getSearchResults(final ProfileActivity.SearchAdapter searchAdapter) {
        createEntriesIfNeeded();
        return (ProfileActivity.SearchAdapter.SearchResult[]) Collection.EL.stream(this.preparedEntries.values()).map(new Function() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda3
            public /* synthetic */ Function andThen(Function function) {
                return Function$CC.$default$andThen(this, function);
            }

            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SettingsRegistry.Entry) obj).toSearchResult(searchAdapter);
            }

            public /* synthetic */ Function compose(Function function) {
                return Function$CC.$default$compose(this, function);
            }
        }).toArray(new IntFunction() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda4
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return SettingsRegistry.$r8$lambda$Uq61hhP3TlTJmft55hT5WjpJsx0(i);
            }
        });
    }

    public static /* synthetic */ ProfileActivity.SearchAdapter.SearchResult[] $r8$lambda$Uq61hhP3TlTJmft55hT5WjpJsx0(int i) {
        return new ProfileActivity.SearchAdapter.SearchResult[i];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getCategoryIcon(Class cls) {
        return ((Integer) Objects.requireNonNullElse((Integer) categoriesIcons.get(cls), 0)).intValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BaseFragment initiateFragment(Class cls) {
        try {
            BaseFragment lastFragment = LaunchActivity.getLastFragment();
            if (lastFragment == null) {
                return null;
            }
            BaseFragment baseFragment = (BaseFragment) cls.getDeclaredConstructor(null).newInstance(null);
            baseFragment.setParentFragment(lastFragment);
            baseFragment.createActionBar(lastFragment.getContext());
            return baseFragment;
        } catch (Exception e) {
            FileLog.m1160e(e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void openActivity(Class cls, final Integer num) {
        final BaseFragment baseFragmentInitiateFragment;
        final BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null || (baseFragmentInitiateFragment = initiateFragment(cls)) == null) {
            return;
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                lastFragment.presentFragment(baseFragmentInitiateFragment);
            }
        });
        if (num == null || !(baseFragmentInitiateFragment instanceof BasePreferencesActivity)) {
            return;
        }
        final BasePreferencesActivity basePreferencesActivity = (BasePreferencesActivity) baseFragmentInitiateFragment;
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                basePreferencesActivity.scrollToItem(num.intValue());
            }
        });
    }

    private void createEntriesIfNeeded() {
        if (this.entriesFetched) {
            return;
        }
        FileLog.m1157d("[Extera] Initialising activities...");
        Iterable$EL.forEach(categoriesIcons.keySet(), new Consumer() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            /* renamed from: accept */
            public final void m971v(Object obj) {
                this.f$0.initiateFragment((Class) obj);
            }

            public /* synthetic */ Consumer andThen(Consumer consumer) {
                return Consumer$CC.$default$andThen(this, consumer);
            }
        });
        this.entriesFetched = true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int generateGUIDForUItem(Class cls, UItem uItem) {
        return Objects.hash(cls.getName(), Integer.valueOf(uItem.f2017id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class Entry extends RecordTag {
        private final Class fragmentClass;
        private final int guid;
        private final int icon;
        private final int itemId;
        private final String subtext;
        private final String title;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Entry)) {
                return false;
            }
            Entry entry = (Entry) obj;
            return this.guid == entry.guid && this.itemId == entry.itemId && this.icon == entry.icon && Objects.equals(this.title, entry.title) && Objects.equals(this.subtext, entry.subtext) && Objects.equals(this.fragmentClass, entry.fragmentClass);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{Integer.valueOf(this.guid), Integer.valueOf(this.itemId), this.title, this.subtext, Integer.valueOf(this.icon), this.fragmentClass};
        }

        private Entry(int i, int i2, String str, String str2, int i3, Class cls) {
            this.guid = i;
            this.itemId = i2;
            this.title = str;
            this.subtext = str2;
            this.icon = i3;
            this.fragmentClass = cls;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return SettingsRegistry$Entry$$ExternalSyntheticRecord0.m232m(this.guid, this.itemId, this.icon, this.title, this.subtext, this.fragmentClass);
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), Entry.class, "guid;itemId;title;subtext;icon;fragmentClass");
        }

        public static Entry fromUItem(BaseFragment baseFragment, UItem uItem) {
            Class<?> cls = baseFragment.getClass();
            CharSequence charSequence = uItem.text;
            return new Entry(SettingsRegistry.generateGUIDForUItem(cls, uItem), uItem.f2017id, charSequence == null ? null : String.valueOf(charSequence), baseFragment instanceof BasePreferencesActivity ? ((BasePreferencesActivity) baseFragment).getTitle() : null, SettingsRegistry.getInstance().getCategoryIcon(cls), cls);
        }

        public ProfileActivity.SearchAdapter.SearchResult toSearchResult(ProfileActivity.SearchAdapter searchAdapter) {
            Objects.requireNonNull(searchAdapter);
            return new ProfileActivity.SearchAdapter.SearchResult(searchAdapter, this.guid, this.title, String.valueOf(this.itemId), this.subtext, this.icon, new Runnable() { // from class: com.exteragram.messenger.preferences.utils.SettingsRegistry$Entry$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$toSearchResult$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$toSearchResult$0() {
            SettingsRegistry.getInstance().openActivity(this.fragmentClass, Integer.valueOf(this.itemId));
        }
    }
}
