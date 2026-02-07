package com.radolyn.ayugram.utils.filters;

import java.util.UUID;
import java.util.regex.Pattern;
import p017j$.util.Objects;

/* loaded from: classes4.dex */
public class HashablePattern {

    /* renamed from: id */
    private final UUID f405id;
    private final ReversiblePattern pattern;

    public HashablePattern(UUID uuid, ReversiblePattern reversiblePattern) {
        this.f405id = uuid;
        this.pattern = reversiblePattern;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.f405id, ((HashablePattern) obj).f405id);
    }

    public int hashCode() {
        return Objects.hash(this.f405id);
    }

    public UUID getId() {
        return this.f405id;
    }

    public Pattern getPattern() {
        return this.pattern.pattern();
    }

    public boolean isReversed() {
        return this.pattern.reversed();
    }
}
