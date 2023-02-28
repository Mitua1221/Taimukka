package com.arjental.taimukka.other.utils.resource

@kotlinx.serialization.Serializable
sealed interface Resource<T> : java.io.Serializable {
    data class Loading<T>(val data: T?, val percents: Int?, val stillLoading: Boolean) : Resource<T> {
        fun <T, Y> copyDiff(y: Y): Loading<Y> {
            return Loading(data = y, percents = percents, stillLoading = stillLoading)
        }
    }

    data class Error<T>(val data: T?, val cause: Throwable) : Resource<T> {
        fun <T, Y> copyDiff(y: Y): Error<Y> {
            return Error(data = y, cause = cause)
        }
    }

    data class Success<T>(val data: T) : Resource<T> {
        fun <T, Y> copyDiff(y: Y): Success<Y> {
            return Success(data = y)
        }
    }

    companion object {
        suspend fun <T> loading(data: T? = null, percents: Int? = null, stillLoading: Boolean = false): Loading<T> {
            if (percents != null && (percents < 0 || percents > 100)) throw IllegalStateException("percents cant be $percents")
            return Resource.Loading(data = data, percents = percents, stillLoading = stillLoading)
        }

        suspend fun <T> error(data: T?, cause: Throwable) = Resource.Error(data = data, cause = cause)
        suspend fun <T> success(data: T) = Resource.Success(data = data)
    }

}

/**
 * Provide fast data handling for all types of [Resource]
 *
 * Bad for changing state, onData calles before other on methods, so state modifies twice.
 *
 * @param onData provides data from all elements, its nullable cause data in error and loading nullable
 * @param onSuccess action
 * @param onError action
 * @param onLoading action

 */
inline fun <T> Resource<T>.onData(
    onSuccess: (res: Resource.Success<T>) -> Unit,
    onError: (res: Resource.Error<T>) -> Unit,
    onLoading: (res: Resource.Loading<T>) -> Unit,
    onData: (data: T?) -> Unit,
) {
    when (this) {
        is Resource.Success -> {
            onData(this.data)
            onSuccess(this)
        }
        is Resource.Error -> {
            onData(this.data)
            onError(this)
        }
        is Resource.Loading -> {
            onData(this.data)
            onLoading(this)
        }
    }
}

/**
 * Provide fast data handling for all types of [Resource] and fast convert
 *
 * @param onDataTransform provides data transformation, not used to return data
 * @param onSuccess action
 * @param onError action
 * @param onLoading action

 */
inline fun <T, F> Resource<T>.onDataTransform(
    onSuccess: (res: Resource.Success<F>) -> Unit,
    onError: (res: Resource.Error<F>) -> Unit,
    onLoading: (res: Resource.Loading<F>) -> Unit,
    onDataTransform: (data: T?) -> F,
) {
    when (this) {
        is Resource.Success -> {
            onSuccess(this.copyDiff<T, F>(onDataTransform(this.data)))
        }
        is Resource.Error -> {
            onError(this.copyDiff<T, F>(onDataTransform(this.data)))
        }
        is Resource.Loading -> {
            onLoading(this.copyDiff<T, F>(onDataTransform(this.data)))
        }
    }
}

//fun <T, Y> Resource<T>.copyDiff(data: Y?): Resource<Y> = Resource(
//    data = data,
//    status = status,
//    messageTitle = messageTitle,
//    message = message,
//    code = code,
//    apiCode = apiCode,
//)
//
//fun <T, Y> Resource<T>.copyDiff(nullReplace: Y? = null, u: (t: T) -> Y): Resource<Y> = Resource(
//    data = if (data == null) nullReplace else u(data),
//    status = status,
//    messageTitle = messageTitle,
//    message = message,
//    code = code,
//    apiCode = apiCode,
//)

//suspend fun <T, Y> Resource<T>.copyDiffSuspend(nullReplace: Y? = null, u: suspend (t: T) -> Y): Resource<Y> = Resource(
//    data = if (data == null) nullReplace else u(data),
//    status = status,
//    messageTitle = messageTitle,
//    message = message,
//    code = code,
//    apiCode = apiCode,
//)
//
///**
// * Проверяет, null ли данные. Если null, превращает респонс в ошибку
// */
//suspend fun <T> Resource<T>.check(context: Context): Resource<T> {
//    if (status != ResourceStatus.SUCCESS) return this
//    val isDataNull = data == null
//    return Resource(
//        data = data,
//        status = if (isDataNull) ResourceStatus.ERROR else status,
//        messageTitle = messageTitle,
//        message = if (isDataNull) {
//            if (message != null) context.getString(R.string.taxcomkit_message_null) + ", " + message else context.getString(R.string.taxcomkit_message_null)
//        } else message,
//        code = code,
//        apiCode = apiCode,
//    )
//}
