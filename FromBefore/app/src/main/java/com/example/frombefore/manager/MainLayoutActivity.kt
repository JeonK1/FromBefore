package com.example.frombefore.manager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.frombefore.R
import com.example.frombefore.calendar.CalendarFragment
import com.example.frombefore.message.MessageFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var dateChecker : DateChecker

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
        val goalDate = Calendar.getInstance()
        val ui = UserInfo(this)
        val jsonObj = ui.readFile()
        goalDate.set(Calendar.YEAR, jsonObj.get("year").toString().toInt())
        goalDate.set(Calendar.MONTH,  jsonObj.get("month").toString().toInt())
        goalDate.set(Calendar.DAY_OF_MONTH, jsonObj.get("dayOfMonth").toString().toInt())
        dateChecker = DateChecker(this, goalDate)
        dateChecker.checkDday()
    }
}

class DateChecker(curContext: Context, goalDate: Calendar) {
    val cal = Calendar.getInstance()
    val goalDate = goalDate
    val curContext = curContext

    fun checkDday() {
        if (cal.get(Calendar.YEAR) > goalDate.get(Calendar.YEAR) ||
            cal.get(Calendar.MONTH) > goalDate.get(Calendar.MONTH) ||
            cal.get(Calendar.DAY_OF_MONTH) >= goalDate.get(Calendar.DAY_OF_MONTH)){
            startEndingActivity()
        }
    }

    fun startEndingActivity(){
        val i = Intent(curContext, EndingActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        curContext.startActivity(i)
    }
}