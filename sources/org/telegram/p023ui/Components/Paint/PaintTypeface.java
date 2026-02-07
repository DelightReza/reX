package org.telegram.p023ui.Components.Paint;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.SystemFonts;
import android.os.Build;
import android.text.TextUtils;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;

/* loaded from: classes6.dex */
public class PaintTypeface {
    public static final List BUILT_IN_FONTS;
    public static final PaintTypeface COURIER_NEW_BOLD;
    public static final PaintTypeface IMPACT;
    public static final PaintTypeface MW_BOLD;
    public static final PaintTypeface ROBOTO_CONDENSED;
    public static final PaintTypeface ROBOTO_ITALIC;
    public static final PaintTypeface ROBOTO_MEDIUM;
    public static final PaintTypeface ROBOTO_MONO;
    public static final PaintTypeface ROBOTO_SERIF;
    public static boolean loadingTypefaces;
    private static final List preferable;
    private static List typefaces;
    private final Font font;
    private final String key;
    private final LazyTypeface lazyTypeface;
    private final String name;
    private final String nameKey;
    private final Typeface typeface;

    static {
        PaintTypeface paintTypeface = new PaintTypeface("roboto", "PhotoEditorTypefaceRoboto", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda0
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM);
            }
        }));
        ROBOTO_MEDIUM = paintTypeface;
        PaintTypeface paintTypeface2 = new PaintTypeface("italic", "PhotoEditorTypefaceItalic", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda1
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MEDIUM_ITALIC);
            }
        }));
        ROBOTO_ITALIC = paintTypeface2;
        PaintTypeface paintTypeface3 = new PaintTypeface("serif", "PhotoEditorTypefaceSerif", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda2
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return Typeface.create("serif", 1);
            }
        }));
        ROBOTO_SERIF = paintTypeface3;
        PaintTypeface paintTypeface4 = new PaintTypeface("condensed", "PhotoEditorTypefaceCondensed", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda3
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_CONDENSED_BOLD);
            }
        }));
        ROBOTO_CONDENSED = paintTypeface4;
        PaintTypeface paintTypeface5 = new PaintTypeface("mono", "PhotoEditorTypefaceMono", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda4
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MONO);
            }
        }));
        ROBOTO_MONO = paintTypeface5;
        PaintTypeface paintTypeface6 = new PaintTypeface("mw_bold", "PhotoEditorTypefaceMerriweather", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda5
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_MERRIWEATHER_BOLD);
            }
        }));
        MW_BOLD = paintTypeface6;
        PaintTypeface paintTypeface7 = new PaintTypeface("courier_new_bold", "PhotoEditorTypefaceCourierNew", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda6
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_COURIER_NEW_BOLD);
            }
        }));
        COURIER_NEW_BOLD = paintTypeface7;
        PaintTypeface paintTypeface8 = new PaintTypeface("impact", "PhotoEditorTypefaceImpact", new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda7
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return AndroidUtilities.getTypeface("fonts/impact.ttf");
            }
        }));
        IMPACT = paintTypeface8;
        BUILT_IN_FONTS = Arrays.asList(paintTypeface, paintTypeface2, paintTypeface3, paintTypeface4, paintTypeface5, paintTypeface8, paintTypeface6, paintTypeface7);
        preferable = Arrays.asList("Google Sans", "Dancing Script", "Carrois Gothic SC", "Cutive Mono", "Droid Sans Mono", "Coming Soon");
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LazyTypeface {
        private final LazyTypefaceLoader loader;
        private Typeface typeface;

        public interface LazyTypefaceLoader {
            Typeface load();
        }

        public LazyTypeface(LazyTypefaceLoader lazyTypefaceLoader) {
            this.loader = lazyTypefaceLoader;
        }

        public Typeface get() {
            if (this.typeface == null) {
                this.typeface = this.loader.load();
            }
            return this.typeface;
        }
    }

    PaintTypeface(String str, String str2, LazyTypeface lazyTypeface) {
        this.key = str;
        this.nameKey = str2;
        this.name = null;
        this.typeface = null;
        this.lazyTypeface = lazyTypeface;
        this.font = null;
    }

    PaintTypeface(final Font font, String str) {
        this.key = str;
        this.name = str;
        this.nameKey = null;
        this.typeface = null;
        this.lazyTypeface = new LazyTypeface(new LazyTypeface.LazyTypefaceLoader() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda10
            @Override // org.telegram.ui.Components.Paint.PaintTypeface.LazyTypeface.LazyTypefaceLoader
            public final Typeface load() {
                return Typeface.createFromFile(font.getFile());
            }
        });
        this.font = font;
    }

    public String getKey() {
        return this.key;
    }

    public Typeface getTypeface() {
        LazyTypeface lazyTypeface = this.lazyTypeface;
        if (lazyTypeface != null) {
            return lazyTypeface.get();
        }
        return this.typeface;
    }

    public String getName() {
        String str = this.name;
        return str != null ? str : LocaleController.getString(this.nameKey);
    }

    private static void load() {
        if (typefaces != null || loadingTypefaces) {
            return;
        }
        loadingTypefaces = true;
        Utilities.themeQueue.postRunnable(new Runnable() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                PaintTypeface.$r8$lambda$seevENs6colX4H5zOKDY6YlIPrM();
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$seevENs6colX4H5zOKDY6YlIPrM() {
        FontData font;
        final ArrayList arrayList = new ArrayList(BUILT_IN_FONTS);
        if (Build.VERSION.SDK_INT >= 29) {
            HashMap map = new HashMap();
            for (Font font2 : SystemFonts.getAvailableFonts()) {
                if (!font2.getFile().getName().contains("Noto") && (font = parseFont(font2)) != null) {
                    Family family = (Family) map.get(font.family);
                    if (family == null) {
                        family = new Family();
                        String str = font.family;
                        family.family = str;
                        map.put(str, family);
                    }
                    family.fonts.add(font);
                }
            }
            Iterator it = preferable.iterator();
            while (it.hasNext()) {
                Family family2 = (Family) map.get((String) it.next());
                if (family2 != null) {
                    FontData bold = family2.getBold();
                    if (bold == null) {
                        bold = family2.getRegular();
                    }
                    if (bold != null) {
                        arrayList.add(new PaintTypeface(bold.font, bold.getName()));
                    }
                }
            }
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.Paint.PaintTypeface$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                PaintTypeface.$r8$lambda$wqLq0YnuGRPM_WxsLs3YpX9ELIk(arrayList);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$wqLq0YnuGRPM_WxsLs3YpX9ELIk(ArrayList arrayList) {
        typefaces = arrayList;
        loadingTypefaces = false;
        NotificationCenter.getGlobalInstance().lambda$postNotificationNameOnUIThread$1(NotificationCenter.customTypefacesLoaded, new Object[0]);
    }

    public static List get() {
        List list = typefaces;
        if (list != null) {
            return list;
        }
        load();
        return BUILT_IN_FONTS;
    }

    public static PaintTypeface find(String str) {
        if (str != null && !TextUtils.isEmpty(str)) {
            List list = get();
            for (int i = 0; i < list.size(); i++) {
                PaintTypeface paintTypeface = (PaintTypeface) list.get(i);
                if (paintTypeface != null && TextUtils.equals(str, paintTypeface.key)) {
                    return paintTypeface;
                }
            }
        }
        return null;
    }

    static class Family {
        String family;
        ArrayList fonts = new ArrayList();

        Family() {
        }

        public FontData getRegular() {
            FontData fontData;
            int i = 0;
            while (true) {
                if (i >= this.fonts.size()) {
                    fontData = null;
                    break;
                }
                if ("Regular".equalsIgnoreCase(((FontData) this.fonts.get(i)).subfamily)) {
                    fontData = (FontData) this.fonts.get(i);
                    break;
                }
                i++;
            }
            return (fontData != null || this.fonts.isEmpty()) ? fontData : (FontData) this.fonts.get(0);
        }

        public FontData getBold() {
            for (int i = 0; i < this.fonts.size(); i++) {
                if ("Bold".equalsIgnoreCase(((FontData) this.fonts.get(i)).subfamily)) {
                    return (FontData) this.fonts.get(i);
                }
            }
            return null;
        }
    }

    static class FontData {
        String family;
        Font font;
        String subfamily;

        FontData() {
        }

        public String getName() {
            if ("Regular".equals(this.subfamily) || TextUtils.isEmpty(this.subfamily)) {
                return this.family;
            }
            return this.family + " " + this.subfamily;
        }
    }

    private static class NameRecord {
        final int encodingID;
        final int languageID;
        final int nameID;
        final int nameLength;
        final int platformID;
        final int stringOffset;

        public NameRecord(RandomAccessFile randomAccessFile) {
            this.platformID = randomAccessFile.readUnsignedShort();
            this.encodingID = randomAccessFile.readUnsignedShort();
            this.languageID = randomAccessFile.readUnsignedShort();
            this.nameID = randomAccessFile.readUnsignedShort();
            this.nameLength = randomAccessFile.readUnsignedShort();
            this.stringOffset = randomAccessFile.readUnsignedShort();
        }

        public String read(RandomAccessFile randomAccessFile, int i) throws IOException {
            Charset charset;
            randomAccessFile.seek(i + this.stringOffset);
            byte[] bArr = new byte[this.nameLength];
            randomAccessFile.read(bArr);
            if (this.encodingID == 1) {
                charset = StandardCharsets.UTF_16BE;
            } else {
                charset = StandardCharsets.UTF_8;
            }
            return new String(bArr, charset);
        }
    }

    private static String parseString(RandomAccessFile randomAccessFile, int i, NameRecord nameRecord) {
        if (nameRecord == null) {
            return null;
        }
        return nameRecord.read(randomAccessFile, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.telegram.ui.Components.Paint.PaintTypeface.FontData parseFont(android.graphics.fonts.Font r9) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r9 != 0) goto L4
            return r0
        L4:
            java.io.File r1 = r9.getFile()
            if (r1 != 0) goto Lb
            return r0
        Lb:
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch: java.lang.Throwable -> La3 java.lang.Exception -> La5
            java.lang.String r3 = "r"
            r2.<init>(r1, r3)     // Catch: java.lang.Throwable -> La3 java.lang.Exception -> La5
            int r1 = r2.readInt()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r3 = 65536(0x10000, float:9.1835E-41)
            if (r1 == r3) goto L23
            r3 = 1330926671(0x4f54544f, float:3.562295E9)
            if (r1 == r3) goto L23
            r2.close()     // Catch: java.lang.Exception -> L22
        L22:
            return r0
        L23:
            int r1 = r2.readUnsignedShort()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r3 = 6
            r2.skipBytes(r3)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r3 = 0
            r4 = 0
        L2d:
            if (r4 >= r1) goto L9f
            int r5 = r2.readInt()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r6 = 4
            r2.skipBytes(r6)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r6 = r2.readInt()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r2.readInt()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r7 = 1851878757(0x6e616d65, float:1.7441594E28)
            if (r5 != r7) goto L9c
            int r1 = r6 + 2
            long r4 = (long) r1     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r2.seek(r4)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r1 = r2.readUnsignedShort()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r4 = r2.readUnsignedShort()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.util.HashMap r5 = new java.util.HashMap     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r5.<init>()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
        L56:
            if (r3 >= r1) goto L6e
            org.telegram.ui.Components.Paint.PaintTypeface$NameRecord r7 = new org.telegram.ui.Components.Paint.PaintTypeface$NameRecord     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r7.<init>(r2)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r8 = r7.nameID     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r5.put(r8, r7)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r3 = r3 + 1
            goto L56
        L69:
            r9 = move-exception
            r0 = r2
            goto Lae
        L6c:
            r9 = move-exception
            goto La7
        L6e:
            org.telegram.ui.Components.Paint.PaintTypeface$FontData r1 = new org.telegram.ui.Components.Paint.PaintTypeface$FontData     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r1.<init>()     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r1.font = r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            int r6 = r6 + r4
            r9 = 1
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.Object r9 = r5.get(r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            org.telegram.ui.Components.Paint.PaintTypeface$NameRecord r9 = (org.telegram.ui.Components.Paint.PaintTypeface.NameRecord) r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.String r9 = parseString(r2, r6, r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r1.family = r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r9 = 2
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.Object r9 = r5.get(r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            org.telegram.ui.Components.Paint.PaintTypeface$NameRecord r9 = (org.telegram.ui.Components.Paint.PaintTypeface.NameRecord) r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            java.lang.String r9 = parseString(r2, r6, r9)     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r1.subfamily = r9     // Catch: java.lang.Throwable -> L69 java.lang.Exception -> L6c
            r2.close()     // Catch: java.lang.Exception -> L9b
        L9b:
            return r1
        L9c:
            int r4 = r4 + 1
            goto L2d
        L9f:
            r2.close()     // Catch: java.lang.Exception -> Lad
            goto Lad
        La3:
            r9 = move-exception
            goto Lae
        La5:
            r9 = move-exception
            r2 = r0
        La7:
            org.telegram.messenger.FileLog.m1160e(r9)     // Catch: java.lang.Throwable -> L69
            if (r2 == 0) goto Lad
            goto L9f
        Lad:
            return r0
        Lae:
            if (r0 == 0) goto Lb3
            r0.close()     // Catch: java.lang.Exception -> Lb3
        Lb3:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Paint.PaintTypeface.parseFont(android.graphics.fonts.Font):org.telegram.ui.Components.Paint.PaintTypeface$FontData");
    }
}
