package org.telegram.p023ui.Components.Reactions;

import android.graphics.Paint;
import android.text.Editable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.UserConfig;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.Premium.LimitReachedBottomSheet;
import org.telegram.p023ui.Components.Reactions.ReactionsLayoutInBubble;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.SelectAnimatedEmojiDialog;
import org.telegram.p023ui.StatisticActivity;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.p022tl.TL_stories;

/* loaded from: classes6.dex */
public abstract class ReactionsUtils {
    public static boolean compare(TLRPC.Reaction reaction, ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        if ((reaction instanceof TLRPC.TL_reactionEmoji) && visibleReaction.documentId == 0 && TextUtils.equals(((TLRPC.TL_reactionEmoji) reaction).emoticon, visibleReaction.emojicon)) {
            return true;
        }
        if (!(reaction instanceof TLRPC.TL_reactionCustomEmoji)) {
            return false;
        }
        long j = visibleReaction.documentId;
        return j != 0 && ((TLRPC.TL_reactionCustomEmoji) reaction).document_id == j;
    }

    public static boolean compare(TLRPC.Reaction reaction, TLRPC.Reaction reaction2) {
        if ((reaction instanceof TLRPC.TL_reactionEmoji) && (reaction2 instanceof TLRPC.TL_reactionEmoji) && TextUtils.equals(((TLRPC.TL_reactionEmoji) reaction).emoticon, ((TLRPC.TL_reactionEmoji) reaction2).emoticon)) {
            return true;
        }
        return (reaction instanceof TLRPC.TL_reactionCustomEmoji) && (reaction2 instanceof TLRPC.TL_reactionCustomEmoji) && ((TLRPC.TL_reactionCustomEmoji) reaction).document_id == ((TLRPC.TL_reactionCustomEmoji) reaction2).document_id;
    }

    public static TLRPC.Reaction toTLReaction(ReactionsLayoutInBubble.VisibleReaction visibleReaction) {
        if (visibleReaction.emojicon != null) {
            TLRPC.TL_reactionEmoji tL_reactionEmoji = new TLRPC.TL_reactionEmoji();
            tL_reactionEmoji.emoticon = visibleReaction.emojicon;
            return tL_reactionEmoji;
        }
        TLRPC.TL_reactionCustomEmoji tL_reactionCustomEmoji = new TLRPC.TL_reactionCustomEmoji();
        tL_reactionCustomEmoji.document_id = visibleReaction.documentId;
        return tL_reactionCustomEmoji;
    }

