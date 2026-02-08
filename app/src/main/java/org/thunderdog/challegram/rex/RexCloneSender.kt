/*
 * This file is a part of reX
 * Copyright © 2024 (DelightReza)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 */
package org.thunderdog.challegram.rex

import org.drinkless.tdlib.TdApi

/**
 * Helper object to handle "cloning" restricted messages (bypassing forward blocks)
 * Creates new messages with the same content instead of forwarding the original
 */
object RexCloneSender {

    /**
     * Creates a clone request for a restricted message
     * @param targetChatId The chat to send the cloned message to
     * @param originalMsg The original restricted message to clone
     * @return TdApi.SendMessage request if content can be cloned, null if file not available
     */
    fun createCloneRequest(targetChatId: Long, originalMsg: TdApi.Message): TdApi.Function<*>? {
        val content = originalMsg.content ?: return null

        return when (content.constructor) {
            // Text messages - simple clone
            TdApi.MessageText.CONSTRUCTOR -> {
                val textContent = content as TdApi.MessageText
                val inputContent = TdApi.InputMessageText(
                    textContent.text,
                    textContent.linkPreviewOptions,
                    true // clearDraft
                )
                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null, // replyTo
                    null, // options
                    null, // replyMarkup
                    inputContent
                )
            }

            // Photo messages - clone if downloaded
            TdApi.MessagePhoto.CONSTRUCTOR -> {
                val photoContent = content as TdApi.MessagePhoto
                val photo = photoContent.photo
                
                // Find the best quality photo size
                val photoSize = photo.sizes.maxByOrNull { it.photo.size } ?: return null
                val file = photoSize.photo
                
                if (!file.local.isDownloadingCompleted) {
                    return null // File not downloaded yet
                }

                val inputContent = TdApi.InputMessagePhoto(
                    TdApi.InputFileLocal(file.local.path),
                    null, // thumbnail
                    null, // addedStickerFileIds
                    0, // width
                    0, // height
                    photoContent.caption,
                    photoContent.showCaptionAboveMedia,
                    null, // selfDestructType
                    photoContent.hasSpoiler
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Video messages - clone if downloaded
            TdApi.MessageVideo.CONSTRUCTOR -> {
                val videoContent = content as TdApi.MessageVideo
                val video = videoContent.video
                val file = video.video

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageVideo(
                    TdApi.InputFileLocal(file.local.path),
                    null, // thumbnail
                    null, // addedVideos
                    0, // addedStickerFileIdsCount
                    null, // addedStickerFileIds
                    video.duration,
                    video.width,
                    video.height,
                    video.supportsStreaming,
                    videoContent.caption,
                    videoContent.showCaptionAboveMedia,
                    null, // selfDestructType
                    videoContent.hasSpoiler
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Document messages - clone if downloaded
            TdApi.MessageDocument.CONSTRUCTOR -> {
                val docContent = content as TdApi.MessageDocument
                val document = docContent.document
                val file = document.document

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageDocument(
                    TdApi.InputFileLocal(file.local.path),
                    null, // thumbnail
                    false, // disableContentTypeDetection
                    docContent.caption
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Audio messages - clone if downloaded
            TdApi.MessageAudio.CONSTRUCTOR -> {
                val audioContent = content as TdApi.MessageAudio
                val audio = audioContent.audio
                val file = audio.audio

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageAudio(
                    TdApi.InputFileLocal(file.local.path),
                    null, // albumCoverThumbnail
                    audio.duration,
                    audio.title,
                    audio.performer,
                    audioContent.caption
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Voice messages - clone if downloaded
            TdApi.MessageVoiceNote.CONSTRUCTOR -> {
                val voiceContent = content as TdApi.MessageVoiceNote
                val voice = voiceContent.voiceNote
                val file = voice.voice

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageVoiceNote(
                    TdApi.InputFileLocal(file.local.path),
                    voice.duration,
                    voice.waveform,
                    voiceContent.caption,
                    null // selfDestructType
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Sticker messages - clone if downloaded
            TdApi.MessageSticker.CONSTRUCTOR -> {
                val stickerContent = content as TdApi.MessageSticker
                val sticker = stickerContent.sticker
                val file = sticker.sticker

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageSticker(
                    TdApi.InputFileLocal(file.local.path),
                    null, // thumbnail
                    sticker.width,
                    sticker.height,
                    null // emoji
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Animation (GIF) messages - clone if downloaded
            TdApi.MessageAnimation.CONSTRUCTOR -> {
                val animContent = content as TdApi.MessageAnimation
                val animation = animContent.animation
                val file = animation.animation

                if (!file.local.isDownloadingCompleted) {
                    return null
                }

                val inputContent = TdApi.InputMessageAnimation(
                    TdApi.InputFileLocal(file.local.path),
                    null, // thumbnail
                    null, // addedStickerFileIds
                    animation.duration,
                    animation.width,
                    animation.height,
                    animContent.caption,
                    animContent.showCaptionAboveMedia,
                    animContent.hasSpoiler
                )

                TdApi.SendMessage(
                    targetChatId,
                    null, // messageTopic
                    null,
                    null,
                    null,
                    inputContent
                )
            }

            // Unsupported content type
            else -> null
        }
    }

    /**
     * Checks if a message can be cloned (all files downloaded)
     */
    fun canClone(message: TdApi.Message): Boolean {
        val content = message.content ?: return false

        return when (content.constructor) {
            TdApi.MessageText.CONSTRUCTOR -> true // Text always can be cloned
            
            TdApi.MessagePhoto.CONSTRUCTOR -> {
                val photo = (content as TdApi.MessagePhoto).photo
                val photoSize = photo.sizes.maxByOrNull { it.photo.size }
                photoSize?.photo?.local?.isDownloadingCompleted == true
            }
            
            TdApi.MessageVideo.CONSTRUCTOR -> {
                (content as TdApi.MessageVideo).video.video.local.isDownloadingCompleted
            }
            
            TdApi.MessageDocument.CONSTRUCTOR -> {
                (content as TdApi.MessageDocument).document.document.local.isDownloadingCompleted
            }
            
            TdApi.MessageAudio.CONSTRUCTOR -> {
                (content as TdApi.MessageAudio).audio.audio.local.isDownloadingCompleted
            }
            
            TdApi.MessageVoiceNote.CONSTRUCTOR -> {
                (content as TdApi.MessageVoiceNote).voiceNote.voice.local.isDownloadingCompleted
            }
            
            TdApi.MessageSticker.CONSTRUCTOR -> {
                (content as TdApi.MessageSticker).sticker.sticker.local.isDownloadingCompleted
            }
            
            TdApi.MessageAnimation.CONSTRUCTOR -> {
                (content as TdApi.MessageAnimation).animation.animation.local.isDownloadingCompleted
            }
            
            else -> false
        }
    }
}
