package org.telegram.p023ui.Components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Date;
import org.mvel2.asm.Opcodes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.AvatarSpan;
import org.telegram.p023ui.Components.AnimatedEmojiDrawable;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.ButtonSpan;
import org.telegram.p023ui.Components.LinkSpanDrawable;
import org.telegram.p023ui.Components.spoilers.SpoilersTextView;
import org.telegram.p023ui.Stars.StarsIntroActivity;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes6.dex */
public class TableView extends TableLayout {
    private final Paint backgroundPaint;
    private final Paint borderPaint;

    /* renamed from: hw */
    private final float f2007hw;
    private final Path path;
    private final float[] radii;
    private final Theme.ResourcesProvider resourcesProvider;

    /* renamed from: w */
    private final float f2008w;

    public TableView(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        this.path = new Path();
        this.radii = new float[8];
        this.backgroundPaint = new Paint(1);
        this.borderPaint = new Paint(1);
        float fMax = Math.max(1, AndroidUtilities.m1146dp(0.66f));
        this.f2008w = fMax;
        this.f2007hw = fMax / 2.0f;
        this.resourcesProvider = resourcesProvider;
        setClipToPadding(false);
        setColumnStretchable(1, true);
    }

    public void clear() {
        removeAllViews();
    }

    public TableRow addRowUnpadded(CharSequence charSequence, View view) {
        TableRow tableRow = new TableRow(getContext());
        tableRow.addView(new TableRowTitle(this, charSequence), new TableRow.LayoutParams(-2, -1));
        tableRow.addView(new TableRowContent(this, view, true), new TableRow.LayoutParams(0, -1, 1.0f));
        addView(tableRow);
        return tableRow;
    }

