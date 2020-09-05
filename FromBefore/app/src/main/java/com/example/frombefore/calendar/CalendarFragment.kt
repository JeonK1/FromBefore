package com.example.frombefore.calendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.frombefore.*
import com.example.frombefore.manager.UserInfo
import com.example.frombefore.message.MsgSendToMeActivity
import com.example.frombefore.message.ReceiveMessageActivity
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
    var firstFragmentOffset = 0
    var lastFragmentOFfset = 0
    var adapter:MyViewPagerAdapter? = null
    var doubleCheckFlag = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var firstFragmentOffset = -1
        var lastFragmentOFfset = 1
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //달력설정
        val myContext = context as FragmentActivity
        adapter = MyViewPagerAdapter(myContext.supportFragmentManager)
        adapter!!.addBackFragment(CalendarViewFragment(-4, false))
        adapter!!.addBackFragment(CalendarViewFragment(-3, false))
        adapter!!.addBackFragment(CalendarViewFragment(-2, false))
        adapter!!.addBackFragment(CalendarViewFragment(-1, false))
        adapter!!.addBackFragment(CalendarViewFragment(0, true))
        adapter!!.addBackFragment(CalendarViewFragment(1, false))
        adapter!!.addBackFragment(CalendarViewFragment(2, false))
        adapter!!.addBackFragment(CalendarViewFragment(3, false))
        adapter!!.addBackFragment(CalendarViewFragment(4, false))
        calendarViewPager.adapter = adapter
        calendarViewPager.currentItem = 4
        tabs.setupWithViewPager(calendarViewPager)
        //날짜설정
        tv_current_day.text = SimpleDateFormat("yyyy년 M월", Locale.getDefault()).format(Calendar.getInstance().time)
        //달력 페이지 넘겼을 때
        calendarViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                val nowCalendar = Calendar.getInstance()
                Log.e("position", position.toString())
                val curMonth = nowCalendar.get(Calendar.MONTH) + position-4
                nowCalendar.set(Calendar.MONTH, curMonth) // Calendar class는 1월을 0으로 저장함
                tv_current_day.text = SimpleDateFormat("yyyy년 M월", Locale.getDefault()).format(nowCalendar.time)
            }
        })

        initDefaultValue()
//        initTestCase()
        initBtn()

//        else{
//            //TODO : 읽을 펼지가 없다고 메시지 출력하는데 편지 읽는 dialog랑 형식 맞출꺼라 일단 주석
//        }

//        val cheatkey = findViewById<LinearLayout>(R.id.check_key)
//        cheatkey.setOnClickListener{
////            val i = Intent(this, GifTestActivity::class.java)
//            val i = Intent(this, DaySelectActivity::class.java)
//            startActivity(i)
////            finish()
//        }
    }

//    private fun initBtn() {
//        val ui = UserInfo(context!!)
//        val jsonObj = ui.readFile()
//        val dayArray = jsonObj.get("dayArray") as JSONArray
//        if(dayArray[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1] == 1){
//            attendButton.visibility = View.VISIBLE
//        }
//        attendButton.setOnClickListener {
//            var attendArray = jsonObj.get("attendArray") as JSONArray
//            val todayCalendar = Calendar.getInstance()
//            val ddayCalendar = Calendar.getInstance()
//            ddayCalendar.set(Calendar.YEAR, jsonObj.getInt("year"))
//            ddayCalendar.set(Calendar.MONTH, jsonObj.getInt("month")-1) // Calendar class는 1월을 0으로 저장함
//            ddayCalendar.set(Calendar.DAY_OF_MONTH, jsonObj.getInt("dayOfMonth"))
//            val dday = ((ddayCalendar.timeInMillis - todayCalendar.timeInMillis) / (60 * 60 * 24 * 1000)).toInt()
//            if(attendArray[attendArray.length()-dday] == -1){
//                attendArray.put(attendArray.length()-dday, 1)
//                Toast.makeText(context, "출석이 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                attendButton.visibility = View.INVISIBLE
//                ui.writeFile("attendArray", attendArray) // json에 출석체크완료로 수정
//            }
//        }
//        btn_send_msg.setOnClickListener {
//            //메시지 전송 버튼
//            val secondIntent = Intent(context, MsgSendToMeActivity::class.java)
//            startActivity(secondIntent)
//        }
//
//        if(/* 나에게 온 편지가 있을 때*/1==1){
//            btn_receive_letter.setImageResource(R.drawable.letter_exist)
//            btn_receive_letter.setOnClickListener {
//                //나에게 온 편지가 있는 경우 버튼 클릭 했을 때
//                val intent = Intent(context, ReceiveMessageActivity::class.java)
//                startActivity(intent)
//            }
//        }
//    }

    private fun initBtn() {
        val dayArray = UserInfo.dayArray
        if(dayArray[Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1] == -1){
            //출석 안했을 때 (지나지 않음)
            attendButton.visibility = View.VISIBLE
        } else {
            attendButton.visibility = View.INVISIBLE
        }
        attendButton.setOnClickListener {
            var attendArray = UserInfo.attendArray
            val todayCalendar = Calendar.getInstance()
            val ddayCalendar = Calendar.getInstance()
            ddayCalendar.set(Calendar.YEAR, UserInfo.year)
            ddayCalendar.set(Calendar.MONTH, UserInfo.month-1) // Calendar class는 1월을 0으로 저장함
            ddayCalendar.set(Calendar.DAY_OF_MONTH, UserInfo.dayOfMonth)
            val dday = ((ddayCalendar.timeInMillis - todayCalendar.timeInMillis) / (60 * 60 * 24 * 1000)).toInt()
            if(attendArray[attendArray.length()-dday] == -1){
                //출석 안했을 때 (지나지 않음)
                attendArray.put(attendArray.length()-dday, 1)
                Toast.makeText(context, "출석이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                attendButton.visibility = View.INVISIBLE
                UserInfo.set("attendArray", attendArray)
            }
        }
        btn_send_msg.setOnClickListener {
            //메시지 전송 버튼
            val secondIntent = Intent(context, MsgSendToMeActivity::class.java)
            startActivity(secondIntent)
        }

        if(/* 나에게 온 편지가 있을 때*/1==1){
            btn_receive_letter.setImageResource(R.drawable.letter_exist)
            btn_receive_letter.setOnClickListener {
                //나에게 온 편지가 있는 경우 버튼 클릭 했을 때
                val intent = Intent(context, ReceiveMessageActivity::class.java)
                startActivity(intent)
            }
        }
    }


//    private fun initDefaultValue() {
//        val ui = UserInfo(activity!!.applicationContext)
//        val jsonObj = ui.readFile()
//        val dday =
//            jsonObj.get("d_day").toString()
//        tv_dday.text = "D-" + dday
//    }

    private fun initDefaultValue() {
        val dday = UserInfo.dday
        tv_dday.text = "D-" + dday.toString()
    }

    //    private fun initTestCase() {
//        //테스트케이스용 함수
//        accStudyCntNum=5 // 누적학습일
//        accStudyCnt.setText(accStudyCntNum.toString())
//        progressNum=10 // 진행도 퍼센트(progressBar)
//        progressBar.setProgress(progressNum)
//
//    }
    class MyViewPagerAdapter(manager:FragmentManager) : FragmentPagerAdapter(manager){

        private val fragmentList : MutableList<Fragment> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addBackFragment(fragment: Fragment){
            fragmentList.add(fragment)
        }

        fun addFrontFragment(fragment: Fragment){
            fragmentList.add(0, fragment)
        }
    }
}
