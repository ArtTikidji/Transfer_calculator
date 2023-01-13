package com.study.transfer_calculator

data class CurrancyRatios(
    val unistreamRatio: Double = 0.0,
    val ricoRatio: Double = 0.0,
    val unistreamCommission: Double = 0.0,
)

data class EfficiencyRatios(
    val usdRatio: Double,
    val euroRatio: Double,
    val gelRatio: Double
)

data class TableRations(
    val usdRatios: CurrancyRatios = CurrancyRatios(),
    val euroRatios: CurrancyRatios = CurrancyRatios(),
    val gelRatios: CurrancyRatios = CurrancyRatios(ricoRatio = 1.0)
)