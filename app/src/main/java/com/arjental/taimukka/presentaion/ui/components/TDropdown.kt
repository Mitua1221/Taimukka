package com.arjental.taimukka.presentaion.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

data class TDropdownItem(
    val type: String,
    val title: String? = null,
    @StringRes val titleRes: Int? = null,
)

@Composable
fun TDropdown(
    items: List<TDropdownItem>,
    expanded: Boolean,
    setExpanded: (Boolean) -> Unit,
    topOffset: Dp = 0.dp,
    onSelect: (TDropdownItem) -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface),
            offset = DpOffset(x = 0.dp, y = topOffset), //bad ass
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    onSelect(item)
                    setExpanded(false)
                }, text = {
                    Text(
                        text = item.title ?: item.titleRes?.let { stringRes -> stringResource(id = stringRes ) }  ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                })
            }
        }
    }
}