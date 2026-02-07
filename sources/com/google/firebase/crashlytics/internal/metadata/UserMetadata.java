package com.google.firebase.crashlytics.internal.metadata;

import com.google.android.exoplayer2.mediacodec.AbstractC0977xa830b30;
import com.google.firebase.crashlytics.internal.common.CrashlyticsBackgroundWorker;
import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import com.google.firebase.crashlytics.internal.persistence.FileStore;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public class UserMetadata {
    private final CrashlyticsBackgroundWorker backgroundWorker;
    private final MetaDataStore metaDataStore;
    private final String sessionIdentifier;
    private final SerializeableKeysMap customKeys = new SerializeableKeysMap(false);
    private final SerializeableKeysMap internalKeys = new SerializeableKeysMap(true);
    private final AtomicMarkableReference userId = new AtomicMarkableReference(null, false);

    public static String readUserId(String str, FileStore fileStore) {
        return new MetaDataStore(fileStore).readUserId(str);
    }

    public static UserMetadata loadFromExistingSession(String str, FileStore fileStore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker) {
        MetaDataStore metaDataStore = new MetaDataStore(fileStore);
        UserMetadata userMetadata = new UserMetadata(str, fileStore, crashlyticsBackgroundWorker);
        ((KeysMap) userMetadata.customKeys.map.getReference()).setKeys(metaDataStore.readKeyData(str, false));
        ((KeysMap) userMetadata.internalKeys.map.getReference()).setKeys(metaDataStore.readKeyData(str, true));
        userMetadata.userId.set(metaDataStore.readUserId(str), false);
        return userMetadata;
    }

    public UserMetadata(String str, FileStore fileStore, CrashlyticsBackgroundWorker crashlyticsBackgroundWorker) {
        this.sessionIdentifier = str;
        this.metaDataStore = new MetaDataStore(fileStore);
        this.backgroundWorker = crashlyticsBackgroundWorker;
    }

    public Map getCustomKeys() {
        return this.customKeys.getKeys();
    }

    public Map getInternalKeys() {
        return this.internalKeys.getKeys();
    }

    public boolean setInternalKey(String str, String str2) {
        return this.internalKeys.setKey(str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SerializeableKeysMap {
        private final boolean isInternal;
        final AtomicMarkableReference map;
        private final AtomicReference queuedSerializer = new AtomicReference(null);

        public SerializeableKeysMap(boolean z) {
            this.isInternal = z;
            this.map = new AtomicMarkableReference(new KeysMap(64, z ? 8192 : 1024), false);
        }

        public Map getKeys() {
            return ((KeysMap) this.map.getReference()).getKeys();
        }

        public boolean setKey(String str, String str2) {
            synchronized (this) {
                try {
                    if (!((KeysMap) this.map.getReference()).setKey(str, str2)) {
                        return false;
                    }
                    AtomicMarkableReference atomicMarkableReference = this.map;
                    atomicMarkableReference.set((KeysMap) atomicMarkableReference.getReference(), true);
                    scheduleSerializationTaskIfNeeded();
                    return true;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private void scheduleSerializationTaskIfNeeded() {
            Callable callable = new Callable() { // from class: com.google.firebase.crashlytics.internal.metadata.UserMetadata$SerializeableKeysMap$$ExternalSyntheticLambda0
                @Override // java.util.concurrent.Callable
                public final Object call() {
                    return UserMetadata.SerializeableKeysMap.$r8$lambda$MRS7FQg6LwlQlWElOiIBARH1194(this.f$0);
                }
            };
            if (AbstractC0977xa830b30.m273m(this.queuedSerializer, null, callable)) {
                UserMetadata.this.backgroundWorker.submit(callable);
            }
        }

        public static /* synthetic */ Void $r8$lambda$MRS7FQg6LwlQlWElOiIBARH1194(SerializeableKeysMap serializeableKeysMap) throws Throwable {
            serializeableKeysMap.queuedSerializer.set(null);
            serializeableKeysMap.serializeIfMarked();
            return null;
        }

        private void serializeIfMarked() throws Throwable {
            Map keys;
            synchronized (this) {
                try {
                    if (this.map.isMarked()) {
                        keys = ((KeysMap) this.map.getReference()).getKeys();
                        AtomicMarkableReference atomicMarkableReference = this.map;
                        atomicMarkableReference.set((KeysMap) atomicMarkableReference.getReference(), false);
                    } else {
                        keys = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (keys != null) {
                UserMetadata.this.metaDataStore.writeKeyData(UserMetadata.this.sessionIdentifier, keys, this.isInternal);
            }
        }
    }
}
