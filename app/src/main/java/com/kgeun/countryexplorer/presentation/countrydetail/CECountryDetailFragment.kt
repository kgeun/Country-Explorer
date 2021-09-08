package com.kgeun.countryexplorer.presentation.countrydetail

import android.animation.ValueAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.databinding.FragmentDetailBinding
import com.kgeun.countryexplorer.extension.observe
import com.kgeun.countryexplorer.network.CENetworkHandler
import com.kgeun.countryexplorer.network.NetworkState
import com.kgeun.countryexplorer.presentation.CEBaseFragment
import com.kgeun.countryexplorer.presentation.countrydetail.data.CECountryViewItem
import com.kgeun.countryexplorer.presentation.countrylist.CECountryListFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CECountryDetailFragment : CEBaseFragment() {
    private lateinit var binding: FragmentDetailBinding
    val detailViewModel: CECountryDetailViewModel by viewModels()
    var alphaCode: String = ""
    private lateinit var handler: CENetworkHandler<CECountryViewItem?>

    private var startAvatarAnimatePointY: Float = 0F
    private var animateWeigt: Float = 0F
    private val mLowerLimitTransparently = ABROAD * 0.45
    private val mUpperLimitTransparently = ABROAD * 0.65
    private var EXPAND_AVATAR_SIZE: Float = 0F
    private var COLLAPSE_IMAGE_SIZE: Float = 0F
    private var margin: Float = 0F
    private var isCalculated = false
    private var cashCollapseState: Pair<Int, Int>? = null

    companion object {
        const val ABROAD = 0.99f
        const val TO_EXPANDED_STATE = 0
        const val TO_COLLAPSED_STATE = 1
        const val WAIT_FOR_SWITCH = 0
        const val SWITCHED = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        alphaCode = CECountryListFragmentArgs.fromBundle(requireArguments()).alphaCode
        handler = CENetworkHandler(requireActivity())
        
        setupCollapsingToolbar()
        setupHandler()
        bindUi()
        subscribeUi()

        return binding.root
    }

    private fun setupHandler() {
        handler.loadingCallBack = {
            binding.contentLoadingIndicator.start()
        }

        handler.errorCallBack = {
            binding.loadingIndicator.stop()
            binding.contentLoadingIndicator.stop()
            binding.communicationFailLayout.root.visibility = View.VISIBLE
        }
    }

    private fun bindUi() {
        binding.loadingIndicator.start()
        binding.backBtn1.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.backBtn2.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.communicationFailLayout.retryButton.setOnClickListener {
            detailViewModel.getCountryDetail(alphaCode)
        }
    }

    private fun setupCollapsingToolbar() {
        EXPAND_AVATAR_SIZE = resources.getDimension(R.dimen.default_expanded_image_size)
        COLLAPSE_IMAGE_SIZE = resources.getDimension(R.dimen.default_collapsed_image_size)

        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                if (isCalculated.not()) {
                    startAvatarAnimatePointY = Math.abs((appBarLayout.height - EXPAND_AVATAR_SIZE - binding.toolbar.height / 2) / appBarLayout.totalScrollRange)
                    animateWeigt = 1 / (1 - startAvatarAnimatePointY)
                    isCalculated = true
                }

                val offset = Math.abs(i / appBarLayout.totalScrollRange.toFloat())
                updateViews(offset)
            })
    }

    private fun subscribeUi() {
        observe(detailViewModel.countryDetailLivedata) {
            handler.success (it) {
                binding.loadingIndicator.stop()
                binding.contentLoadingIndicator.stop()
                binding.communicationFailLayout.root.visibility = View.GONE
                binding.country = it
            }
        }
        detailViewModel.getCountryDetail(alphaCode)
    }

    private fun updateViews(percentOffset: Float) {
        when {
            percentOffset > mUpperLimitTransparently -> {
                binding.bigTitle2.alpha = 0.0F
            }

            percentOffset < mUpperLimitTransparently -> {
                binding.bigTitle2.alpha = 1f
            }
        }

        val result: Pair<Int, Int> = when {
            percentOffset < ABROAD -> {
                Pair(TO_EXPANDED_STATE, cashCollapseState?.second
                    ?: WAIT_FOR_SWITCH)
            }
            else -> {
                Pair(TO_COLLAPSED_STATE, cashCollapseState?.second ?: WAIT_FOR_SWITCH)
            }
        }

        result.apply {
            var translationY = 0f
            var headContainerHeight = 0f
            val translationX: Float
            var currentImageSize = 0
            when {
                cashCollapseState != null && cashCollapseState != this -> {
                    when (first) {
                        TO_EXPANDED_STATE -> {
                            translationY = binding.toolbar.height.toFloat()
                            headContainerHeight = binding.appBarLayout.totalScrollRange.toFloat()
                            currentImageSize = EXPAND_AVATAR_SIZE.toInt()
                            /**/
                            binding.bigTitle2.visibility = View.VISIBLE
                            binding.bigTitle1.visibility = View.INVISIBLE
                            binding.flBackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.transparent))
                            /**/
                            binding.flagImage.translationX = 0f
                            binding.backBtn1.visibility = View.VISIBLE
                            binding.backBtn2.visibility = View.GONE
                        }

                        TO_COLLAPSED_STATE -> {
                            binding.backBtn1.visibility = View.GONE
                            binding.backBtn2.visibility = View.VISIBLE

                            binding.flBackground.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.solid_grey_0_5))
                            currentImageSize = COLLAPSE_IMAGE_SIZE.toInt()
                            translationY = binding.appBarLayout.totalScrollRange.toFloat() - (binding.toolbar.height - COLLAPSE_IMAGE_SIZE) / 2
                            headContainerHeight = binding.toolbar.height.toFloat()
                            translationX = binding.appBarLayout.width / 2f - COLLAPSE_IMAGE_SIZE / 2 - margin * 2 - resources.getDimension(R.dimen.global_horizontal_margin)
                            /**/
                            ValueAnimator.ofFloat(binding.flagImage.translationX, translationX).apply {
                                addUpdateListener {
                                    if (cashCollapseState!!.first == TO_COLLAPSED_STATE) {
                                        binding.flagImage.translationX = it.animatedValue as Float
                                    }
                                }
                                interpolator = AnticipateOvershootInterpolator()
                                startDelay = 69
                                duration = 350
                                start()
                            }
                            /**/
                            binding.bigTitle2.visibility = View.INVISIBLE
                            binding.bigTitle1.apply {
                                visibility = View.VISIBLE
                                alpha = 0.2f
                                this.translationX = width.toFloat() / 2
                                animate().translationX(0f)
                                    .setInterpolator(AnticipateOvershootInterpolator())
                                    .alpha(1.0f)
                                    .setStartDelay(69)
                                    .setDuration(450)
                                    .setListener(null)
                            }
                        }
                    }

                    binding.flagImage.apply {
                        layoutParams.height = currentImageSize
                        layoutParams.width = currentImageSize
                    }
                    binding.stuffContainer.apply {
                        layoutParams.height = headContainerHeight.toInt()
                        this.translationY = translationY
                        requestLayout()
                    }
                    /**/
                    cashCollapseState = Pair(first, SWITCHED)
                }
                else -> {
                    /* Collapse avatar img*/
                    binding.flagImage.apply {
                        if (percentOffset > startAvatarAnimatePointY) {

                            val animateOffset = (percentOffset - startAvatarAnimatePointY) * animateWeigt
//                            Timber.d("offset for anim $animateOffset")
                            val avatarSize = EXPAND_AVATAR_SIZE - (EXPAND_AVATAR_SIZE - COLLAPSE_IMAGE_SIZE) * animateOffset

                            this.layoutParams.also {
                                if (it.height != Math.round(avatarSize)) {
                                    it.height = Math.round(avatarSize)
                                    it.width = Math.round(avatarSize)
                                    this.requestLayout()
                                }
                            }

                            this.translationY = (COLLAPSE_IMAGE_SIZE - avatarSize) * animateOffset

                        } else {
                            this.layoutParams.also {
                                if (it.height != EXPAND_AVATAR_SIZE.toInt()) {
                                    it.height = EXPAND_AVATAR_SIZE.toInt()
                                    it.width = EXPAND_AVATAR_SIZE.toInt()
                                    this.layoutParams = it
                                }
                            }
                        }
                    }
                    cashCollapseState = Pair(first, WAIT_FOR_SWITCH)
                }
            }
        }
    }
}