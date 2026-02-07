package com.google.android.exoplayer2.text.subrip;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.common.base.Charsets;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public final class SubripDecoder extends SimpleSubtitleDecoder {
    private final ArrayList tags;
    private final StringBuilder textBuilder;
    private static final Pattern SUBRIP_TIMING_LINE = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+)(?:,(\\d+))?)\\s*");
    private static final Pattern SUBRIP_TAG_PATTERN = Pattern.compile("\\{\\\\.*?\\}");

    public SubripDecoder() {
        super("SubripDecoder");
        this.textBuilder = new StringBuilder();
        this.tags = new ArrayList();
    }

    @Override // com.google.android.exoplayer2.text.SimpleSubtitleDecoder
    protected Subtitle decode(byte[] bArr, int i, boolean z) throws NumberFormatException {
        String str;
        ArrayList arrayList = new ArrayList();
        LongArray longArray = new LongArray();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        Charset charsetDetectUtfCharset = detectUtfCharset(parsableByteArray);
        while (true) {
            String line = parsableByteArray.readLine(charsetDetectUtfCharset);
            int i2 = 0;
            if (line == null) {
                break;
            }
            if (line.length() != 0) {
                try {
                    Integer.parseInt(line);
                    String line2 = parsableByteArray.readLine(charsetDetectUtfCharset);
                    if (line2 == null) {
                        Log.m290w("SubripDecoder", "Unexpected end");
                        break;
                    }
                    Matcher matcher = SUBRIP_TIMING_LINE.matcher(line2);
                    if (!matcher.matches()) {
                        Log.m290w("SubripDecoder", "Skipping invalid timing: " + line2);
                    } else {
                        longArray.add(parseTimecode(matcher, 1));
                        longArray.add(parseTimecode(matcher, 6));
                        this.textBuilder.setLength(0);
                        this.tags.clear();
                        for (String line3 = parsableByteArray.readLine(charsetDetectUtfCharset); !TextUtils.isEmpty(line3); line3 = parsableByteArray.readLine(charsetDetectUtfCharset)) {
                            if (this.textBuilder.length() > 0) {
                                this.textBuilder.append("<br>");
                            }
                            this.textBuilder.append(processLine(line3, this.tags));
                        }
                        Spanned spannedFromHtml = Html.fromHtml(this.textBuilder.toString());
                        while (true) {
                            if (i2 >= this.tags.size()) {
                                str = null;
                                break;
                            }
                            str = (String) this.tags.get(i2);
                            if (str.matches("\\{\\\\an[1-9]\\}")) {
                                break;
                            }
                            i2++;
                        }
                        arrayList.add(buildCue(spannedFromHtml, str));
                        arrayList.add(Cue.EMPTY);
                    }
                } catch (NumberFormatException unused) {
                    Log.m290w("SubripDecoder", "Skipping invalid index: " + line);
                }
            }
        }
        return new SubripSubtitle((Cue[]) arrayList.toArray(new Cue[0]), longArray.toArray());
    }

    private Charset detectUtfCharset(ParsableByteArray parsableByteArray) {
        Charset utfCharsetFromBom = parsableByteArray.readUtfCharsetFromBom();
        return utfCharsetFromBom != null ? utfCharsetFromBom : Charsets.UTF_8;
    }

    private String processLine(String str, ArrayList arrayList) {
        String strTrim = str.trim();
        StringBuilder sb = new StringBuilder(strTrim);
        Matcher matcher = SUBRIP_TAG_PATTERN.matcher(strTrim);
        int i = 0;
        while (matcher.find()) {
            String strGroup = matcher.group();
            arrayList.add(strGroup);
            int iStart = matcher.start() - i;
            int length = strGroup.length();
            sb.replace(iStart, iStart + length, "");
            i += length;
        }
        return sb.toString();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00b6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private com.google.android.exoplayer2.text.Cue buildCue(android.text.Spanned r14, java.lang.String r15) {
        /*
            r13 = this;
            com.google.android.exoplayer2.text.Cue$Builder r0 = new com.google.android.exoplayer2.text.Cue$Builder
            r0.<init>()
            com.google.android.exoplayer2.text.Cue$Builder r14 = r0.setText(r14)
            if (r15 != 0) goto L10
            com.google.android.exoplayer2.text.Cue r14 = r14.build()
            return r14
        L10:
            int r0 = r15.hashCode()
            r1 = 2
            r2 = 1
            java.lang.String r3 = "{\\an1}"
            java.lang.String r4 = "{\\an2}"
            java.lang.String r5 = "{\\an3}"
            java.lang.String r6 = "{\\an4}"
            java.lang.String r7 = "{\\an5}"
            java.lang.String r8 = "{\\an6}"
            java.lang.String r9 = "{\\an7}"
            java.lang.String r10 = "{\\an8}"
            java.lang.String r11 = "{\\an9}"
            r12 = 0
            switch(r0) {
                case -685620710: goto L62;
                case -685620679: goto L5d;
                case -685620648: goto L53;
                case -685620617: goto L4c;
                case -685620586: goto L47;
                case -685620555: goto L40;
                case -685620524: goto L39;
                case -685620493: goto L34;
                case -685620462: goto L2d;
                default: goto L2c;
            }
        L2c:
            goto L6c
        L2d:
            boolean r0 = r15.equals(r11)
            if (r0 == 0) goto L6c
            goto L59
        L34:
            boolean r0 = r15.equals(r10)
            goto L6c
        L39:
            boolean r0 = r15.equals(r9)
            if (r0 == 0) goto L6c
            goto L68
        L40:
            boolean r0 = r15.equals(r8)
            if (r0 == 0) goto L6c
            goto L59
        L47:
            boolean r0 = r15.equals(r7)
            goto L6c
        L4c:
            boolean r0 = r15.equals(r6)
            if (r0 == 0) goto L6c
            goto L68
        L53:
            boolean r0 = r15.equals(r5)
            if (r0 == 0) goto L6c
        L59:
            r14.setPositionAnchor(r1)
            goto L6f
        L5d:
            boolean r0 = r15.equals(r4)
            goto L6c
        L62:
            boolean r0 = r15.equals(r3)
            if (r0 == 0) goto L6c
        L68:
            r14.setPositionAnchor(r12)
            goto L6f
        L6c:
            r14.setPositionAnchor(r2)
        L6f:
            int r0 = r15.hashCode()
            switch(r0) {
                case -685620710: goto Lac;
                case -685620679: goto La5;
                case -685620648: goto L9e;
                case -685620617: goto L99;
                case -685620586: goto L94;
                case -685620555: goto L8f;
                case -685620524: goto L85;
                case -685620493: goto L7e;
                case -685620462: goto L77;
                default: goto L76;
            }
        L76:
            goto Lb6
        L77:
            boolean r15 = r15.equals(r11)
            if (r15 == 0) goto Lb6
            goto L8b
        L7e:
            boolean r15 = r15.equals(r10)
            if (r15 == 0) goto Lb6
            goto L8b
        L85:
            boolean r15 = r15.equals(r9)
            if (r15 == 0) goto Lb6
        L8b:
            r14.setLineAnchor(r12)
            goto Lb9
        L8f:
            boolean r15 = r15.equals(r8)
            goto Lb6
        L94:
            boolean r15 = r15.equals(r7)
            goto Lb6
        L99:
            boolean r15 = r15.equals(r6)
            goto Lb6
        L9e:
            boolean r15 = r15.equals(r5)
            if (r15 == 0) goto Lb6
            goto Lb2
        La5:
            boolean r15 = r15.equals(r4)
            if (r15 == 0) goto Lb6
            goto Lb2
        Lac:
            boolean r15 = r15.equals(r3)
            if (r15 == 0) goto Lb6
        Lb2:
            r14.setLineAnchor(r1)
            goto Lb9
        Lb6:
            r14.setLineAnchor(r2)
        Lb9:
            int r15 = r14.getPositionAnchor()
            float r15 = getFractionalPositionForAnchorType(r15)
            com.google.android.exoplayer2.text.Cue$Builder r15 = r14.setPosition(r15)
            int r14 = r14.getLineAnchor()
            float r14 = getFractionalPositionForAnchorType(r14)
            com.google.android.exoplayer2.text.Cue$Builder r14 = r15.setLine(r14, r12)
            com.google.android.exoplayer2.text.Cue r14 = r14.build()
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.subrip.SubripDecoder.buildCue(android.text.Spanned, java.lang.String):com.google.android.exoplayer2.text.Cue");
    }

    private static long parseTimecode(Matcher matcher, int i) {
        String strGroup = matcher.group(i + 1);
        long j = (strGroup != null ? Long.parseLong(strGroup) * 3600000 : 0L) + (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 2))) * 60000) + (Long.parseLong((String) Assertions.checkNotNull(matcher.group(i + 3))) * 1000);
        String strGroup2 = matcher.group(i + 4);
        if (strGroup2 != null) {
            j += Long.parseLong(strGroup2);
        }
        return j * 1000;
    }

    static float getFractionalPositionForAnchorType(int i) {
        if (i == 0) {
            return 0.08f;
        }
        if (i == 1) {
            return 0.5f;
        }
        if (i == 2) {
            return 0.92f;
        }
        throw new IllegalArgumentException();
    }
}
