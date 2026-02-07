package com.exteragram.messenger.export.api;

import com.android.tools.p002r8.RecordTag;
import com.exteragram.messenger.p003ai.p004ui.AbstractC0746x1d8a54ff;
import java.util.ArrayList;
import p017j$.util.Objects;

/* loaded from: classes3.dex */
public class ApiWrap$Poll {
    public String question;

    /* renamed from: id */
    public long f157id = 0;
    public int totalVotes = 0;
    public boolean closed = false;
    public ArrayList answers = new ArrayList();

    public static final class Answer extends RecordTag {

        /* renamed from: my */
        private final boolean f158my;
        private final byte[] option;
        private final String text;
        private final int votes;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof Answer)) {
                return false;
            }
            Answer answer = (Answer) obj;
            return this.f158my == answer.f158my && this.votes == answer.votes && Objects.equals(this.text, answer.text) && Objects.equals(this.option, answer.option);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.text, this.option, Integer.valueOf(this.votes), Boolean.valueOf(this.f158my)};
        }

        public Answer(String str, byte[] bArr, int i, boolean z) {
            this.text = str;
            this.option = bArr;
            this.votes = i;
            this.f158my = z;
        }

        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public final int hashCode() {
            return ApiWrap$Poll$Answer$$ExternalSyntheticRecord0.m200m(this.f158my, this.votes, this.text, this.option);
        }

        /* renamed from: my */
        public boolean m204my() {
            return this.f158my;
        }

        public String text() {
            return this.text;
        }

        public final String toString() {
            return AbstractC0746x1d8a54ff.m185m($record$getFieldsAsObjects(), Answer.class, "text;option;votes;my");
        }

        public int votes() {
            return this.votes;
        }
    }
}
