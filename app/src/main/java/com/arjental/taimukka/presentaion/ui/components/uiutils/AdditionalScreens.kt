package com.arjental.taimukka.presentaion.ui.components.uiutils

/**
 * Some pages need loading, error states or etc.
 *
 * This class helps to create one state sample for all ui parts of application
 *
 */

abstract class AdditionalScreens {
    abstract val loading: Boolean
    abstract val error: AdditionalError?
}

@kotlinx.serialization.Serializable
data class AdditionalError(
    val message: String? = null,
): java.io.Serializable

public fun createAddError(t: Throwable): AdditionalError {
    return AdditionalError(
        message = t.localizedMessage
    )
}

