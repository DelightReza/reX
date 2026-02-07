package p017j$.time.format;

import java.text.DateFormatSymbols;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import p017j$.com.android.tools.p018r8.AbstractC1636a;
import p017j$.time.chrono.C1668r;
import p017j$.time.chrono.InterfaceC1661k;
import p017j$.time.temporal.EnumC1727a;
import p017j$.time.temporal.InterfaceC1744r;
import p017j$.util.concurrent.ConcurrentHashMap;

/* renamed from: j$.time.format.B */
/* loaded from: classes2.dex */
public class C1681B {

    /* renamed from: a */
    public static final ConcurrentHashMap f536a = new ConcurrentHashMap(16, 0.75f, 2);

    /* renamed from: b */
    public static final C1712z f537b = new C1712z();

    /* renamed from: c */
    public static final C1681B f538c = new C1681B();

    /* renamed from: c */
    public String mo708c(InterfaceC1744r interfaceC1744r, long j, TextStyle textStyle, Locale locale) {
        Object objM706a = m706a(interfaceC1744r, locale);
        if (objM706a instanceof C1680A) {
            return ((C1680A) objM706a).m705a(j, textStyle);
        }
        return null;
    }

    /* renamed from: b */
    public String mo707b(InterfaceC1661k interfaceC1661k, InterfaceC1744r interfaceC1744r, long j, TextStyle textStyle, Locale locale) {
        if (interfaceC1661k == C1668r.f512c || !(interfaceC1744r instanceof EnumC1727a)) {
            return mo708c(interfaceC1744r, j, textStyle, locale);
        }
        return null;
    }

    /* renamed from: e */
    public Iterator mo710e(InterfaceC1744r interfaceC1744r, TextStyle textStyle, Locale locale) {
        List list;
        Object objM706a = m706a(interfaceC1744r, locale);
        if (!(objM706a instanceof C1680A) || (list = (List) ((HashMap) ((C1680A) objM706a).f535b).get(textStyle)) == null) {
            return null;
        }
        return list.iterator();
    }

    /* renamed from: d */
    public Iterator mo709d(InterfaceC1661k interfaceC1661k, InterfaceC1744r interfaceC1744r, TextStyle textStyle, Locale locale) {
        if (interfaceC1661k == C1668r.f512c || !(interfaceC1744r instanceof EnumC1727a)) {
            return mo710e(interfaceC1744r, textStyle, locale);
        }
        return null;
    }

