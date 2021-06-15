package com.example.stravadiploma.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun <T: Fragment> T.withArguments(action: Bundle.() ->Unit): T{
    return apply{
        val args = Bundle().apply(action)
        arguments = args
    }
}

fun Fragment.toast(@StringRes stringRes: Int) {
    Toast.makeText(requireContext(), stringRes, Toast.LENGTH_SHORT).show()
}

fun logInfo(text: Any? = "gotHere"){
    Log.d("DiplomaProject", text.toString())
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Long.timeFormat(): String {
    val hour = if ((this / 3600) == 0.toLong()) "" else "${(this / 3600)} h "
    return "$hour${(this % 3660 )/ 60} min"

}


