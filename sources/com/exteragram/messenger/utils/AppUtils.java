package com.exteragram.messenger.utils;

import android.graphics.Point;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.Keep;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Calendar;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.p023ui.ActionBar.Theme;

/* loaded from: classes.dex */
public class AppUtils {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().serializeSpecialFloatingPointValues().addSerializationExclusionStrategy(new ExclusionStrategy() { // from class: com.exteragram.messenger.utils.AppUtils.1
                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                    if (fieldAttributes.getDeclaringClass().getPackage() == null) {
                        return false;
                    }
                    String name = fieldAttributes.getDeclaringClass().getPackage().getName();
                    return name.startsWith("android.") || name.startsWith("androidx.");
                }

                @Override // com.google.gson.ExclusionStrategy
                public boolean shouldSkipClass(Class cls) {
                    if (cls.getPackage() == null) {
                        return false;
                    }
                    String name = cls.getPackage().getName();
                    return name.startsWith("android.") || name.startsWith("androidx.");
                }
            }).create();
        }
        return gson;
    }

    public static void ensureRunningOnUi(Runnable runnable) {
        if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
            AndroidUtilities.runOnUIThread(runnable);
        } else {
            runnable.run();
        }
    }

    public static int getNotificationColor() {
        int accentColor = Theme.getActiveTheme().hasAccentColors() ? Theme.getActiveTheme().getAccentColor(Theme.getActiveTheme().currentAccentId) : 0;
        if (accentColor == 0) {
            accentColor = Theme.getColor(Theme.key_actionBarDefault) | (-16777216);
        }
        float fComputePerceivedBrightness = AndroidUtilities.computePerceivedBrightness(accentColor);
        return (fComputePerceivedBrightness >= 0.721f || fComputePerceivedBrightness <= 0.279f) ? Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader) | (-16777216) : accentColor;
    }

    public static int[] getDrawerIconPack() {
        int eventType = Theme.getEventType();
        if (eventType == 0) {
            return new int[]{C2369R.drawable.msg_groups_ny, C2369R.drawable.msg_secret_ny, C2369R.drawable.msg_channel_ny, C2369R.drawable.msg_contacts_ny, C2369R.drawable.msg_calls_ny, C2369R.drawable.msg_saved_ny};
        }
        if (eventType == 1) {
            return new int[]{C2369R.drawable.msg_groups_14, C2369R.drawable.msg_secret_14, C2369R.drawable.msg_channel_14, C2369R.drawable.msg_contacts_14, C2369R.drawable.msg_calls_14, C2369R.drawable.msg_saved_14};
        }
        if (eventType == 2) {
            return new int[]{C2369R.drawable.msg_groups_hw, C2369R.drawable.msg_secret_hw, C2369R.drawable.msg_channel_hw, C2369R.drawable.msg_contacts_hw, C2369R.drawable.msg_calls_hw, C2369R.drawable.msg_saved_hw};
        }
        return new int[]{C2369R.drawable.msg_groups, C2369R.drawable.msg_secret, C2369R.drawable.msg_channel, C2369R.drawable.msg_contacts, C2369R.drawable.msg_calls, C2369R.drawable.msg_saved};
    }

    public static boolean isWinter() {
        int i = Calendar.getInstance().get(2);
        return i == 11 || i == 0 || i == 1;
    }

    public static int getSwipeVelocity() {
        Point point = AndroidUtilities.displaySize;
        return point.x > point.y ? 1250 : 850;
    }

    @Keep
    public static void log(String str) {
        logInternal(str, null, 5);
    }

    @Keep
    public static void log(Throwable th) {
        logInternal("", th, 5);
    }

    @Keep
    public static void log(String str, Throwable th) {
        logInternal(str, th, 5);
    }

    private static void logInternal(String str, Throwable th, int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = stackTrace[Math.max(3, Math.min(i, stackTrace.length - 1))];
        String className = stackTraceElement.getClassName();
        if (className.contains(".")) {
            className = className.substring(className.lastIndexOf(46) + 1);
        }
        if (className.contains("$")) {
            className = className.substring(className.lastIndexOf(36) + 1);
        }
        String str2 = "[" + className + "]";
        String str3 = String.format("[%s] %s", stackTraceElement.getMethodName(), str);
        if (th != null) {
            Log.e(str2, str3, th);
        } else {
            Log.d(str2, str3);
        }
    }

    @Keep
    public static void printObjectDetails(Object obj) {
        if (obj == null) {
            return;
        }
        try {
            logInternal(obj.getClass().getName() + ": " + getGson().toJson(obj), null, 6);
        } catch (Exception e) {
            logInternal(obj.getClass().getName(), e, 6);
        }
    }

    @Keep
    public static Object getPrivateField(Object obj, String str) throws NoSuchFieldException, SecurityException {
        try {
            Class<?> superclass = obj.getClass();
            Field declaredField = null;
            while (superclass != null) {
                try {
                    declaredField = superclass.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    superclass = superclass.getSuperclass();
                }
            }
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return declaredField.get(obj);
            }
        } catch (Exception e) {
            logInternal(obj.getClass().getName(), e, 6);
        }
        return null;
    }

    @Keep
    public static void setPrivateField(Object obj, String str, Object obj2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        try {
            Class<?> superclass = obj.getClass();
            Field declaredField = null;
            while (superclass != null) {
                try {
                    declaredField = superclass.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    superclass = superclass.getSuperclass();
                }
            }
            if (declaredField != null) {
                declaredField.setAccessible(true);
                declaredField.set(obj, obj2);
            }
        } catch (Exception e) {
            logInternal(obj.getClass().getName(), e, 6);
        }
    }

    @Keep
    public static Object getPrivateStaticField(Class<?> cls, String str) throws NoSuchFieldException, SecurityException {
        Class<?> superclass = cls;
        Field declaredField = null;
        while (superclass != null) {
            try {
                try {
                    declaredField = superclass.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    superclass = superclass.getSuperclass();
                }
            } catch (Exception e) {
                logInternal(cls.getName(), e, 6);
            }
        }
        if (declaredField != null) {
            declaredField.setAccessible(true);
            return declaredField.get(null);
        }
        return null;
    }

    @Keep
    public static void setPrivateStaticField(Class<?> cls, String str, Object obj) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        Class<?> superclass = cls;
        Field declaredField = null;
        while (superclass != null) {
            try {
                try {
                    declaredField = superclass.getDeclaredField(str);
                } catch (NoSuchFieldException unused) {
                    superclass = superclass.getSuperclass();
                }
            } catch (Exception e) {
                logInternal(cls.getName(), e, 6);
                return;
            }
        }
        if (declaredField != null) {
            declaredField.setAccessible(true);
            declaredField.set(null, obj);
        }
    }

    public static String stackTraceToString(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
