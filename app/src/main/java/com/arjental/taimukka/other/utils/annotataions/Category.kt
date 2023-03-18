package com.arjental.taimukka.other.utils.annotataions

import android.content.pm.ApplicationInfo

/**
 * Means that this int is equals to [ApplicationInfo.category] and can be received via
 * [com.arjental.taimukka.other.utils.resources.getAppCategoryName] function.
 */

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION, AnnotationTarget.TYPE)
annotation class Category()
