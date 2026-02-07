package org.telegram.PhoneFormat;

/* loaded from: classes.dex */
public class PhoneRule {
    public int byte8;
    public int flag12;
    public int flag13;
    public String format;
    public boolean hasIntlPrefix;
    public boolean hasTrunkPrefix;
    public int maxLen;
    public int maxVal;
    public int minVal;
    public int otherFlag;
    public int prefixLen;

    /* JADX WARN: Removed duplicated region for block: B:30:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    java.lang.String format(java.lang.String r13, java.lang.String r14, java.lang.String r15) {
        /*
            r12 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 20
            r0.<init>(r1)
            r1 = 0
            r2 = 0
            r3 = 0
            r4 = 0
            r5 = 0
            r6 = 0
        Ld:
            java.lang.String r7 = r12.format
            int r7 = r7.length()
            java.lang.String r8 = " "
            if (r2 >= r7) goto L95
            java.lang.String r7 = r12.format
            char r7 = r7.charAt(r2)
            r9 = 35
            if (r7 == r9) goto L7b
            r8 = 40
            r9 = 110(0x6e, float:1.54E-43)
            r10 = 99
            r11 = 1
            if (r7 == r8) goto L3d
            if (r7 == r10) goto L36
            if (r7 == r9) goto L2f
            goto L44
        L2f:
            if (r15 == 0) goto L34
            r0.append(r15)
        L34:
            r5 = 1
            goto L91
        L36:
            if (r14 == 0) goto L3b
            r0.append(r14)
        L3b:
            r3 = 1
            goto L91
        L3d:
            int r8 = r13.length()
            if (r4 >= r8) goto L44
            r6 = 1
        L44:
            r8 = 32
            if (r7 != r8) goto L60
            if (r2 <= 0) goto L60
            java.lang.String r8 = r12.format
            int r11 = r2 + (-1)
            char r8 = r8.charAt(r11)
            if (r8 != r9) goto L56
            if (r15 == 0) goto L91
        L56:
            java.lang.String r8 = r12.format
            char r8 = r8.charAt(r11)
            if (r8 != r10) goto L60
            if (r14 == 0) goto L91
        L60:
            int r8 = r13.length()
            r9 = 41
            if (r4 < r8) goto L6c
            if (r6 == 0) goto L91
            if (r7 != r9) goto L91
        L6c:
            java.lang.String r8 = r12.format
            int r10 = r2 + 1
            java.lang.String r8 = r8.substring(r2, r10)
            r0.append(r8)
            if (r7 != r9) goto L91
            r6 = 0
            goto L91
        L7b:
            int r7 = r13.length()
            if (r4 >= r7) goto L8c
            int r7 = r4 + 1
            java.lang.String r4 = r13.substring(r4, r7)
            r0.append(r4)
            r4 = r7
            goto L91
        L8c:
            if (r6 == 0) goto L91
            r0.append(r8)
        L91:
            int r2 = r2 + 1
            goto Ld
        L95:
            if (r14 == 0) goto Lac
            if (r3 != 0) goto Lac
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r14)
            r13.append(r8)
            java.lang.String r13 = r13.toString()
            r0.insert(r1, r13)
            goto Lb3
        Lac:
            if (r15 == 0) goto Lb3
            if (r5 != 0) goto Lb3
            r0.insert(r1, r15)
        Lb3:
            java.lang.String r13 = r0.toString()
            return r13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.PhoneFormat.PhoneRule.format(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }
}
