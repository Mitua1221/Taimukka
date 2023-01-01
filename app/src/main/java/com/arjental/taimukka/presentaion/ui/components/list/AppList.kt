package com.arjental.taimukka.presentaion.ui.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.entities.presentaion.applist.AppListItemPres
import com.arjental.taimukka.presentaion.ui.components.loading.CircularProgressIndicator
import com.arjental.taimukka.presentaion.ui.components.search.SearchLayout
import com.arjental.taimukka.presentaion.ui.screens.app_list.AppListState


@Composable
fun AppList(applicationsListState: AppListState) {

    when (applicationsListState) {
        is AppListState.PagePreparing -> {
            CircularProgressIndicator(statusBarPadding = true)
        }
        is AppListState.PageLoading -> {
            CircularProgressIndicator(statusBarPadding = true)
        }
        is AppListState.PageLoaded -> {
            LazyColumn {
                item {
                    SearchLayout(modifier = Modifier.statusBarsPadding())
                }
                items(applicationsListState.list.size) { index ->
                    AppListItem(applicationsListState.list[index])
                }
            }
        }
        is AppListState.PageError -> {}
    }

}

@Composable
fun AppListItem(
    item: AppListItemPres
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageBitmap = item.appIcon
        imageBitmap?.let {
            Image(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, start = 16.dp)
                    .size(40.dp)
                    .clip(CircleShape), bitmap = it, contentDescription = ""
            )
        }
        Text(
            style = MaterialTheme.typography.titleMedium, modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .weight(1f), text = item.title
        )
    }
}


