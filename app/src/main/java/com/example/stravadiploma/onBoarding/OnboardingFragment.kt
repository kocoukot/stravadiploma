package com.example.stravadiploma.onBoarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.adapters.OnBoardingAdapter
import com.example.stravadiploma.data.OnBoardingScreenData
import com.example.stravadiploma.R
import com.example.stravadiploma.auth.AuthFragment
import com.example.stravadiploma.databinding.FragmentOnboardingBinding
import com.example.stravadiploma.utils.Constants

class OnboardingFragment : Fragment(R.layout.fragment_onboarding) {

    private val binding by viewBinding(FragmentOnboardingBinding::bind)


    private val sharedPref by lazy {
        requireContext().getSharedPreferences(
            Constants.SHARED_PREF,
            Context.MODE_PRIVATE
        )
    }

    private val onBoardingScreens: List<OnBoardingScreenData> = listOf(
        OnBoardingScreenData(
            screenTitle = R.string.onboarding_first_title,
            screenText = R.string.onboarding_first_text,
            screenImage = R.drawable.onb_first
        ),
        OnBoardingScreenData(
            screenTitle = R.string.onboarding_second_title,
            screenText = R.string.onboarding_second_text,
            screenImage = R.drawable.onb_second
        ),
        OnBoardingScreenData(
            screenTitle = R.string.onboarding_third_title,
            screenText = R.string.onboarding_third_text,
            screenImage = R.drawable.onb_third
        )
    )

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewPager()
        setBindings()

    }

    private fun setBindings() {
        binding.skipButton.setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.frame_for_Fragment, AuthFragment())
                .commit()
            sharedPref.edit()
                .putBoolean(Constants.IS_FIRST_TIME_LAUNCH_KEY, false)
                .apply()
        }
    }

    private fun setViewPager() {
        val adapter = OnBoardingAdapter(screens = onBoardingScreens, requireActivity())
        binding.onBoardingViewPager.adapter = adapter
        binding.wormDotsIndicator.setViewPager2(binding.onBoardingViewPager)
        binding.onBoardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    2 -> binding.skipButton.setText(R.string.okay)
                    else -> binding.skipButton.setText(R.string.on_boarding_skip)
                }
            }
        })
    }
}