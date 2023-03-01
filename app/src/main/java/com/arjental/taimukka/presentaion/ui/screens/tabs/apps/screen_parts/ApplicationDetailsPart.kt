package com.arjental.taimukka.presentaion.ui.screens.tabs.apps.screen_parts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.header.THeader
import com.arjental.taimukka.presentaion.ui.components.loading.TCircularProgressIndicator
import com.arjental.taimukka.presentaion.ui.components.uiutils.ScreenPart
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.AppListVM
import com.arjental.taimukka.presentaion.ui.screens.tabs.apps.ApplicationDetailsState

class ApplicationDetailsPart(
    override val onElevated: Boolean = false
) : ScreenPart() {

    override val implementParentLifecycle: Boolean = true

    @Composable
    override fun TContent() {

        val viewModel = daggerViewModel<AppListVM>()
        val state = viewModel.appDetailsState().collectAsState()


        AppDetails(screenState = state)
    }

    override fun inverseElevated(): ScreenPart = ApplicationDetailsPart(onElevated = !onElevated)

}

@Composable
fun AppDetails(screenState: State<ApplicationDetailsState>) {

    val stateValue = screenState.value
    when {
        stateValue.loading -> {
            TCircularProgressIndicator()
        }
        else -> {
            LazyColumn {

                item(
                    key = "appTitle"
                ) {
                    THeader(
                        title = stateValue.appName
                    )
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
    }


}
