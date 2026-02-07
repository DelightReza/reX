package org.telegram.p023ui.Components;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mvel2.DataTypes;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.DialogObject;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLocation;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaDataController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.ActionBar.BottomSheet;
import org.telegram.p023ui.ActionBar.Theme;
import org.telegram.p023ui.ChatActivity;
import org.telegram.p023ui.Components.AnimatedEmojiSpan;
import org.telegram.p023ui.Components.Bulletin;
import org.telegram.p023ui.LaunchActivity;
import org.telegram.p023ui.PeerColorActivity;
import org.telegram.p023ui.PremiumPreviewFragment;
import org.telegram.p023ui.Stories.recorder.HintView2;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;

/* loaded from: classes3.dex */
public final class BulletinFactory {
    private final FrameLayout containerLayout;
    private final BaseFragment fragment;
    private final Theme.ResourcesProvider resourcesProvider;

    /* loaded from: classes6.dex */
    public static class UndoObject {
        public Runnable onAction;
        public Runnable onUndo;
        public CharSequence undoText;
    }

    /* renamed from: of */
    public static BulletinFactory m1267of(BaseFragment baseFragment) {
        return new BulletinFactory(baseFragment);
    }

    /* renamed from: of */
    public static BulletinFactory m1266of(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider) {
        return new BulletinFactory(frameLayout, resourcesProvider);
    }

    public static boolean canShowBulletin(BaseFragment baseFragment) {
        return (baseFragment == null || baseFragment.getParentActivity() == null || baseFragment.getLayoutContainer() == null) ? false : true;
    }

    public static BulletinFactory global() {
        BaseFragment safeLastFragment = LaunchActivity.getSafeLastFragment();
        if (safeLastFragment == null) {
            return m1266of(Bulletin.BulletinWindow.make(ApplicationLoader.applicationContext), null);
        }
        Dialog dialog = safeLastFragment.visibleDialog;
        if (dialog instanceof BottomSheet) {
            return m1266of(((BottomSheet) dialog).container, safeLastFragment.getResourceProvider());
        }
        return m1267of(safeLastFragment);
    }

    private static String invalidConnectionLayerMessage() {
        return LocaleController.getString(C2369R.string.InvalidConnectionLayerNote);
    }

    public Bulletin makeForError(TLRPC.TL_error tL_error) {
        if (!LaunchActivity.isActive) {
            return new Bulletin.EmptyBulletin();
        }
        if (tL_error == null) {
            return createErrorBulletin(LocaleController.formatString(C2369R.string.UnknownError, new Object[0]));
        }
        return createErrorBulletin("CONNECTION_LAYER_INVALID".equals(tL_error.text) ? invalidConnectionLayerMessage() : LocaleController.formatString(C2369R.string.UnknownErrorCode, tL_error.text));
    }

    public void showForError(TLRPC.TL_error tL_error) {
        showForError(tL_error, false);
    }

    public void showForError(TLRPC.TL_error tL_error, boolean z) {
        if (LaunchActivity.isActive) {
            if (tL_error == null) {
                Bulletin bulletinCreateErrorBulletin = createErrorBulletin(LocaleController.formatString(C2369R.string.UnknownError, new Object[0]));
                bulletinCreateErrorBulletin.hideAfterBottomSheet = false;
                bulletinCreateErrorBulletin.show(z);
            } else {
                Bulletin bulletinCreateErrorBulletin2 = createErrorBulletin("CONNECTION_LAYER_INVALID".equals(tL_error.text) ? invalidConnectionLayerMessage() : LocaleController.formatString(C2369R.string.UnknownErrorCode, tL_error.text));
                bulletinCreateErrorBulletin2.hideAfterBottomSheet = false;
                bulletinCreateErrorBulletin2.show(z);
            }
        }
    }

    public void showForError(String str) {
        showForError(str, false);
    }

    public void showForError(String str, boolean z) {
        if (LaunchActivity.isActive) {
            if (TextUtils.isEmpty(str)) {
                Bulletin bulletinCreateErrorBulletin = createErrorBulletin(LocaleController.formatString(C2369R.string.UnknownError, new Object[0]));
                bulletinCreateErrorBulletin.hideAfterBottomSheet = false;
                bulletinCreateErrorBulletin.show(z);
            } else {
                Bulletin bulletinCreateErrorBulletin2 = createErrorBulletin("CONNECTION_LAYER_INVALID".equals(str) ? invalidConnectionLayerMessage() : LocaleController.formatString(C2369R.string.UnknownErrorCode, str));
                bulletinCreateErrorBulletin2.hideAfterBottomSheet = false;
                bulletinCreateErrorBulletin2.show(z);
            }
        }
    }

