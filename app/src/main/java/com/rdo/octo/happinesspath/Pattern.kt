package com.rdo.octo.happinesspath

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class Pattern(
    val id: Int,
    @DrawableRes val categoryColor: Int,
    @DrawableRes val image: Int,
    @StringRes val text: Int,
    @StringRes val title: Int
) {
    AUGMENTED_LIST(1, R.drawable.button_drawer_1, R.drawable.image_augmented_list, R.string.content_augmented_list, R.string.title_augmented_list_1),
    KEEP_THEM_WAITING(2, R.drawable.button_drawer_2, R.drawable.waiting_image, R.string.content_waiting, R.string.title_waiting),
    GRATIFICATION(3, R.drawable.button_drawer_3, R.drawable.gratification_image, R.string.content_gratification, R.string.title_gratification),
    FINGERPRINT(4, R.drawable.button_drawer_1, R.drawable.gratification_image, R.string.content_gratification, R.string.title_fingerprint),
    GESTURES(5, R.drawable.button_drawer_2, R.drawable.gratification_image, R.string.content_gratification, R.string.title_gestures)
    ;

    fun getPatternLabel() = "Pattern #$id"
}