    public TableRow addRowMonospaced(CharSequence charSequence, final CharSequence charSequence2, int i, final Runnable runnable) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(10.66f), AndroidUtilities.m1146dp(9.33f));
        TextView textView = new TextView(getContext());
        textView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MONO));
        textView.setTextSize(1, i);
        textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, this.resourcesProvider));
        textView.setMaxLines(4);
        textView.setSingleLine(false);
        textView.setText(charSequence2);
        frameLayout.addView(textView, LayoutHelper.createFrame(-1, -1.0f, Opcodes.DNEG, 0.0f, 0.0f, 34.0f, 0.0f));
        if (runnable != null) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(C2369R.drawable.msg_copy);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            int i2 = Theme.key_windowBackgroundWhiteBlueIcon;
            imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(i2, this.resourcesProvider), PorterDuff.Mode.SRC_IN));
            imageView.setOnClickListener(new View.OnClickListener() { // from class: org.telegram.ui.Components.TableView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    TableView.m11826$r8$lambda$94D2tet7nd1WDBXBAcVpSQDBqE(charSequence2, runnable, view);
                }
            });
            ScaleStateListAnimator.apply(imageView);
            imageView.setBackground(Theme.createSelectorDrawable(Theme.multAlpha(Theme.getColor(i2, this.resourcesProvider), 0.1f), 7));
            frameLayout.addView(imageView, LayoutHelper.createFrame(30, 30, 21));
        }
        return addRowUnpadded(charSequence, frameLayout);
    }

    /* renamed from: $r8$lambda$94D2tet7nd1WDB-XBAcVpSQDBqE, reason: not valid java name */
    public static /* synthetic */ void m11826$r8$lambda$94D2tet7nd1WDBXBAcVpSQDBqE(CharSequence charSequence, Runnable runnable, View view) {
        AndroidUtilities.addToClipboard(charSequence);
        runnable.run();
    }

    public TableRow addWalletAddressRow(CharSequence charSequence, final CharSequence charSequence2, final Runnable runnable) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(getContext());
        linksTextView.setTypeface(AndroidUtilities.getTypeface(AndroidUtilities.TYPEFACE_ROBOTO_MONO));
        linksTextView.setTextSize(1, 13.0f);
        linksTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack, this.resourcesProvider));
        linksTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn, this.resourcesProvider));
        linksTextView.setMaxLines(1);
        linksTextView.setSingleLine();
        linksTextView.setEllipsize(TextUtils.TruncateAt.MIDDLE);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence2);
        if (runnable != null) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.TableView.1
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    AndroidUtilities.addToClipboard(charSequence2);
                    runnable.run();
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setColor(textPaint.linkColor);
                }
            }, 0, spannableStringBuilder.length(), 33);
        }
        linksTextView.setText(spannableStringBuilder);
        linksTextView.setDisablePaddingsOffsetY(true);
        linksTextView.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(10.66f), AndroidUtilities.m1146dp(9.33f));
        frameLayout.addView(linksTextView, LayoutHelper.createFrame(-1, -1.0f, Opcodes.DNEG, 0.0f, 0.0f, 0.0f, 0.0f));
        return addRowUnpadded(charSequence, frameLayout);
    }

    public TableRow addRowUserWithEmojiStatus(CharSequence charSequence, final int i, final long j, final Runnable runnable) {
        String userName;
        String string;
        boolean z;
        final LinkSpanDrawable.LinksSimpleTextView linksSimpleTextView = new LinkSpanDrawable.LinksSimpleTextView(getContext(), this.resourcesProvider);
        linksSimpleTextView.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
        int i2 = Theme.key_chat_messageLinkIn;
        linksSimpleTextView.setTextColor(Theme.getColor(i2, this.resourcesProvider));
        linksSimpleTextView.setLinkTextColor(Theme.getColor(i2, this.resourcesProvider));
        linksSimpleTextView.setTextSize(14);
        AvatarSpan avatarSpan = new AvatarSpan(linksSimpleTextView, i, 24.0f);
        if (j == UserObject.ANONYMOUS) {
            string = LocaleController.getString(C2369R.string.StarsTransactionHidden);
            CombinedDrawable platformDrawable = StarsIntroActivity.StarsTransactionView.getPlatformDrawable("anonymous");
            platformDrawable.setIconSize(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f));
            avatarSpan.setImageDrawable(platformDrawable);
            z = false;
        } else {
            if (UserObject.isService(j)) {
                string = LocaleController.getString(C2369R.string.StarsTransactionUnknown);
                CombinedDrawable platformDrawable2 = StarsIntroActivity.StarsTransactionView.getPlatformDrawable("fragment");
                platformDrawable2.setIconSize(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f));
                avatarSpan.setImageDrawable(platformDrawable2);
            } else {
                if (j >= 0) {
                    TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
                    userName = UserObject.getUserName(user);
                    avatarSpan.setUser(user);
                } else {
                    TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
                    userName = chat == null ? "" : chat.title;
                    avatarSpan.setChat(chat);
                }
                string = userName;
            }
            z = true;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("x  " + ((Object) string));
        spannableStringBuilder.setSpan(avatarSpan, 0, 1, 33);
        if (z) {
            linksSimpleTextView.setClickable(true);
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.TableView.2
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }
            }, 3, spannableStringBuilder.length(), 33);
        }
        final int color = Theme.getColor(Theme.key_featuredStickers_addButton, this.resourcesProvider);
        final AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable swapAnimatedEmojiDrawable = new AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable(linksSimpleTextView, AndroidUtilities.m1146dp(20.0f));
        swapAnimatedEmojiDrawable.setColor(Integer.valueOf(color));
        swapAnimatedEmojiDrawable.offset(AndroidUtilities.m1146dp(12.0f), 0);
        linksSimpleTextView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: org.telegram.ui.Components.TableView.3
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                swapAnimatedEmojiDrawable.attach();
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                swapAnimatedEmojiDrawable.detach();
            }
        });
        final Drawable drawableMutate = getContext().getResources().getDrawable(C2369R.drawable.msg_premium_liststar).mutate();
        drawableMutate.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        Utilities.Callback<Object[]> callback = new Utilities.Callback() { // from class: org.telegram.ui.Components.TableView$$ExternalSyntheticLambda1
            @Override // org.telegram.messenger.Utilities.Callback
            public final void run(Object obj) {
                TableView.$r8$lambda$rEbVSy3GeF1keOVG62K5kjVlt_A(j, i, swapAnimatedEmojiDrawable, linksSimpleTextView, drawableMutate, color, (Object[]) obj);
            }
        };
        callback.run(null);
        linksSimpleTextView.setRightDrawable(swapAnimatedEmojiDrawable);
        NotificationCenter.getInstance(i).listen(linksSimpleTextView, NotificationCenter.updateInterfaces, callback);
        NotificationCenter.getInstance(i).listen(linksSimpleTextView, NotificationCenter.userEmojiStatusUpdated, callback);
        linksSimpleTextView.setText(spannableStringBuilder);
        return addRowUnpadded(charSequence, linksSimpleTextView);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static /* synthetic */ void $r8$lambda$rEbVSy3GeF1keOVG62K5kjVlt_A(long r6, int r8, org.telegram.ui.Components.AnimatedEmojiDrawable.SwapAnimatedEmojiDrawable r9, org.telegram.ui.Components.LinkSpanDrawable.LinksSimpleTextView r10, android.graphics.drawable.Drawable r11, int r12, java.lang.Object[] r13) {
        /*
            r0 = 2666000(0x28ae10, double:1.317179E-317)
            int r13 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r13 == 0) goto L72
            boolean r13 = org.telegram.messenger.UserObject.isService(r6)
            if (r13 == 0) goto Le
            goto L72
        Le:
            r0 = 0
            r13 = 0
            r2 = 0
            r3 = 1
            int r4 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r4 <= 0) goto L31
            org.telegram.messenger.MessagesController r8 = org.telegram.messenger.MessagesController.getInstance(r8)
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$User r6 = r8.getUser(r6)
            if (r6 == 0) goto L28
            org.telegram.tgnet.TLRPC$EmojiStatus r7 = r6.emoji_status
            goto L29
        L28:
            r7 = r2
        L29:
            if (r6 == 0) goto L45
            boolean r6 = r6.premium
            if (r6 == 0) goto L45
            r6 = 1
            goto L46
        L31:
            org.telegram.messenger.MessagesController r8 = org.telegram.messenger.MessagesController.getInstance(r8)
            long r6 = -r6
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$Chat r6 = r8.getChat(r6)
            if (r6 == 0) goto L44
            org.telegram.tgnet.TLRPC$EmojiStatus r6 = r6.emoji_status
            r7 = r6
            goto L45
        L44:
            r7 = r2
        L45:
            r6 = 0
        L46:
            long r4 = org.telegram.messenger.DialogObject.getEmojiStatusDocumentId(r7)
            int r8 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r8 == 0) goto L5c
            r9.set(r4, r3)
            boolean r6 = org.telegram.messenger.DialogObject.isEmojiStatusCollectible(r7)
            r9.setParticles(r6, r3)
            r10.setRightDrawable(r9)
            goto L6b
        L5c:
            if (r6 == 0) goto L68
            r9.set(r11, r3)
            r9.setParticles(r13, r3)
            r10.setRightDrawable(r9)
            goto L6b
        L68:
            r10.setRightDrawable(r2)
        L6b:
            java.lang.Integer r6 = java.lang.Integer.valueOf(r12)
            r9.setColor(r6)
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.TableView.$r8$lambda$rEbVSy3GeF1keOVG62K5kjVlt_A(long, int, org.telegram.ui.Components.AnimatedEmojiDrawable$SwapAnimatedEmojiDrawable, org.telegram.ui.Components.LinkSpanDrawable$LinksSimpleTextView, android.graphics.drawable.Drawable, int, java.lang.Object[]):void");
    }

    public TableRow addRowUser(CharSequence charSequence, int i, long j, Runnable runnable) {
        return addRowUser(charSequence, i, j, runnable, null, null);
    }

    public TableRow addRowUser(CharSequence charSequence, int i, long j, final Runnable runnable, CharSequence charSequence2, Runnable runnable2) {
        boolean z;
        String userName;
        String string;
        boolean z2;
        ButtonSpan.TextViewButtons textViewButtons = new ButtonSpan.TextViewButtons(getContext(), this.resourcesProvider);
        textViewButtons.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
        textViewButtons.setEllipsize(TextUtils.TruncateAt.END);
        int i2 = Theme.key_chat_messageLinkIn;
        textViewButtons.setTextColor(Theme.getColor(i2, this.resourcesProvider));
        textViewButtons.setLinkTextColor(Theme.getColor(i2, this.resourcesProvider));
        textViewButtons.setTextSize(1, 14.0f);
        textViewButtons.setSingleLine(true);
        textViewButtons.setDisablePaddingsOffsetY(true);
        AvatarSpan avatarSpan = new AvatarSpan(textViewButtons, i, 24.0f);
        if (j == UserObject.ANONYMOUS) {
            string = LocaleController.getString(C2369R.string.StarsTransactionHidden);
            CombinedDrawable platformDrawable = StarsIntroActivity.StarsTransactionView.getPlatformDrawable("anonymous");
            platformDrawable.setIconSize(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f));
            avatarSpan.setImageDrawable(platformDrawable);
            z = false;
            z2 = false;
        } else {
            if (UserObject.isService(j)) {
                string = LocaleController.getString(C2369R.string.StarsTransactionUnknown);
                CombinedDrawable platformDrawable2 = StarsIntroActivity.StarsTransactionView.getPlatformDrawable("fragment");
                platformDrawable2.setIconSize(AndroidUtilities.m1146dp(16.0f), AndroidUtilities.m1146dp(16.0f));
                avatarSpan.setImageDrawable(platformDrawable2);
                z = false;
            } else {
                if (j >= 0) {
                    TLRPC.User user = MessagesController.getInstance(i).getUser(Long.valueOf(j));
                    z = user == null;
                    userName = UserObject.getUserName(user);
                    avatarSpan.setUser(user);
                } else {
                    TLRPC.Chat chat = MessagesController.getInstance(i).getChat(Long.valueOf(-j));
                    z = chat == null;
                    userName = chat == null ? "" : chat.title;
                    avatarSpan.setChat(chat);
                }
                string = userName;
            }
            z2 = true;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("x  " + ((Object) string));
        spannableStringBuilder.setSpan(avatarSpan, 0, 1, 33);
        if (z2) {
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.TableView.4
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    Runnable runnable3 = runnable;
                    if (runnable3 != null) {
                        runnable3.run();
                    }
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    textPaint.setUnderlineText(false);
                }
            }, 3, spannableStringBuilder.length(), 33);
        }
        if (charSequence2 != null) {
            textViewButtons.addButton(new ButtonSpan(charSequence2, runnable2, this.resourcesProvider));
        }
        textViewButtons.setText(spannableStringBuilder);
        if (z) {
            return null;
        }
        return addRowUnpadded(charSequence, textViewButtons);
    }

    public TableRow addRowDateTime(CharSequence charSequence, int i) {
        long j = i * 1000;
        return addRow(charSequence, LocaleController.formatString(C2369R.string.formatDateAtTime, LocaleController.getInstance().getFormatterGiveawayCard().format(new Date(j)), LocaleController.getInstance().getFormatterDay().format(new Date(j))));
    }

    public TableRow addRowLink(CharSequence charSequence, CharSequence charSequence2, final Runnable runnable) {
        LinkSpanDrawable.LinksTextView linksTextView = new LinkSpanDrawable.LinksTextView(getContext(), this.resourcesProvider);
        linksTextView.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
        linksTextView.setEllipsize(TextUtils.TruncateAt.END);
        int i = Theme.key_chat_messageLinkIn;
        linksTextView.setTextColor(Theme.getColor(i, this.resourcesProvider));
        linksTextView.setLinkTextColor(Theme.getColor(i, this.resourcesProvider));
        linksTextView.setTextSize(1, 14.0f);
        linksTextView.setSingleLine(true);
        linksTextView.setDisablePaddingsOffsetY(true);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence2);
        spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.TableView.5
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                textPaint.setUnderlineText(false);
            }
        }, 0, spannableStringBuilder.length(), 33);
        linksTextView.setText(spannableStringBuilder);
        return addRowUnpadded(charSequence, linksTextView);
    }

    public TableRow addRow(CharSequence charSequence, CharSequence charSequence2) {
        return addRow(charSequence, charSequence2, null);
    }

    public TableRow addRow(CharSequence charSequence, CharSequence charSequence2, ButtonSpan.TextViewButtons[] textViewButtonsArr) {
        return addRow(charSequence, charSequence2, (TableRowTitle[]) null, textViewButtonsArr);
    }

    public TableRow addRow(CharSequence charSequence, CharSequence charSequence2, TableRowTitle[] tableRowTitleArr, ButtonSpan.TextViewButtons[] textViewButtonsArr) {
        ButtonSpan.TextViewButtons textViewButtons = new ButtonSpan.TextViewButtons(getContext());
        textViewButtons.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        textViewButtons.setTextSize(1, 14.0f);
        textViewButtons.setText(Emoji.replaceEmoji(charSequence2, textViewButtons.getPaint().getFontMetricsInt(), false));
        NotificationCenter.listenEmojiLoading(textViewButtons);
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-2, -1);
        TableRowTitle tableRowTitle = new TableRowTitle(this, charSequence);
        if (tableRowTitleArr != null) {
            tableRowTitleArr[0] = tableRowTitle;
        }
        tableRow.addView(tableRowTitle, layoutParams);
        tableRow.addView(new TableRowContent(this, textViewButtons), new TableRow.LayoutParams(0, -1, 1.0f));
        addView(tableRow);
        if (textViewButtonsArr != null) {
            textViewButtonsArr[0] = textViewButtons;
        }
        return tableRow;
    }

    public TableRow addRow(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Runnable runnable) {
        ButtonSpan.TextViewButtons textViewButtons = new ButtonSpan.TextViewButtons(getContext());
        textViewButtons.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        textViewButtons.setTextSize(1, 14.0f);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(Emoji.replaceEmoji(charSequence2, textViewButtons.getPaint().getFontMetricsInt(), false));
        if (charSequence3 != null) {
            spannableStringBuilder.append((CharSequence) " ").append(ButtonSpan.make(charSequence3, runnable, this.resourcesProvider));
        }
        textViewButtons.setText(spannableStringBuilder);
        NotificationCenter.listenEmojiLoading(textViewButtons);
        TableRow tableRow = new TableRow(getContext());
        tableRow.addView(new TableRowTitle(this, charSequence), new TableRow.LayoutParams(-2, -1));
        tableRow.addView(new TableRowContent(this, textViewButtons), new TableRow.LayoutParams(0, -1, 1.0f));
        addView(tableRow);
        return tableRow;
    }

    public TableRowFullContent addFullRow(CharSequence charSequence) {
        SpoilersTextView spoilersTextView = new SpoilersTextView(getContext());
        spoilersTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        spoilersTextView.setLinkTextColor(Theme.getColor(Theme.key_chat_messageLinkIn, this.resourcesProvider));
        spoilersTextView.setTextSize(1, 14.0f);
        spoilersTextView.setText(Emoji.replaceEmoji(charSequence, spoilersTextView.getPaint().getFontMetricsInt(), false));
        NotificationCenter.listenEmojiLoading(spoilersTextView);
        spoilersTextView.setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-2, -1);
        layoutParams.span = 2;
        TableRowFullContent tableRowFullContent = new TableRowFullContent(this, spoilersTextView, true);
        tableRow.addView(tableRowFullContent, layoutParams);
        addView(tableRow);
        return tableRowFullContent;
    }

    public void addFullRow(CharSequence charSequence, ArrayList arrayList) {
        AnimatedEmojiSpan.TextViewEmojis textViewEmojis = new AnimatedEmojiSpan.TextViewEmojis(getContext());
        textViewEmojis.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, this.resourcesProvider));
        textViewEmojis.setTextSize(1, 14.0f);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        MessageObject.addEntitiesToText(spannableStringBuilder, arrayList, false, false, false, false);
        textViewEmojis.setText(MessageObject.replaceAnimatedEmoji(Emoji.replaceEmoji(spannableStringBuilder, textViewEmojis.getPaint().getFontMetricsInt(), false), arrayList, textViewEmojis.getPaint().getFontMetricsInt()));
        NotificationCenter.listenEmojiLoading(textViewEmojis);
        TableRow tableRow = new TableRow(getContext());
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(-2, -1);
        layoutParams.span = 2;
        tableRow.addView(new TableRowFullContent(this, textViewEmojis), layoutParams);
        addView(tableRow);
    }

    public static class TableRowTitle extends TextView {
        private boolean first;
        private boolean last;
        private final Theme.ResourcesProvider resourcesProvider;
        private final TableView table;

        public TableRowTitle(TableView tableView, CharSequence charSequence) {
            super(tableView.getContext());
            this.table = tableView;
            Theme.ResourcesProvider resourcesProvider = tableView.resourcesProvider;
            this.resourcesProvider = resourcesProvider;
            setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
            setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText, resourcesProvider));
            setTypeface(AndroidUtilities.bold());
            setTextSize(1, 14.0f);
            setText(charSequence);
        }

        public void setFirstLast(boolean z, boolean z2) {
            if (this.first == z && this.last == z2) {
                return;
            }
            this.first = z;
            this.last = z2;
            invalidate();
        }

        @Override // android.widget.TextView, android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            if (this.first || this.last) {
                canvas2 = canvas;
                float fM1146dp = AndroidUtilities.m1146dp(4.0f);
                float[] fArr = this.table.radii;
                float[] fArr2 = this.table.radii;
                float f = this.first ? fM1146dp : 0.0f;
                fArr2[1] = f;
                fArr[0] = f;
                float[] fArr3 = this.table.radii;
                this.table.radii[3] = 0.0f;
                fArr3[2] = 0.0f;
                float[] fArr4 = this.table.radii;
                this.table.radii[5] = 0.0f;
                fArr4[4] = 0.0f;
                float[] fArr5 = this.table.radii;
                float[] fArr6 = this.table.radii;
                if (!this.last) {
                    fM1146dp = 0.0f;
                }
                fArr6[7] = fM1146dp;
                fArr5[6] = fM1146dp;
                this.table.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.table.f2007hw, this.table.f2007hw, getWidth() + this.table.f2007hw, getHeight() + (this.table.f2007hw * AndroidUtilities.m1146dp(this.last ? -1.0f : 1.0f)));
                this.table.path.addRoundRect(rectF, this.table.radii, Path.Direction.CW);
                canvas2.drawPath(this.table.path, this.table.backgroundPaint);
                canvas2.drawPath(this.table.path, this.table.borderPaint);
            } else {
                canvas2 = canvas;
                canvas2.drawRect(this.table.f2007hw, this.table.f2007hw, getWidth() + this.table.f2007hw, getHeight() + this.table.f2007hw, this.table.backgroundPaint);
                canvas2.drawRect(this.table.f2007hw, this.table.f2007hw, getWidth() + this.table.f2007hw, getHeight() + this.table.f2007hw, this.table.borderPaint);
            }
            super.onDraw(canvas2);
        }
    }

    public static class TableRowFullContent extends FrameLayout {
        private boolean filled;
        private boolean first;
        private boolean last;
        private final Theme.ResourcesProvider resourcesProvider;
        private final TableView table;

        public TableRowFullContent(TableView tableView, View view) {
            this(tableView, view, false);
        }

        public TableRowFullContent(TableView tableView, View view, boolean z) {
            super(tableView.getContext());
            this.table = tableView;
            this.resourcesProvider = tableView.resourcesProvider;
            setWillNotDraw(false);
            if (!z) {
                setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f));
        }

        public void setFirstLast(boolean z, boolean z2) {
            if (this.first == z && this.last == z2) {
                return;
            }
            this.first = z;
            this.last = z2;
            invalidate();
        }

        public void setFilled(boolean z) {
            this.filled = z;
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            if (this.first || this.last) {
                canvas2 = canvas;
                float fM1146dp = AndroidUtilities.m1146dp(4.0f);
                float[] fArr = this.table.radii;
                float[] fArr2 = this.table.radii;
                float f = this.first ? fM1146dp : 0.0f;
                fArr2[1] = f;
                fArr[0] = f;
                float[] fArr3 = this.table.radii;
                float[] fArr4 = this.table.radii;
                float f2 = this.first ? fM1146dp : 0.0f;
                fArr4[3] = f2;
                fArr3[2] = f2;
                float[] fArr5 = this.table.radii;
                float[] fArr6 = this.table.radii;
                float f3 = this.last ? fM1146dp : 0.0f;
                fArr6[5] = f3;
                fArr5[4] = f3;
                float[] fArr7 = this.table.radii;
                float[] fArr8 = this.table.radii;
                if (!this.last) {
                    fM1146dp = 0.0f;
                }
                fArr8[7] = fM1146dp;
                fArr7[6] = fM1146dp;
                this.table.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.table.f2007hw, this.table.f2007hw, getWidth() - this.table.f2007hw, getHeight() + (this.table.f2007hw * AndroidUtilities.m1146dp(this.last ? -1.0f : 1.0f)));
                this.table.path.addRoundRect(rectF, this.table.radii, Path.Direction.CW);
                if (this.filled) {
                    canvas2.drawPath(this.table.path, this.table.backgroundPaint);
                }
                canvas2.drawPath(this.table.path, this.table.borderPaint);
            } else {
                if (this.filled) {
                    canvas2 = canvas;
                    canvas2.drawRect(this.table.f2007hw, this.table.f2007hw, getWidth() + this.table.f2007hw, getHeight() + this.table.f2007hw, this.table.backgroundPaint);
                } else {
                    canvas2 = canvas;
                }
                canvas2.drawRect(this.table.f2007hw, this.table.f2007hw, getWidth() - this.table.f2007hw, getHeight() + this.table.f2007hw, this.table.borderPaint);
            }
            super.onDraw(canvas2);
        }
    }

    public static class TableRowContent extends FrameLayout {
        private boolean first;
        private boolean last;
        private final Theme.ResourcesProvider resourcesProvider;
        private final TableView table;

        public TableRowContent(TableView tableView, View view) {
            this(tableView, view, false);
        }

        public TableRowContent(TableView tableView, View view, boolean z) {
            super(tableView.getContext());
            this.table = tableView;
            this.resourcesProvider = tableView.resourcesProvider;
            setWillNotDraw(false);
            if (!z) {
                setPadding(AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f), AndroidUtilities.m1146dp(12.66f), AndroidUtilities.m1146dp(9.33f));
            }
            addView(view, LayoutHelper.createFrame(-1, -1.0f));
        }

        public void setFirstLast(boolean z, boolean z2) {
            if (this.first == z && this.last == z2) {
                return;
            }
            this.first = z;
            this.last = z2;
            invalidate();
        }

        @Override // android.view.View
        protected void onDraw(Canvas canvas) {
            Canvas canvas2;
            if (this.first || this.last) {
                canvas2 = canvas;
                float fM1146dp = AndroidUtilities.m1146dp(4.0f);
                float[] fArr = this.table.radii;
                this.table.radii[1] = 0.0f;
                fArr[0] = 0.0f;
                float[] fArr2 = this.table.radii;
                float[] fArr3 = this.table.radii;
                float f = this.first ? fM1146dp : 0.0f;
                fArr3[3] = f;
                fArr2[2] = f;
                float[] fArr4 = this.table.radii;
                float[] fArr5 = this.table.radii;
                if (!this.last) {
                    fM1146dp = 0.0f;
                }
                fArr5[5] = fM1146dp;
                fArr4[4] = fM1146dp;
                float[] fArr6 = this.table.radii;
                this.table.radii[7] = 0.0f;
                fArr6[6] = 0.0f;
                this.table.path.rewind();
                RectF rectF = AndroidUtilities.rectTmp;
                rectF.set(this.table.f2007hw, this.table.f2007hw, getWidth() - this.table.f2007hw, getHeight() + (this.table.f2007hw * AndroidUtilities.m1146dp(this.last ? -1.0f : 1.0f)));
                this.table.path.addRoundRect(rectF, this.table.radii, Path.Direction.CW);
                canvas2.drawPath(this.table.path, this.table.borderPaint);
            } else {
                canvas2 = canvas;
                canvas2.drawRect(this.table.f2007hw, this.table.f2007hw, getWidth() - this.table.f2007hw, getHeight() + this.table.f2007hw, this.table.borderPaint);
            }
            super.onDraw(canvas2);
        }
    }

    @Override // android.widget.TableLayout, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setStrokeWidth(this.f2008w);
        this.borderPaint.setColor(Theme.getColor(Theme.key_table_border, this.resourcesProvider));
        this.backgroundPaint.setStyle(Paint.Style.FILL);
        this.backgroundPaint.setColor(Theme.getColor(Theme.key_table_background, this.resourcesProvider));
        int childCount = getChildCount();
        int i5 = 0;
        while (i5 < childCount) {
            if (getChildAt(i5) instanceof TableRow) {
                TableRow tableRow = (TableRow) getChildAt(i5);
                int childCount2 = tableRow.getChildCount();
                for (int i6 = 0; i6 < childCount2; i6++) {
                    View childAt = tableRow.getChildAt(i6);
                    if (childAt instanceof TableRowTitle) {
                        ((TableRowTitle) childAt).setFirstLast(i5 == 0, i5 == childCount + (-1));
                    } else if (childAt instanceof TableRowContent) {
                        ((TableRowContent) childAt).setFirstLast(i5 == 0, i5 == childCount + (-1));
                    } else if (childAt instanceof TableRowFullContent) {
                        ((TableRowFullContent) childAt).setFirstLast(i5 == 0, i5 == childCount + (-1));
                    }
                }
            }
            i5++;
        }
    }
}
