package com.manyacov.presentation.filter

import com.manyacov.ui.R

enum class SortOptions(val descriptionRes: Int) {
    CODE_A_Z(R.string.sort_a_z),
    CODE_Z_A(R.string.sort_z_a),
    QUOTE_ASC(R.string.sort_asc),
    QUOTE_DESC(R.string.sort_desc);
}