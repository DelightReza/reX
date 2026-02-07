package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectSpliterators;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import p017j$.util.Spliterator;
import p017j$.util.function.Consumer$CC;
import p017j$.util.function.IntConsumer$CC;
import p017j$.util.stream.IntStream;

/* loaded from: classes4.dex */
abstract class CollectSpliterators {
    static Spliterator indexed(int i, int i2, IntFunction intFunction) {
        return indexed(i, i2, intFunction, null);
    }

    static Spliterator indexed(int i, int i2, IntFunction intFunction, Comparator comparator) {
        if (comparator != null) {
            Preconditions.checkArgument((i2 & 4) != 0);
        }
        return new C1WithCharacteristics(IntStream.CC.range(0, i).spliterator(), intFunction, i2, comparator);
    }

    /* renamed from: com.google.common.collect.CollectSpliterators$1WithCharacteristics, reason: invalid class name */
    /* loaded from: classes.dex */
    class C1WithCharacteristics implements Spliterator {
        private final Spliterator.OfInt delegate;
        final /* synthetic */ Comparator val$comparator;
        final /* synthetic */ int val$extraCharacteristics;
        final /* synthetic */ IntFunction val$function;

        @Override // p017j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        C1WithCharacteristics(Spliterator.OfInt ofInt, IntFunction intFunction, int i, Comparator comparator) {
            this.val$function = intFunction;
            this.val$extraCharacteristics = i;
            this.val$comparator = comparator;
            this.delegate = ofInt;
        }

