package com.radolyn.ayugram.utils.filters;

import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import com.radolyn.ayugram.AyuForward$ForwardChunk$$ExternalSyntheticRecord0;
import java.util.regex.Pattern;
import p017j$.util.Objects;

/* loaded from: classes4.dex */
public final class ReversiblePattern extends RecordTag {
    private final Pattern pattern;
    private final boolean reversed;

    private /* synthetic */ boolean $record$equals(Object obj) {
        if (!(obj instanceof ReversiblePattern)) {
            return false;
        }
        ReversiblePattern reversiblePattern = (ReversiblePattern) obj;
        return this.reversed == reversiblePattern.reversed && Objects.equals(this.pattern, reversiblePattern.pattern);
    }

    private /* synthetic */ Object[] $record$getFieldsAsObjects() {
        return new Object[]{this.pattern, Boolean.valueOf(this.reversed)};
    }

    public ReversiblePattern(Pattern pattern, boolean z) {
        this.pattern = pattern;
        this.reversed = z;
    }

    public final boolean equals(Object obj) {
        return $record$equals(obj);
    }

    public final int hashCode() {
        return AyuForward$ForwardChunk$$ExternalSyntheticRecord0.m464m(this.reversed, this.pattern);
    }

    public Pattern pattern() {
        return this.pattern;
    }

    public boolean reversed() {
        return this.reversed;
    }

    public final String toString() {
        return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), ReversiblePattern.class, "pattern;reversed");
    }
}
