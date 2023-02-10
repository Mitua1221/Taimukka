package com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.header.THeader
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.components.uiutils.TPreviewWrap
import com.arjental.taimukka.presentaion.ui.images.TIcons
import com.arjental.taimukka.presentaion.ui.images.ticons.Follow
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Control
import com.arjental.taimukka.presentaion.ui.images.ticons.tabs.Stats
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.AppListVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.ApplicationsListState

class ApplicationsListPart : ScreenPart() {

    override val implementParentLifecycle: Boolean = true

    @Composable
    override fun TContent() {

        val viewModel = daggerViewModel<AppListVM>()
        val state = viewModel.appListState().collectAsState()

        AppList(state)
    }

}

@Composable
fun AppList(state: State<ApplicationsListState>) {

    LazyColumn {

        item(
            key = "appListTitle"
        ) {
            THeader(
                title = stringResource(id = state.value.title)
            )
        }

        state.value.list.forEach { app ->
            item(
                key = app.packageName
            ) {
                AppListItem(app)
            }
        }


    }

}

@Preview
@Composable
fun Foo() {
    TPreviewWrap {
        AppListItem(
            AppListItemPres(title = "Tinkoff", packageName = "com.tinkoff.bank", appIcon = null, nonSystem = true)
        )
    }
}

@Composable
fun AppListItem(
    item: AppListItemPres
) {
    val startP = 16.dp
    val endP = 16.dp
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = startP, end = endP)
    ) {
        val (appLogo, appCategory, appTitle, appBar, appTime, follow) = createRefs()

        if (item.appIcon != null) {
            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(appLogo) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                bitmap = item.appIcon,
                contentDescription = stringResource(id = com.arjental.taimukka.R.string.applications_application_logo, item.title),
            )
        } else {
            Image(
                modifier = Modifier
                    .height(24.dp)
                    .width(24.dp)
                    .constrainAs(appLogo) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start, margin = 16.dp)
                    },
                imageVector = TIcons.Control,
                contentDescription = stringResource(id = com.arjental.taimukka.R.string.applications_application_logo, item.title),
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface)
            )
        }

        Text(
            modifier = Modifier
                .constrainAs(appCategory) {
                    top.linkTo(parent.top, margin = 8.dp)
                    start.linkTo(appLogo.end, margin = 16.dp)
                    end.linkTo(follow.start, margin = 16.dp)
                    width = Dimension.fillToConstraints

                },
            text = "category",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            modifier = Modifier
                .constrainAs(appTitle) {
                    top.linkTo(appCategory.bottom)
                    start.linkTo(appLogo.end, margin = 16.dp)
                    end.linkTo(follow.start, margin = 16.dp)
                    width = Dimension.fillToConstraints
                },
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        val startGuideline = createGuidelineFromStart(0.64f)

        LinearProgressIndicator(
            progress = 0.5f,
            modifier = Modifier.constrainAs(appBar) {
                top.linkTo(appTitle.bottom, margin = 12.dp)
                start.linkTo(appLogo.end, margin = 16.dp)
                end.linkTo(startGuideline, margin = 16.dp)
                bottom.linkTo(parent.bottom, margin = 20.dp)
                width = Dimension.fillToConstraints
            },
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )

        Text(
            modifier = Modifier
                .constrainAs(appTime) {
                    top.linkTo(appBar.top)
                    start.linkTo(appBar.end, margin = 10.dp)
                    end.linkTo(follow.start, margin = 16.dp)
                    bottom.linkTo(appBar.bottom)
                    width = Dimension.fillToConstraints
                },
            text = "appname",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

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