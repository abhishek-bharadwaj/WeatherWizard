package com.abhishek.weatherwizard.data.repository.livedata

//a generic class that describes a data with a status
class Resource<out T> private constructor(@param:LiveDataStatus.LiveDataAPIStatus val status: String,
                                          val data: T?,
                                          val message: String?) {
    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(LiveDataStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(LiveDataStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LiveDataStatus.LOADING, data, null)
        }
    }
}