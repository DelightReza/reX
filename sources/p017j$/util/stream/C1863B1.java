package p017j$.util.stream;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;

/* renamed from: j$.util.stream.B1 */
/* loaded from: classes2.dex */
public final class C1863B1 extends AbstractC2106w1 {

    /* renamed from: h */
    public final /* synthetic */ int f966h;

    /* renamed from: i */
    public final /* synthetic */ Object f967i;

    /* renamed from: j */
    public final /* synthetic */ Object f968j;

    /* renamed from: k */
    public final /* synthetic */ Object f969k;

    public /* synthetic */ C1863B1(EnumC2001c3 enumC2001c3, Object obj, Object obj2, Object obj3, int i) {
        this.f966h = i;
        this.f968j = obj;
        this.f969k = obj2;
        this.f967i = obj3;
    }

    @Override // p017j$.util.stream.AbstractC2106w1
    /* renamed from: s0 */
    public final InterfaceC1942R1 mo939s0() {
        switch (this.f966h) {
            case 0:
                return new C2116y1((Supplier) this.f967i, (ObjLongConsumer) this.f969k, (C2069p) this.f968j);
            case 1:
                return new C1878E1((Supplier) this.f967i, (ObjDoubleConsumer) this.f969k, (C2069p) this.f968j);
            case 2:
                return new C1888G1(this.f967i, (BiFunction) this.f969k, (BinaryOperator) this.f968j);
            case 3:
                return new C1908K1((Supplier) this.f967i, (BiConsumer) this.f969k, (BiConsumer) this.f968j);
            default:
                return new C1928O1((Supplier) this.f967i, (ObjIntConsumer) this.f969k, (C2069p) this.f968j);
        }
    }
}