    public static void showError(TLRPC.TL_error tL_error) {
        if (LaunchActivity.isActive) {
            global().createErrorBulletin("CONNECTION_LAYER_INVALID".equals(tL_error.text) ? invalidConnectionLayerMessage() : LocaleController.formatString(C2369R.string.UnknownErrorCode, tL_error.text)).show();
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'STICKER' uses external variables
    	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:451)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByField(EnumVisitor.java:372)
    	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByWrappedInsn(EnumVisitor.java:337)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:322)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:262)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInvoke(EnumVisitor.java:293)
    	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:266)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:151)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* loaded from: classes6.dex */
    public static final class FileType {
        private static final /* synthetic */ FileType[] $VALUES;
        public static final FileType AUDIO;
        public static final FileType AUDIOS;
        public static final FileType EMOJI;
        public static final FileType GIF;
        public static final FileType GIF_TO_DOWNLOADS;
        public static final FileType MEDIA;
        public static final FileType PHOTO;
        public static final FileType PHOTOS;
        public static final FileType PHOTO_TO_DOWNLOADS;
        public static final FileType STICKER;
        public static final FileType UNKNOWN;
        public static final FileType UNKNOWNS;
        public static final FileType VIDEO;
        public static final FileType VIDEOS;
        public static final FileType VIDEO_TO_DOWNLOADS;
        private final Icon icon;
        private final String localeKey;
        private final int localeRes;
        private final boolean plural;

        private static /* synthetic */ FileType[] $values() {
            return new FileType[]{STICKER, EMOJI, PHOTO, PHOTOS, VIDEO, VIDEOS, MEDIA, PHOTO_TO_DOWNLOADS, VIDEO_TO_DOWNLOADS, GIF, GIF_TO_DOWNLOADS, AUDIO, AUDIOS, UNKNOWN, UNKNOWNS};
        }

        public static FileType valueOf(String str) {
            return (FileType) Enum.valueOf(FileType.class, str);
        }

        public static FileType[] values() {
            return (FileType[]) $VALUES.clone();
        }

        static {
            int i = C2369R.string.StickerSavedHint;
            Icon icon = Icon.SAVED_TO_GALLERY;
            STICKER = new FileType("STICKER", 0, "StickerSavedHint", i, icon);
            EMOJI = new FileType("EMOJI", 1, "EmojiSavedHint", C2369R.string.EmojiSavedHint, icon);
            PHOTO = new FileType("PHOTO", 2, "PhotoSavedHint", C2369R.string.PhotoSavedHint, icon);
            PHOTOS = new FileType("PHOTOS", 3, "PhotosSavedHint", icon);
            VIDEO = new FileType("VIDEO", 4, "VideoSavedHint", C2369R.string.VideoSavedHint, icon);
            VIDEOS = new FileType("VIDEOS", 5, "VideosSavedHint", icon);
            MEDIA = new FileType("MEDIA", 6, "MediaSavedHint", icon);
            int i2 = C2369R.string.PhotoSavedToDownloadsHintLinked;
            Icon icon2 = Icon.SAVED_TO_DOWNLOADS;
            PHOTO_TO_DOWNLOADS = new FileType("PHOTO_TO_DOWNLOADS", 7, "PhotoSavedToDownloadsHintLinked", i2, icon2);
            VIDEO_TO_DOWNLOADS = new FileType("VIDEO_TO_DOWNLOADS", 8, "VideoSavedToDownloadsHintLinked", C2369R.string.VideoSavedToDownloadsHintLinked, icon2);
            GIF = new FileType("GIF", 9, "GifSavedHint", C2369R.string.GifSavedHint, Icon.SAVED_TO_GIFS);
            GIF_TO_DOWNLOADS = new FileType("GIF_TO_DOWNLOADS", 10, "GifSavedToDownloadsHintLinked", C2369R.string.GifSavedToDownloadsHintLinked, icon2);
            int i3 = C2369R.string.AudioSavedHint;
            Icon icon3 = Icon.SAVED_TO_MUSIC;
            AUDIO = new FileType("AUDIO", 11, "AudioSavedHint", i3, icon3);
            AUDIOS = new FileType("AUDIOS", 12, "AudiosSavedHint", icon3);
            UNKNOWN = new FileType("UNKNOWN", 13, "FileSavedHintLinked", C2369R.string.FileSavedHintLinked, icon2);
            UNKNOWNS = new FileType("UNKNOWNS", 14, "FilesSavedHintLinked", icon2);
            $VALUES = $values();
        }

        private FileType(String str, int i, String str2, int i2, Icon icon) {
            this.localeKey = str2;
            this.localeRes = i2;
            this.icon = icon;
            this.plural = false;
        }

        private FileType(String str, int i, String str2, Icon icon) {
            this.localeKey = str2;
            this.icon = icon;
            this.localeRes = 0;
            this.plural = true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String getText(int i) {
            if (this.plural) {
                return LocaleController.formatPluralString(this.localeKey, i, new Object[0]);
            }
            return LocaleController.getString(this.localeKey, this.localeRes);
        }

        private enum Icon {
            SAVED_TO_DOWNLOADS(C2369R.raw.ic_download, 2, "Box", "Arrow"),
            SAVED_TO_GALLERY(C2369R.raw.ic_save_to_gallery, 0, "Box", "Arrow", "Mask", "Arrow 2", "Splash"),
            SAVED_TO_MUSIC(C2369R.raw.ic_save_to_music, 2, "Box", "Arrow"),
            SAVED_TO_GIFS(C2369R.raw.ic_save_to_gifs, 0, "gif");

            private final String[] layers;
            private final int paddingBottom;
            private final int resId;

            Icon(int i, int i2, String... strArr) {
                this.resId = i;
                this.paddingBottom = i2;
                this.layers = strArr;
            }
        }
    }

    private BulletinFactory(BaseFragment baseFragment) {
        if (baseFragment != null && baseFragment.getLastStoryViewer() != null && baseFragment.getLastStoryViewer().attachedToParent()) {
            this.fragment = null;
            this.containerLayout = baseFragment.getLastStoryViewer().getContainerForBulletin();
            this.resourcesProvider = baseFragment.getLastStoryViewer().getResourceProvider();
        } else {
            this.fragment = baseFragment;
            this.containerLayout = null;
            this.resourcesProvider = baseFragment != null ? baseFragment.getResourceProvider() : null;
        }
    }

    private BulletinFactory(FrameLayout frameLayout, Theme.ResourcesProvider resourcesProvider) {
        this.containerLayout = frameLayout;
        this.fragment = null;
        this.resourcesProvider = resourcesProvider;
    }

    public Bulletin createSimpleBulletin(CharSequence charSequence, CharSequence charSequence2, int i) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.setSticker(i);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, 5000);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence) {
        return createSimpleBulletinWithIconSize(i, charSequence, 36);
    }

    public Bulletin createSimpleBulletin(TLRPC.Document document, CharSequence charSequence) {
        if (document == null) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.TwoLineLayout twoLineLayout = new Bulletin.TwoLineLayout(getContext(), this.resourcesProvider);
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, null, false);
        twoLineLayout.imageView.setImage(ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, closestPhotoSizeWithSize, true), document), "28_28", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), "28_28", (String) null, 0L, 0, (Object) null);
        twoLineLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.m1146dp(5.0f));
        twoLineLayout.titleTextView.setText(charSequence);
        twoLineLayout.titleTextView.setSingleLine(false);
        twoLineLayout.titleTextView.setTextSize(1, 15.0f);
        twoLineLayout.titleTextView.setMaxLines(2);
        twoLineLayout.titleTextView.setTypeface(null);
        twoLineLayout.subtitleTextView.setVisibility(8);
        return create(twoLineLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleMultiBulletin(TLRPC.Document document, CharSequence charSequence) {
        if (document == null) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.TwoLineLayout twoLineLayout = new Bulletin.TwoLineLayout(getContext(), this.resourcesProvider);
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, null, false);
        twoLineLayout.imageView.setImage(ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, closestPhotoSizeWithSize, true), document), "28_28", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), "28_28", (String) null, 0L, 0, (Object) null);
        twoLineLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.m1146dp(5.0f));
        twoLineLayout.titleTextView.setSingleLine(false);
        twoLineLayout.titleTextView.setText(charSequence);
        twoLineLayout.titleTextView.setTextSize(1, 14.0f);
        twoLineLayout.titleTextView.setMaxLines(3);
        twoLineLayout.titleTextView.setTypeface(null);
        twoLineLayout.subtitleTextView.setVisibility(8);
        return create(twoLineLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(TLRPC.Document document, CharSequence charSequence, CharSequence charSequence2) {
        if (document == null) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.TwoLineLayout twoLineLayout = new Bulletin.TwoLineLayout(getContext(), this.resourcesProvider);
        TLRPC.PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, null, false);
        twoLineLayout.imageView.setImage(ImageLocation.getForDocument(FileLoader.getClosestPhotoSizeWithSize(document.thumbs, AndroidUtilities.m1146dp(28.0f), true, closestPhotoSizeWithSize, true), document), "28_28", ImageLocation.getForDocument(closestPhotoSizeWithSize, document), "28_28", (String) null, 0L, 0, (Object) null);
        twoLineLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.m1146dp(5.0f));
        twoLineLayout.titleTextView.setText(charSequence);
        twoLineLayout.titleTextView.setSingleLine(true);
        twoLineLayout.titleTextView.setTextSize(1, 15.0f);
        twoLineLayout.titleTextView.setMaxLines(1);
        twoLineLayout.titleTextView.setTypeface(AndroidUtilities.bold());
        twoLineLayout.subtitleTextView.setText(charSequence2);
        twoLineLayout.subtitleTextView.setSingleLine(false);
        twoLineLayout.subtitleTextView.setMaxLines(5);
        return create(twoLineLayout, charSequence2.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletinWithIconSize(int i, CharSequence charSequence, int i2) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, i2, i2, new String[0]);
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(charSequence.toString()));
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletinDetail(int i, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setMaxLines(4);
        return create(lottieLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createImageBulletin(int i, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setBackground(Theme.getColor(Theme.key_undo_background, this.resourcesProvider), 12);
        lottieLayout.imageView.setImageResource(i);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setLines(2);
        lottieLayout.textView.setMaxLines(4);
        TextView textView = lottieLayout.textView;
        textView.setMaxWidth(HintView2.cutInFancyHalf(textView.getText(), lottieLayout.textView.getPaint()));
        lottieLayout.textView.setLineSpacing(AndroidUtilities.m1146dp(1.33f), 1.0f);
        ((ViewGroup.MarginLayoutParams) lottieLayout.textView.getLayoutParams()).rightMargin = AndroidUtilities.m1146dp(12.0f);
        lottieLayout.setWrapWidth();
        return create(lottieLayout, 5000);
    }

    public Bulletin createSimpleLargeBulletin(int i, CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLayout twoLineLayout = new Bulletin.TwoLineLayout(getContext(), this.resourcesProvider);
        twoLineLayout.imageView.setImageResource(i);
        twoLineLayout.titleTextView.setText(charSequence);
        twoLineLayout.subtitleTextView.setText(charSequence2);
        twoLineLayout.subtitleTextView.setSingleLine(false);
        twoLineLayout.subtitleTextView.setMaxLines(5);
        return create(twoLineLayout, 5000);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, int i2) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        if (charSequence != null) {
            String string = charSequence.toString();
            SpannableStringBuilder spannableStringBuilder = charSequence instanceof SpannableStringBuilder ? (SpannableStringBuilder) charSequence : new SpannableStringBuilder(charSequence);
            int i3 = 0;
            for (int iIndexOf = string.indexOf(10); iIndexOf >= 0 && iIndexOf < charSequence.length(); iIndexOf = string.indexOf(10, iIndexOf + 1)) {
                if (i3 >= i2) {
                    spannableStringBuilder.replace(iIndexOf, iIndexOf + 1, (CharSequence) " ");
                }
                i3++;
            }
            charSequence = spannableStringBuilder;
        }
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(i2);
        lottieLayout.textView.setText(charSequence);
        return create(lottieLayout, charSequence.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, int i2, int i3) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(i, 36, 36, new String[0]);
        if (charSequence != null) {
            String string = charSequence.toString();
            SpannableStringBuilder spannableStringBuilder = charSequence instanceof SpannableStringBuilder ? (SpannableStringBuilder) charSequence : new SpannableStringBuilder(charSequence);
            int i4 = 0;
            for (int iIndexOf = string.indexOf(10); iIndexOf >= 0 && iIndexOf < charSequence.length(); iIndexOf = string.indexOf(10, iIndexOf + 1)) {
                if (i4 >= i2) {
                    spannableStringBuilder.replace(iIndexOf, iIndexOf + 1, (CharSequence) " ");
                }
                i4++;
            }
            charSequence = spannableStringBuilder;
        }
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(i2);
        return create(lottieLayout, i3);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.setAnimation(i, 36, 36, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, charSequence.length() + charSequence2.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        return createSimpleBulletin(i, charSequence, charSequence2, charSequence.length() < 20 ? 1500 : 2750, runnable);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, Runnable runnable) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.setAnimation(i, 36, 36, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        twoLineLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence3).setUndoAction(runnable));
        return create(twoLineLottieLayout, 5000);
    }

    public Bulletin createSimpleBulletin(int i, CharSequence charSequence, CharSequence charSequence2, int i2, Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (i != 0) {
            lottieLayout.setAnimation(i, 36, 36, new String[0]);
        } else {
            lottieLayout.imageView.setVisibility(4);
            ((ViewGroup.MarginLayoutParams) lottieLayout.textView.getLayoutParams()).leftMargin = AndroidUtilities.m1146dp(16.0f);
        }
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setTextDirection(5);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(4);
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(charSequence.toString()));
        lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        return create(lottieLayout, i2);
    }

    public Bulletin createSimpleBulletin(Drawable drawable, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.imageView.setImageDrawable(drawable);
        if (drawable instanceof PeerColorActivity.PeerColorDrawable) {
            ((PeerColorActivity.PeerColorDrawable) drawable).setView(lottieLayout.imageView);
        }
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 2750);
    }

    public Bulletin createSimpleBulletin(Drawable drawable, CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.imageView.setImageDrawable(drawable);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, 2750);
    }

    public Bulletin createSimpleBulletin(CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.hideImage();
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, 5000);
    }

    public Bulletin createSimpleBulletin(Drawable drawable, CharSequence charSequence, CharSequence charSequence2, String str, Runnable runnable) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        twoLineLottieLayout.imageView.setImageDrawable(drawable);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        twoLineLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(str).setUndoAction(runnable));
        return create(twoLineLottieLayout, 2750);
    }

    public Bulletin createUndoBulletin(CharSequence charSequence, Runnable runnable, Runnable runnable2) {
        return createUndoBulletin(charSequence, false, runnable, runnable2);
    }

    public Bulletin createUndoBulletin(CharSequence charSequence, boolean z, Runnable runnable, Runnable runnable2) {
        return createUndoBulletin(charSequence, null, z, runnable, runnable2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public Bulletin createUndoBulletin(CharSequence charSequence, CharSequence charSequence2, boolean z, Runnable runnable, Runnable runnable2) {
        Bulletin.LottieLayout lottieLayout;
        if (!TextUtils.isEmpty(charSequence2)) {
            Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
            twoLineLottieLayout.titleTextView.setText(charSequence);
            twoLineLottieLayout.subtitleTextView.setText(charSequence2);
            lottieLayout = twoLineLottieLayout;
        } else {
            Bulletin.LottieLayout lottieLayout2 = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
            lottieLayout2.textView.setText(charSequence);
            lottieLayout2.textView.setSingleLine(false);
            lottieLayout2.textView.setMaxLines(2);
            lottieLayout = lottieLayout2;
        }
        lottieLayout.setTimer();
        lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, z, this.resourcesProvider).setText(LocaleController.getString(C2369R.string.Undo)).setUndoAction(runnable).setDelayedAction(runnable2));
        return create(lottieLayout, 5000);
    }

    public Bulletin createUsersBulletin(List list, CharSequence charSequence) {
        return createUsersBulletin(list, charSequence, null, null);
    }

    public Bulletin createUsersBulletin(TLObject tLObject, CharSequence charSequence, CharSequence charSequence2) {
        return createUsersBulletin(Arrays.asList(tLObject), charSequence, charSequence2, null);
    }

    public Bulletin createUsersBulletin(TLObject tLObject, CharSequence charSequence) {
        return createUsersBulletin(Arrays.asList(tLObject), charSequence, null, null);
    }

    public Bulletin createUsersBulletin(List list, CharSequence charSequence, CharSequence charSequence2) {
        return createUsersBulletin(list, charSequence, charSequence2, null);
    }

    public Bulletin createUsersBulletin(List list, CharSequence charSequence, CharSequence charSequence2, UndoObject undoObject) {
        float f;
        int i;
        Bulletin.UsersLayout usersLayout = new Bulletin.UsersLayout(getContext(), charSequence2 != null, this.resourcesProvider);
        if (list != null) {
            i = 0;
            for (int i2 = 0; i2 < list.size() && i < 3; i2++) {
                TLObject tLObject = (TLObject) list.get(i2);
                if (tLObject != null) {
                    int i3 = i + 1;
                    usersLayout.avatarsImageView.setCount(i3);
                    usersLayout.avatarsImageView.setObject(i, UserConfig.selectedAccount, tLObject);
                    i = i3;
                }
            }
            f = 4.0f;
            if (list.size() == 1) {
                usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.m1146dp(4.0f));
                usersLayout.avatarsImageView.setScaleX(1.2f);
                usersLayout.avatarsImageView.setScaleY(1.2f);
            } else {
                usersLayout.avatarsImageView.setScaleX(1.0f);
                usersLayout.avatarsImageView.setScaleY(1.0f);
            }
        } else {
            f = 4.0f;
            i = 0;
        }
        usersLayout.avatarsImageView.commitTransition(false);
        if (charSequence2 != null) {
            usersLayout.textView.setSingleLine(true);
            usersLayout.textView.setMaxLines(1);
            usersLayout.textView.setText(charSequence);
            usersLayout.subtitleView.setText(charSequence2);
            usersLayout.subtitleView.setSingleLine(false);
            usersLayout.subtitleView.setMaxLines(3);
            if (usersLayout.linearLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int iM1146dp = AndroidUtilities.m1146dp(70 - ((3 - i) * 12));
                if (i == 1) {
                    iM1146dp += AndroidUtilities.m1146dp(f);
                }
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).rightMargin = iM1146dp;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).leftMargin = iM1146dp;
                }
            }
        } else {
            usersLayout.textView.setSingleLine(false);
            usersLayout.textView.setMaxLines(4);
            usersLayout.textView.setText(charSequence);
            if (usersLayout.textView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int iM1146dp2 = AndroidUtilities.m1146dp(70 - ((3 - i) * 12));
                if (i == 1) {
                    usersLayout.textView.setTranslationY(-AndroidUtilities.m1146dp(1.0f));
                    iM1146dp2 += AndroidUtilities.m1146dp(f);
                }
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).rightMargin = iM1146dp2;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).leftMargin = iM1146dp2;
                }
            }
        }
        if (undoObject != null) {
            usersLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(LocaleController.getString(C2369R.string.Undo)).setUndoAction(undoObject.onUndo).setDelayedAction(undoObject.onAction));
        }
        return create(usersLayout, 5000);
    }

    public Bulletin createChatsBulletin(List list, CharSequence charSequence, CharSequence charSequence2) {
        int i;
        Bulletin.UsersLayout usersLayout = new Bulletin.UsersLayout(getContext(), charSequence2 != null, this.resourcesProvider);
        if (list != null) {
            i = 0;
            for (int i2 = 0; i2 < list.size() && i < 3; i2++) {
                TLObject tLObject = (TLObject) list.get(i2);
                if (tLObject != null) {
                    int i3 = i + 1;
                    usersLayout.avatarsImageView.setCount(i3);
                    usersLayout.avatarsImageView.setObject(i, UserConfig.selectedAccount, tLObject);
                    i = i3;
                }
            }
            if (list.size() == 1) {
                usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.m1146dp(4.0f));
                usersLayout.avatarsImageView.setScaleX(1.2f);
                usersLayout.avatarsImageView.setScaleY(1.2f);
            } else {
                usersLayout.avatarsImageView.setScaleX(1.0f);
                usersLayout.avatarsImageView.setScaleY(1.0f);
            }
        } else {
            i = 0;
        }
        usersLayout.avatarsImageView.commitTransition(false);
        if (charSequence2 != null) {
            usersLayout.textView.setSingleLine(true);
            usersLayout.textView.setMaxLines(1);
            usersLayout.textView.setText(charSequence);
            usersLayout.subtitleView.setText(charSequence2);
            usersLayout.subtitleView.setSingleLine(true);
            usersLayout.subtitleView.setMaxLines(1);
            if (usersLayout.linearLayout.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int iM1146dp = AndroidUtilities.m1146dp(74 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).rightMargin = iM1146dp;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.linearLayout.getLayoutParams()).leftMargin = iM1146dp;
                }
            }
        } else {
            usersLayout.textView.setSingleLine(false);
            usersLayout.textView.setMaxLines(2);
            usersLayout.textView.setText(charSequence);
            if (usersLayout.textView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                int iM1146dp2 = AndroidUtilities.m1146dp(74 - ((3 - i) * 12));
                if (LocaleController.isRTL) {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).rightMargin = iM1146dp2;
                } else {
                    ((ViewGroup.MarginLayoutParams) usersLayout.textView.getLayoutParams()).leftMargin = iM1146dp2;
                }
            }
        }
        if (LocaleController.isRTL) {
            usersLayout.avatarsImageView.setTranslationX(AndroidUtilities.m1146dp(32 - ((i - 1) * 12)));
        }
        return create(usersLayout, 5000);
    }

    public Bulletin createUsersAddedBulletin(ArrayList arrayList, TLRPC.Chat chat) {
        SpannableStringBuilder spannableStringBuilderReplaceTags;
        if (arrayList == null || arrayList.size() == 0) {
            spannableStringBuilderReplaceTags = null;
        } else if (arrayList.size() == 1) {
            if (ChatObject.isChannelAndNotMegaGroup(chat)) {
                spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString("HasBeenAddedToChannel", C2369R.string.HasBeenAddedToChannel, "**" + UserObject.getFirstName((TLRPC.User) arrayList.get(0)) + "**"));
            } else {
                spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString("HasBeenAddedToGroup", C2369R.string.HasBeenAddedToGroup, "**" + UserObject.getFirstName((TLRPC.User) arrayList.get(0)) + "**"));
            }
        } else if (ChatObject.isChannelAndNotMegaGroup(chat)) {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatPluralString("AddedMembersToChannel", arrayList.size(), new Object[0]));
        } else {
            spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatPluralString("AddedSubscribersToChannel", arrayList.size(), new Object[0]));
        }
        return createUsersBulletin(arrayList, spannableStringBuilderReplaceTags);
    }

    public Bulletin createEmojiBulletin(String str, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(MediaDataController.getInstance(UserConfig.selectedAccount).getEmojiAnimatedSticker(str), 36, 36, new String[0]);
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(charSequence));
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 2750);
    }

    public Bulletin createEmojiBulletin(TLRPC.Document document, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(document)) {
            lottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        lottieLayout.setAnimation(document, 36, 36, new String[0]);
        if (lottieLayout.imageView.getImageReceiver() != null) {
            lottieLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.m1146dp(5.0f));
        }
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        return create(lottieLayout, 2750);
    }

    public Bulletin createEmojiBulletin(TLRPC.Document document, CharSequence charSequence, CharSequence charSequence2) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(document)) {
            twoLineLottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        twoLineLottieLayout.setAnimation(document, 36, 36, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, charSequence.length() + charSequence2.length() < 20 ? 1500 : 2750);
    }

    public Bulletin createStaticEmojiBulletin(TLRPC.Document document, CharSequence charSequence) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(document)) {
            lottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        lottieLayout.setAnimation(document, 36, 36, new String[0]);
        lottieLayout.imageView.stopAnimation();
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        return create(lottieLayout, 2750);
    }

    public Bulletin createEmojiBulletin(TLRPC.Document document, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(document)) {
            lottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        lottieLayout.setAnimation(document, 36, 36, new String[0]);
        if (lottieLayout.imageView.getImageReceiver() != null) {
            lottieLayout.imageView.getImageReceiver().setRoundRadius(AndroidUtilities.m1146dp(4.0f));
        }
        TextView textView = lottieLayout.textView;
        if (charSequence.toString().contains("**")) {
            charSequence = AndroidUtilities.replaceTags(charSequence);
        }
        textView.setText(charSequence);
        lottieLayout.textView.setTextSize(1, 14.0f);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(4);
        if (charSequence2 != null && runnable != null) {
            lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        }
        return create(lottieLayout, 2750);
    }

    public Bulletin createEmojiLoadingBulletin(TLRPC.Document document, CharSequence charSequence, CharSequence charSequence2, Runnable runnable) {
        Bulletin.LoadingLottieLayout loadingLottieLayout = new Bulletin.LoadingLottieLayout(getContext(), this.resourcesProvider);
        if (MessageObject.isTextColorEmoji(document)) {
            loadingLottieLayout.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_undo_infoColor), PorterDuff.Mode.SRC_IN));
        }
        loadingLottieLayout.setAnimation(document, 36, 36, new String[0]);
        loadingLottieLayout.textView.setTextSize(1, 14.0f);
        loadingLottieLayout.textView.setSingleLine(false);
        loadingLottieLayout.textView.setMaxLines(3);
        loadingLottieLayout.textLoadingView.setText(charSequence);
        loadingLottieLayout.textLoadingView.setTextSize(1, 14.0f);
        loadingLottieLayout.textLoadingView.setSingleLine(false);
        loadingLottieLayout.textLoadingView.setMaxLines(3);
        loadingLottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(charSequence2).setUndoAction(runnable));
        return create(loadingLottieLayout, 2750);
    }

    public Bulletin createContainsEmojiBulletin(TLRPC.Document document, final int i, final Utilities.Callback callback) {
        SpannableStringBuilder spannableStringBuilder;
        LoadingSpan loadingSpan;
        TLRPC.StickerSet stickerSet;
        CharSequence charSequenceReplaceTags;
        final TLRPC.InputStickerSet inputStickerSet = MessageObject.getInputStickerSet(document);
        if (inputStickerSet == null) {
            return null;
        }
        TLRPC.TL_messages_stickerSet stickerSet2 = MediaDataController.getInstance(UserConfig.selectedAccount).getStickerSet(inputStickerSet, true);
        if (stickerSet2 == null || (stickerSet = stickerSet2.set) == null) {
            if (i == 1) {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", C2369R.string.TopicContainsEmojiPackSingle, "<{LOADING}>")));
            } else if (i == 2) {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString("StoryContainsEmojiPackSingle", C2369R.string.StoryContainsEmojiPackSingle, "<{LOADING}>")));
            } else {
                spannableStringBuilder = new SpannableStringBuilder(AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", C2369R.string.MessageContainsEmojiPackSingle, "<{LOADING}>")));
            }
            int iIndexOf = spannableStringBuilder.toString().indexOf("<{LOADING}>");
            if (iIndexOf >= 0) {
                loadingSpan = new LoadingSpan(null, AndroidUtilities.m1146dp(100.0f), AndroidUtilities.m1146dp(2.0f), this.resourcesProvider);
                spannableStringBuilder.setSpan(loadingSpan, iIndexOf, iIndexOf + 11, 33);
                int i2 = Theme.key_undo_infoColor;
                loadingSpan.setColors(ColorUtils.setAlphaComponent(Theme.getColor(i2, this.resourcesProvider), 32), ColorUtils.setAlphaComponent(Theme.getColor(i2, this.resourcesProvider), 72));
            } else {
                loadingSpan = null;
            }
            final long jCurrentTimeMillis = System.currentTimeMillis();
            final Bulletin bulletinCreateEmojiLoadingBulletin = createEmojiLoadingBulletin(document, spannableStringBuilder, LocaleController.getString(C2369R.string.ViewAction), new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    callback.run(inputStickerSet);
                }
            });
            if (loadingSpan != null && (bulletinCreateEmojiLoadingBulletin.getLayout() instanceof Bulletin.LoadingLottieLayout)) {
                loadingSpan.setView(((Bulletin.LoadingLottieLayout) bulletinCreateEmojiLoadingBulletin.getLayout()).textLoadingView);
            }
            MediaDataController.getInstance(UserConfig.selectedAccount).getStickerSet(inputStickerSet, null, false, new Utilities.Callback() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda3
                @Override // org.telegram.messenger.Utilities.Callback
                public final void run(Object obj) {
                    BulletinFactory.$r8$lambda$yygjfMhXwGKIBOss6ktGnM1K6ow(i, bulletinCreateEmojiLoadingBulletin, jCurrentTimeMillis, (TLRPC.TL_messages_stickerSet) obj);
                }
            });
            return bulletinCreateEmojiLoadingBulletin;
        }
        if (i == 1) {
            charSequenceReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", C2369R.string.TopicContainsEmojiPackSingle, stickerSet.title));
        } else {
            charSequenceReplaceTags = i == 2 ? AndroidUtilities.replaceTags(LocaleController.formatString("StoryContainsEmojiPackSingle", C2369R.string.StoryContainsEmojiPackSingle, stickerSet.title)) : AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", C2369R.string.MessageContainsEmojiPackSingle, stickerSet.title));
        }
        return createEmojiBulletin(document, charSequenceReplaceTags, LocaleController.getString(C2369R.string.ViewAction), new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                callback.run(inputStickerSet);
            }
        });
    }

    public static /* synthetic */ void $r8$lambda$yygjfMhXwGKIBOss6ktGnM1K6ow(int i, final Bulletin bulletin, long j, TLRPC.TL_messages_stickerSet tL_messages_stickerSet) {
        final CharSequence string;
        TLRPC.StickerSet stickerSet;
        if (tL_messages_stickerSet == null || (stickerSet = tL_messages_stickerSet.set) == null) {
            string = LocaleController.getString(C2369R.string.AddEmojiNotFound);
        } else {
            string = i == 1 ? AndroidUtilities.replaceTags(LocaleController.formatString("TopicContainsEmojiPackSingle", C2369R.string.TopicContainsEmojiPackSingle, stickerSet.title)) : i == 2 ? AndroidUtilities.replaceTags(LocaleController.formatString("StoryContainsEmojiPackSingle", C2369R.string.StoryContainsEmojiPackSingle, stickerSet.title)) : AndroidUtilities.replaceTags(LocaleController.formatString("MessageContainsEmojiPackSingle", C2369R.string.MessageContainsEmojiPackSingle, stickerSet.title));
        }
        AndroidUtilities.runOnUIThread(new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                bulletin.onLoaded(string);
            }
        }, Math.max(1L, 750 - (System.currentTimeMillis() - j)));
    }

    public Bulletin createDownloadBulletin(FileType fileType) {
        return createDownloadBulletin(fileType, this.resourcesProvider);
    }

    public Bulletin createDownloadBulletin(FileType fileType, Theme.ResourcesProvider resourcesProvider) {
        return createDownloadBulletin(fileType, 1, resourcesProvider);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, Theme.ResourcesProvider resourcesProvider) {
        return createDownloadBulletin(fileType, i, 0, 0, resourcesProvider);
    }

    public Bulletin createReportSent(Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.chats_infotip, new String[0]);
        lottieLayout.textView.setText(LocaleController.getString(C2369R.string.ReportChatSent));
        return create(lottieLayout, 1500);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, int i2, int i3) {
        return createDownloadBulletin(fileType, i, i2, i3, null);
    }

    public Bulletin createDownloadBulletin(FileType fileType, int i, int i2, int i3, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout;
        if (i2 != 0 && i3 != 0) {
            lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider, i2, i3);
        } else {
            lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        }
        lottieLayout.setAnimation(fileType.icon.resId, fileType.icon.layers);
        lottieLayout.textView.setText(AndroidUtilities.replaceSingleTag(fileType.getText(i), new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BulletinFactory.m7987$r8$lambda$8J3OiIFslIyIXkHdLTVDv63vFs();
            }
        }));
        if (fileType.icon.paddingBottom != 0) {
            lottieLayout.setIconPaddingBottom(fileType.icon.paddingBottom);
        }
        return create(lottieLayout, 1500);
    }

    /* renamed from: $r8$lambda$8J3OiIFslIyIXkHdLTVDv63vF-s, reason: not valid java name */
    public static /* synthetic */ void m7987$r8$lambda$8J3OiIFslIyIXkHdLTVDv63vFs() {
        LaunchActivity launchActivity = LaunchActivity.instance;
        if (launchActivity == null || launchActivity.isFinishing()) {
            return;
        }
        Intent intent = new Intent("android.intent.action.VIEW_DOWNLOADS");
        intent.setFlags(268468224);
        LaunchActivity.instance.startActivity(intent);
    }

    public Bulletin createErrorBulletin(CharSequence charSequence) {
        return createErrorBulletin(charSequence, null);
    }

    public Bulletin createErrorBulletin(CharSequence charSequence, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.chats_infotip, new String[0]);
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(charSequence.toString()));
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 1500);
    }

    public Bulletin createSuccessBulletin(CharSequence charSequence) {
        return createSuccessBulletin(charSequence, null);
    }

    public Bulletin createSuccessBulletin(CharSequence charSequence, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.contact_check, new String[0]);
        lottieLayout.textView.setText(charSequence);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 1500);
    }

    public Bulletin createCaptionLimitBulletin(int i, final Runnable runnable) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(C2369R.raw.caption_limit, new String[0]);
        String pluralString = LocaleController.formatPluralString("ChannelCaptionLimitPremiumPromo", i, new Object[0]);
        SpannableStringBuilder spannableStringBuilderValueOf = SpannableStringBuilder.valueOf(AndroidUtilities.replaceTags(pluralString));
        int iIndexOf = pluralString.indexOf(42);
        int i2 = iIndexOf + 1;
        int iIndexOf2 = pluralString.indexOf(42, i2);
        spannableStringBuilderValueOf.replace(iIndexOf, iIndexOf2 + 1, (CharSequence) pluralString.substring(i2, iIndexOf2));
        spannableStringBuilderValueOf.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.BulletinFactory.1
            @Override // android.text.style.ClickableSpan
            public void onClick(View view) {
                runnable.run();
            }

            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
            }
        }, iIndexOf, iIndexOf2 - 1, 33);
        lottieLayout.textView.setText(spannableStringBuilderValueOf);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        return create(lottieLayout, 5000);
    }

    public Bulletin createRestrictVoiceMessagesPremiumBulletin() {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(C2369R.raw.voip_muted, new String[0]);
        String string = LocaleController.getString(C2369R.string.PrivacyVoiceMessagesPremiumOnly);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(string);
        int iIndexOf = string.indexOf(42);
        int iLastIndexOf = string.lastIndexOf(42);
        if (iIndexOf >= 0) {
            spannableStringBuilder.replace(iIndexOf, iLastIndexOf + 1, (CharSequence) string.substring(iIndexOf + 1, iLastIndexOf));
            spannableStringBuilder.setSpan(new ClickableSpan() { // from class: org.telegram.ui.Components.BulletinFactory.2
                @Override // android.text.style.ClickableSpan
                public void onClick(View view) {
                    BulletinFactory.this.fragment.presentFragment(new PremiumPreviewFragment("settings"));
                }

                @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
                public void updateDrawState(TextPaint textPaint) {
                    super.updateDrawState(textPaint);
                    textPaint.setUnderlineText(false);
                }
            }, iIndexOf, iLastIndexOf - 1, 33);
        }
        lottieLayout.textView.setText(spannableStringBuilder);
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(2);
        return create(lottieLayout, 2750);
    }

    public Bulletin createErrorBulletinSubtitle(CharSequence charSequence, CharSequence charSequence2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), resourcesProvider);
        twoLineLottieLayout.setAnimation(C2369R.raw.chats_infotip, new String[0]);
        twoLineLottieLayout.titleTextView.setText(charSequence);
        twoLineLottieLayout.subtitleTextView.setText(charSequence2);
        return create(twoLineLottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin() {
        return createCopyLinkBulletin(false);
    }

    public Bulletin createCopyBulletin(String str) {
        return createCopyBulletin(str, null);
    }

    public Bulletin createCopyBulletin(String str, Theme.ResourcesProvider resourcesProvider) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), null);
        lottieLayout.setAnimation(C2369R.raw.copy, 36, 36, "NULL ROTATION", "Back", "Front");
        lottieLayout.textView.setText(str);
        return create(lottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin(boolean z) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        if (z) {
            Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(getContext(), this.resourcesProvider);
            twoLineLottieLayout.setAnimation(C2369R.raw.voip_invite, 36, 36, "Wibe", "Circle");
            twoLineLottieLayout.titleTextView.setText(LocaleController.getString(C2369R.string.LinkCopied));
            twoLineLottieLayout.subtitleTextView.setText(LocaleController.getString(C2369R.string.LinkCopiedPrivateInfo));
            return create(twoLineLottieLayout, 2750);
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.voip_invite, 36, 36, "Wibe", "Circle");
        lottieLayout.textView.setText(LocaleController.getString(C2369R.string.LinkCopied));
        return create(lottieLayout, 1500);
    }

    public Bulletin createCopyLinkBulletin(String str, Theme.ResourcesProvider resourcesProvider) {
        if (!AndroidUtilities.shouldShowClipboardToast()) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.voip_invite, 36, 36, "Wibe", "Circle");
        lottieLayout.textView.setText(str);
        return create(lottieLayout, 1500);
    }

    public Bulletin create(Bulletin.Layout layout, int i) {
        BaseFragment baseFragment = this.fragment;
        if (baseFragment != null) {
            return Bulletin.make(baseFragment, layout, i);
        }
        return Bulletin.make(this.containerLayout, layout, i);
    }

    public Context getContext() {
        Context context;
        BaseFragment baseFragment = this.fragment;
        if (baseFragment != null) {
            context = baseFragment.getParentActivity();
            if (context == null && this.fragment.getLayoutContainer() != null) {
                context = this.fragment.getLayoutContainer().getContext();
            }
        } else {
            FrameLayout frameLayout = this.containerLayout;
            context = frameLayout != null ? frameLayout.getContext() : null;
        }
        return context == null ? ApplicationLoader.applicationContext : context;
    }

    public Theme.ResourcesProvider getResourcesProvider() {
        return this.resourcesProvider;
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, int i) {
        return createMuteBulletin(baseFragment, i, 0, (Theme.ResourcesProvider) null);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.telegram.p023ui.Components.Bulletin createMuteBulletin(org.telegram.p023ui.ActionBar.BaseFragment r5, int r6, int r7, org.telegram.ui.ActionBar.Theme.ResourcesProvider r8) {
        /*
            org.telegram.ui.Components.Bulletin$LottieLayout r0 = new org.telegram.ui.Components.Bulletin$LottieLayout
            android.app.Activity r1 = r5.getParentActivity()
            r0.<init>(r1, r8)
            java.lang.String r8 = "Hours"
            java.lang.String r1 = "NotificationsMutedForHint"
            r2 = 1
            r3 = 0
            if (r6 == 0) goto L6c
            if (r6 == r2) goto L59
            r8 = 2
            if (r6 == r8) goto L46
            r8 = 3
            if (r6 == r8) goto L3e
            r8 = 4
            if (r6 == r8) goto L35
            r8 = 5
            if (r6 != r8) goto L2f
            int r6 = org.telegram.messenger.C2369R.string.NotificationsMutedForHint
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatTTLString(r7)
            java.lang.Object[] r8 = new java.lang.Object[r2]
            r8[r3] = r7
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r6, r8)
            r7 = 1
            goto L7d
        L2f:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            r5.<init>()
            throw r5
        L35:
            int r6 = org.telegram.messenger.C2369R.string.NotificationsUnmutedHint
            java.lang.String r6 = org.telegram.messenger.LocaleController.getString(r6)
            r7 = 0
        L3c:
            r2 = 0
            goto L7d
        L3e:
            int r6 = org.telegram.messenger.C2369R.string.NotificationsMutedHint
            java.lang.String r6 = org.telegram.messenger.LocaleController.getString(r6)
        L44:
            r7 = 1
            goto L3c
        L46:
            int r6 = org.telegram.messenger.C2369R.string.NotificationsMutedForHint
            java.lang.String r7 = "Days"
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatPluralString(r7, r8, r4)
            java.lang.Object[] r8 = new java.lang.Object[r2]
            r8[r3] = r7
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r6, r8)
            goto L44
        L59:
            int r6 = org.telegram.messenger.C2369R.string.NotificationsMutedForHint
            r7 = 8
            java.lang.Object[] r4 = new java.lang.Object[r3]
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatPluralString(r8, r7, r4)
            java.lang.Object[] r8 = new java.lang.Object[r2]
            r8[r3] = r7
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r6, r8)
            goto L44
        L6c:
            int r6 = org.telegram.messenger.C2369R.string.NotificationsMutedForHint
            java.lang.Object[] r7 = new java.lang.Object[r3]
            java.lang.String r7 = org.telegram.messenger.LocaleController.formatPluralString(r8, r2, r7)
            java.lang.Object[] r8 = new java.lang.Object[r2]
            r8[r3] = r7
            java.lang.String r6 = org.telegram.messenger.LocaleController.formatString(r1, r6, r8)
            goto L44
        L7d:
            if (r2 == 0) goto L87
            int r7 = org.telegram.messenger.C2369R.raw.mute_for
            java.lang.String[] r8 = new java.lang.String[r3]
            r0.setAnimation(r7, r8)
            goto Lae
        L87:
            if (r7 == 0) goto L9d
            int r7 = org.telegram.messenger.C2369R.raw.ic_mute
            java.lang.String r8 = "Curve Big"
            java.lang.String r1 = "Curve Small"
            java.lang.String r2 = "Body Main"
            java.lang.String r3 = "Body Top"
            java.lang.String r4 = "Line"
            java.lang.String[] r8 = new java.lang.String[]{r2, r3, r4, r8, r1}
            r0.setAnimation(r7, r8)
            goto Lae
        L9d:
            int r7 = org.telegram.messenger.C2369R.raw.ic_unmute
            java.lang.String r8 = "Wibe Big 3"
            java.lang.String r1 = "Wibe Small"
            java.lang.String r2 = "BODY"
            java.lang.String r3 = "Wibe Big"
            java.lang.String[] r8 = new java.lang.String[]{r2, r3, r8, r1}
            r0.setAnimation(r7, r8)
        Lae:
            android.widget.TextView r7 = r0.textView
            r7.setText(r6)
            r6 = 1500(0x5dc, float:2.102E-42)
            org.telegram.ui.Components.Bulletin r5 = org.telegram.p023ui.Components.Bulletin.make(r5, r0, r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.BulletinFactory.createMuteBulletin(org.telegram.ui.ActionBar.BaseFragment, int, int, org.telegram.ui.ActionBar.Theme$ResourcesProvider):org.telegram.ui.Components.Bulletin");
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, boolean z, int i, Theme.ResourcesProvider resourcesProvider) {
        String pluralString;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        TextView textView = lottieLayout.textView;
        if (z) {
            pluralString = LocaleController.formatPluralString("NotificationsMutedHintChats", i, new Object[0]);
        } else {
            pluralString = LocaleController.formatPluralString("NotificationsUnmutedHintChats", i, new Object[0]);
        }
        textView.setText(pluralString);
        if (z) {
            lottieLayout.setAnimation(C2369R.raw.ic_mute, "Body Main", "Body Top", "Line", "Curve Big", "Curve Small");
        } else {
            lottieLayout.setAnimation(C2369R.raw.ic_unmute, "BODY", "Wibe Big", "Wibe Big 3", "Wibe Small");
        }
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createMuteBulletin(BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
        return createMuteBulletin(baseFragment, z ? 3 : 4, 0, resourcesProvider);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Bulletin createUnpinAllMessagesBulletin(BaseFragment baseFragment, int i, boolean z, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout;
        if (baseFragment.getParentActivity() == null) {
            if (runnable2 == null) {
                return null;
            }
            runnable2.run();
            return null;
        }
        if (z) {
            Bulletin.TwoLineLottieLayout twoLineLottieLayout = new Bulletin.TwoLineLottieLayout(baseFragment.getParentActivity(), resourcesProvider);
            twoLineLottieLayout.setAnimation(C2369R.raw.ic_unpin, 28, 28, "Pin", "Line");
            twoLineLottieLayout.titleTextView.setText(LocaleController.getString(C2369R.string.PinnedMessagesHidden));
            twoLineLottieLayout.subtitleTextView.setText(LocaleController.getString(C2369R.string.PinnedMessagesHiddenInfo));
            lottieLayout = twoLineLottieLayout;
        } else {
            Bulletin.LottieLayout lottieLayout2 = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
            lottieLayout2.setAnimation(C2369R.raw.ic_unpin, 28, 28, "Pin", "Line");
            lottieLayout2.textView.setText(LocaleController.formatPluralString("MessagesUnpinned", i, new Object[0]));
            lottieLayout = lottieLayout2;
        }
        lottieLayout.setButton(new Bulletin.UndoButton(baseFragment.getParentActivity(), true, resourcesProvider).setUndoAction(runnable).setDelayedAction(runnable2));
        return Bulletin.make(baseFragment, lottieLayout, 5000);
    }

    public static Bulletin createSaveToGalleryBulletin(BaseFragment baseFragment, boolean z, Theme.ResourcesProvider resourcesProvider) {
        return m1267of(baseFragment).createDownloadBulletin(z ? FileType.VIDEO : FileType.PHOTO, resourcesProvider);
    }

    public static Bulletin createSaveToGalleryBulletin(FrameLayout frameLayout, boolean z, Theme.ResourcesProvider resourcesProvider) {
        return m1266of(frameLayout, resourcesProvider).createDownloadBulletin(z ? FileType.VIDEO : FileType.PHOTO, resourcesProvider);
    }

    public static Bulletin createSaveToGalleryBulletin(FrameLayout frameLayout, boolean z, int i, int i2) {
        return m1266of(frameLayout, null).createDownloadBulletin(z ? FileType.VIDEO : FileType.PHOTO, 1, i, i2);
    }

    public static Bulletin createSaveToGalleryBulletin(FrameLayout frameLayout, int i, boolean z, int i2, int i3) {
        return m1266of(frameLayout, null).createDownloadBulletin(z ? i > 1 ? FileType.VIDEOS : FileType.VIDEO : i > 1 ? FileType.PHOTOS : FileType.PHOTO, i, i2, i3);
    }

    public static Bulletin createPromoteToAdminBulletin(BaseFragment baseFragment, String str) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(C2369R.raw.ic_admin, "Shield");
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserSetAsAdminHint", C2369R.string.UserSetAsAdminHint, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createAddedAsAdminBulletin(BaseFragment baseFragment, String str) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(C2369R.raw.ic_admin, "Shield");
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserAddedAsAdminHint", C2369R.string.UserAddedAsAdminHint, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static org.telegram.p023ui.Components.Bulletin createInviteSentBulletin(android.content.Context r3, android.widget.FrameLayout r4, int r5, long r6, int r8, int r9, int r10) {
        /*
            org.telegram.ui.Components.Bulletin$LottieLayout r8 = new org.telegram.ui.Components.Bulletin$LottieLayout
            r0 = 0
            r8.<init>(r3, r0, r9, r10)
            r3 = 300(0x12c, float:4.2E-43)
            r9 = 0
            r10 = 30
            r0 = 1
            if (r5 > r0) goto L83
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.UserConfig r5 = org.telegram.messenger.UserConfig.getInstance(r5)
            long r1 = r5.clientUserId
            int r5 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r5 != 0) goto L2e
            int r3 = org.telegram.messenger.C2369R.string.InvLinkToSavedMessages
            java.lang.String r3 = org.telegram.messenger.LocaleController.getString(r3)
            android.text.SpannableStringBuilder r3 = org.telegram.messenger.AndroidUtilities.replaceTags(r3)
            int r5 = org.telegram.messenger.C2369R.raw.saved_messages
            java.lang.String[] r6 = new java.lang.String[r9]
            r8.setAnimation(r5, r10, r10, r6)
            r5 = -1
            goto La3
        L2e:
            boolean r5 = org.telegram.messenger.DialogObject.isChatDialog(r6)
            if (r5 == 0) goto L56
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            long r6 = -r6
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$Chat r5 = r5.getChat(r6)
            int r6 = org.telegram.messenger.C2369R.string.InvLinkToGroup
            java.lang.String r5 = r5.title
            java.lang.Object[] r7 = new java.lang.Object[r0]
            r7[r9] = r5
            java.lang.String r5 = "InvLinkToGroup"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
            goto L78
        L56:
            int r5 = org.telegram.messenger.UserConfig.selectedAccount
            org.telegram.messenger.MessagesController r5 = org.telegram.messenger.MessagesController.getInstance(r5)
            java.lang.Long r6 = java.lang.Long.valueOf(r6)
            org.telegram.tgnet.TLRPC$User r5 = r5.getUser(r6)
            int r6 = org.telegram.messenger.C2369R.string.InvLinkToUser
            java.lang.String r5 = org.telegram.messenger.UserObject.getFirstName(r5)
            java.lang.Object[] r7 = new java.lang.Object[r0]
            r7[r9] = r5
            java.lang.String r5 = "InvLinkToUser"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
        L78:
            int r6 = org.telegram.messenger.C2369R.raw.forward
            java.lang.String[] r7 = new java.lang.String[r9]
            r8.setAnimation(r6, r10, r10, r7)
        L7f:
            r3 = r5
            r5 = 300(0x12c, float:4.2E-43)
            goto La3
        L83:
            int r6 = org.telegram.messenger.C2369R.string.InvLinkToChats
            java.lang.String r7 = "Chats"
            java.lang.Object[] r1 = new java.lang.Object[r9]
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatPluralString(r7, r5, r1)
            java.lang.Object[] r7 = new java.lang.Object[r0]
            r7[r9] = r5
            java.lang.String r5 = "InvLinkToChats"
            java.lang.String r5 = org.telegram.messenger.LocaleController.formatString(r5, r6, r7)
            android.text.SpannableStringBuilder r5 = org.telegram.messenger.AndroidUtilities.replaceTags(r5)
            int r6 = org.telegram.messenger.C2369R.raw.forward
            java.lang.String[] r7 = new java.lang.String[r9]
            r8.setAnimation(r6, r10, r10, r7)
            goto L7f
        La3:
            android.widget.TextView r6 = r8.textView
            r6.setText(r3)
            if (r5 <= 0) goto Lb3
            org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda9 r3 = new org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda9
            r3.<init>()
            long r5 = (long) r5
            r8.postDelayed(r3, r5)
        Lb3:
            r3 = 1500(0x5dc, float:2.102E-42)
            org.telegram.ui.Components.Bulletin r3 = org.telegram.p023ui.Components.Bulletin.make(r4, r8, r3)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.p023ui.Components.BulletinFactory.createInviteSentBulletin(android.content.Context, android.widget.FrameLayout, int, long, int, int, int):org.telegram.ui.Components.Bulletin");
    }

    public Bulletin createAdReportedBulletin(CharSequence charSequence) {
        if (getContext() == null) {
            return new Bulletin.EmptyBulletin();
        }
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.ic_admin, "Shield");
        lottieLayout.textView.setSingleLine(false);
        lottieLayout.textView.setMaxLines(3);
        lottieLayout.textView.setText(charSequence);
        return create(lottieLayout, 2750);
    }

    public boolean showForwardedBulletinWithTag(long j, int i) {
        SpannableStringBuilder spannableStringBuilderReplaceSingleTag;
        if (!UserConfig.getInstance(UserConfig.selectedAccount).isPremium()) {
            return false;
        }
        Bulletin.LottieLayoutWithReactions lottieLayoutWithReactions = new Bulletin.LottieLayoutWithReactions(this.fragment, i);
        if (j != UserConfig.getInstance(UserConfig.selectedAccount).clientUserId) {
            return false;
        }
        if (i <= 1) {
            spannableStringBuilderReplaceSingleTag = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.FwdMessageToSavedMessages), -1, 2, new BulletinFactory$$ExternalSyntheticLambda1());
        } else {
            spannableStringBuilderReplaceSingleTag = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.FwdMessagesToSavedMessages), -1, 2, new BulletinFactory$$ExternalSyntheticLambda1());
        }
        lottieLayoutWithReactions.setAnimation(C2369R.raw.saved_messages, 36, 36, new String[0]);
        lottieLayoutWithReactions.textView.setText(spannableStringBuilderReplaceSingleTag);
        lottieLayoutWithReactions.textView.setSingleLine(false);
        lottieLayoutWithReactions.textView.setMaxLines(2);
        Bulletin bulletinCreate = create(lottieLayoutWithReactions, 3500);
        lottieLayoutWithReactions.setBulletin(bulletinCreate);
        bulletinCreate.hideAfterBottomSheet(false);
        bulletinCreate.show(true);
        return true;
    }

    public static Bulletin createForwardedBulletin(Context context, BaseFragment baseFragment, FrameLayout frameLayout, int i, long j, int i2, int i3, int i4, int i5) {
        return createForwardedBulletin(context, baseFragment, frameLayout, i, j, i2, i3, i4, i5, null, null);
    }

    public static Bulletin createForwardedBulletin(Context context, final BaseFragment baseFragment, FrameLayout frameLayout, int i, final long j, int i2, int i3, int i4, int i5, Runnable runnable, final Runnable runnable2) {
        final Bulletin.LottieLayout lottieLayout;
        SpannableStringBuilder spannableStringBuilderReplaceTags;
        Bulletin bulletinMake;
        if (UserConfig.getInstance(UserConfig.selectedAccount).isPremium() && baseFragment != null && i <= 1 && j == UserConfig.getInstance(UserConfig.selectedAccount).clientUserId) {
            lottieLayout = new Bulletin.LottieLayoutWithReactions(baseFragment, i2);
        } else {
            lottieLayout = new Bulletin.LottieLayout(context, baseFragment != null ? baseFragment.getResourceProvider() : null, i3, i4);
        }
        boolean z = (runnable2 == null && runnable == null) ? false : true;
        final boolean[] zArr = {false};
        final Runnable runnable3 = runnable2 != null ? new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                BulletinFactory.$r8$lambda$cmdwCWTjWScaKVp1N2mPSyjQJGc(zArr, runnable2);
            }
        } : null;
        if (i <= 1) {
            Bulletin.LottieLayout lottieLayout2 = lottieLayout;
            if (j == UserConfig.getInstance(UserConfig.selectedAccount).clientUserId) {
                if (i2 <= 1) {
                    spannableStringBuilderReplaceTags = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.FwdMessageToSavedMessages), -1, 2, new BulletinFactory$$ExternalSyntheticLambda1());
                } else {
                    spannableStringBuilderReplaceTags = AndroidUtilities.replaceSingleTag(LocaleController.getString(C2369R.string.FwdMessagesToSavedMessages), -1, 2, new BulletinFactory$$ExternalSyntheticLambda1());
                }
                lottieLayout = lottieLayout2;
                lottieLayout.setAnimation(C2369R.raw.saved_messages, 30, 30, new String[0]);
            } else {
                lottieLayout = lottieLayout2;
                Runnable runnable4 = new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        BulletinFactory.$r8$lambda$rGqRMmPy33W71zUl4SsdlueKl8w(runnable3, baseFragment, j);
                    }
                };
                if (DialogObject.isChatDialog(j)) {
                    TLRPC.Chat chat = MessagesController.getInstance(UserConfig.selectedAccount).getChat(Long.valueOf(-j));
                    spannableStringBuilderReplaceTags = i2 <= 1 ? baseFragment != null ? AndroidUtilities.replaceSingleTag(LocaleController.formatString(C2369R.string.FwdMessageToGroup, chat.title), -1, 2, runnable4) : AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.FwdMessageToGroup, chat.title)) : baseFragment != null ? AndroidUtilities.replaceSingleTag(LocaleController.formatString(C2369R.string.FwdMessagesToGroup, chat.title), -1, 2, runnable4) : AndroidUtilities.replaceTags(LocaleController.formatString(C2369R.string.FwdMessagesToGroup, chat.title));
                } else {
                    TLRPC.User user = MessagesController.getInstance(UserConfig.selectedAccount).getUser(Long.valueOf(j));
                    if (i2 <= 1) {
                        int i6 = z ? C2369R.string.FwdMessageToUserShort : C2369R.string.FwdMessageToUser;
                        spannableStringBuilderReplaceTags = baseFragment != null ? AndroidUtilities.replaceSingleTag(LocaleController.formatString(i6, UserObject.getFirstName(user)), -1, 2, runnable4) : AndroidUtilities.replaceTags(LocaleController.formatString(i6, UserObject.getFirstName(user)));
                    } else {
                        int i7 = z ? C2369R.string.FwdMessagesToUserShort : C2369R.string.FwdMessagesToUser;
                        spannableStringBuilderReplaceTags = baseFragment != null ? AndroidUtilities.replaceSingleTag(LocaleController.formatString(i7, UserObject.getFirstName(user)), -1, 2, runnable4) : AndroidUtilities.replaceTags(LocaleController.formatString(i7, UserObject.getFirstName(user)));
                    }
                }
                lottieLayout.setAnimation(C2369R.raw.forward, 30, 30, new String[0]);
            }
        } else {
            if (i2 <= 1) {
                spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatPluralString("FwdMessageToManyChats", i, new Object[0]));
            } else {
                spannableStringBuilderReplaceTags = AndroidUtilities.replaceTags(LocaleController.formatPluralString("FwdMessagesToManyChats", i, new Object[0]));
            }
            lottieLayout.setAnimation(C2369R.raw.forward, 30, 30, new String[0]);
        }
        lottieLayout.textView.setText(spannableStringBuilderReplaceTags);
        if (z) {
            lottieLayout.setButton(new Bulletin.UndoButton(lottieLayout.getContext(), true, true, baseFragment != null ? baseFragment.getResourceProvider() : null).setUndoAction(runnable).setDelayedAction(runnable3));
        }
        lottieLayout.postDelayed(new Runnable() { // from class: org.telegram.ui.Components.BulletinFactory$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                lottieLayout.performHapticFeedback(3, 2);
            }
        }, DataTypes.UNIT);
        if (frameLayout != null) {
            bulletinMake = Bulletin.make(frameLayout, lottieLayout, i5);
        } else if (baseFragment != null) {
            bulletinMake = Bulletin.make(baseFragment, lottieLayout, i5);
        } else {
            throw new IllegalArgumentException();
        }
        if (lottieLayout instanceof Bulletin.LottieLayoutWithReactions) {
            lottieLayout.textView.setSingleLine(false);
            lottieLayout.textView.setMaxLines(2);
            ((Bulletin.LottieLayoutWithReactions) lottieLayout).setBulletin(bulletinMake);
            bulletinMake.hideAfterBottomSheet(false);
        }
        return bulletinMake;
    }

    public static /* synthetic */ void $r8$lambda$cmdwCWTjWScaKVp1N2mPSyjQJGc(boolean[] zArr, Runnable runnable) {
        if (zArr[0]) {
            return;
        }
        zArr[0] = true;
        runnable.run();
    }

    public static /* synthetic */ void $r8$lambda$rGqRMmPy33W71zUl4SsdlueKl8w(Runnable runnable, BaseFragment baseFragment, long j) {
        if (runnable != null) {
            runnable.run();
        }
        if (baseFragment != null) {
            baseFragment.presentFragment(ChatActivity.m1258of(j));
        }
    }

    public static Bulletin createRemoveFromChatBulletin(BaseFragment baseFragment, TLRPC.User user, String str) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        lottieLayout.setAnimation(C2369R.raw.ic_ban, "Hand");
        if (user.deleted) {
            string = LocaleController.formatString("HiddenName", C2369R.string.HiddenName, new Object[0]);
        } else {
            string = user.first_name;
        }
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(LocaleController.formatString("UserRemovedFromChatHint", C2369R.string.UserRemovedFromChatHint, string, str)));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public static Bulletin createBanBulletin(BaseFragment baseFragment, boolean z) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), baseFragment.getResourceProvider());
        if (z) {
            lottieLayout.setAnimation(C2369R.raw.ic_ban, "Hand");
            string = LocaleController.getString(C2369R.string.UserBlocked);
        } else {
            lottieLayout.setAnimation(C2369R.raw.ic_unban, "Main", "Finger 1", "Finger 2", "Finger 3", "Finger 4");
            string = LocaleController.getString(C2369R.string.UserUnblocked);
        }
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(string));
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public Bulletin createBanBulletin(boolean z) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        if (z) {
            lottieLayout.setAnimation(C2369R.raw.ic_ban, "Hand");
            string = LocaleController.getString(C2369R.string.UserBlocked);
        } else {
            lottieLayout.setAnimation(C2369R.raw.ic_unban, "Main", "Finger 1", "Finger 2", "Finger 3", "Finger 4");
            string = LocaleController.getString(C2369R.string.UserUnblocked);
        }
        lottieLayout.textView.setText(AndroidUtilities.replaceTags(string));
        return create(lottieLayout, 1500);
    }

    public static Bulletin createCopyLinkBulletin(BaseFragment baseFragment) {
        return m1267of(baseFragment).createCopyLinkBulletin();
    }

    public static Bulletin createCopyLinkBulletin(FrameLayout frameLayout) {
        return m1266of(frameLayout, null).createCopyLinkBulletin();
    }

    public static Bulletin createPinMessageBulletin(BaseFragment baseFragment, Theme.ResourcesProvider resourcesProvider) {
        return createPinMessageBulletin(baseFragment, true, null, null, resourcesProvider);
    }

    public static Bulletin createUnpinMessageBulletin(BaseFragment baseFragment, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        return createPinMessageBulletin(baseFragment, false, runnable, runnable2, resourcesProvider);
    }

    private static Bulletin createPinMessageBulletin(BaseFragment baseFragment, boolean z, Runnable runnable, Runnable runnable2, Theme.ResourcesProvider resourcesProvider) {
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        lottieLayout.setAnimation(z ? C2369R.raw.ic_pin : C2369R.raw.ic_unpin, 28, 28, "Pin", "Line");
        lottieLayout.textView.setText(LocaleController.getString(z ? "MessagePinnedHint" : "MessageUnpinnedHint", z ? C2369R.string.MessagePinnedHint : C2369R.string.MessageUnpinnedHint));
        if (!z) {
            lottieLayout.setButton(new Bulletin.UndoButton(baseFragment.getParentActivity(), true, resourcesProvider).setUndoAction(runnable).setDelayedAction(runnable2));
        }
        return Bulletin.make(baseFragment, lottieLayout, z ? 1500 : 5000);
    }

    public static Bulletin createSoundEnabledBulletin(BaseFragment baseFragment, int i, Theme.ResourcesProvider resourcesProvider) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(baseFragment.getParentActivity(), resourcesProvider);
        boolean z = true;
        if (i == 0) {
            string = LocaleController.getString(C2369R.string.SoundOnHint);
        } else if (i == 1) {
            string = LocaleController.getString(C2369R.string.SoundOffHint);
            z = false;
        } else {
            throw new IllegalArgumentException();
        }
        if (z) {
            lottieLayout.setAnimation(C2369R.raw.sound_on, new String[0]);
        } else {
            lottieLayout.setAnimation(C2369R.raw.sound_off, new String[0]);
        }
        lottieLayout.textView.setText(string);
        return Bulletin.make(baseFragment, lottieLayout, 1500);
    }

    public Bulletin createMessagesTaggedBulletin(int i, TLRPC.Document document, Runnable runnable) {
        String string;
        Bulletin.LottieLayout lottieLayout = new Bulletin.LottieLayout(getContext(), this.resourcesProvider);
        lottieLayout.setAnimation(C2369R.raw.tag_icon_3, 36, 36, new String[0]);
        lottieLayout.removeView(lottieLayout.textView);
        AnimatedEmojiSpan.TextViewEmojis textViewEmojis = new AnimatedEmojiSpan.TextViewEmojis(lottieLayout.getContext());
        lottieLayout.textView = textViewEmojis;
        textViewEmojis.setTypeface(Typeface.SANS_SERIF);
        lottieLayout.textView.setTextSize(1, 15.0f);
        lottieLayout.textView.setEllipsize(TextUtils.TruncateAt.END);
        lottieLayout.textView.setPadding(0, 0, 0, AndroidUtilities.m1146dp(8.0f));
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(AndroidUtilities.m1146dp(20.0f));
        SpannableString spannableString = new SpannableString("d");
        spannableString.setSpan(new AnimatedEmojiSpan(document, textPaint.getFontMetricsInt()), 0, spannableString.length(), 33);
        TextView textView = lottieLayout.textView;
        if (i > 1) {
            string = LocaleController.formatPluralString("SavedTagMessagesTagged", i, new Object[0]);
        } else {
            string = LocaleController.getString(C2369R.string.SavedTagMessageTagged);
        }
        textView.setText(new SpannableStringBuilder(string).append((CharSequence) " ").append((CharSequence) spannableString));
        if (runnable != null) {
            lottieLayout.setButton(new Bulletin.UndoButton(getContext(), true, this.resourcesProvider).setText(LocaleController.getString(C2369R.string.ViewAction)).setUndoAction(runnable));
        }
        lottieLayout.setTextColor(Theme.getColor(Theme.key_undo_infoColor, this.resourcesProvider));
        lottieLayout.addView(lottieLayout.textView, LayoutHelper.createFrameRelatively(-2.0f, -2.0f, 8388627, 56.0f, 2.0f, 8.0f, 0.0f));
        return create(lottieLayout, 2750);
    }
}
