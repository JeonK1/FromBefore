package com.example.frombefore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_calendar.*
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
        initTestCase()
        initCalendarView()

//        val cheatkey = findViewById<LinearLayout>(R.id.check_key)
//        cheatkey.setOnClickListener{
////            val i = Intent(this, GifTestActivity::class.java)
//            val i = Intent(this, DaySelectActivity::class.java)
//            startActivity(i)
////            finish()
//        }
    }
    private fun initDefaultValue() {
        val dday = UserInfo.readFile(context, "d_day")
        tv_dday.text = "D-" + dday
        if(dday.toInt()<=0){
            //dday가 되었거나 지나가면 ending을 보여준다.
            val intent = Intent(context, EndingMessageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initTestCase() {
        //테스트케이스용 함수
        accStudyCntNum=5 // 누적학습일
        accStudyCnt.setText(accStudyCntNum.toString())
        progressNum=10 // 진행도 퍼센트(progressBar)
//        progressBar.setProgress(progressNum)

    }
    private fun initCalendarView() {

//        scheduleRecyclerViewAdapter = CalendarRecyclerViewAdapter()

        tv_current_day.setText(SimpleDateFormat("M월 dd일", Locale.getDefault()).format(Calendar.getInstance().time))

        val calendarFB = CalendarFB(context, tableLayout)

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