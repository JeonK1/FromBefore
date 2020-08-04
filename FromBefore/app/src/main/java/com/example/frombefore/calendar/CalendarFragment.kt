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
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment CalendarFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            CalendarFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
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
        val ui = UserInfo(context!!)
        val jsonObj = ui.readFile()
        val dayArray = jsonObj.get("dayArray") as JSONArray
        if(dayArray[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1] == 1){
            attendButton.visibility = View.VISIBLE
        }
        attendButton.setOnClickListener {
            var attendArray = jsonObj.get("attendArray") as JSONArray
            val todayCalendar = Calendar.getInstance()
            val ddayCalendar = Calendar.getInstance()
            ddayCalendar.set(Calendar.YEAR, jsonObj.getInt("year"))
            ddayCalendar.set(Calendar.MONTH, jsonObj.getInt("month")-1) // Calendar class는 1월을 0으로 저장함
            ddayCalendar.set(Calendar.DAY_OF_MONTH, jsonObj.getInt("dayOfMonth"))
            val dday = ((ddayCalendar.timeInMillis - todayCalendar.timeInMillis) / (60 * 60 * 24 * 1000)).toInt()
            if(attendArray[attendArray.length()-dday] == -1){
                attendArray.put(attendArray.length()-dday, 1)
                Toast.makeText(context, "출석이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                attendButton.visibility = View.INVISIBLE
                ui.writeFile("attendArray", attendArray) // json에 출석체크완료로 수정
            }
        }
    }

    private fun initDefaultValue() {
        val ui = UserInfo(activity!!.applicationContext)
        val jsonObj = ui.readFile()
        val dday =
            jsonObj.get("d_day").toString()
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

        tv_current_day.setText(SimpleDateFormat("M월 dd일", Locale.getDefault()).format(Calendar.getInstance().time))

        CalendarFB(context, tableLayout)

        btn_send_msg.setOnClickListener {
            //메시지 전송 버튼
            val secondIntent = Intent(context, MsgSendToMeActivity::class.java)
            startActivity(secondIntent)
        }

        if(/* 나에게 온 편지가 있을 때*/1==1){
            btn_receive_letter.setImageResource(R.drawable.letter_exist)
            btn_receive_letter.setOnClickListener {
                //나에게 온 편지가 있는 경우 버튼 클릭 했을 때
                //TODO : 서버에서 해당 편지 내용 가져와서 읽기
                val intent = Intent(context, ReceiveMessageActivity::class.java)
                startActivity(intent)
            }
        }

//        else{
//            //TODO : 읽을 펼지가 없다고 메시지 출력하는데 편지 읽는 dialog랑 형식 맞출꺼라 일단 주석
//        }

    }
}
