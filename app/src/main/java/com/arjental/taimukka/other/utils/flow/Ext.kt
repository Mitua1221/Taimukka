package com.arjental.taimukka.other.utils.flow

import com.arjental.taimukka.other.utils.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<Resource<T>>.handleErrors(defaultOnError: T?): Flow<Resource<T>> = catch { e ->
    Resource.error(data = defaultOnError, cause = e)
}