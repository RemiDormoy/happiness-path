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
    AUGMENTED_LIST(1, R.drawable.button_drawer_1, R.drawable.augmented_listes, R.string.content_augmented_list, R.string.title_augmented_list_1),
    KEEP_THEM_WAITING(2, R.drawable.button_drawer_2, R.drawable.attente, R.string.content_waiting, R.string.title_waiting),
    GRATIFICATION(3, R.drawable.button_drawer_3, R.drawable.gratifier, R.string.content_gratification, R.string.title_gratification),
    FINGERPRINT(4, R.drawable.button_drawer_1, R.drawable.simple_auth, R.string.content_fingerprint, R.string.title_fingerprint),
    GESTURES(5, R.drawable.button_drawer_2, R.drawable.gestures, R.string.content_gestures, R.string.title_gestures),
    HUMANE_DESIGN(6, R.drawable.button_drawer_3, R.drawable.humane_design, R.string.content_humane, R.string.title_humane),
    SIMPLE_ACCESS(7, R.drawable.button_drawer_1, R.drawable.personaliser, R.string.content_access, R.string.title_access),
    BOTTOM(8, R.drawable.button_drawer_2, R.drawable.bottom_bouttons, R.string.content_bottom, R.string.title_bottom),
    INFINITE_NAV(9, R.drawable.button_drawer_3, R.drawable.borderless, R.string.content_nav, R.string.title_nav)
    ;

    fun getPatternLabel() = "Pattern #$id"
}