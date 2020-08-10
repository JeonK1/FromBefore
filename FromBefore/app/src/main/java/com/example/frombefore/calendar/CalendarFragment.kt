package com.example.frombefore.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frombefore.*
import com.example.frombefore.manager.MyCalendar
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.message.MsgSendToMeActivity
import com.example.frombefore.message.ReceiveMessageActivity
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {
    lateinit var scheduleRecyclerViewAdapter: CalendarRecyclerViewAdapter
    var progressNum=0
    var accStudyCntNum=0 // 누적 학습일

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDefaultValue()
//        initTestCase()
        initCalendarView()
        initBtn()

//        val cheatkey = findViewById<LinearLayout>(R.id.check_key)
//        cheatkey.setOnClickListener{
////            val i = Intent(this, GifTestActivity::class.java)
//            val i = Intent(this, DaySelectActivity::class.java)
//            startActivity(i)
////            finish()
//        }
    }

    private fun initBtn() {
        val dayArray = UserInfo.get("dayArray") as JSONArray
        if (dayArray[MyCalendar.day - 1] == 1){
            attendButton.visibility = View.VISIBLE
        }

        attendButton.setOnClickListener {
            var attendArray = UserInfo.get("attendArray") as JSONArray
            val todayCalendar = MyCalendar.today()
            val ddayCalendar = MyCalendar.with(UserInfo.calendarStr())

            val dday = ((ddayCalendar.timeInMillis - todayCalendar.timeInMillis) / (60 * 60 * 24 * 1000)).toInt()

            if(attendArray[attendArray.length() - dday] == UserInfo.ATTEND_NOT_DONE_NO_MSG){
                attendArray.put(attendArray.length()-dday, UserInfo.ATTEND_DONE)
                Toast.makeText(context, "출석이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                attendButton.visibility = View.INVISIBLE
                UserInfo.set("attendArray", attendArray) // json에 출석체크완료로 수정
            }
        }
    }

    private fun initDefaultValue() {
        val dday = UserInfo.get("d_day").toString()
        tv_dday.text = "D-" + dday
    }

//    private fun initTestCase() {
//        //테스트케이스용 함수
//        accStudyCntNum=5 // 누적학습일
//        accStudyCnt.setText(accStudyCntNum.toString())
//        progressNum=10 // 진행도 퍼센트(progressBar)
//        progressBar.setProgress(progressNum)
//
//    }

    private fun initCalendarView() {
        tv_current_day.text = SimpleDateFormat("M월 dd일", Locale.getDefault()).format(MyCalendar.today().time)

        CalendarFB(context, tableLayout)
    }
}
