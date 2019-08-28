package com.rdo.octo.happinesspath

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class Pattern(
    val id: Int,
    @ColorRes val categoryColor: Int,
    @DrawableRes val image: Int,
    @StringRes val text: Int
) {


}