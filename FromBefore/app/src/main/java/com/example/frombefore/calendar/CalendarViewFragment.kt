package com.example.frombefore.calendar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.frombefore.R
import kotlinx.android.synthetic.main.fragment_calendar_view.*

/**
 * A simple [Fragment] subclass.
 */
class CalendarViewFragment(val offset: Int, val isCurMonth: Boolean) : Fragment() {

    var curOffset = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        curOffset = offset
        return inflater.inflate(R.layout.fragment_calendar_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initCalendarView()
    }
    private fun initCalendarView() {
        val myCalendar = CalendarFB(context, tableLayout, offset, isCurMonth)
        myCalendar
    }

}
