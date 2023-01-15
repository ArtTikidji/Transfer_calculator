package com.study.transfer_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.study.transfer_calculator.databinding.FragmentCalculatorBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CalculatorFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentCalculatorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val tableObserver = Observer<TableRations> {
        val newRations: EfficiencyRatios = viewModel.calculateRatios()
        setResultField(binding.textUsdRatio, newRations.usdRatio)
        setResultField(binding.textEuroRatio, newRations.euroRatio)
        setResultField(binding.textGelRatio, newRations.gelRatio)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.tableLiveData.observe(viewLifecycleOwner, tableObserver)

        initTableFields()

    }

    private fun initTableFields() {
        val currentRations = viewModel.tableLiveData.value ?: return
        initTableField(binding.textInputUsdUniRatio, currentRations.usdRatios.unistreamRatio)
        initTableField(binding.textInputUsdRicoRatio, currentRations.usdRatios.ricoRatio)

        initTableField(binding.textInputEuroUniRatio, currentRations.euroRatios.unistreamRatio)
        initTableField(binding.textInputEuroRicoRatio, currentRations.euroRatios.ricoRatio)

        initTableField(binding.textInputGelUniRatio, currentRations.gelRatios.unistreamRatio)
        binding.textGelRicoRatio.text = "${currentRations.gelRatios.ricoRatio}"

        binding.textInputUsdUniRatio.doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
            viewModel.updateLivedata(s ?: "0.0", "usdUni")
        }

        binding.textInputUsdRicoRatio.doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
            viewModel.updateLivedata(s ?: "0.0", "usdRico")
        }

        binding.textInputEuroUniRatio.doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
            viewModel.updateLivedata(s ?: "0.0", "euroUni")
        }

        binding.textInputEuroRicoRatio.doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
            viewModel.updateLivedata(s ?: "0.0", "euroRico")
        }

        binding.textInputGelUniRatio.doOnTextChanged { s: CharSequence?, start: Int, before: Int, count: Int ->
            viewModel.updateLivedata(s ?: "0.0", "gelUni")
        }
    }

    private fun initTableField(field: TextInputEditText, value: Double) {
        val valueInString: String = if (value != 0.0) "$value" else ""
        field.setText(valueInString)
    }

    private fun setResultField(field: TextView, value: Double) {
        val isExceptionableValue = value.isFinite() && value != 0.0
        val strValue = if (isExceptionableValue) "%.4f".format(value) else ""
        field.text = strValue
    }

    override fun onDestroyView() {
        viewModel.tableLiveData.removeObserver(tableObserver)

        super.onDestroyView()
        _binding = null
    }
}