package p017j$.util.stream;

import java.util.Iterator;
import java.util.stream.BaseStream;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import p017j$.util.C1791c0;
import p017j$.util.Spliterator;
import p017j$.util.stream.IntStream;
import p017j$.util.stream.Stream;

/* renamed from: j$.util.stream.e */
/* loaded from: classes2.dex */
public final /* synthetic */ class C2009e implements BaseStream {

    /* renamed from: a */
    public final /* synthetic */ BaseStream f1235a;

    public /* synthetic */ C2009e(BaseStream baseStream) {
        this.f1235a = baseStream;
    }

    /* renamed from: f */
    public static /* synthetic */ BaseStream m1038f(BaseStream baseStream) {
        if (baseStream == null) {
            return null;
        }
        return baseStream instanceof C2015f ? ((C2015f) baseStream).f1239a : baseStream instanceof DoubleStream ? C1861B.m937f((DoubleStream) baseStream) : baseStream instanceof IntStream ? IntStream.VivifiedWrapper.convert((java.util.stream.IntStream) baseStream) : baseStream instanceof LongStream ? C2050l0.m1047f((LongStream) baseStream) : baseStream instanceof Stream ? Stream.VivifiedWrapper.convert((java.util.stream.Stream) baseStream) : new C2009e(baseStream);
    }

    @Override // p017j$.util.stream.BaseStream, java.lang.AutoCloseable
    public final /* synthetic */ void close() {
        this.f1235a.close();
    }

    public final /* synthetic */ boolean equals(Object obj) {
        BaseStream baseStream = this.f1235a;
        if (obj instanceof C2009e) {
            obj = ((C2009e) obj).f1235a;
        }
        return baseStream.equals(obj);
    }

    public final /* synthetic */ int hashCode() {
        return this.f1235a.hashCode();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ boolean isParallel() {
        return this.f1235a.isParallel();
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Iterator iterator() {
        return this.f1235a.iterator();
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream onClose(Runnable runnable) {
        return m1038f(this.f1235a.onClose(runnable));
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream parallel() {
        return m1038f(this.f1235a.parallel());
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream sequential() {
        return m1038f(this.f1235a.sequential());
    }

    @Override // p017j$.util.stream.BaseStream, p017j$.util.stream.InterfaceC1871D
    public final /* synthetic */ Spliterator spliterator() {
        return C1791c0.m867a(this.f1235a.spliterator());
    }

    @Override // p017j$.util.stream.BaseStream
    public final /* synthetic */ BaseStream unordered() {
        return m1038f(this.f1235a.unordered());
    }
}
