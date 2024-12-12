package com.manyacov.presentation.utils

import com.manyacov.domain.rate_tracker.utils.NetworkIssues
import com.manyacov.ui.R

fun NetworkIssues?.handleError(): Int {
    return when (this) {
        NetworkIssues.REQUEST_TIMEOUT_ERROR -> R.string.timeout_error
        NetworkIssues.NO_INTERNET_ERROR -> R.string.no_internet_error
        else -> R.string.other_error
    }
}
