package com.google.zxing.qrcode.decoder;

import com.google.zxing.common.BitMatrix;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes4.dex */
abstract class DataMask {
    private static final /* synthetic */ DataMask[] $VALUES = $values();
    public static final DataMask DATA_MASK_000;
    public static final DataMask DATA_MASK_001;
    public static final DataMask DATA_MASK_010;
    public static final DataMask DATA_MASK_011;
    public static final DataMask DATA_MASK_100;
    public static final DataMask DATA_MASK_101;
    public static final DataMask DATA_MASK_110;
    public static final DataMask DATA_MASK_111;

    abstract boolean isMasked(int i, int i2);

    private static /* synthetic */ DataMask[] $values() {
        return new DataMask[]{DATA_MASK_000, DATA_MASK_001, DATA_MASK_010, DATA_MASK_011, DATA_MASK_100, DATA_MASK_101, DATA_MASK_110, DATA_MASK_111};
    }

    private DataMask(String str, int i) {
    }

    public static DataMask valueOf(String str) {
        return (DataMask) Enum.valueOf(DataMask.class, str);
    }

    public static DataMask[] values() {
        return (DataMask[]) $VALUES.clone();
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$1 */
    enum C14461 extends DataMask {
        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return ((i + i2) & 1) == 0;
        }

        private C14461(String str, int i) {
            super(str, i);
        }
    }

    static {
        DATA_MASK_000 = new C14461("DATA_MASK_000", 0);
        DATA_MASK_001 = new C14472("DATA_MASK_001", 1);
        DATA_MASK_010 = new C14483("DATA_MASK_010", 2);
        DATA_MASK_011 = new C14494("DATA_MASK_011", 3);
        DATA_MASK_100 = new C14505("DATA_MASK_100", 4);
        DATA_MASK_101 = new C14516("DATA_MASK_101", 5);
        DATA_MASK_110 = new C14527("DATA_MASK_110", 6);
        DATA_MASK_111 = new C14538("DATA_MASK_111", 7);
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$2 */
    enum C14472 extends DataMask {
        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (i & 1) == 0;
        }

        private C14472(String str, int i) {
            super(str, i);
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$3 */
    enum C14483 extends DataMask {
        private C14483(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return i2 % 3 == 0;
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$4 */
    enum C14494 extends DataMask {
        private C14494(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (i + i2) % 3 == 0;
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$5 */
    enum C14505 extends DataMask {
        private C14505(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (((i / 2) + (i2 / 3)) & 1) == 0;
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$6 */
    enum C14516 extends DataMask {
        private C14516(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (i * i2) % 6 == 0;
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$7 */
    enum C14527 extends DataMask {
        private C14527(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (i * i2) % 6 < 3;
        }
    }

    /* renamed from: com.google.zxing.qrcode.decoder.DataMask$8 */
    enum C14538 extends DataMask {
        private C14538(String str, int i) {
            super(str, i);
        }

        @Override // com.google.zxing.qrcode.decoder.DataMask
        boolean isMasked(int i, int i2) {
            return (((i + i2) + ((i * i2) % 3)) & 1) == 0;
        }
    }

    final void unmaskBitMatrix(BitMatrix bitMatrix, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            for (int i3 = 0; i3 < i; i3++) {
                if (isMasked(i2, i3)) {
                    bitMatrix.flip(i3, i2);
                }
            }
        }
    }
}
