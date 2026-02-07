package p017j$.util.stream;

import java.util.function.IntFunction;
import java.util.function.LongFunction;
import p017j$.util.C1830g;
import p017j$.util.stream.IntStream;

/* renamed from: j$.util.stream.L */
/* loaded from: classes2.dex */
public final class C1911L implements IntFunction, LongFunction {

    /* renamed from: a */
    public IntFunction f1054a;

    @Override // java.util.function.IntFunction
    public Object apply(int i) {
        Object objApply = this.f1054a.apply(i);
        if (objApply == null) {
            return null;
        }
        if (objApply instanceof IntStream) {
            return IntStream.Wrapper.convert((IntStream) objApply);
        }
        if (objApply instanceof java.util.stream.IntStream) {
            return IntStream.VivifiedWrapper.convert((java.util.stream.IntStream) objApply);
        }
        C1830g.m910a(objApply.getClass(), "java.util.stream.IntStream");
        throw null;
    }

    @Override // java.util.function.LongFunction
    public Object apply(long j) {
        return AbstractC2106w1.m1087T(j, this.f1054a);
    }
}
