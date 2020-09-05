package com.example.frombefore.manager

import android.app.AlertDialog
import java.util.Calendar
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.frombefore.R
import com.example.frombefore.calendar.CalendarFragment
import com.example.frombefore.message.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.msgbox_dialog.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainLayoutActivity : AppCompatActivity() {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private val fragmentManager: FragmentManager = supportFragmentManager

    // 3개의 메뉴에 들어갈 Fragment들
    private val calendarFragment: CalendarFragment =
        CalendarFragment()
    private val messageFragment: MessageFragment =
        MessageFragment()
    private val myPageFragment: MyPageFragment =
        MyPageFragment()

    // 현재 날짜와 목표 날짜를 비교
    private lateinit var dateChecker: DateChecker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        // 첫 화면 지정
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, calendarFragment).commitAllowingStateLoss()

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            when (item.itemId) {
                R.id.action_calendar -> {
                    transaction.replace(R.id.frame_layout, calendarFragment)
                        .commitAllowingStateLoss()
                }
                R.id.action_letter -> {
                    transaction.replace(R.id.frame_layout, messageFragment)
                        .commitAllowingStateLoss()
                }
                R.id.action_my_page -> {
                    transaction.replace(R.id.frame_layout, myPageFragment)
                        .commitAllowingStateLoss()
                }
            }
            true
        }

        val goalDate: Calendar = MyCalendar.with(UserInfo.calendarStr())
        dateChecker = DateChecker(this, goalDate)
        if (dateChecker.checkEndingDay()) dateChecker.startEndingActivity()
        dateChecker.checkMsgDay();

        //TODO : 출석체크 안했을 때 자기반성편지 보이도록 하기(주의 : 처음은 자기반성편지 없음), 편지 관련 JSON 저장방법 확실해지면 그때 시작하기
    }
}

class DateChecker(curContext: Context, goalDate: Calendar) {
    val cal = MyCalendar.today()
    val goalDate = goalDate
    val curContext = curContext

    fun checkMsgDay(): Boolean {
        val today = MyCalendar.today()
        val messageDdayArray = UserInfo.messageDdayArray
        val distanceFromToday =
            ((goalDate.timeInMillis - today.timeInMillis) / (60 * 60 * 24 * 1000)).toInt() + 1
        for(i in 0 until messageDdayArray.length()){
            if(messageDdayArray[i]==distanceFromToday){
                messageFromMeDialog(MessagesFromMe.get(distanceFromToday))
                return true
            }
        }
        return false
    }

    fun checkEndingDay(): Boolean {
        return cal.get(Calendar.YEAR) > goalDate.get(Calendar.YEAR) ||
                cal.get(Calendar.MONTH) > goalDate.get(Calendar.MONTH) ||
                cal.get(Calendar.DAY_OF_MONTH) >= goalDate.get(Calendar.DAY_OF_MONTH)
    }

    fun messageFromMeDialog(msg:JSONObject) {
        val mDialogView =
            LayoutInflater.from(curContext).inflate(R.layout.msgbox_dialog, null)
        val mBuilder = this?.let {
            AlertDialog.Builder(curContext)
                .setView(mDialogView)
        }
        val mAlertDialog = mBuilder!!.show()
        val msgTo = "D-" + msg.get("dday") + "" + "의 나에게"
        val msgFrom = "D-100의 내가"
        val msgContext = msg.get("text").toString()

        mDialogView.msgToTextView.setText(msgTo)
        mDialogView.msgFromTextView.setText(msgFrom)
        mDialogView.msgContextTextView.setText(msgContext)
    }

    fun startEndingActivity() {
        val i = Intent(curContext, EndingMessageActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        curContext.startActivity(i)
    }
}