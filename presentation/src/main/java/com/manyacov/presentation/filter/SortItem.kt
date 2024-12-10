package com.manyacov.presentation.filter

import com.manyacov.presentation.filter.SortOptions.CODE_A_Z
import com.manyacov.presentation.filter.SortOptions.CODE_Z_A
import com.manyacov.presentation.filter.SortOptions.QUOTE_ASC
import com.manyacov.presentation.filter.SortOptions.QUOTE_DESC
import com.manyacov.ui.R

enum class SortOptions(private val descriptionRes: Int) {
    CODE_A_Z(R.string.sort_a_z),
    CODE_Z_A(R.string.sort_z_a),
    QUOTE_ASC(R.string.sort_asc),
    QUOTE_DESC(R.string.sort_desc);

    fun getDescriptionRes(): Int {
        return descriptionRes
    }
}

fun String?.getSortOptionByDescription(): SortOptions {
    return when(this) {
        "CODE_Z_A" -> CODE_Z_A
        "QUOTE_ASC" -> QUOTE_ASC
        "QUOTE_DESC" -> QUOTE_DESC
        else -> CODE_A_Z
    }
}