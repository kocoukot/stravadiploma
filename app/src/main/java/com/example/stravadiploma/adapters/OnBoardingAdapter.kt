package com.example.stravadiploma.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.stravadiploma.data.OnBoardingScreenData
import com.example.stravadiploma.onBoarding.OnBoardingFragmentItem

class OnBoardingAdapter (
    private val screens: List<OnBoardingScreenData>,
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int {
        return screens.size
    }

    override fun createFragment(position: Int): Fragment {
        val screen: OnBoardingScreenData = screens[position]
        return OnBoardingFragmentItem.newInstance(
            screenTitle = screen.screenTitle,
            screenText = screen.screenText,
            screenImage = screen.screenImage
        )
    }

}