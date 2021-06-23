package com.example.stravadiploma.useractivitylist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.WorkInfo
import androidx.work.WorkManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.adapters.activities.ActivityAdapter
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.databinding.FragmentActivitiesListBinding
import com.example.stravadiploma.net.Network
import com.example.stravadiploma.newActivity.NewActivityFragment
import com.example.stravadiploma.utils.logInfo
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegate
import kotlin.math.log

class UserActivityFragment : Fragment(R.layout.fragment_activities_list) {

    private var firstStart = false
    private var viewModel: UserActivityViewModel? = null
    private val binding by viewBinding(FragmentActivitiesListBinding::bind)

    private var activityAdapter: ActivityAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(requireActivity()).get(UserActivityViewModel::class.java)

        if (savedInstanceState == null && Network.isInternetAvailable(requireContext()) && viewModel!!.activityList.value == null) {
            logInfo("internet")

            viewModel!!.getAllActivities(true)
        } else {
            logInfo("room ")
            viewModel!!.getAllActivities(false)
        }
        WorkManager.getInstance(requireContext())
            .getWorkInfosForUniqueWorkLiveData(UserActivityRepository.UPLOAD_KEY)
            .observe(viewLifecycleOwner, { work ->
                if (!work.isNullOrEmpty()){
                    handleWorkInfo(work.first())
                }
            })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("Activity")
        bindViews()
        initRefreshListener()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as UserActivity).setBottomBarVisibility(true)
    }

    private fun initRefreshListener() {
        binding.swipeLayout.setOnRefreshListener {
            viewModel!!.getAllActivities(true)
        }
    }


    private fun bindViews() {
        viewModel!!.userName.observe(viewLifecycleOwner, ::initList)
        viewModel!!.activityList.observe(viewLifecycleOwner) { activityAdapter?.items = it }
        viewModel!!.isLoading.observe(viewLifecycleOwner, ::isLoading)
        viewModel!!.isError.observe(viewLifecycleOwner, ::isError)
        viewModel!!.isListEmpty.observe(viewLifecycleOwner, ::isListEmpty)


        binding.addActivity.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction
                .addToBackStack(USER_ACTIVITY_FRAGMENT_KEY)
                .replace(R.id.fragmentStrava, NewActivityFragment())
                .commit()
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
        binding.swipeLayout.isRefreshing = isLoading
    }

    private fun isError(isError: Boolean) {
        if (isError) {
            showSnack("Connection failed.")
        }
    }

    private fun isListEmpty(isEmpty: Boolean){
        binding.emptyListTextView.isVisible = isEmpty
    }

    private fun showSnack(text: String) {
        binding.fragmentViewSnack.isVisible = true
        val snack =
            Snackbar.make(binding.fragmentViewSnack, text, Snackbar.LENGTH_LONG)
        snack.show()

    }

    private fun handleWorkInfo(workInfo: WorkInfo) {
        if (workInfo.state == WorkInfo.State.SUCCEEDED && firstStart) {
            viewModel!!.isUploaded()
            showSnack("Uploaded")
        } else {
            firstStart = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityAdapter = null
    }


    companion object {
        const val USER_ACTIVITY_FRAGMENT_KEY = "userActivityFragmentKey"
    }

}