package com.arjental.taimukka.presentaion.ui.components.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arjental.taimukka.R

@Composable
fun SearchLayout(modifier: Modifier = Modifier) {
    Surface(
        shape = CircleShape,
        tonalElevation = 3.dp,
        modifier = modifier.padding(top = 12.dp, bottom = 12.dp, start = 16.dp, end = 16.dp)
    ) {
        
        Row(
            modifier = Modifier.height(52.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search_hint),
                modifier = Modifier.padding(start = 16.dp),
                tint = MaterialTheme.colorScheme.outline
            )
            Text(
                text = "stringResource(id = R.string.search_replies)",
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
//        ReplyProfileImage(
//            drawableResource = R.drawable.avatar_6,
//            description = stringResource(id = R.string.profile),
//            modifier = Modifier
//                .padding(12.dp)
//                .size(32.dp)
//        )
        }
    }
}