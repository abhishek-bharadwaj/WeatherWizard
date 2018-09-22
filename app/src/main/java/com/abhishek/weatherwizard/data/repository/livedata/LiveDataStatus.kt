package com.abhishek.weatherwizard.data.repository.livedata

import android.support.annotation.StringDef

class LiveDataStatus {

    companion object {
        const val SUCCESS = "success"
        const val LOADING = "loading"
        const val ERROR = "error"
    }

    // Declare the @StringDef for these constants
    @StringDef(SUCCESS, LOADING, ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class LiveDataAPIStatus

}