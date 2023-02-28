package com.arjental.taimukka.presentaion.ui.components.filters

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import com.arjental.taimukka.entities.presentaion.applist.CategoriesSelection
import com.arjental.taimukka.presentaion.ui.components.TDropdown
import com.arjental.taimukka.presentaion.ui.components.TDropdownItem
import com.arjental.taimukka.presentaion.ui.components.buttons.TSelectionButton
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Cross
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow

/**
 * Preview filters for applications screen
 */

@Composable
fun TFilters(
    modifier: Modifier = Modifier,
    timelineState: State<Timeline?>,
    changeTimeline: (Timeline) -> Unit = { },
    categoriesState: State<CategoriesSelection?>? = null,
    changeCategory: (Int?) -> Unit = { }
) {

    val localDensity = LocalDensity.current

    LazyRow(modifier = modifier) {

        if (timelineState.value != null) {
            item(key = "timeline filter") {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .width(16.dp)
                )
                var expanded by remember { mutableStateOf(false) }
                var buttonHeightDp by remember { mutableStateOf(0.dp) }
                val dropdownItems = remember { getTimelines() }
                TDropdown(
                    items = dropdownItems,
                    expanded = expanded,
                    setExpanded = { expanded = it },
                    topOffset = buttonHeightDp,
                ) { tDropdownItem ->
                    changeTimeline(tDropdownItem.toTimeline())
                }
                TSelectionButton(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            buttonHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                        },
                    //first get from res
                    text = timelineState.value!!.timelineType.toPreviews().titleRes?.let { stringResource(id = it) }
                    //after that from string title
                        ?: timelineState.value!!.timelineType.toPreviews().title ?: "",
                    icon = TIcons.Follow,
                    iconRotated = expanded,
                ) {
                    expanded = true
                }
            }
        }

        //remember, we cant provide categories on api < 28
        if (categoriesState?.value != null && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val categoriesStateV = categoriesState.value!!
            item(key = "category filter") {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .width(8.dp)
                )
                var expanded by remember { mutableStateOf(false) }
                var buttonHeightDp by remember { mutableStateOf(0.dp) }

                TDropdown(
                    items = categoriesStateV.categoriesList.fromCategoryToPreviews(LocalContext.current),
                    expanded = expanded,
                    setExpanded = { expanded = it },
                    topOffset = buttonHeightDp,
                ) { tDropdownItem ->
                    changeCategory(tDropdownItem.type.toIntOrNull())
                }

                val buttonText = categoriesStateV.selectedCategory?.fromCategoryToPreview(LocalContext.current)
                    //if its null, its not selected
                    ?: stringResource(id = com.arjental.taimukka.R.string.filters_categories_all)

                val iconClickable = categoriesStateV.selectedCategory != null

                TSelectionButton(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            buttonHeightDp = with(localDensity) { coordinates.size.height.toDp() }
                        },
                    text = buttonText,
                    icon = if (iconClickable) TIcons.Cross else TIcons.Follow,
                    iconRotated = expanded,
                    iconClickable = iconClickable,
                    onClickIcon = {
                        changeCategory(null)
                    }
                ) {
                    expanded = true
                }
            }
        }

    }
}

/**
 * Get timeline from type
 * @param from only for custom selection
 * @param to only for custom selection
 */

private fun TDropdownItem.toTimeline(
    from: Long? = null,
    to: Long? = null,
): Timeline =
    when (this.type) {
        TimelineType.CUSTOM.name -> Timeline(timelineType = TimelineType.CUSTOM, from = from, to = to)
        TimelineType.MONTH.name -> Timeline(timelineType = TimelineType.MONTH)
        TimelineType.WEEK.name -> Timeline(timelineType = TimelineType.WEEK)
        TimelineType.DAY.name -> Timeline(timelineType = TimelineType.DAY)
        else -> error("Incorrent timeline generating, no timelineType like this")
    }

/**
 * Get preview types for timeline
 */
private fun TimelineType.toPreviews(): TDropdownItem =
    when (this) {
        TimelineType.CUSTOM -> TDropdownItem(type = TimelineType.CUSTOM.name, titleRes = com.arjental.taimukka.R.string.filters_timeline_custom)
        TimelineType.MONTH -> TDropdownItem(type = TimelineType.MONTH.name, titleRes = com.arjental.taimukka.R.string.filters_timeline_month)
        TimelineType.WEEK -> TDropdownItem(type = TimelineType.WEEK.name, titleRes = com.arjental.taimukka.R.string.filters_timeline_week)
        TimelineType.DAY -> TDropdownItem(type = TimelineType.DAY.name, titleRes = com.arjental.taimukka.R.string.filters_timeline_day)
        else -> error("Incorrent dropdown item generating, no timelineType like this")
    }

@RequiresApi(Build.VERSION_CODES.O)
private fun List<Int>.fromCategoryToPreviews(context: Context): List<TDropdownItem> {
    return this.mapNotNull {
        TDropdownItem(
            //!!! remember, here we store string as int
            type = it.toString(),
            title = ApplicationInfo.getCategoryTitle(context, it)?.toString() ?: "",
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun Int.fromCategoryToPreview(context: Context): String {
    return ApplicationInfo.getCategoryTitle(context, this)?.toString() ?: ""
}

fun getTimelines() = TimelineType.values().map { it.toPreviews() }




