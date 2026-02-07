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

import java.io.File
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Manages attachment folder size and cleanup
 * Monitors folder size and deletes oldest files when limit is exceeded
 */
object RexAttachmentManager {
    
    /**
     * Check folder size and cleanup if needed
     * @param folderPath Path to the attachments folder
     * @param maxSizeMB Maximum size in MB (0 = no limit)
     */
    suspend fun checkAndCleanup(folderPath: String, maxSizeMB: Int) = withContext(Dispatchers.IO) {
        if (maxSizeMB <= 0 || folderPath.isEmpty()) return@withContext
        
        val folder = File(folderPath)
        if (!folder.exists() || !folder.isDirectory) return@withContext
        
        val maxSizeBytes = maxSizeMB * 1024L * 1024L
        val currentSize = calculateFolderSize(folder)
        
        if (currentSize > maxSizeBytes) {
            // Delete oldest files until size is under limit
            deleteOldestFiles(folder, currentSize - maxSizeBytes)
        }
    }
    
    /**
     * Calculate total size of all files in folder
     */
    private fun calculateFolderSize(folder: File): Long {
        var size = 0L
        folder.listFiles()?.forEach { file ->
            size += if (file.isDirectory) {
                calculateFolderSize(file)
            } else {
                file.length()
            }
        }
        return size
    }
    
    /**
     * Delete oldest files until specified amount of space is freed
     */
    private fun deleteOldestFiles(folder: File, bytesToFree: Long) {
        val files = mutableListOf<File>()
        collectFiles(folder, files)
        
        // Sort by last modified (oldest first)
        files.sortBy { it.lastModified() }
        
        var freedBytes = 0L
        for (file in files) {
            if (freedBytes >= bytesToFree) break
            
            freedBytes += file.length()
            file.delete()
        }
    }
    
    /**
     * Recursively collect all files from folder
     */
    private fun collectFiles(folder: File, fileList: MutableList<File>) {
        folder.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                collectFiles(file, fileList)
            } else {
                fileList.add(file)
            }
        }
    }
    
    /**
     * Get human-readable size string
     */
    fun formatSize(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> "${bytes / 1024} KB"
            bytes < 1024 * 1024 * 1024 -> "${bytes / (1024 * 1024)} MB"
            else -> "${bytes / (1024 * 1024 * 1024)} GB"
        }
    }
    
    /**
     * Get current folder size
     */
    suspend fun getFolderSize(folderPath: String): Long = withContext(Dispatchers.IO) {
        if (folderPath.isEmpty()) return@withContext 0L
        val folder = File(folderPath)
        if (!folder.exists() || !folder.isDirectory) return@withContext 0L
        calculateFolderSize(folder)
    }
}
