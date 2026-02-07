package com.google.common.base;

import p017j$.util.function.Predicate$CC;

/* loaded from: classes.dex */
public abstract class CharMatcher implements Predicate {
    public /* synthetic */ java.util.function.Predicate and(java.util.function.Predicate predicate) {
        return Predicate$CC.$default$and(this, predicate);
    }

    public abstract boolean matches(char c);

    public /* synthetic */ java.util.function.Predicate negate() {
        return Predicate$CC.$default$negate(this);
    }

    /* renamed from: or */
    public /* synthetic */ java.util.function.Predicate m428or(java.util.function.Predicate predicate) {
        return Predicate$CC.$default$or(this, predicate);
    }

    @Override // com.google.common.base.Predicate, java.util.function.Predicate
    public /* synthetic */ boolean test(Object obj) {
        return apply(obj);
    }

    public static CharMatcher any() {
        return Any.INSTANCE;
    }

    public static CharMatcher none() {
        return None.INSTANCE;
    }

    /* renamed from: is */
    public static CharMatcher m427is(char c) {
        return new C1240Is(c);
    }

    public static CharMatcher isNot(char c) {
        return new IsNot(c);
    }

    protected CharMatcher() {
    }

    public int indexIn(CharSequence charSequence, int i) {
        int length = charSequence.length();
        Preconditions.checkPositionIndex(i, length);
        while (i < length) {
            if (matches(charSequence.charAt(i))) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public boolean apply(Character ch) {
        return matches(ch.charValue());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String showCharacter(char c) {
        char[] cArr = new char[6];
        cArr[0] = '\\';
        cArr[1] = 'u';
        cArr[2] = 0;
        cArr[3] = 0;
        cArr[4] = 0;
        cArr[5] = 0;
        for (int i = 0; i < 4; i++) {
            cArr[5 - i] = "0123456789ABCDEF".charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(cArr);
    }

    /* loaded from: classes4.dex */
    static abstract class FastMatcher extends CharMatcher {
        FastMatcher() {
        }

        @Override // com.google.common.base.Predicate
        public /* bridge */ /* synthetic */ boolean apply(Object obj) {
            return super.apply((Character) obj);
        }
    }

    /* loaded from: classes4.dex */
    static abstract class NamedFastMatcher extends FastMatcher {
        private final String description;

        NamedFastMatcher(String str) {
            this.description = (String) Preconditions.checkNotNull(str);
        }

        public final String toString() {
            return this.description;
        }
    }

    /* loaded from: classes4.dex */
    private static final class Any extends NamedFastMatcher {
        static final Any INSTANCE = new Any();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return true;
        }

        private Any() {
            super("CharMatcher.any()");
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i) {
            int length = charSequence.length();
            Preconditions.checkPositionIndex(i, length);
            if (i == length) {
                return -1;
            }
            return i;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.none();
        }
    }

    /* loaded from: classes4.dex */
    private static final class None extends NamedFastMatcher {
        static final None INSTANCE = new None();

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return false;
        }

        private None() {
            super("CharMatcher.none()");
        }

        @Override // com.google.common.base.CharMatcher
        public int indexIn(CharSequence charSequence, int i) {
            Preconditions.checkPositionIndex(i, charSequence.length());
            return -1;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.any();
        }
    }

    /* renamed from: com.google.common.base.CharMatcher$Is */
    /* loaded from: classes4.dex */
    private static final class C1240Is extends FastMatcher {
        private final char match;

        C1240Is(char c) {
            this.match = c;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c == this.match;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.isNot(this.match);
        }

        public String toString() {
            return "CharMatcher.is('" + CharMatcher.showCharacter(this.match) + "')";
        }
    }

    /* loaded from: classes4.dex */
    private static final class IsNot extends FastMatcher {
        private final char match;

        IsNot(char c) {
            this.match = c;
        }

        @Override // com.google.common.base.CharMatcher
        public boolean matches(char c) {
            return c != this.match;
        }

        @Override // com.google.common.base.CharMatcher
        public CharMatcher negate() {
            return CharMatcher.m427is(this.match);
        }

        public String toString() {
            return "CharMatcher.isNot('" + CharMatcher.showCharacter(this.match) + "')";
        }
    }
}
