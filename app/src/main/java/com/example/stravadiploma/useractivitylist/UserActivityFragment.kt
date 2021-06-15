package com.example.stravadiploma.useractivitylist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.MainActivity
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.adapters.activities.ActivityAdapter
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.database.Database
import com.example.stravadiploma.databinding.FragmentActivitiesListBinding
import com.example.stravadiploma.databinding.FragmentProfileBinding
import com.example.stravadiploma.newactivity.NewActivityFragment
import com.example.stravadiploma.profile.ProfileViewModel

class UserActivityFragment : Fragment(R.layout.fragment_activities_list) {

    private val viewModel: UserActivityViewModel by viewModels()
    private val binding by viewBinding(FragmentActivitiesListBinding::bind)

    private var activityAdapter: ActivityAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getAllActivities()
        viewModel.getUserName()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("Activity")
        bindViews()
        // initList()
    }


    override fun onResume() {
        super.onResume()
        (requireActivity() as UserActivity).setBottomBarVisibility(true)
    }

    private fun bindViews() {
        viewModel.activityList.observe(viewLifecycleOwner) { activityAdapter?.items = it }
        viewModel.userName.observe(viewLifecycleOwner, ::initList)
        viewModel.isLoading.observe(viewLifecycleOwner, ::isLoading)
        viewModel.isError.observe(viewLifecycleOwner, ::isError)
        binding.addActivity.setOnClickListener {
            val action =
                UserActivityFragmentDirections.actionUserActivityFragmentToNewActivityFragment()
            findNavController().navigate(action)
        }
    }

    private fun initList(user: UserForActivity) {
        activityAdapter = ActivityAdapter(user)
        with(binding.activityListRecyclerView) {
            adapter = activityAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun isLoading(isLoading: Boolean) {

    }

    private fun isError(isError: Boolean) {

    }

    override fun onDestroy() {
        super.onDestroy()
        activityAdapter = null
    }
}