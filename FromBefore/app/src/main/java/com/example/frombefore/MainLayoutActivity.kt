package com.example.frombefore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.frombefore.CalendarFragment
import com.example.frombefore.MessageFragment
import com.example.frombefore.MyPageFragment
import com.example.frombefore.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainLayoutActivity : AppCompatActivity() {
    // FrameLayout에 각 메뉴의 Fragment를 바꿔 줌
    private val fragmentManager: FragmentManager = supportFragmentManager

    // 3개의 메뉴에 들어갈 Fragment들
    private val calendarFragment: CalendarFragment = CalendarFragment()
    private val messageFragment: MessageFragment = MessageFragment()
    private val myPageFragment: MyPageFragment = MyPageFragment()

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
    }
}