package com.kgeun.countryexplorer.presentation.countrydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.kgeun.countryexplorer.databinding.FragmentDetailBinding
import com.kgeun.countryexplorer.presentation.CEBaseFragment
import com.kgeun.countryexplorer.presentation.countrylist.CECountryListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CECountryDetailFragment : CEBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    val mainViewModel: CECountryListViewModel by viewModels()
    var alphaCode: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        alphaCode = CECountryListFragmentArgs.fromBundle(requireArguments()).alphaCode

        subscribeUi()
        setListener()

        return binding.root
    }

    private fun subscribeUi() {
//        mainViewModel.getCountryByCode(charId).observe(viewLifecycleOwner) {
//            if (it != null) {
//                binding.character = it
//            }
//        }
    }

    private fun setListener() {
//        binding.btnBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
    }
}