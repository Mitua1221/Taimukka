package com.arjental.taimukka.presentaion.ui.screens.onboarding

import com.arjental.taimukka.domain.uc.TPermission

@kotlinx.serialization.Serializable
sealed interface OnBoardingScreenTypes : java.io.Serializable {

    @kotlinx.serialization.Serializable
    class FirstLaunch(
    ) : OnBoardingScreenTypes {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    @kotlinx.serialization.Serializable
    class ApplicationDescription(
    ) : OnBoardingScreenTypes {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    @kotlinx.serialization.Serializable
    class PermissionsRequest(
        val permission: TPermission
    ) : OnBoardingScreenTypes

}


