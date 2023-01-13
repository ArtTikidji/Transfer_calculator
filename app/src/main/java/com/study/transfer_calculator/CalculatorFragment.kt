package com.study.transfer_calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
        binding.textUsdRatio.text = "%.4f".format(newRations.usdRatio)
        binding.textEuroRatio.text = "%.4f".format(newRations.euroRatio)
        binding.textGelRatio.text = "%.4f".format(newRations.gelRatio)
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
        binding.textInputUsdUniRatio.setText("${currentRations.usdRatios.unistreamRatio}")
        binding.textInputUsdRicoRatio.setText("${currentRations.usdRatios.ricoRatio}")

        binding.textInputEuroUniRatio.setText("${currentRations.euroRatios.unistreamRatio}")
        binding.textInputEuroRicoRatio.setText("${currentRations.euroRatios.ricoRatio}")

        binding.textInputGelUniRatio.setText("${currentRations.gelRatios.unistreamRatio}")
        binding.textGelRicoRatio.setText("${currentRations.gelRatios.ricoRatio}")

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

    override fun onDestroyView() {
        viewModel.tableLiveData.removeObserver(tableObserver)

        super.onDestroyView()
        _binding = null
    }
}