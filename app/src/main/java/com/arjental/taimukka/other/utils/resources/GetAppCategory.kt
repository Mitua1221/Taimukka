package com.arjental.taimukka.other.utils.resources

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import com.arjental.taimukka.other.utils.annotataions.Category

/**
 * Gets app category as string from int annotated [Category]
 */

fun getAppCategoryName(@Category appCategory: Int?, context: Context): String? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && appCategory != null ) {
        ApplicationInfo.getCategoryTitle(context, appCategory)?.toString()
    } else {
        null
    }
}

