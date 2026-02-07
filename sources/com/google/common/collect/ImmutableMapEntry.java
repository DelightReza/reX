package com.google.common.collect;

/* loaded from: classes.dex */
class ImmutableMapEntry extends ImmutableEntry {
    ImmutableMapEntry getNextInKeyBucket() {
        return null;
    }

    boolean isReusable() {
        return true;
    }

    static ImmutableMapEntry[] createEntryArray(int i) {
        return new ImmutableMapEntry[i];
    }

    ImmutableMapEntry(Object obj, Object obj2) {
        super(obj, obj2);
        CollectPreconditions.checkEntryNotNull(obj, obj2);
    }

    static class NonTerminalImmutableMapEntry extends ImmutableMapEntry {
        private final transient ImmutableMapEntry nextInKeyBucket;

        @Override // com.google.common.collect.ImmutableMapEntry
        final boolean isReusable() {
            return false;
        }

        NonTerminalImmutableMapEntry(Object obj, Object obj2, ImmutableMapEntry immutableMapEntry) {
            super(obj, obj2);
            this.nextInKeyBucket = immutableMapEntry;
        }

        @Override // com.google.common.collect.ImmutableMapEntry
        final ImmutableMapEntry getNextInKeyBucket() {
            return this.nextInKeyBucket;
        }
    }
}
