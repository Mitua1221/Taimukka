package com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import com.arjental.taimukka.R
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.presentaion.app_details.AppDetailedListItemPresentation
import com.arjental.taimukka.entities.presentaion.app_details.AppDetailedPresentation
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.TDropdownItem
import com.arjental.taimukka.presentaion.ui.components.app.ContentType
import com.arjental.taimukka.presentaion.ui.components.filters.TDetailsFilters
import com.arjental.taimukka.presentaion.ui.components.header.THeader
import com.arjental.taimukka.presentaion.ui.components.loading.TCircularProgressIndicator
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalContentType
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.catagories.Notifications
import com.arjental.taimukka.presentaion.ui.images.ticons.catagories.Seances
import com.arjental.taimukka.presentaion.ui.images.ticons.catagories.Timeline
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.AppDetailsVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.ApplicationDetailsState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * @param openedApplication provides flow that received last application package name that is selected by user.
 * Can change in any time.
 */
class ApplicationDetailsPart(
    override val onElevated: Boolean = false,
    private val openedApplication: StateFlow<String?>,
) : ScreenPart() {

    override val implementParentLifecycle: Boolean = false //viewmodel belongs to this part of screen

    @Composable
    override fun TContent() {

        val viewModel = daggerViewModel<AppDetailsVM>()

        LaunchedEffect(openedApplication) {
            //paste app package to different vm
            openedApplication.collectLatest { appPackage ->
                appPackage?.let {
                    viewModel.setApplication(it)
                }
            }
        }

        val state = viewModel.collectState().collectAsState()

        AppDetails(screenState = state)
    }

    override fun inverseElevated(): ScreenPart = ApplicationDetailsPart(onElevated = !onElevated, openedApplication = openedApplication)

}

@Composable
fun AppDetails(screenState: State<ApplicationDetailsState>) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        val stateValue = screenState.value
        when {
            stateValue.loading -> {
                TCircularProgressIndicator()
            }
            stateValue.appPresentationInformation != null -> {
                val appPresValue = stateValue.appPresentationInformation
                LazyColumn {

                    item(
                        key = "appPreviewCard"
                    ) {

                        when (LocalContentType.current) {
                            ContentType.SINGLE_PANE -> {
                                AppDetailsTopElements(appPresentation = appPresValue)
                            }
                            ContentType.DUAL_PANE -> {
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .padding(top = 16.dp)
                                        .clip(MaterialTheme.shapes.large)
                                ) {
                                    AppDetailsTopElements(appPresentation = appPresValue)
                                }
                            }
                        }
                    }

                    item(key = "applicationInformationKey") {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, bottom = 16.dp)
                                .padding(horizontal = 16.dp),
                            text = appPresValue.appName,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    appPresValue.detailedList.forEachIndexed { index, it ->
                        item(
                            key = it.type.name
                        ) {
                            AppPresentationStatisticDefaultCardElement(it)
                            if (index != appPresValue.detailedList.lastIndex) Spacer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(16.dp)
                            )
                        }
                    }


//        item(
//            key = "filters"
//        ) {
//            val viewModel = daggerViewModel<AppListVM>()
//            val timelineState = viewModel.timeline().collectAsState()
//            val selectedCategoryState = viewModel.selectedCategory().collectAsState()
//            TFilters(
//                modifier = Modifier.padding(bottom = 16.dp),
//                timelineState = timelineState,
//                changeTimeline = {
//                    viewModel.changeTimeline(it)
//                },
//                categoriesState = selectedCategoryState,
//                changeCategory = { viewModel.selectCategory(it) }
//            )
//        }
//
//
//        screenState.value.list.forEach { app ->
//            item(
//                key = app.packageName
//            ) {
//                val ctx = LocalContext.current
//                val category = remember { getAppCategoryName(appCategory = app.appCategory, context = ctx) }
//                AppListItem(item = app, category = category)
//            }
//        }


                }
            }
            else -> Unit
        }

    }


}

@Composable
private fun AppDetailsTopElements(appPresentation: AppDetailedPresentation) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    ) {
        THeader(
            title = appPresentation.appName,
            icon = appPresentation.appIcon
        )
        val viewModel = daggerViewModel<AppDetailsVM>()
        TDetailsFilters(
            timelineState = viewModel.timeline().collectAsState(),
            changeTimeline = { viewModel.changeTimeline(it) },
            typeState = viewModel.selectedType().collectAsState(),
            changeType = { viewModel.changeSelectionType(it) })
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            text = "TODO",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        ) {
            Text(text = "GRAPH")
        }
    }
}

@Preview
@Composable
fun AppPresentationStatisticDefaultCardElementPreview() {
    AppPresentationStatisticDefaultCardElement(
        AppDetailedListItemPresentation(
            type = SelectionType.SCREEN_TIME,
            percentage = 0.34f,
            value = 12312412L
        )
    )
}

@Composable
private fun AppPresentationStatisticDefaultCardElement(item: AppDetailedListItemPresentation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp))
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (iconBack, value, title, bar) = createRefs()

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(48.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .constrainAs(iconBack) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                    }
            ) {
                item.type.typedIcon()?.let {
                    Icon(modifier = Modifier.align(Alignment.Center), imageVector = it, contentDescription = it.name, tint = MaterialTheme.colorScheme.onSecondaryContainer)
                }
            }

            val context = LocalContext.current

            val appValuableText = remember {
                formatSelectionTypeValue(context = context, selectionType = item.type, quality = item.value)
            }

            Text(
                modifier = Modifier
                    .constrainAs(value) {
                        start.linkTo(iconBack.end, margin = 8.dp)
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(iconBack.top)
                        bottom.linkTo(iconBack.bottom)
                        width = Dimension.fillToConstraints
                    },
                text = appValuableText,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
            )

            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        start.linkTo(iconBack.start)
                        end.linkTo(parent.end, margin = 16.dp)
                        top.linkTo(iconBack.bottom)
                        width = Dimension.fillToConstraints
                    },
                text = item.type.getString(context),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            LinearProgressIndicator(
                progress = item.percentage,
                modifier = Modifier.constrainAs(bar) {
                    top.linkTo(title.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 16.dp)
                    end.linkTo(parent.end, margin = 16.dp)
                    bottom.linkTo(parent.bottom, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

        }

    }
}

@Composable
private fun SelectionType.typedIcon() =
    when (this) {
        SelectionType.NOTIFICATIONS -> TIcons.Notifications
        SelectionType.SCREEN_TIME -> TIcons.Timeline
        SelectionType.SEANCES -> TIcons.Seances
        else -> null
    }

private fun SelectionType.getString(context: Context): String =
    context.getString(
        when (this) {
            SelectionType.SEANCES -> R.string.filters_seances
            SelectionType.NOTIFICATIONS -> R.string.filters_notifications
            SelectionType.SCREEN_TIME -> R.string.filters_screen_time
        }
    )


