package com.arjental.taimukka.presentaion.ui.screens.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.arjental.taimukka.TaimukkaActivity
import com.arjental.taimukka.domain.uc.TPermission
import com.arjental.taimukka.other.utils.factories.viewmodel.daggerViewModel
import com.arjental.taimukka.presentaion.ui.components.app.TBoxBackground
import com.arjental.taimukka.presentaion.ui.components.uiutils.LocalTActivity
import com.arjental.taimukka.presentaion.ui.components.uiutils.rememberTCoroutineScope
import com.arjental.taimukka.presentaion.ui.screens.splash.SplashVM
import kotlinx.coroutines.launch

@Preview
@Composable
fun Preview() {
    val activity = TaimukkaActivity()
    CompositionLocalProvider(LocalTActivity provides activity,
        LocalLifecycleOwner provides LifecycleOwner { activity.lifecycle }
    ) {
        OnBoarding(
            onBoardingList = listOf(
//             OnBoardingScreenTypes.FirstLaunch(),
//            OnBoardingScreenTypes.ApplicationDescription(),
                OnBoardingScreenTypes.PermissionsRequest(
                    permission = TPermission.CHECK_USAGE_STATS()
                )
            )
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoarding(
    onBoardingList: List<OnBoardingScreenTypes>
) {
    TBoxBackground(isFaceOnBackground = true, bottomText = true) {

        val horizontalPagerState = rememberPagerState()
        val scope = rememberTCoroutineScope()

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
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 48.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (val type = onBoardingList[page]) {
                        is OnBoardingScreenTypes.FirstLaunch -> FirstLaunch()
                        is OnBoardingScreenTypes.ApplicationDescription -> ApplicationDescription(onNextClick = { navigateNext(page) }, last = page == onBoardingList.lastIndex)
                        is OnBoardingScreenTypes.PermissionsRequest -> PermissionRequest(permissionType = type.permission, last = page == onBoardingList.lastIndex)
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
    last: Boolean,
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
    val splashVM = daggerViewModel<SplashVM>()
    Button(modifier = Modifier.padding(top = 40.dp), onClick = {
        if (last) splashVM.lastPermissionGranted() else onNextClick()
    }) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = com.arjental.taimukka.R.string.onboarding_appeal_start),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun PermissionRequest(
    permissionType: TPermission,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    last: Boolean,
) {
    Text(
        textAlign = TextAlign.Center,
        text = stringResource(id = com.arjental.taimukka.R.string.onboarding_permission),
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        style = MaterialTheme.typography.displaySmall
    )
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 16.dp),
        text = stringResource(id = permissionType.description),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.bodyLarge
    )
    val activity = LocalTActivity.current

    val splashVM = daggerViewModel<SplashVM>()
    val scope = rememberTCoroutineScope()

    var permissionGranted by remember { mutableStateOf(false) }

    if (last && permissionGranted) splashVM.lastPermissionGranted()

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                scope.launch {
                    permissionGranted = splashVM.ensurePermissionGranted(permissionType)
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Button(enabled = !permissionGranted, modifier = Modifier.padding(top = 40.dp), onClick = { permissionType.requestSystemPermission(activity) }) {
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(id = com.arjental.taimukka.R.string.onboarding_allow),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.labelLarge,
        )
    }


}