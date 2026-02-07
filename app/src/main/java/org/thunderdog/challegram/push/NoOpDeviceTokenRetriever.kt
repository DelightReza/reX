/*
 * This file is a part of Telegram X
 * Copyright © 2014 (tgx-android@pm.me)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.push

import android.content.Context
import tgx.bridge.DeviceTokenRetriever
import tgx.bridge.PushManagerBridge
import tgx.bridge.TokenRetrieverListener

/**
 * No-op implementation of DeviceTokenRetriever for FOSS builds without Google Play Services.
 * This allows the app to function without push notifications.
 */
class NoOpDeviceTokenRetriever : DeviceTokenRetriever("noop") {
  override fun isAvailable(context: Context): Boolean {
    // Always return false since we don't have push notification support
    return false
  }

  override fun performInitialization(context: Context): Boolean {
    PushManagerBridge.log("NoOpDeviceTokenRetriever: No push notification support in FOSS build")
    // Return true to indicate "successful" initialization as a no-op retriever
    // This prevents spurious error messages about initialization failures
    return true
  }

  override fun fetchDeviceToken(context: Context, listener: TokenRetrieverListener) {
    PushManagerBridge.log("NoOpDeviceTokenRetriever: Push notifications not available")
    listener.onTokenRetrievalError("NO_PUSH_SUPPORT", 
      UnsupportedOperationException("Push notifications not available in FOSS build"))
  }
}
