package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public abstract class Table {

    /* renamed from: bb */
    protected ByteBuffer f34bb;
    protected int bb_pos;
    Utf8 utf8 = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    protected int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f34bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    protected int __indirect(int i) {
        return i + this.f34bb.getInt(i);
    }

    protected int __vector_len(int i) {
        int i2 = i + this.bb_pos;
        return this.f34bb.getInt(i2 + this.f34bb.getInt(i2));
    }

    protected int __vector(int i) {
        int i2 = i + this.bb_pos;
        return i2 + this.f34bb.getInt(i2) + 4;
    }

    protected void __reset(int i, ByteBuffer byteBuffer) {
        this.f34bb = byteBuffer;
        if (byteBuffer != null) {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.f34bb.getShort(i2);
            return;
        }
        this.bb_pos = 0;
        this.vtable_start = 0;
        this.vtable_size = 0;
    }
}
