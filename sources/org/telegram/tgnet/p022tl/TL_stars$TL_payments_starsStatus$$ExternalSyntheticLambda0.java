package org.telegram.tgnet.p022tl;

import org.telegram.tgnet.InputSerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p022tl.TL_stars;

/* loaded from: classes.dex */
public final /* synthetic */ class TL_stars$TL_payments_starsStatus$$ExternalSyntheticLambda0 implements Vector.TLDeserializer {
    @Override // org.telegram.tgnet.Vector.TLDeserializer
    public final TLObject deserialize(InputSerializedData inputSerializedData, int i, boolean z) {
        return TL_stars.StarsSubscription.TLdeserialize(inputSerializedData, i, z);
    }
}
