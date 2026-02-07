package kotlinx.coroutines.flow;

/* loaded from: classes.dex */
public interface MutableStateFlow extends MutableSharedFlow {
    boolean compareAndSet(Object obj, Object obj2);

    Object getValue();

    void setValue(Object obj);
}
