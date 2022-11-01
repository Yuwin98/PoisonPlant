package com.yuwin.proj.util

import com.yuwin.proj.util.Constants.GALLERY_SCREEN
import com.yuwin.proj.util.Constants.HOME_SCREEN
import com.yuwin.proj.util.Constants.RESULT_SCREEN

sealed class Screens(val route: String) {
    object Home : Screens(HOME_SCREEN)
    object Gallery : Screens(GALLERY_SCREEN)
    object Result : Screens(RESULT_SCREEN)
}
