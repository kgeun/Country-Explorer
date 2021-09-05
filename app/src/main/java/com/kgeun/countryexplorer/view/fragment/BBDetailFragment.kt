package com.kgeun.countryexplorer.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.kgeun.countryexplorer.databinding.FragmentDetailBinding
import com.kgeun.countryexplorer.view.CEBaseFragment
import com.kgeun.countryexplorer.viewmodel.CEMainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BBDetailFragment : CEBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    val mainViewModel: CEMainViewModel by viewModels()
    var charId: Long = 1L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        charId = BBCharacterListFragmentArgs.fromBundle(requireArguments()).charId

        subscribeUi()
        setListener()

        return binding.root
    }

    private fun subscribeUi() {
        mainViewModel.getCharacterByCharId(charId).observe(viewLifecycleOwner) {
            if (it != null) {
                binding.character = it
            }
        }
    }

    private fun setListener() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}