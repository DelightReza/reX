package org.telegram.p023ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import com.exteragram.messenger.plugins.PluginsConstants;
import com.exteragram.messenger.utils.AppUtils;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;

/* loaded from: classes.dex */
public abstract class LauncherIconController {
    public static void tryFixLauncherIconIfNeeded() {
        for (LauncherIcon launcherIcon : LauncherIcon.values()) {
            if (isEnabled(launcherIcon)) {
                LauncherIcon launcherIcon2 = LauncherIcon.MONET;
                if (launcherIcon == launcherIcon2) {
                    setIcon(LauncherIcon.DEFAULT);
                    setIcon(launcherIcon2);
                    return;
                }
                LauncherIcon launcherIcon3 = LauncherIcon.MONET_EXTERA;
                if (launcherIcon == launcherIcon3) {
                    setIcon(LauncherIcon.DEFAULT);
                    setIcon(launcherIcon3);
                    return;
                }
                return;
            }
        }
        setIcon(LauncherIcon.DEFAULT);
    }

    public static boolean isEnabled(LauncherIcon launcherIcon) {
        Context context = ApplicationLoader.applicationContext;
        int componentEnabledSetting = context.getPackageManager().getComponentEnabledSetting(launcherIcon.getComponentName(context));
        return componentEnabledSetting == 1 || (componentEnabledSetting == 0 && launcherIcon == LauncherIcon.DEFAULT);
    }

