package com.arjental.taimukka.other.utils.resource

@kotlinx.serialization.Serializable
sealed interface Resource<T> {
    data class Loading<T>(val data: T?, val percents: Int?, val stillLoading: Boolean): Resource<T>
    data class Error<T>(val data: T?, val cause: Throwable): Resource<T>
    data class Success<T>(val data: T): Resource<T>

    companion object {
        suspend fun <T> loading(data: T? = null, percents: Int? = null, stillLoading: Boolean = false): Loading<T> {
            if (percents != null && (percents < 0 || percents > 100)) throw IllegalStateException("percents cant be $percents")
            return Resource.Loading(data = data, percents = percents, stillLoading = stillLoading)
        }
        suspend fun <T> error(data: T?, cause: Throwable) = Resource.Error(data = data, cause = cause)
        suspend fun <T> success(data: T) = Resource.Success(data = data)
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
//
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
