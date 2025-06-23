package com.base.library.utils

import java.io.File
import kotlin.math.round


object FileUtil {

    fun getFolderSize(folder: File): Long {
        if (!folder.exists() || !folder.isDirectory) {
            return 0L
        }

        return folder.walk()
            .filter { it.isFile }
            .map { it.length() }
            .sum()
    }

    /**
     * Deletes all files and subdirectories in the specified folder.
     * @param folder The folder to clear.
     * @return True if all deletions were successful, false otherwise.
     */
    fun clearFolder(folder: File): Boolean {
        if (!folder.exists() || !folder.isDirectory) {
            return false
        }

        return folder.walkBottomUp()
            .filter { it != folder }
            .all { it.delete() }
    }

    fun formatSize(sizeInBytes: Long): String {
        val sizeInKb = sizeInBytes / 1024.0
        return if (sizeInKb < 1024) {
            "${round(sizeInKb * 10) / 10} KB"
        } else {
            "${round(sizeInKb / 102.4) / 10} MB"
        }
    }
}