package kotlinx.coroutines.flow.internal;

/* loaded from: classes.dex */
public abstract class AbstractSharedFlow {
    private int nCollectors;
    private AbstractSharedFlowSlot[] slots;

    protected final AbstractSharedFlowSlot[] getSlots() {
        return this.slots;
    }

    protected final int getNCollectors() {
        return this.nCollectors;
    }
}
