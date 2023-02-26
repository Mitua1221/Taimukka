package com.arjental.taimukka.presentaion.ui.components.filters

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.entities.pierce.timeline.Timeline
import com.arjental.taimukka.entities.pierce.timeline.TimelineType
import com.arjental.taimukka.presentaion.ui.components.TDropdown
import com.arjental.taimukka.presentaion.ui.components.TDropdownItem
import com.arjental.taimukka.presentaion.ui.components.buttons.TSelectionButton
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow

/**
 * Preview filters for applications screen
 */

@Composable
fun TFilters(
    modifier: Modifier = Modifier,
    timelineState: State<Timeline?>,
    changeTimeline: (Timeline) -> Unit
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
                    text = stringResource(id = timelineState.value!!.timelineType.toPreviews().titleRes),
                    icon = TIcons.Follow,
                    iconRotated = expanded,
                ) {
                    expanded = true
                }
            }
        }


        //        Spacer(
//            modifier = Modifier
//                .fillMaxSize()
//                .width(8.dp)
//        )


        //            if (index == raw.lastIndex) Spacer(
        //                modifier = Modifier
        //                    .fillMaxSize()
        //                    .width(16.dp)
        //            )


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

private fun getTimelines() = TimelineType.values().map { it.toPreviews() }




