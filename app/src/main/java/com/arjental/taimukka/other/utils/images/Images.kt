package com.arjental.taimukka.other.utils.images

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun Context.loadPackageIcon(
    packageName: String
): ImageBitmap? = withContext(Dispatchers.IO) {
    return@withContext try {
        val icon: Drawable = this@loadPackageIcon.packageManager.getApplicationIcon(packageName)
        return@withContext icon.toBitmap().asImageBitmap()
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        null
    }
}