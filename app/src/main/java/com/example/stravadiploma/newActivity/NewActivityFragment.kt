package com.example.stravadiploma.newActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import com.example.stravadiploma.R
import com.example.stravadiploma.UserActivity
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.databinding.FragmentNewActivityBinding
import com.example.stravadiploma.useractivitylist.UserActivityViewModel
import com.example.stravadiploma.utils.logInfo
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*

class NewActivityFragment : Fragment(R.layout.fragment_new_activity) {

    private var _binding: FragmentNewActivityBinding? = null
    private val binding get() = _binding!!
    private var timePickerDialog: Unit? = null
    private var datePickerDialog: Unit? = null
    private var adapterType: ArrayAdapter<String>? = null

    private val viewModel: NewActivityViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindButton()
        bindViews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewActivityBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as UserActivity).setTitle("New activity")
        (requireActivity() as UserActivity).setBottomBarVisibility(false)

    }

    private fun bindButton() {
        binding.newActivityAddButton.setOnClickListener {

            if (binding.newActivityDistance.text.toString().toFloatOrNull() != null) {
                viewModel.saveNewActivity(
                    binding.newActivityName.text.toString(),
                    binding.newActivityTypeTextView.text.toString(),
                    binding.newActivityDistance.text.toString().toFloat(),
                    binding.newActivityDescription.text.toString()
                )
            } else {
                binding.newActivityDistance.error = getString(R.string.incorrect_format)
            }

        }

        binding.newActivityTime.setOnClickListener {
            val currentDateTime = LocalDateTime.now()
            timePickerDialog = TimePickerDialog(
                requireContext(), { _, hour, minute ->
                    viewModel.setDuration(hour, minute)
                    binding.newActivityTime.setText("${hour}:${minute}")
                },
                currentDateTime.hour,
                currentDateTime.minute, true
            ).show()
        }

        binding.newActivityDate.setOnClickListener {
            val currentDateTime = LocalDateTime.now()

            datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    viewModel.setDate(year, month, day)
                    binding.newActivityDate.setText("${day}.${month + 1}.${year}")
                },
                currentDateTime.year, currentDateTime.month.value - 1, currentDateTime.dayOfMonth
            ).show()
        }

        val appThemeList = resources.getStringArray(R.array.sport_types)
        adapterType =
            ArrayAdapter(requireContext(), R.layout.list_item, appThemeList)
        binding.newActivityTypeTextView.setAdapter(adapterType)
    }

    private fun bindViews() {
        viewModel.isErrorAdding.observe(viewLifecycleOwner, ::isErrorAdding)
        viewModel.isErrorSaving.observe(viewLifecycleOwner, ::isErrorAdding)
        viewModel.isSuccess.observe(viewLifecycleOwner, ::isSuccess)

    }

    private fun isSuccess(activity: ActivityData) {
        val model = ViewModelProviders.of(requireActivity()).get(UserActivityViewModel::class.java)
        model.addNewActivity(activity)
        parentFragmentManager
            .popBackStack()
    }

    private fun isErrorAdding(isError: Boolean) {
        val fields = listOf(
            binding.newActivityName,
            binding.newActivityTypeTextView,
            binding.newActivityDate,
            binding.newActivityDistance,
            binding.newActivityTime
        )

        fields.forEach {
            if (it.text.isNullOrEmpty()) {
                it.error = getString(R.string.should_be_filled)
            } else {
                it.error = null
            }
        }
        if (isError){
            Toast.makeText(requireContext(), "Error. Incorrect information.", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        timePickerDialog = null
        datePickerDialog = null
    }
}