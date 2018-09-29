package com.abhishek.weatherwizard

import org.junit.Assert
import org.junit.Test

class UtilKtTest {

    @Test
    fun roundTo4DecimalPlacesTest() {
        Assert.assertEquals(21.4356, roundTo4DecimalPlaces(21.43561232), 0.0)
        Assert.assertEquals(2.4357, roundTo4DecimalPlaces(2.4356999), 0.0)
        Assert.assertEquals(0.5356, roundTo4DecimalPlaces(21.53565232), 0.0)
    }
}