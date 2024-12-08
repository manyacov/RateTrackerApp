package com.manyacov.data.rate_tracker.util

import com.manyacov.domain.rate_tracker.utils.CustomResult
import com.manyacov.domain.rate_tracker.utils.NetworkIssues
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeCall(call: suspend () -> T): CustomResult<T?> {
    return try {
        CustomResult.Success(call())
    } catch (ex: Exception) {
        val issue = handleNetworkError(ex)
        CustomResult.Error(issueType = issue)
    }
}

fun handleNetworkError(exception: Exception): NetworkIssues {
    return when (exception) {
        is SocketTimeoutException -> NetworkIssues.REQUEST_TIMEOUT_ERROR
        is UnknownHostException -> NetworkIssues.NO_INTERNET_ERROR
        is HttpException -> {
            when (exception.code()) {
                in 500..599 -> NetworkIssues.SERVER_ERROR
                else -> NetworkIssues.UNKNOWN_ERROR
            }
        }
        is SerializationException -> NetworkIssues.SERIALIZATION_ERROR
        else -> NetworkIssues.UNKNOWN_ERROR
    }
}