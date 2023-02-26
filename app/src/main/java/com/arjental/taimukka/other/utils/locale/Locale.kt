package com.arjental.taimukka.other.utils.locale

import android.content.Context
import android.os.Build
import java.util.*

fun getLocale(context: Context): Locale {
    val locale = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.resources.configuration.locales.get(0)
        else
            context.resources.configuration.locale
    } catch (t: Throwable) {
        Locale.getDefault()
    }
    return locale
}