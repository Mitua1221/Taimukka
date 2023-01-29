package com.arjental.taimukka.presentaion.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arjental.taimukka.presentaion.ui.components.app.TBoxBackground
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview() {
    OnBoarding(
        onBoardingList = listOf(
         //    OnBoardingScreenTypes.FirstLaunch(),
            OnBoardingScreenTypes.ApplicationDescription(),
//        OnBoardingScreenTypes.PermissionsRequest(
//            permission = TPermission.CHECK_USAGE_STATS()
//        )
        )
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(

    //lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onBoardingList: List<OnBoardingScreenTypes>

) {


    //val activity = LocalTActivity.current

//    val splashVM = daggerViewModel<SplashVM>()
//
//    val currentOnResume by rememberUpdatedState {
//        splashVM.ensureSplashActive()
//    }
//
//    LocalNavigator.current
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                currentOnResume()
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }

    TBoxBackground(isFaceOnBackground = true) {

        val horizontalPagerState = rememberPagerState()
        val scope = rememberCoroutineScope()

        val navigateNext: (Int) -> Unit = { page ->
            scope.launch {
                val p = page + 1
                if (p <= onBoardingList.lastIndex)
                    horizontalPagerState.animateScrollToPage(page = page + 1)
            }
        }

        HorizontalPager(
            state = horizontalPagerState,
            pageCount = onBoardingList.size,

        ) { page ->
            Box(modifier = Modifier.fillMaxHeight().padding(horizontal = 48.dp)) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (onBoardingList[page]) {
                        is OnBoardingScreenTypes.FirstLaunch -> FirstLaunch()
                        is OnBoardingScreenTypes.ApplicationDescription -> ApplicationDescription(onNextClick = { navigateNext(page) })
                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun FirstLaunch() {
    Text(
        text = stringResource(id = com.arjental.taimukka.R.string.app_name),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displayLarge
    )
    Text(
        text = stringResource(id = com.arjental.taimukka.R.string.onboarding_greetings),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.displaySmall
    )
}

@Composable
private fun ApplicationDescription(
    onNextClick: () -> Unit,
) {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = com.arjental.taimukka.R.string.app_name),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.displayLarge
    )
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(id = com.arjental.taimukka.R.string.onboarding_appeal),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.titleLarge
    )
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 40.dp),
        text = stringResource(id = com.arjental.taimukka.R.string.onboarding_appeal_permissions),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyMedium,
        letterSpacing = -0.25.sp
    )
    Button(modifier = Modifier.padding(top = 40.dp), onClick = onNextClick) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = com.arjental.taimukka.R.string.onboarding_appeal_start),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}