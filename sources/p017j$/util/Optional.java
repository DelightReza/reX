package p017j$.util;

import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public final class Optional<T> {

    /* renamed from: b */
    public static final Optional f777b = new Optional();

    /* renamed from: a */
    public final Object f778a;

    public Optional() {
        this.f778a = null;
    }

    public static <T> Optional<T> empty() {
        return f777b;
    }

    public Optional(Object obj) {
        this.f778a = Objects.requireNonNull(obj);
    }

    /* renamed from: of */
    public static <T> Optional<T> m856of(T t) {
        return new Optional<>(t);
    }

    public static <T> Optional<T> ofNullable(T t) {
        return t == null ? empty() : m856of(t);
    }

    public T get() {
        T t = (T) this.f778a;
        if (t != null) {
            return t;
        }
        throw new NoSuchElementException("No value present");
    }

    public boolean isPresent() {
        return this.f778a != null;
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        Objects.requireNonNull(function);
        if (!isPresent()) {
            return empty();
        }
        return ofNullable(function.apply((Object) this.f778a));
    }

    public T orElse(T t) {
        T t2 = (T) this.f778a;
        return t2 != null ? t2 : t;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: X extends java.lang.Throwable */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) throws X {
        T t = (T) this.f778a;
        if (t != null) {
            return t;
        }
        throw supplier.get();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Optional) {
            return Objects.equals(this.f778a, ((Optional) obj).f778a);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hashCode(this.f778a);
    }

    public final String toString() {
        Object obj = this.f778a;
        return obj != null ? String.format("Optional[%s]", obj) : "Optional.empty";
    }
}
