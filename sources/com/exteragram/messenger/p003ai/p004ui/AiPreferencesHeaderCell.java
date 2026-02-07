package com.exteragram.messenger.p003ai.p004ui;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.exteragram.messenger.preferences.components.CustomPreferenceCell;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.ActionBar;
import org.telegram.p023ui.Components.LayoutHelper;
import org.telegram.p023ui.Components.StickerImageView;
import org.telegram.tgnet.TLObject;

/* loaded from: classes.dex */
public class AiPreferencesHeaderCell extends FrameLayout implements CustomPreferenceCell {
    public StickerImageView backupImageView;

    public AiPreferencesHeaderCell(Context context) {
        super(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        StickerImageView stickerImageView = new StickerImageView(context, UserConfig.selectedAccount);
        this.backupImageView = stickerImageView;
        stickerImageView.setStickerPackName("exteraGramPlaceholders");
        this.backupImageView.setStickerNum(0);
        this.backupImageView.getImageReceiver().setAutoRepeatCount(1);
        this.backupImageView.setOnClickListener(new View.OnClickListener() { // from class: com.exteragram.messenger.ai.ui.AiPreferencesHeaderCell$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$new$0(view);
            }
        });
        linearLayout.addView(this.backupImageView, LayoutHelper.createLinear(Opcodes.IXOR, Opcodes.IXOR, 1, 0, 27, 0, 30));
        linearLayout.setPadding(0, (ActionBar.getCurrentActionBarHeight() + (!AndroidUtilities.isTablet() ? AndroidUtilities.statusBarHeight : 0)) - AndroidUtilities.m1146dp(40.0f), 0, 0);
        addView(linearLayout, LayoutHelper.createLinear(-1, -2, 49));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(View view) {
        this.backupImageView.getImageReceiver().startAnimation();
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i), TLObject.FLAG_30), View.MeasureSpec.makeMeasureSpec(0, 0));
    }

    @Override // com.exteragram.messenger.preferences.components.CustomPreferenceCell
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj instanceof AiPreferencesHeaderCell;
    }
}