    public static void setIcon(LauncherIcon launcherIcon) {
        Context context = ApplicationLoader.applicationContext;
        PackageManager packageManager = context.getPackageManager();
        LauncherIcon[] launcherIconArrValues = LauncherIcon.values();
        int length = launcherIconArrValues.length;
        for (int i = 0; i < length; i++) {
            LauncherIcon launcherIcon2 = launcherIconArrValues[i];
            packageManager.setComponentEnabledSetting(launcherIcon2.getComponentName(context), launcherIcon2 == launcherIcon ? 1 : 2, 1);
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'MONET' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    public static final class LauncherIcon {
        private static final /* synthetic */ LauncherIcon[] $VALUES;
        public static final LauncherIcon AMETHYST;
        public static final LauncherIcon AURORA;
        public static final LauncherIcon AYUGRAM_ALT;
        public static final LauncherIcon AYUGRAM_BARD;
        public static final LauncherIcon AYUGRAM_DISCORD;
        public static final LauncherIcon AYUGRAM_EXTERA;
        public static final LauncherIcon AYUGRAM_NOTHING;
        public static final LauncherIcon AYUGRAM_SPOTIFY;
        public static final LauncherIcon AYUGRAM_YAPLUS;
        public static final LauncherIcon AYUTYAN;
        public static final LauncherIcon CYBERPUNK;
        public static final LauncherIcon DEFAULT = new LauncherIcon(PluginsConstants.Strategy.DEFAULT, 0, "DefaultIcon", C2369R.drawable.ic_background, C2369R.drawable.ic_foreground, C2369R.string.IconDefault);
        public static final LauncherIcon DSGN480;
        public static final LauncherIcon EDITOR;
        public static final LauncherIcon EXTERA;
        public static final LauncherIcon GOOGLE;
        public static final LauncherIcon ICEAGE;
        public static final LauncherIcon INVINCIBLE;
        public static final LauncherIcon MONET;
        public static final LauncherIcon MONET_EXTERA;
        public static final LauncherIcon ORBIT;
        public static final LauncherIcon SAPPHIRE;
        public static final LauncherIcon SPACE;
        public static final LauncherIcon SUNSET;
        public static final LauncherIcon SUS;
        public static final LauncherIcon WINTER;
        public final int background;
        private ComponentName componentName;
        public final int foreground;
        public final boolean hidden;
        public final String key;
        public final boolean premium;
        public final int title;

        private static /* synthetic */ LauncherIcon[] $values() {
            return new LauncherIcon[]{DEFAULT, MONET, AYUGRAM_ALT, AYUGRAM_DISCORD, AYUGRAM_SPOTIFY, AYUGRAM_EXTERA, AYUGRAM_NOTHING, AYUGRAM_BARD, AYUGRAM_YAPLUS, AYUTYAN, EXTERA, MONET_EXTERA, WINTER, ORBIT, AURORA, SUNSET, ICEAGE, EDITOR, SPACE, SAPPHIRE, AMETHYST, DSGN480, CYBERPUNK, GOOGLE, INVINCIBLE, SUS};
        }

        public static LauncherIcon valueOf(String str) {
            return (LauncherIcon) Enum.valueOf(LauncherIcon.class, str);
        }

        public static LauncherIcon[] values() {
            return (LauncherIcon[]) $VALUES.clone();
        }

        static {
            int i = C2369R.color.ic_background_monet;
            int i2 = C2369R.drawable.ic_foreground_monet;
            int i3 = C2369R.string.AppIconMonet;
            int i4 = Build.VERSION.SDK_INT;
            MONET = new LauncherIcon("MONET", 1, "MonetIcon", i, i2, i3, i4 < 31);
            AYUGRAM_ALT = new LauncherIcon("AYUGRAM_ALT", 2, "AyuGramAlt", C2369R.drawable.ic_background_alt, C2369R.drawable.ic_foreground_alt, C2369R.string.IconAlternative);
            AYUGRAM_DISCORD = new LauncherIcon("AYUGRAM_DISCORD", 3, "AyuGramDiscord", C2369R.drawable.ic_background_discord, C2369R.drawable.ic_foreground_discord, C2369R.string.IconDiscord);
            AYUGRAM_SPOTIFY = new LauncherIcon("AYUGRAM_SPOTIFY", 4, "AyuGramSpotify", C2369R.drawable.ic_background_spotify, C2369R.drawable.ic_foreground_spotify, C2369R.string.IconSpotify);
            AYUGRAM_EXTERA = new LauncherIcon("AYUGRAM_EXTERA", 5, "AyuGramExtera", C2369R.drawable.ic_background_ayuextera, C2369R.drawable.ic_foreground_ayuextera, C2369R.string.IconExtera);
            AYUGRAM_NOTHING = new LauncherIcon("AYUGRAM_NOTHING", 6, "AyuGramNothing", C2369R.color.white, C2369R.drawable.ic_foreground_nothing, C2369R.string.IconNothing);
            AYUGRAM_BARD = new LauncherIcon("AYUGRAM_BARD", 7, "AyuGramBard", C2369R.drawable.ic_background_bard, C2369R.drawable.ic_foreground_bard, C2369R.string.IconBard);
            AYUGRAM_YAPLUS = new LauncherIcon("AYUGRAM_YAPLUS", 8, "AyuGramYaPlus", C2369R.drawable.ic_background_yaplus, C2369R.drawable.ic_foreground_yaplus, C2369R.string.IconYaPlus);
            AYUTYAN = new LauncherIcon("AYUTYAN", 9, "AyuTyan", C2369R.color.ic_launcher_ayutyan_background, C2369R.mipmap.ic_launcher_ayutyan_foreground, C2369R.string.IconAyuTyan);
            EXTERA = new LauncherIcon("EXTERA", 10, "ExteraIcon", C2369R.color.ic_background_extera, C2369R.mipmap.ic_launcher_foreground, C2369R.string.IconExtera);
            MONET_EXTERA = new LauncherIcon("MONET_EXTERA", 11, "MonetIconExtera", C2369R.color.ic_background_monet, C2369R.drawable.ic_foreground_monet_extera, C2369R.string.AppIconMonet, i4 < 31);
            WINTER = new LauncherIcon("WINTER", 12, "WinterIcon", C2369R.mipmap.ic_launcher_winter_background, C2369R.mipmap.ic_launcher_foreground, C2369R.string.AppIconWinter, !AppUtils.isWinter());
            ORBIT = new LauncherIcon("ORBIT", 13, "OrbitIcon", C2369R.color.ic_background, C2369R.mipmap.ic_launcher_orbit_foreground, C2369R.string.AppIconOrbit);
            AURORA = new LauncherIcon("AURORA", 14, "AuroraIcon", C2369R.mipmap.ic_launcher_aurora_background, C2369R.mipmap.ic_launcher_aurora_foreground, C2369R.string.AppIconAurora);
            SUNSET = new LauncherIcon("SUNSET", 15, "SunsetIcon", C2369R.mipmap.ic_launcher_sunset_background, C2369R.mipmap.ic_launcher_sunset_foreground, C2369R.string.AppIconSunset);
            ICEAGE = new LauncherIcon("ICEAGE", 16, "IceAgeIcon", C2369R.mipmap.ic_launcher_ice_age_background, C2369R.mipmap.ic_launcher_ice_age_foreground, C2369R.string.AppIconIceAge);
            EDITOR = new LauncherIcon("EDITOR", 17, "EditorIcon", C2369R.mipmap.ic_launcher_editor_background, C2369R.mipmap.ic_launcher_editor_foreground, C2369R.string.AppIconEditor);
            SPACE = new LauncherIcon("SPACE", 18, "SpaceIcon", C2369R.mipmap.ic_launcher_space_background, C2369R.mipmap.ic_launcher_space_foreground, C2369R.string.AppIconSpace);
            SAPPHIRE = new LauncherIcon("SAPPHIRE", 19, "SapphireIcon", C2369R.mipmap.ic_launcher_sapphire_background, C2369R.mipmap.ic_launcher_sapphire_foreground, C2369R.string.AppIconSapphire);
            AMETHYST = new LauncherIcon("AMETHYST", 20, "AmethystIcon", C2369R.mipmap.ic_launcher_amethyst_background, C2369R.mipmap.ic_launcher_amethyst_foreground, C2369R.string.AppIconAmethyst);
            DSGN480 = new LauncherIcon("DSGN480", 21, "Dsgn480Icon", C2369R.mipmap.ic_launcher_480dsgn_background, C2369R.mipmap.ic_launcher_480dsgn_foreground, C2369R.string.AppIcon480DSGN);
            CYBERPUNK = new LauncherIcon("CYBERPUNK", 22, "CyberpunkIcon", C2369R.color.ic_background_cyberpunk, C2369R.mipmap.ic_launcher_cyberpunk_foreground, C2369R.string.AppIconCyberpunk);
            GOOGLE = new LauncherIcon("GOOGLE", 23, "GoogleIcon", C2369R.color.white, C2369R.mipmap.ic_launcher_google_foreground, C2369R.string.AppIconGoogle);
            INVINCIBLE = new LauncherIcon("INVINCIBLE", 24, "InvincibleIcon", C2369R.mipmap.ic_launcher_invincible_background, C2369R.mipmap.ic_launcher_invincible_foreground, C2369R.string.AppIconInvincible);
            SUS = new LauncherIcon("SUS", 25, "SusIcon", C2369R.color.ic_background_sus, C2369R.mipmap.ic_launcher_sus_foreground, C2369R.string.AppIconSus);
            $VALUES = $values();
        }

        public ComponentName getComponentName(Context context) {
            if (this.componentName == null) {
                this.componentName = new ComponentName(context.getPackageName(), "com.exteragram.messenger." + this.key);
            }
            return this.componentName;
        }

        private LauncherIcon(String str, int i, String str2, int i2, int i3, int i4) {
            this(str, i, str2, i2, i3, i4, false);
        }

        private LauncherIcon(String str, int i, String str2, int i2, int i3, int i4, boolean z) {
            this.key = str2;
            this.background = i2;
            this.foreground = i3;
            this.title = i4;
            this.premium = false;
            this.hidden = z;
        }
    }
}
