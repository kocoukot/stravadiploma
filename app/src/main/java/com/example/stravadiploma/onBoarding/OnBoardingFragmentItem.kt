package com.example.stravadiploma.onBoarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.R
import com.example.stravadiploma.databinding.FragmentOnboardingItemBinding
import com.example.stravadiploma.utils.withArguments

class OnBoardingFragmentItem : Fragment(R.layout.fragment_onboarding_item) {


    private val binding by viewBinding(FragmentOnboardingItemBinding::bind)

    companion object {
        private const val KEY_TITLE = "text"
        private const val KEY_TEXT = "image"
        private const val KEY_IMAGE = "shown"

        fun newInstance(
            @StringRes screenTitle: Int,
            @StringRes screenText: Int,
            @DrawableRes screenImage: Int
        ): OnBoardingFragmentItem {
            return OnBoardingFragmentItem().withArguments {
                putInt(KEY_TITLE, screenTitle)
                putInt(KEY_TEXT, screenText)
                putInt(KEY_IMAGE, screenImage)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.onBoardingTitle.setText(requireArguments().getInt(KEY_TITLE))
        binding.onBoardingText.setText(requireArguments().getInt(KEY_TEXT))
        binding.imageViewOnBoarding.setImageResource(requireArguments().getInt(KEY_IMAGE))
    }
}
