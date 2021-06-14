package com.example.stravadiploma.useractivitylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.MainActivity
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.databinding.FragmentActivitiesListBinding
import com.example.stravadiploma.databinding.FragmentProfileBinding
import com.example.stravadiploma.newactivity.NewActivityFragment

class UserActivityFragment : Fragment(R.layout.fragment_activities_list) {

    private val binding by viewBinding(FragmentActivitiesListBinding::bind)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("Activity")
        bindViews()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as UserActivity).setBottomBarVisibility(true)
    }

    private fun bindViews() {
        binding.addActivity.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameForUser, NewActivityFragment())
                .addToBackStack(USER_ACTIVITY_FRAGMENT_KEY)
                .commit()
        }
    }

    companion object {
        const val USER_ACTIVITY_FRAGMENT_KEY = "userActivityFragmentKey"
    }
}