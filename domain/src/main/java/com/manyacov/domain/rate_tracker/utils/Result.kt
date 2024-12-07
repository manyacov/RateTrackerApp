package com.manyacov.domain.rate_tracker.utils

sealed class CustomResult<T>(val data: T? = null, val message: String? = null, val issueType: NetworkIssues? = null) {
    class Success<T>(data: T?): CustomResult<T>(data)
    class Error<T>(message: String? = null, issueType: NetworkIssues): CustomResult<T>(null, message, issueType)
}