    public static CharSequence reactionToCharSequence(TLRPC.Reaction reaction) {
        if (reaction instanceof TLRPC.TL_reactionEmoji) {
            return ((TLRPC.TL_reactionEmoji) reaction).emoticon;
        }
        if (reaction instanceof TLRPC.TL_reactionCustomEmoji) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("d");
            spannableStringBuilder.setSpan(new AnimatedEmojiSpan(((TLRPC.TL_reactionCustomEmoji) reaction).document_id, (Paint.FontMetricsInt) null), 0, 1, 0);
            return spannableStringBuilder;
        }
        return "";
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void applyForStoryViews(org.telegram.tgnet.TLRPC.Reaction r5, org.telegram.tgnet.TLRPC.Reaction r6, org.telegram.tgnet.tl.TL_stories.StoryViews r7) {
        /*
            if (r7 != 0) goto L3
            goto L51
        L3:
            r0 = 0
            r1 = 0
        L5:
            java.util.ArrayList<org.telegram.tgnet.TLRPC$ReactionCount> r2 = r7.reactions
            int r2 = r2.size()
            r3 = 1
            if (r0 >= r2) goto L41
            java.util.ArrayList<org.telegram.tgnet.TLRPC$ReactionCount> r2 = r7.reactions
            java.lang.Object r2 = r2.get(r0)
            org.telegram.tgnet.TLRPC$ReactionCount r2 = (org.telegram.tgnet.TLRPC.ReactionCount) r2
            if (r5 == 0) goto L2f
            org.telegram.tgnet.TLRPC$Reaction r4 = r2.reaction
            boolean r4 = compare(r4, r5)
            if (r4 == 0) goto L2f
            int r4 = r2.count
            int r4 = r4 - r3
            r2.count = r4
            if (r4 > 0) goto L2f
            java.util.ArrayList<org.telegram.tgnet.TLRPC$ReactionCount> r2 = r7.reactions
            r2.remove(r0)
            int r0 = r0 + (-1)
            goto L3f
        L2f:
            if (r6 == 0) goto L3f
            org.telegram.tgnet.TLRPC$Reaction r4 = r2.reaction
            boolean r4 = compare(r4, r6)
            if (r4 == 0) goto L3f
            int r1 = r2.count
            int r1 = r1 + r3
            r2.count = r1
            r1 = 1
        L3f:
            int r0 = r0 + r3
            goto L5
        L41:
            if (r1 != 0) goto L51
            org.telegram.tgnet.TLRPC$TL_reactionCount r5 = new org.telegram.tgnet.TLRPC$TL_reactionCount
            r5.<init>()
            r5.count = r3
            r5.reaction = r6
            java.util.ArrayList<org.telegram.tgnet.TLRPC$ReactionCount> r6 = r7.reactions
            r6.add(r5)
        L51:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.Reactions.ReactionsUtils.applyForStoryViews(org.telegram.tgnet.TLRPC$Reaction, org.telegram.tgnet.TLRPC$Reaction, org.telegram.tgnet.tl.TL_stories$StoryViews):void");
    }

    public static void showLimitReachedDialogForReactions(final long j, int i, TL_stories.TL_premium_boostsStatus tL_premium_boostsStatus) {
        final BaseFragment lastFragment = LaunchActivity.getLastFragment();
        if (lastFragment == null || tL_premium_boostsStatus == null) {
            return;
        }
        LimitReachedBottomSheet limitReachedBottomSheet = new LimitReachedBottomSheet(lastFragment, lastFragment.getContext(), 21, UserConfig.selectedAccount, lastFragment.getResourceProvider());
        limitReachedBottomSheet.setRequiredLvl(i);
        limitReachedBottomSheet.setBoostsStats(tL_premium_boostsStatus, true);
        limitReachedBottomSheet.setDialogId(j);
        limitReachedBottomSheet.showStatisticButtonInLink(new Runnable() { // from class: org.telegram.ui.Components.Reactions.ReactionsUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseFragment baseFragment = lastFragment;
                baseFragment.presentFragment(StatisticActivity.create(baseFragment.getMessagesController().getChat(Long.valueOf(-j))));
            }
        });
        limitReachedBottomSheet.show();
    }

    public static SpannableString createSpannableText(AnimatedEmojiSpan animatedEmojiSpan, String str) {
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(animatedEmojiSpan, 0, spannableString.length(), 33);
        return spannableString;
    }

    public static AnimatedEmojiSpan createAnimatedEmojiSpan(TLRPC.Document document, Long l, Paint.FontMetricsInt fontMetricsInt) {
        AnimatedEmojiSpan animatedEmojiSpan;
        if (document != null) {
            animatedEmojiSpan = new AnimatedEmojiSpan(document, 1.0f, fontMetricsInt);
        } else {
            animatedEmojiSpan = new AnimatedEmojiSpan(l.longValue(), 1.0f, fontMetricsInt);
        }
        animatedEmojiSpan.cacheType = AnimatedEmojiDrawable.getCacheTypeForEnterView();
        return animatedEmojiSpan;
    }

    public static void addReactionToEditText(TLRPC.TL_availableReaction tL_availableReaction, HashMap map, List list, Editable editable, SelectAnimatedEmojiDialog selectAnimatedEmojiDialog, Paint.FontMetricsInt fontMetricsInt) {
        TLRPC.Document document = tL_availableReaction.activate_animation;
        long j = document.f1579id;
        AnimatedEmojiSpan animatedEmojiSpanCreateAnimatedEmojiSpan = createAnimatedEmojiSpan(document, Long.valueOf(j), fontMetricsInt);
        map.put(Long.valueOf(j), animatedEmojiSpanCreateAnimatedEmojiSpan);
        list.add(Long.valueOf(j));
        editable.append((CharSequence) createSpannableText(animatedEmojiSpanCreateAnimatedEmojiSpan, "e"));
        if (selectAnimatedEmojiDialog != null) {
            selectAnimatedEmojiDialog.setMultiSelected(Long.valueOf(j), false);
        }
    }

    public static void addReactionToEditText(TLRPC.TL_reactionCustomEmoji tL_reactionCustomEmoji, HashMap map, List list, Editable editable, SelectAnimatedEmojiDialog selectAnimatedEmojiDialog, Paint.FontMetricsInt fontMetricsInt) {
        AnimatedEmojiSpan animatedEmojiSpanCreateAnimatedEmojiSpan = createAnimatedEmojiSpan(null, Long.valueOf(tL_reactionCustomEmoji.document_id), fontMetricsInt);
        map.put(Long.valueOf(tL_reactionCustomEmoji.document_id), animatedEmojiSpanCreateAnimatedEmojiSpan);
        list.add(Long.valueOf(tL_reactionCustomEmoji.document_id));
        editable.append((CharSequence) createSpannableText(animatedEmojiSpanCreateAnimatedEmojiSpan, "e"));
        if (selectAnimatedEmojiDialog != null) {
            selectAnimatedEmojiDialog.setMultiSelected(Long.valueOf(tL_reactionCustomEmoji.document_id), false);
        }
    }

    public static List startPreloadReactions(TLRPC.Chat chat, TLRPC.ChatFull chatFull) {
        AnimatedEmojiDrawable animatedEmojiDrawableMake;
        ArrayList arrayList = new ArrayList();
        if (chatFull != null && ChatObject.isChannelAndNotMegaGroup(chat)) {
            TLRPC.ChatReactions chatReactions = chatFull.available_reactions;
            if (chatReactions instanceof TLRPC.TL_chatReactionsSome) {
                ArrayList arrayList2 = ((TLRPC.TL_chatReactionsSome) chatReactions).reactions;
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    TLRPC.Reaction reaction = (TLRPC.Reaction) obj;
                    if (reaction instanceof TLRPC.TL_reactionEmoji) {
                        TLRPC.TL_availableReaction tL_availableReaction = MediaDataController.getInstance(UserConfig.selectedAccount).getReactionsMap().get(((TLRPC.TL_reactionEmoji) reaction).emoticon);
                        if (tL_availableReaction != null) {
                            animatedEmojiDrawableMake = AnimatedEmojiDrawable.make(UserConfig.selectedAccount, AnimatedEmojiDrawable.getCacheTypeForEnterView(), tL_availableReaction.activate_animation);
                        }
                    } else {
                        animatedEmojiDrawableMake = reaction instanceof TLRPC.TL_reactionCustomEmoji ? AnimatedEmojiDrawable.make(UserConfig.selectedAccount, AnimatedEmojiDrawable.getCacheTypeForEnterView(), ((TLRPC.TL_reactionCustomEmoji) reaction).document_id) : null;
                    }
                    if (animatedEmojiDrawableMake != null) {
                        arrayList.add(animatedEmojiDrawableMake);
                        animatedEmojiDrawableMake.addView((AnimatedEmojiSpan.InvalidateHolder) null);
                    }
                }
            } else if (chatReactions instanceof TLRPC.TL_chatReactionsAll) {
                for (TLRPC.TL_availableReaction tL_availableReaction2 : MediaDataController.getInstance(UserConfig.selectedAccount).getEnabledReactionsList()) {
                    if (tL_availableReaction2 != null) {
                        AnimatedEmojiDrawable animatedEmojiDrawableMake2 = AnimatedEmojiDrawable.make(UserConfig.selectedAccount, AnimatedEmojiDrawable.getCacheTypeForEnterView(), tL_availableReaction2.activate_animation);
                        arrayList.add(animatedEmojiDrawableMake2);
                        animatedEmojiDrawableMake2.addView((AnimatedEmojiSpan.InvalidateHolder) null);
                    }
                }
            }
        }
        return arrayList;
    }

    public static void stopPreloadReactions(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((AnimatedEmojiDrawable) it.next()).removeView((AnimatedEmojiSpan.InvalidateHolder) null);
        }
    }
}
