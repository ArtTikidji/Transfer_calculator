package com.study.transfer_calculator

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var usdUnistream: Double = 0.0
    var usdRico: Double = 0.0
    var usdRatio: Double = 0.0

    var euroUnistream: Double = 0.0
    var euroRico: Double = 0.0
    var euroRatio: Double = 0.0

    var gelUnistream: Double = 0.0
    var gelRico: Double = 1.0
    var gelRatio: Double = 0.0

    fun calculatePerformance(
        ricoRate: Double,
        uniRate: Double,
        uniCommission: Double = 0.0
    ): Double {
        return try {
            ricoRate / (uniRate + uniCommission)
        } catch (e: ArithmeticException) {
            0.0
        }
    }

    fun calculateRatios() {
        usdRatio = calculatePerformance(usdRico, usdUnistream)
        euroRatio = calculatePerformance(euroRico, euroUnistream)
        gelRatio = calculatePerformance(gelRico, gelUnistream)
    }
}