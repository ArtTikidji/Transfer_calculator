package com.study.transfer_calculator

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.study.transfer_calculator.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels()
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTableFields()

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        binding.buttonCalculateRatios.setOnClickListener {
            viewModel.calculateRatios()
            binding.textUsdRatio.text = "%.4f".format(viewModel.usdRatio)
            binding.textEuroRatio.text = "%.4f".format(viewModel.euroRatio)
            binding.textGelRatio.text = "%.4f".format(viewModel.gelRatio)
        }
    }

    private fun initTableFields() {
        binding.textInputUsdUniRatio.setText(viewModel.usdUnistream.toString())
        binding.textInputUsdRicoRatio.setText(viewModel.usdRico.toString())

        binding.textInputEuroUniRatio.setText(viewModel.euroUnistream.toString())
        binding.textInputEuroRicoRatio.setText(viewModel.euroRico.toString())

        binding.textInputGelUniRatio.setText(viewModel.gelUnistream.toString())
        binding.textGelRicoRatio.setText(viewModel.gelRico.toString())

        binding.textInputUsdUniRatio.doAfterTextChanged {
            viewModel.usdUnistream = dataDoubleFieldExtractor(it)
        }

        binding.textInputUsdRicoRatio.doAfterTextChanged {
            viewModel.usdRico = dataDoubleFieldExtractor(it)
        }

        binding.textInputEuroUniRatio.doAfterTextChanged {
            viewModel.euroUnistream = dataDoubleFieldExtractor(it)
        }

        binding.textInputEuroRicoRatio.doAfterTextChanged {
            viewModel.euroRico = dataDoubleFieldExtractor(it)
        }

        binding.textInputGelUniRatio.doAfterTextChanged {
            viewModel.gelUnistream = dataDoubleFieldExtractor(it)
        }
    }

    private fun dataDoubleFieldExtractor(text: Editable?): Double {
        return try {
            (text ?: "0.0").toString().toDouble()
        } catch (e: java.lang.NumberFormatException) {
            0.0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}