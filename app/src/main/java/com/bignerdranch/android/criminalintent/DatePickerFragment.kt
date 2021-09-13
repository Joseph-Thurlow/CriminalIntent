package com.bignerdranch.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker

import androidx.fragment.app.DialogFragment
import java.util.*


private const val ARG_DATE = "date"
private const val ARG_REQUEST_CODE = "requestDate"
private const val RESULT_DATE_KEY = "resultDate"

//The fragment manager of the hosting activity calls this function to put the DialogFragment on screen.
class DatePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //OnDateSetListener is used to receive the date the user selects.
        val dateListener = DatePickerDialog.OnDateSetListener {
                _: DatePicker, year: Int, month: Int, day: Int ->

            val result = Bundle().apply {
                putSerializable(RESULT_DATE_KEY, GregorianCalendar(year, month, day).time)
            }
            parentFragmentManager.setFragmentResult(ARG_REQUEST_CODE, result)
        }


        //Gets the date from the arguments
        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(), //Required to access the necessary resources for the view.
            dateListener,
            initialYear,
            initialMonth,
            initialDay
        )
    }

    companion object {

        //Creating and setting fragment arguments is typically done in a newInstance() function.
        fun newInstance(date: Date) : DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }

            return DatePickerFragment().apply {
                arguments = args
            }
        }
    }
}