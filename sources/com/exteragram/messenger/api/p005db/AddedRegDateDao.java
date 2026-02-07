package com.exteragram.messenger.api.p005db;

import com.exteragram.messenger.api.dto.AddedRegDateDTO;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

/* loaded from: classes.dex */
public interface AddedRegDateDao {
    Object insert(AddedRegDateDTO addedRegDateDTO, Continuation<? super Unit> continuation);

    Object isAdded(long j, Continuation<? super Boolean> continuation);
}
