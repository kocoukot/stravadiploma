package com.example.stravadiploma.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingScreenData(
    @StringRes val screenTitle: Int,
    @StringRes val screenText: Int,
    @DrawableRes val screenImage: Int
)

