package com.sun.jna.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes4.dex */
public class Cleaner {
    private static final Cleaner INSTANCE = new Cleaner();
    private final Thread cleanerThread;
    private CleanerRef firstCleanable;
    private final ReferenceQueue referenceQueue = new ReferenceQueue();

    public interface Cleanable {
        void clean();
    }

    public static Cleaner getCleaner() {
        return INSTANCE;
    }

    private Cleaner() {
        Thread thread = new Thread() { // from class: com.sun.jna.internal.Cleaner.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() throws InterruptedException {
                while (true) {
                    try {
                        Reference referenceRemove = Cleaner.this.referenceQueue.remove();
                        if (referenceRemove instanceof CleanerRef) {
                            ((CleanerRef) referenceRemove).clean();
                        }
                    } catch (InterruptedException unused) {
                        return;
                    } catch (Exception e) {
                        Logger.getLogger(Cleaner.class.getName()).log(Level.SEVERE, (String) null, (Throwable) e);
                    }
                }
            }
        };
        this.cleanerThread = thread;
        thread.setName("JNA Cleaner");
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized Cleanable register(Object obj, Runnable runnable) {
        return add(new CleanerRef(this, obj, this.referenceQueue, runnable));
    }

    private synchronized CleanerRef add(CleanerRef cleanerRef) {
        try {
            CleanerRef cleanerRef2 = this.firstCleanable;
            if (cleanerRef2 == null) {
                this.firstCleanable = cleanerRef;
            } else {
                cleanerRef.setNext(cleanerRef2);
                this.firstCleanable.setPrevious(cleanerRef);
                this.firstCleanable = cleanerRef;
            }
        } catch (Throwable th) {
            throw th;
        }
        return cleanerRef;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean remove(CleanerRef cleanerRef) {
        boolean z;
        boolean z2;
        try {
            z = true;
            if (cleanerRef == this.firstCleanable) {
                this.firstCleanable = cleanerRef.getNext();
                z2 = true;
            } else {
                z2 = false;
            }
            if (cleanerRef.getPrevious() != null) {
                cleanerRef.getPrevious().setNext(cleanerRef.getNext());
            }
            if (cleanerRef.getNext() != null) {
                cleanerRef.getNext().setPrevious(cleanerRef.getPrevious());
            }
            if (cleanerRef.getPrevious() == null && cleanerRef.getNext() == null) {
                z = z2;
            }
            cleanerRef.setNext(null);
            cleanerRef.setPrevious(null);
        } catch (Throwable th) {
            throw th;
        }
        return z;
    }

    private static class CleanerRef extends PhantomReference implements Cleanable {
        private final Cleaner cleaner;
        private final Runnable cleanupTask;
        private CleanerRef next;
        private CleanerRef previous;

        public CleanerRef(Cleaner cleaner, Object obj, ReferenceQueue referenceQueue, Runnable runnable) {
            super(obj, referenceQueue);
            this.cleaner = cleaner;
            this.cleanupTask = runnable;
        }

        @Override // com.sun.jna.internal.Cleaner.Cleanable
        public void clean() {
            if (this.cleaner.remove(this)) {
                this.cleanupTask.run();
            }
        }

        CleanerRef getPrevious() {
            return this.previous;
        }

        void setPrevious(CleanerRef cleanerRef) {
            this.previous = cleanerRef;
        }

        CleanerRef getNext() {
            return this.next;
        }

        void setNext(CleanerRef cleanerRef) {
            this.next = cleanerRef;
        }
    }
}
