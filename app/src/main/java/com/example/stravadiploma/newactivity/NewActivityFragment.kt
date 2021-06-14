package com.example.stravadiploma.newactivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity

class NewActivityFragment:Fragment(R.layout.fragment_new_activity) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("New activity")
        (requireActivity() as UserActivity).setBottomBarVisibility(false)
    }


}