package okio;

import java.util.List;
import java.util.RandomAccess;
import kotlin.collections.AbstractList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Options extends AbstractList implements RandomAccess {
    public static final Companion Companion = new Companion(null);
    private final ByteString[] byteStrings;
    private final int[] trie;

    public /* synthetic */ Options(ByteString[] byteStringArr, int[] iArr, DefaultConstructorMarker defaultConstructorMarker) {
        this(byteStringArr, iArr);
    }

    @Override // kotlin.collections.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj instanceof ByteString) {
            return contains((ByteString) obj);
        }
        return false;
    }

    public /* bridge */ boolean contains(ByteString byteString) {
        return super.contains((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object obj) {
        if (obj instanceof ByteString) {
            return indexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int indexOf(ByteString byteString) {
        return super.indexOf((Object) byteString);
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object obj) {
        if (obj instanceof ByteString) {
            return lastIndexOf((ByteString) obj);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(ByteString byteString) {
        return super.lastIndexOf((Object) byteString);
    }

    public final ByteString[] getByteStrings$okio() {
        return this.byteStrings;
    }

    public final int[] getTrie$okio() {
        return this.trie;
    }

    private Options(ByteString[] byteStringArr, int[] iArr) {
        this.byteStrings = byteStringArr;
        this.trie = iArr;
    }

    @Override // kotlin.collections.AbstractCollection
    public int getSize() {
        return this.byteStrings.length;
    }

    @Override // kotlin.collections.AbstractList, java.util.List
    public ByteString get(int i) {
        return this.byteStrings[i];
    }

    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:43:0x00cb, code lost:
        
            continue;
         */
        /* renamed from: of */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final okio.Options m1138of(okio.ByteString... r17) {
            /*
                Method dump skipped, instructions count: 271
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: okio.Options.Companion.m1138of(okio.ByteString[]):okio.Options");
        }

        static /* synthetic */ void buildTrieRecursive$default(Companion companion, long j, Buffer buffer, int i, List list, int i2, int i3, List list2, int i4, Object obj) {
            if ((i4 & 1) != 0) {
                j = 0;
            }
            companion.buildTrieRecursive(j, buffer, (i4 & 4) != 0 ? 0 : i, list, (i4 & 16) != 0 ? 0 : i2, (i4 & 32) != 0 ? list.size() : i3, list2);
        }

        private final void buildTrieRecursive(long j, Buffer buffer, int i, List list, int i2, int i3, List list2) {
            int i4;
            int i5;
            int i6;
            long j2;
            int i7 = i;
            if (i2 >= i3) {
                throw new IllegalArgumentException("Failed requirement.");
            }
            for (int i8 = i2; i8 < i3; i8++) {
                if (((ByteString) list.get(i8)).size() < i7) {
                    throw new IllegalArgumentException("Failed requirement.");
                }
            }
            ByteString byteString = (ByteString) list.get(i2);
            ByteString byteString2 = (ByteString) list.get(i3 - 1);
            if (i7 == byteString.size()) {
                int iIntValue = ((Number) list2.get(i2)).intValue();
                int i9 = i2 + 1;
                ByteString byteString3 = (ByteString) list.get(i9);
                i4 = i9;
                i5 = iIntValue;
                byteString = byteString3;
            } else {
                i4 = i2;
                i5 = -1;
            }
            if (byteString.getByte(i7) != byteString2.getByte(i7)) {
                int i10 = 1;
                for (int i11 = i4 + 1; i11 < i3; i11++) {
                    if (((ByteString) list.get(i11 - 1)).getByte(i7) != ((ByteString) list.get(i11)).getByte(i7)) {
                        i10++;
                    }
                }
                long intCount = j + getIntCount(buffer) + 2 + (i10 * 2);
                buffer.writeInt(i10);
                buffer.writeInt(i5);
                for (int i12 = i4; i12 < i3; i12++) {
                    byte b = ((ByteString) list.get(i12)).getByte(i7);
                    if (i12 == i4 || b != ((ByteString) list.get(i12 - 1)).getByte(i7)) {
                        buffer.writeInt(b & 255);
                    }
                }
                Buffer buffer2 = new Buffer();
                while (i4 < i3) {
                    byte b2 = ((ByteString) list.get(i4)).getByte(i7);
                    int i13 = i4 + 1;
                    int i14 = i13;
                    while (true) {
                        if (i14 >= i3) {
                            i6 = i3;
                            break;
                        } else {
                            if (b2 != ((ByteString) list.get(i14)).getByte(i7)) {
                                i6 = i14;
                                break;
                            }
                            i14++;
                        }
                    }
                    if (i13 == i6 && i7 + 1 == ((ByteString) list.get(i4)).size()) {
                        buffer.writeInt(((Number) list2.get(i4)).intValue());
                        j2 = intCount;
                    } else {
                        buffer.writeInt(((int) (getIntCount(buffer2) + intCount)) * (-1));
                        j2 = intCount;
                        buildTrieRecursive(j2, buffer2, i7 + 1, list, i4, i6, list2);
                    }
                    intCount = j2;
                    i4 = i6;
                }
                buffer.writeAll(buffer2);
                return;
            }
            int iMin = Math.min(byteString.size(), byteString2.size());
            int i15 = 0;
            for (int i16 = i7; i16 < iMin && byteString.getByte(i16) == byteString2.getByte(i16); i16++) {
                i15++;
            }
            long intCount2 = j + getIntCount(buffer) + 2 + i15 + 1;
            buffer.writeInt(-i15);
            buffer.writeInt(i5);
            int i17 = i7 + i15;
            while (i7 < i17) {
                buffer.writeInt(byteString.getByte(i7) & 255);
                i7++;
            }
            if (i4 + 1 == i3) {
                if (i17 != ((ByteString) list.get(i4)).size()) {
                    throw new IllegalStateException("Check failed.");
                }
                buffer.writeInt(((Number) list2.get(i4)).intValue());
            } else {
                Buffer buffer3 = new Buffer();
                buffer.writeInt(((int) (getIntCount(buffer3) + intCount2)) * (-1));
                buildTrieRecursive(intCount2, buffer3, i17, list, i4, i3, list2);
                buffer.writeAll(buffer3);
            }
        }

        private final long getIntCount(Buffer buffer) {
            return buffer.size() / 4;
        }
    }
}