    /* renamed from: a */
    public static Object m706a(InterfaceC1744r interfaceC1744r, Locale locale) {
        Object c1680a;
        long j;
        String strSubstring;
        AbstractMap.SimpleImmutableEntry simpleImmutableEntry = new AbstractMap.SimpleImmutableEntry(interfaceC1744r, locale);
        ConcurrentHashMap concurrentHashMap = f536a;
        V v = concurrentHashMap.get(simpleImmutableEntry);
        if (v != 0) {
            return v;
        }
        HashMap map = new HashMap();
        if (interfaceC1744r == EnumC1727a.ERA) {
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(locale);
            HashMap map2 = new HashMap();
            HashMap map3 = new HashMap();
            String[] eras = dateFormatSymbols.getEras();
            for (int i = 0; i < eras.length; i++) {
                if (!eras[i].isEmpty()) {
                    long j2 = i;
                    map2.put(Long.valueOf(j2), eras[i]);
                    Long lValueOf = Long.valueOf(j2);
                    String str = eras[i];
                    map3.put(lValueOf, str.substring(0, Character.charCount(str.codePointAt(0))));
                }
            }
            if (!map2.isEmpty()) {
                map.put(TextStyle.FULL, map2);
                map.put(TextStyle.SHORT, map2);
                map.put(TextStyle.NARROW, map3);
            }
            c1680a = new C1680A(map);
        } else {
            long j3 = 1;
            if (interfaceC1744r == EnumC1727a.MONTH_OF_YEAR) {
                int length = DateFormatSymbols.getInstance(locale).getMonths().length;
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                LinkedHashMap linkedHashMap3 = new LinkedHashMap();
                for (long j4 = 1; j4 <= length; j4++) {
                    String strM482A = AbstractC1636a.m482A(j4, "LLLL", locale);
                    linkedHashMap.put(Long.valueOf(j4), strM482A);
                    linkedHashMap2.put(Long.valueOf(j4), strM482A.substring(0, Character.charCount(strM482A.codePointAt(0))));
                    linkedHashMap3.put(Long.valueOf(j4), AbstractC1636a.m482A(j4, "LLL", locale));
                }
                if (length > 0) {
                    map.put(TextStyle.FULL_STANDALONE, linkedHashMap);
                    map.put(TextStyle.NARROW_STANDALONE, linkedHashMap2);
                    map.put(TextStyle.SHORT_STANDALONE, linkedHashMap3);
                    map.put(TextStyle.FULL, linkedHashMap);
                    map.put(TextStyle.NARROW, linkedHashMap2);
                    map.put(TextStyle.SHORT, linkedHashMap3);
                }
                c1680a = new C1680A(map);
            } else if (interfaceC1744r == EnumC1727a.DAY_OF_WEEK) {
                int length2 = DateFormatSymbols.getInstance(locale).getWeekdays().length;
                LinkedHashMap linkedHashMap4 = new LinkedHashMap();
                LinkedHashMap linkedHashMap5 = new LinkedHashMap();
                LinkedHashMap linkedHashMap6 = new LinkedHashMap();
                boolean z = locale == Locale.SIMPLIFIED_CHINESE || locale == Locale.TRADITIONAL_CHINESE;
                long j5 = 1;
                while (j5 <= length2) {
                    String strM530z = AbstractC1636a.m530z(j5, "cccc", locale);
                    linkedHashMap4.put(Long.valueOf(j5), strM530z);
                    Long lValueOf2 = Long.valueOf(j5);
                    if (!z) {
                        j = j3;
                        strSubstring = strM530z.substring(0, Character.charCount(strM530z.codePointAt(0)));
                    } else {
                        j = j3;
                        strSubstring = new StringBuilder().appendCodePoint(strM530z.codePointBefore(strM530z.length())).toString();
                    }
                    linkedHashMap5.put(lValueOf2, strSubstring);
                    linkedHashMap6.put(Long.valueOf(j5), AbstractC1636a.m530z(j5, "ccc", locale));
                    j5 += j;
                    j3 = j;
                }
                if (length2 > 0) {
                    map.put(TextStyle.FULL_STANDALONE, linkedHashMap4);
                    map.put(TextStyle.NARROW_STANDALONE, linkedHashMap5);
                    map.put(TextStyle.SHORT_STANDALONE, linkedHashMap6);
                    map.put(TextStyle.FULL, linkedHashMap4);
                    map.put(TextStyle.NARROW, linkedHashMap5);
                    map.put(TextStyle.SHORT, linkedHashMap6);
                }
                c1680a = new C1680A(map);
            } else if (interfaceC1744r == EnumC1727a.AMPM_OF_DAY) {
                DateFormatSymbols dateFormatSymbols2 = DateFormatSymbols.getInstance(locale);
                HashMap map4 = new HashMap();
                HashMap map5 = new HashMap();
                String[] amPmStrings = dateFormatSymbols2.getAmPmStrings();
                for (int i2 = 0; i2 < amPmStrings.length; i2++) {
                    if (!amPmStrings[i2].isEmpty()) {
                        long j6 = i2;
                        map4.put(Long.valueOf(j6), amPmStrings[i2]);
                        Long lValueOf3 = Long.valueOf(j6);
                        String str2 = amPmStrings[i2];
                        map5.put(lValueOf3, str2.substring(0, Character.charCount(str2.codePointAt(0))));
                    }
                }
                if (!map4.isEmpty()) {
                    map.put(TextStyle.FULL, map4);
                    map.put(TextStyle.SHORT, map4);
                    map.put(TextStyle.NARROW, map5);
                }
                c1680a = new C1680A(map);
            } else {
                c1680a = "";
            }
        }
        concurrentHashMap.putIfAbsent(simpleImmutableEntry, c1680a);
        return concurrentHashMap.get(simpleImmutableEntry);
    }
}
