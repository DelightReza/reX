package com.exteragram.messenger.icons.p007ui.picker;

import com.exteragram.messenger.ExteraConfig;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.WeakHashMap;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.LaunchActivity;

/* loaded from: classes.dex */
public final class IconObserver {
    public static final IconObserver INSTANCE = new IconObserver();
    private static final WeakHashMap iconSources = new WeakHashMap();

    private IconObserver() {
    }

    public final void log(int i) {
        BaseFragment safeLastFragment;
        if (ExteraConfig.editingIconPackId == null || (safeLastFragment = LaunchActivity.getSafeLastFragment()) == null) {
            return;
        }
        WeakHashMap weakHashMap = iconSources;
        synchronized (weakHashMap) {
            try {
                Object hashSet = weakHashMap.get(safeLastFragment);
                if (hashSet == null) {
                    hashSet = new HashSet();
                    weakHashMap.put(safeLastFragment, hashSet);
                }
                ((Set) hashSet).add(Integer.valueOf(i));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeSource(BaseFragment owner) {
        Intrinsics.checkNotNullParameter(owner, "owner");
        WeakHashMap weakHashMap = iconSources;
        synchronized (weakHashMap) {
        }
    }

    public final Set getUsedIcons() {
        Set set;
        WeakHashMap weakHashMap = iconSources;
        synchronized (weakHashMap) {
            Collection collectionValues = weakHashMap.values();
            Intrinsics.checkNotNullExpressionValue(collectionValues, "<get-values>(...)");
            set = CollectionsKt.toSet(CollectionsKt.flatten(collectionValues));
        }
        return set;
    }

    public final void clear() {
        WeakHashMap weakHashMap = iconSources;
        synchronized (weakHashMap) {
            weakHashMap.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}
