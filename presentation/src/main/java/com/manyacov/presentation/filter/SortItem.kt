package com.manyacov.presentation.filter

import androidx.compose.ui.res.stringResource
import com.manyacov.ui.R

//enum class SortOptions {
//    CODE_A_Z, CODE_Z_A, QUOTE_ASC, QUOTE_DESC
//}
enum class SortOptions(private val descriptionRes: Int) {
    CODE_A_Z(R.string.sort_a_z),
    CODE_Z_A(R.string.sort_z_a),
    QUOTE_ASC(R.string.sort_asc),
    QUOTE_DESC(R.string.sort_desc);

    fun getDescriptionRes(): Int {
        return descriptionRes
    }
}