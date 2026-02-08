/*
 * This file is a part of reX
 * Copyright © 2024
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package org.thunderdog.challegram.core;

import org.drinkless.tdlib.TdApi;
import org.thunderdog.challegram.config.RexConfig;

public final class GhostInterceptor {

  private GhostInterceptor () { }

  /**
   * Checks whether the given outgoing TdApi function should be blocked
   * based on the current Ghost Mode configuration.
   *
   * @param func the TdApi function about to be sent
   * @return true if the function should be blocked (not sent), false otherwise
   */
  public static boolean shouldBlock (TdApi.Function<?> func) {
    RexConfig config = RexConfig.getInstance();
    if (!config.isGhostEnabled()) {
      return false;
    }

    if (func instanceof TdApi.ViewMessages) {
      return config.isGhostOptionEnabled(RexConfig.GHOST_NO_READ);
    }

    if (func instanceof TdApi.OpenChat) {
      return config.isGhostOptionEnabled(RexConfig.GHOST_NO_ONLINE);
    }

    if (func instanceof TdApi.SendChatAction) {
      return config.isGhostOptionEnabled(RexConfig.GHOST_NO_TYPING);
    }

    return false;
  }
}
