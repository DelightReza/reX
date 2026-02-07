package com.exteragram.messenger.icons;

import java.io.File;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.p019io.FilesKt;
import kotlinx.coroutines.CoroutineScope;
import org.telegram.messenger.C2369R;
import org.telegram.messenger.LocaleController;
import org.telegram.p023ui.ActionBar.BaseFragment;
import org.telegram.p023ui.Components.BulletinFactory;

/* loaded from: classes3.dex */
final class IconManager$handleIconPack$1$1$bottomSheet$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ BaseFragment $baseFragment;
    final /* synthetic */ boolean $enable;
    final /* synthetic */ File $file;
    final /* synthetic */ IconPack $pack;
    final /* synthetic */ boolean $update;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    IconManager$handleIconPack$1$1$bottomSheet$1$1(File file, BaseFragment baseFragment, boolean z, IconPack iconPack, boolean z2, Continuation continuation) {
        super(2, continuation);
        this.$file = file;
        this.$baseFragment = baseFragment;
        this.$update = z;
        this.$pack = iconPack;
        this.$enable = z2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new IconManager$handleIconPack$1$1$bottomSheet$1$1(this.$file, this.$baseFragment, this.$update, this.$pack, this.$enable, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((IconManager$handleIconPack$1$1$bottomSheet$1$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x004c, code lost:
    
        if (kotlinx.coroutines.BuildersKt.withContext(r11, r3, r10) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) throws java.lang.Throwable {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1e
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            kotlin.ResultKt.throwOnFailure(r11)
            goto L4f
        L12:
            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
            java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
            r11.<init>(r0)
            throw r11
        L1a:
            kotlin.ResultKt.throwOnFailure(r11)
            goto L2e
        L1e:
            kotlin.ResultKt.throwOnFailure(r11)
            com.exteragram.messenger.icons.IconPackStorage r11 = com.exteragram.messenger.icons.IconPackStorage.INSTANCE
            java.io.File r1 = r10.$file
            r10.label = r3
            java.lang.Object r11 = r11.installPack(r1, r10)
            if (r11 != r0) goto L2e
            goto L4e
        L2e:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r4 = r11.booleanValue()
            kotlinx.coroutines.MainCoroutineDispatcher r11 = kotlinx.coroutines.Dispatchers.getMain()
            com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1 r3 = new com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1
            org.telegram.ui.ActionBar.BaseFragment r5 = r10.$baseFragment
            boolean r6 = r10.$update
            com.exteragram.messenger.icons.IconPack r7 = r10.$pack
            boolean r8 = r10.$enable
            r9 = 0
            r3.<init>(r4, r5, r6, r7, r8, r9)
            r10.label = r2
            java.lang.Object r11 = kotlinx.coroutines.BuildersKt.withContext(r11, r3, r10)
            if (r11 != r0) goto L4f
        L4e:
            return r0
        L4f:
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }

    /* renamed from: com.exteragram.messenger.icons.IconManager$handleIconPack$1$1$bottomSheet$1$1$1 */
    static final class C08331 extends SuspendLambda implements Function2 {
        final /* synthetic */ BaseFragment $baseFragment;
        final /* synthetic */ boolean $enable;
        final /* synthetic */ IconPack $pack;
        final /* synthetic */ boolean $success;
        final /* synthetic */ boolean $update;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C08331(boolean z, BaseFragment baseFragment, boolean z2, IconPack iconPack, boolean z3, Continuation continuation) {
            super(2, continuation);
            this.$success = z;
            this.$baseFragment = baseFragment;
            this.$update = z2;
            this.$pack = iconPack;
            this.$enable = z3;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08331(this.$success, this.$baseFragment, this.$update, this.$pack, this.$enable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C08331) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            if (this.$success) {
                BulletinFactory.m1267of(this.$baseFragment).createSimpleBulletin(C2369R.raw.contact_check, LocaleController.formatString(this.$update ? C2369R.string.PluginUpdated : C2369R.string.PluginInstalled, this.$pack.getName())).show();
                if (this.$enable) {
                    IconManager.INSTANCE.setActiveCustomPack(this.$pack.getId());
                } else {
                    IconManager.INSTANCE.initialize(true);
                }
            } else {
                BulletinFactory.m1267of(this.$baseFragment).createSimpleBulletin(C2369R.raw.error, LocaleController.formatString(C2369R.string.PluginInstallError, this.$pack.getName())).show();
            }
            File location = this.$pack.getLocation();
            if (location != null) {
                Boxing.boxBoolean(FilesKt.deleteRecursively(location));
            }
            return Unit.INSTANCE;
        }
    }
}
