package com.abhishek.weatherwizard.data

import java.util.*

class Optional<M>(private val optional: M?) {

    fun isEmpty() = optional == null

    fun isNotEmpty() = !isEmpty()

    fun get(): M {
        if (optional == null) {
            throw NoSuchElementException("No value present")
        }
        return optional
    }
}