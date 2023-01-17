package com.study.transfer_calculator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val tableLiveData = MutableLiveData<TableRations>(TableRations())

    fun updateLivedata(newValue: CharSequence, modificationPosition: String) {
        val parsedValue = newValue.toDouble()
        when (modificationPosition) {
            "usdUni" -> tableLiveData.value = tableLiveData.value!!.copy(
                usdRatios = tableLiveData.value!!.usdRatios.copy(unistreamRatio = parsedValue)
            )

            "usdRico" -> tableLiveData.value = tableLiveData.value!!.copy(
                usdRatios = tableLiveData.value!!.usdRatios.copy(ricoRatio = parsedValue)
            )

            "euroUni" -> tableLiveData.value = tableLiveData.value!!.copy(
                euroRatios = tableLiveData.value!!.euroRatios.copy(unistreamRatio = parsedValue)
            )

            "euroRico" -> tableLiveData.value = tableLiveData.value!!.copy(
                euroRatios = tableLiveData.value!!.euroRatios.copy(ricoRatio = parsedValue)
            )

            "gelUni" -> tableLiveData.value = tableLiveData.value!!.copy(
                gelRatios = tableLiveData.value!!.gelRatios.copy(unistreamRatio = parsedValue)
            )

            "gelRico" -> tableLiveData.value = tableLiveData.value!!.copy(
                gelRatios = tableLiveData.value!!.gelRatios.copy(ricoRatio = parsedValue)
            )
        }
    }

    fun calculatePerformance(currencyRatio: CurrancyRatios): Double =
        currencyRatio.ricoRatio / (currencyRatio.unistreamRatio + currencyRatio.unistreamCommission)


    fun calculateRatios(): EfficiencyRatios = EfficiencyRatios(
        usdRatio = calculatePerformance(tableLiveData.value!!.usdRatios),
        euroRatio = calculatePerformance(tableLiveData.value!!.euroRatios),
        gelRatio = calculatePerformance(tableLiveData.value!!.gelRatios)
    )

    fun CharSequence.toDouble() = toString().toDoubleOrNull() ?: 0.0

}