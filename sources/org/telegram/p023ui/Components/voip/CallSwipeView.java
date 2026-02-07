package org.telegram.p023ui.Components.voip;

import android.view.View;
import androidx.annotation.Keep;

/* loaded from: classes6.dex */
public abstract class CallSwipeView extends View {
    /* renamed from: -$$Nest$fgetarrowAlphas, reason: not valid java name */
    static /* bridge */ /* synthetic */ int[] m12328$$Nest$fgetarrowAlphas(CallSwipeView callSwipeView) {
        throw null;
    }

    private class ArrowAnimWrapper {
        private int index;

        @Keep
        public int getArrowAlpha() {
            return CallSwipeView.m12328$$Nest$fgetarrowAlphas(null)[this.index];
        }

        @Keep
        public void setArrowAlpha(int i) {
            CallSwipeView.m12328$$Nest$fgetarrowAlphas(null)[this.index] = i;
        }
    }
}
