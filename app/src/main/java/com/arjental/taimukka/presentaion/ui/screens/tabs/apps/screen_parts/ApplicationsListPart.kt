package com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arjental.taimukka.entities.pierce.selection_type.SelectionType
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.other.utils.resources.formatMillisToPresentation
import com.arjental.taimukka.other.utils.resources.getAppCategoryName
import com.arjental.taimukka.presentaion.ui.components.filters.TFilters
import com.arjental.taimukka.presentaion.ui.components.header.THeader
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Control
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.AppListVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.ApplicationsListState

class ApplicationsListPart : ScreenPart() {

    override val implementParentLifecycle: Boolean = true

    @Composable
    override fun TContent() {

        val viewModel = daggerViewModel<AppListVM>()
        val state = viewModel.appListState().collectAsState()


        AppList(screenState = state)
    }

}

@Composable
fun AppList(screenState: State<ApplicationsListState>) {

    val viewModel = daggerViewModel<AppListVM>()

    LazyColumn {

        item(
            key = "appListTitle"
        ) {
            THeader(
                title = stringResource(id = screenState.value.title)
            )
        }

        item(
            key = "filters"
        ) {
            val timelineState = viewModel.timeline().collectAsState()
            val selectedCategoryState = viewModel.selectedCategory().collectAsState()
            val selectedTypeState = viewModel.selectedType().collectAsState()
            TFilters(
                modifier = Modifier.padding(bottom = 16.dp),
                timelineState = timelineState,
                changeTimeline = {
                    viewModel.changeTimeline(it)
                },
                categoriesState = selectedCategoryState,
                changeCategory = { viewModel.selectCategory(it) },
                typeState = selectedTypeState,
                changeType = {
                    viewModel.changeSelectionType(it)
                }
            )
        }


        screenState.value.list.forEach { app ->
            item(
                key = app.packageName
            ) {
                val ctx = LocalContext.current
                val category = remember { getAppCategoryName(appCategory = app.appCategory, context = ctx) }
                AppListItem(item = app, category = category, onClick = { viewModel.selectApplication(it) })
            }
        }


    }

}

@Preview
@Composable
fun Foo() {
    AppListItem(
        item = AppListItemPres(title = "Tinkoff", packageName = "com.tinkoff.bank", appIcon = null, nonSystem = true, appCategory = 1, realQuality = 1000000000, selectionType = SelectionType.NOTIFICATIONS),
        category = "somecategory",
        onClick = {  }
    )
}

@Composable
fun AppListItem(
    item: AppListItemPres,
    category: String? = null,
    onClick: (AppListItemPres) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClick(item)
        }) {
        val startP = remember { 16.dp }
        val endP = remember { 16.dp }
        val context = LocalContext.current
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startP, end = endP)
        ) {
            val (appLogo, appCategory, appTitle, appBar, appValuable, follow) = createRefs()

            if (item.appIcon != null) {
                Image(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .constrainAs(appLogo) {
                            top.linkTo(parent.top, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                        },
                    bitmap = item.appIcon,
                    contentDescription = stringResource(id = com.arjental.taimukka.R.string.applications_application_logo, item.title),
                )
            } else {
                Image(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp)
                        .constrainAs(appLogo) {
                            top.linkTo(parent.top, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 16.dp)
                        },
                    imageVector = TIcons.Control,
                    contentDescription = stringResource(id = com.arjental.taimukka.R.string.applications_application_logo, item.title),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
                )
            }

            if (!category.isNullOrEmpty()) {
                Text(
                    modifier = Modifier
                        .constrainAs(appCategory) {
                            top.linkTo(parent.top, margin = 8.dp)
                            start.linkTo(appLogo.end, margin = 16.dp)
                            end.linkTo(follow.start, margin = 28.dp)
                            width = Dimension.fillToConstraints

                        },
                    text = category,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Text(
                modifier = Modifier
                    .constrainAs(appTitle) {
                        if (!category.isNullOrEmpty())
                            top.linkTo(appCategory.bottom)
                        else
                            top.linkTo(parent.top, margin = 8.dp)
                        start.linkTo(appLogo.end, margin = 16.dp)
                        end.linkTo(appValuable.start, margin = 4.dp)
                        width = Dimension.fillToConstraints
                    },
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )

            LinearProgressIndicator(
                progress = item.percentage,
                modifier = Modifier.constrainAs(appBar) {
                    top.linkTo(appTitle.bottom, margin = 4.dp)
                    start.linkTo(appLogo.end, margin = 16.dp)
                    end.linkTo(follow.start, margin = 28.dp)
                    bottom.linkTo(parent.bottom, margin = 12.dp)
                    width = Dimension.fillToConstraints
                },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )


            val appValuableText = remember {
                formatSelectionTypeValue(context = context, selectionType = item.selectionType, quality = item.realQuality)
            }

            Box(modifier = Modifier
                .constrainAs(appValuable) {
                    end.linkTo(follow.start, margin = 28.dp)
                    bottom.linkTo(appTitle.bottom)
                    width = Dimension.fillToConstraints
                }) {
                Text(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    text = appValuableText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(follow) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end, margin = 8.dp)
                    }, imageVector = TIcons.Follow, contentDescription = TIcons.Follow.name, colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = startP, end = endP)
                .background(color = MaterialTheme.colorScheme.outlineVariant)
        )
    }
}

/**
 * Formatting typed value to presentation string
 */
fun formatSelectionTypeValue(selectionType: SelectionType, context: Context, quality: Long): String {
    return when (selectionType) {
        SelectionType.SCREEN_TIME -> formatMillisToPresentation(context, quality)
        SelectionType.SEANCES -> quality.toString()
        SelectionType.NOTIFICATIONS -> quality.toString()
        else -> error("SelectionType not handled on ui")
    }
}



