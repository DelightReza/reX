package org.telegram.tgnet.p022tl;

import org.telegram.tgnet.InputSerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.Vector;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes.dex */
public final /* synthetic */ class TL_stories$TL_storyItem$$ExternalSyntheticLambda0 implements Vector.TLDeserializer {
    @Override // org.telegram.tgnet.Vector.TLDeserializer
    public final TLObject deserialize(InputSerializedData inputSerializedData, int i, boolean z) {
        return TL_stories.MediaArea.TLdeserialize(inputSerializedData, i, z);
    }
}