        @Override // p017j$.util.Spliterator
        public boolean tryAdvance(final Consumer consumer) {
            Spliterator.OfInt ofInt = this.delegate;
            final IntFunction intFunction = this.val$function;
            return ofInt.tryAdvance(new IntConsumer() { // from class: com.google.common.collect.CollectSpliterators$1WithCharacteristics$$ExternalSyntheticLambda1
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    consumer.m971v(intFunction.apply(i));
                }

                public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                    return IntConsumer$CC.$default$andThen(this, intConsumer);
                }
            });
        }

        @Override // p017j$.util.Spliterator
        public void forEachRemaining(final Consumer consumer) {
            Spliterator.OfInt ofInt = this.delegate;
            final IntFunction intFunction = this.val$function;
            ofInt.forEachRemaining(new IntConsumer() { // from class: com.google.common.collect.CollectSpliterators$1WithCharacteristics$$ExternalSyntheticLambda0
                @Override // java.util.function.IntConsumer
                public final void accept(int i) {
                    consumer.m971v(intFunction.apply(i));
                }

                public /* synthetic */ IntConsumer andThen(IntConsumer intConsumer) {
                    return IntConsumer$CC.$default$andThen(this, intConsumer);
                }
            });
        }

        @Override // p017j$.util.Spliterator
        public Spliterator trySplit() {
            Spliterator.OfInt ofIntTrySplit = this.delegate.trySplit();
            if (ofIntTrySplit == null) {
                return null;
            }
            return new C1WithCharacteristics(ofIntTrySplit, this.val$function, this.val$extraCharacteristics, this.val$comparator);
        }

        @Override // p017j$.util.Spliterator
        public long estimateSize() {
            return this.delegate.estimateSize();
        }

        @Override // p017j$.util.Spliterator
        public int characteristics() {
            return this.val$extraCharacteristics | 16464;
        }

        @Override // p017j$.util.Spliterator
        public Comparator getComparator() {
            if (hasCharacteristics(4)) {
                return this.val$comparator;
            }
            throw new IllegalStateException();
        }
    }

    static Spliterator map(Spliterator spliterator, Function function) {
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(function);
        return new C12511(spliterator, function);
    }

    /* renamed from: com.google.common.collect.CollectSpliterators$1 */
    /* loaded from: classes.dex */
    class C12511 implements Spliterator {
        final /* synthetic */ Spliterator val$fromSpliterator;
        final /* synthetic */ Function val$function;

        @Override // p017j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        C12511(Spliterator spliterator, Function function) {
            this.val$fromSpliterator = spliterator;
            this.val$function = function;
        }

        @Override // p017j$.util.Spliterator
        public boolean tryAdvance(final Consumer consumer) {
            Spliterator spliterator = this.val$fromSpliterator;
            final Function function = this.val$function;
            return spliterator.tryAdvance(new Consumer() { // from class: com.google.common.collect.CollectSpliterators$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    consumer.m971v(function.apply(obj));
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }
            });
        }

        @Override // p017j$.util.Spliterator
        public void forEachRemaining(final Consumer consumer) {
            Spliterator spliterator = this.val$fromSpliterator;
            final Function function = this.val$function;
            spliterator.forEachRemaining(new Consumer() { // from class: com.google.common.collect.CollectSpliterators$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    consumer.m971v(function.apply(obj));
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }
            });
        }

        @Override // p017j$.util.Spliterator
        public Spliterator trySplit() {
            Spliterator spliteratorTrySplit = this.val$fromSpliterator.trySplit();
            if (spliteratorTrySplit != null) {
                return CollectSpliterators.map(spliteratorTrySplit, this.val$function);
            }
            return null;
        }

        @Override // p017j$.util.Spliterator
        public long estimateSize() {
            return this.val$fromSpliterator.estimateSize();
        }

        @Override // p017j$.util.Spliterator
        public int characteristics() {
            return this.val$fromSpliterator.characteristics() & (-262);
        }
    }

    static Spliterator filter(Spliterator spliterator, Predicate predicate) {
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(predicate);
        return new C1Splitr(spliterator, predicate);
    }

    /* renamed from: com.google.common.collect.CollectSpliterators$1Splitr, reason: invalid class name */
    /* loaded from: classes.dex */
    class C1Splitr implements Spliterator, Consumer {
        Object holder = null;
        final /* synthetic */ Spliterator val$fromSpliterator;
        final /* synthetic */ Predicate val$predicate;

        public /* synthetic */ Consumer andThen(Consumer consumer) {
            return Consumer$CC.$default$andThen(this, consumer);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ void forEachRemaining(Consumer consumer) {
            Spliterator.CC.$default$forEachRemaining(this, consumer);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        C1Splitr(Spliterator spliterator, Predicate predicate) {
            this.val$fromSpliterator = spliterator;
            this.val$predicate = predicate;
        }

        @Override // java.util.function.Consumer
        /* renamed from: accept */
        public void m971v(Object obj) {
            this.holder = obj;
        }

        @Override // p017j$.util.Spliterator
        public boolean tryAdvance(Consumer consumer) {
            while (this.val$fromSpliterator.tryAdvance(this)) {
                try {
                    Object objUncheckedCastNullableTToT = NullnessCasts.uncheckedCastNullableTToT(this.holder);
                    if (this.val$predicate.test(objUncheckedCastNullableTToT)) {
                        consumer.m971v(objUncheckedCastNullableTToT);
                        this.holder = null;
                        return true;
                    }
                } finally {
                    this.holder = null;
                }
            }
            return false;
        }

        @Override // p017j$.util.Spliterator
        public Spliterator trySplit() {
            Spliterator spliteratorTrySplit = this.val$fromSpliterator.trySplit();
            if (spliteratorTrySplit == null) {
                return null;
            }
            return CollectSpliterators.filter(spliteratorTrySplit, this.val$predicate);
        }

        @Override // p017j$.util.Spliterator
        public long estimateSize() {
            return this.val$fromSpliterator.estimateSize() / 2;
        }

        @Override // p017j$.util.Spliterator
        public Comparator getComparator() {
            return this.val$fromSpliterator.getComparator();
        }

        @Override // p017j$.util.Spliterator
        public int characteristics() {
            return this.val$fromSpliterator.characteristics() & 277;
        }
    }

    static Spliterator flatMap(Spliterator spliterator, Function function, int i, long j) {
        Preconditions.checkArgument((i & 16384) == 0, "flatMap does not support SUBSIZED characteristic");
        Preconditions.checkArgument((i & 4) == 0, "flatMap does not support SORTED characteristic");
        Preconditions.checkNotNull(spliterator);
        Preconditions.checkNotNull(function);
        return new FlatMapSpliteratorOfObject(null, spliterator, function, i, j);
    }

    /* loaded from: classes.dex */
    static abstract class FlatMapSpliterator implements Spliterator {
        int characteristics;
        long estimatedSize;
        final Factory factory;
        final Spliterator from;
        final Function function;
        Spliterator prefix;

        /* loaded from: classes4.dex */
        interface Factory {
            Spliterator newFlatMapSpliterator(Spliterator spliterator, Spliterator spliterator2, Function function, int i, long j);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ Comparator getComparator() {
            return Spliterator.CC.$default$getComparator(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ long getExactSizeIfKnown() {
            return Spliterator.CC.$default$getExactSizeIfKnown(this);
        }

        @Override // p017j$.util.Spliterator
        public /* synthetic */ boolean hasCharacteristics(int i) {
            return Spliterator.CC.$default$hasCharacteristics(this, i);
        }

        FlatMapSpliterator(Spliterator spliterator, Spliterator spliterator2, Function function, Factory factory, int i, long j) {
            this.prefix = spliterator;
            this.from = spliterator2;
            this.function = function;
            this.factory = factory;
            this.characteristics = i;
            this.estimatedSize = j;
        }

        @Override // p017j$.util.Spliterator
        public final boolean tryAdvance(Consumer consumer) {
            do {
                Spliterator spliterator = this.prefix;
                if (spliterator != null && spliterator.tryAdvance(consumer)) {
                    long j = this.estimatedSize;
                    if (j == Long.MAX_VALUE) {
                        return true;
                    }
                    this.estimatedSize = j - 1;
                    return true;
                }
                this.prefix = null;
            } while (this.from.tryAdvance(new Consumer() { // from class: com.google.common.collect.CollectSpliterators$FlatMapSpliterator$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    CollectSpliterators.FlatMapSpliterator flatMapSpliterator = this.f$0;
                    flatMapSpliterator.prefix = (Spliterator) flatMapSpliterator.function.apply(obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }
            }));
            return false;
        }

        @Override // p017j$.util.Spliterator
        public final void forEachRemaining(final Consumer consumer) {
            Spliterator spliterator = this.prefix;
            if (spliterator != null) {
                spliterator.forEachRemaining(consumer);
                this.prefix = null;
            }
            this.from.forEachRemaining(new Consumer() { // from class: com.google.common.collect.CollectSpliterators$FlatMapSpliterator$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                /* renamed from: accept */
                public final void m971v(Object obj) {
                    CollectSpliterators.FlatMapSpliterator.$r8$lambda$p9ptQIkLXWjRnMw2XxQkmK61RTg(this.f$0, consumer, obj);
                }

                public /* synthetic */ Consumer andThen(Consumer consumer2) {
                    return Consumer$CC.$default$andThen(this, consumer2);
                }
            });
            this.estimatedSize = 0L;
        }

        public static /* synthetic */ void $r8$lambda$p9ptQIkLXWjRnMw2XxQkmK61RTg(FlatMapSpliterator flatMapSpliterator, Consumer consumer, Object obj) {
            Spliterator spliterator = (Spliterator) flatMapSpliterator.function.apply(obj);
            if (spliterator != null) {
                spliterator.forEachRemaining(consumer);
            }
        }

        @Override // p017j$.util.Spliterator
        public final Spliterator trySplit() {
            Spliterator spliteratorTrySplit = this.from.trySplit();
            if (spliteratorTrySplit != null) {
                int i = this.characteristics & (-65);
                long jEstimateSize = estimateSize();
                if (jEstimateSize < Long.MAX_VALUE) {
                    jEstimateSize /= 2;
                    this.estimatedSize -= jEstimateSize;
                    this.characteristics = i;
                }
                Spliterator spliteratorNewFlatMapSpliterator = this.factory.newFlatMapSpliterator(this.prefix, spliteratorTrySplit, this.function, i, jEstimateSize);
                this.prefix = null;
                return spliteratorNewFlatMapSpliterator;
            }
            Spliterator spliterator = this.prefix;
            if (spliterator == null) {
                return null;
            }
            this.prefix = null;
            return spliterator;
        }

        @Override // p017j$.util.Spliterator
        public final long estimateSize() {
            Spliterator spliterator = this.prefix;
            if (spliterator != null) {
                this.estimatedSize = Math.max(this.estimatedSize, spliterator.estimateSize());
            }
            return Math.max(this.estimatedSize, 0L);
        }

        @Override // p017j$.util.Spliterator
        public final int characteristics() {
            return this.characteristics;
        }
    }

    static final class FlatMapSpliteratorOfObject extends FlatMapSpliterator {
        FlatMapSpliteratorOfObject(Spliterator spliterator, Spliterator spliterator2, Function function, int i, long j) {
            super(spliterator, spliterator2, function, new FlatMapSpliterator.Factory() { // from class: com.google.common.collect.CollectSpliterators$FlatMapSpliteratorOfObject$$ExternalSyntheticLambda0
                @Override // com.google.common.collect.CollectSpliterators.FlatMapSpliterator.Factory
                public final Spliterator newFlatMapSpliterator(Spliterator spliterator3, Spliterator spliterator4, Function function2, int i2, long j2) {
                    return new CollectSpliterators.FlatMapSpliteratorOfObject(spliterator3, spliterator4, function2, i2, j2);
                }
            }, i, j);
        }
    }
}
