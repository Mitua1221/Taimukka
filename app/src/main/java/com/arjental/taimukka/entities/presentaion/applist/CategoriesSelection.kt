package com.arjental.taimukka.entities.presentaion.applist

import android.content.Context
import com.arjental.taimukka.entities.domain.stats.LaunchedAppDomain
import com.arjental.taimukka.other.utils.images.loadPackageIcon
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

/**
 * @param selectedCategory If *selected* is null, there is no default selected elements
 * @param categoriesList may be empty, we have to hide filter if its empty
 */

data class CategoriesSelection(
    val selectedCategory: Int? = null,
    val categoriesList: ImmutableList<Int>
)


suspend fun List<LaunchedAppDomain>.toCategories(selectedCategory: Int?): CategoriesSelection {
    val set = mutableSetOf<Int>()
    this.forEach {
        if (it.appCategory != null) set.add(it.appCategory)
    }
    return CategoriesSelection(selectedCategory = selectedCategory, categoriesList = set.toList().sorted().toImmutableList())
}
