package com.exteragram.messenger.nowplaying;

import com.exteragram.messenger.nowplaying.NowPlayingController;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.UnknownFieldException;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.encoding.CompositeDecoder;
import kotlinx.serialization.encoding.Decoder;
import kotlinx.serialization.internal.GeneratedSerializer;
import kotlinx.serialization.internal.IntSerializer;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptor;
import kotlinx.serialization.internal.PluginHelperInterfacesKt;

/* loaded from: classes3.dex */
public /* synthetic */ class NowPlayingController$ItunesSearchResponse$$serializer implements GeneratedSerializer {
    public static final NowPlayingController$ItunesSearchResponse$$serializer INSTANCE;
    private static final SerialDescriptor descriptor;

    private NowPlayingController$ItunesSearchResponse$$serializer() {
    }

    @Override // kotlinx.serialization.KSerializer, kotlinx.serialization.DeserializationStrategy
    public final SerialDescriptor getDescriptor() {
        return descriptor;
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    public /* synthetic */ KSerializer[] typeParametersSerializers() {
        return PluginHelperInterfacesKt.EMPTY_SERIALIZER_ARRAY;
    }

    static {
        NowPlayingController$ItunesSearchResponse$$serializer nowPlayingController$ItunesSearchResponse$$serializer = new NowPlayingController$ItunesSearchResponse$$serializer();
        INSTANCE = nowPlayingController$ItunesSearchResponse$$serializer;
        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = new PluginGeneratedSerialDescriptor("com.exteragram.messenger.nowplaying.NowPlayingController.ItunesSearchResponse", nowPlayingController$ItunesSearchResponse$$serializer, 2);
        pluginGeneratedSerialDescriptor.addElement("resultCount", false);
        pluginGeneratedSerialDescriptor.addElement("results", false);
        descriptor = pluginGeneratedSerialDescriptor;
    }

    @Override // kotlinx.serialization.internal.GeneratedSerializer
    public final KSerializer[] childSerializers() {
        return new KSerializer[]{IntSerializer.INSTANCE, NowPlayingController.ItunesSearchResponse.$childSerializers[1]};
    }

    @Override // kotlinx.serialization.DeserializationStrategy
    public final NowPlayingController.ItunesSearchResponse deserialize(Decoder decoder) {
        List list;
        int iDecodeIntElement;
        int i;
        Intrinsics.checkNotNullParameter(decoder, "decoder");
        SerialDescriptor serialDescriptor = descriptor;
        CompositeDecoder compositeDecoderBeginStructure = decoder.beginStructure(serialDescriptor);
        KSerializer[] kSerializerArr = NowPlayingController.ItunesSearchResponse.$childSerializers;
        if (compositeDecoderBeginStructure.decodeSequentially()) {
            iDecodeIntElement = compositeDecoderBeginStructure.decodeIntElement(serialDescriptor, 0);
            list = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 1, kSerializerArr[1], null);
            i = 3;
        } else {
            List list2 = null;
            int iDecodeIntElement2 = 0;
            int i2 = 0;
            boolean z = true;
            while (z) {
                int iDecodeElementIndex = compositeDecoderBeginStructure.decodeElementIndex(serialDescriptor);
                if (iDecodeElementIndex == -1) {
                    z = false;
                } else if (iDecodeElementIndex == 0) {
                    iDecodeIntElement2 = compositeDecoderBeginStructure.decodeIntElement(serialDescriptor, 0);
                    i2 |= 1;
                } else {
                    if (iDecodeElementIndex != 1) {
                        throw new UnknownFieldException(iDecodeElementIndex);
                    }
                    list2 = (List) compositeDecoderBeginStructure.decodeSerializableElement(serialDescriptor, 1, kSerializerArr[1], list2);
                    i2 |= 2;
                }
            }
            list = list2;
            iDecodeIntElement = iDecodeIntElement2;
            i = i2;
        }
        compositeDecoderBeginStructure.endStructure(serialDescriptor);
        return new NowPlayingController.ItunesSearchResponse(i, iDecodeIntElement, list, null);
    }
}
