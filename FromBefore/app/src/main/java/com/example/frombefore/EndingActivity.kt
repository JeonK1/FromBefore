package com.example.frombefore

import android.app.AlertDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import androidx.core.view.marginLeft
import kotlinx.android.synthetic.main.activity_ending.*
import kotlinx.android.synthetic.main.attendbox_dialog.view.*
import kotlinx.android.synthetic.main.msgbox_dialog_ending.*

class EndingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ending)
        createEndingAnimate()
    }

    private fun createEndingAnimate() {
        val msgCnt = 10 // 메시지 개수
        for(i in 1..msgCnt) {
            //TODO : 서버로부터 편지 내용과 보낸사람 정보 가져와서 for문 안에서 열심히 만들어주기
            val msgContext = "정말 수고하셨어요!\n끝까지 해내시다니 정말 멋지세요"
            val fromContext = "D-50의 누군가로부터"
            val frameLayout = createMessageLayout(msgContext, fromContext)
            endingFrameLayout.addView(frameLayout)
        }
    }

    private fun createMessageLayout(msgContext:String, fromContext:String):FrameLayout {
        //메시지 내용 textview 만들기
        val msgContextTextView = TextView(this)
        msgContextTextView.setText(msgContext)
        val params = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.setMargins(10, 10, 10, 10)
        msgContextTextView.layoutParams = params
        msgContextTextView.setTextColor(Color.parseColor("#383838"))
        val noto_bold = resources.getFont(R.font.notoserifkr_bold)
        val noto_black = resources.getFont(R.font.notoserifkr_black)
        msgContextTextView.setTypeface(noto_bold)

        //메시지 보낸이 textview 만들기
        val fromContextTextView = TextView(this)
        fromContextTextView.setText(fromContext)
        val params2 = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params2.setMargins(10, 10, 10, 10)
        params2.gravity = Gravity.RIGHT
        fromContextTextView.layoutParams = params2
        fromContextTextView.gravity = Gravity.CENTER
        fromContextTextView.setTypeface(noto_black)
        fromContextTextView.setTextColor(Color.parseColor("#383838"))

        //메시지 내용 담는 layout 만들기
        val params4 = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val linearLayout = LinearLayout(this)
        linearLayout.setBackgroundResource(R.drawable.bgimage_hompaper_light)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.layoutParams = params4
        linearLayout.setPadding(100, 0 , 100, 0)
        linearLayout.setVerticalGravity(Gravity.CENTER)
        linearLayout.addView(msgContextTextView)
        linearLayout.addView(fromContextTextView)
        linearLayout.setOnClickListener {
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.msgbox_dialog_ending, null)
            val mBuilder = this?.let {
                AlertDialog.Builder(it)
                    .setView(mDialogView)
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.msgEndingContextTextView.setText(msgContext)
            mAlertDialog.msgEndingFromTextView.setText(fromContext)
        }

        //가장 외각 layout인 frameLayout 만들어주기
        val frameLayout = FrameLayout(this)
        //TODO : 편지의 길이가 길수록 편지의 세로 길이도 길어져야하는데 아직 해결하지 못하였음
        val layoutWidth = 800
        val layoutHeight = 400
        val params3 = FrameLayout.LayoutParams(layoutWidth, layoutHeight)
        val initX = ((resources.displayMetrics.widthPixels-layoutWidth)/2)
        val initY = ((resources.displayMetrics.heightPixels-layoutHeight)/2)
        params3.setMargins(initX, initY,0,0)
        val randX = (Math.random()*(resources.displayMetrics.widthPixels)).toFloat()
        val randY = (Math.random()*(resources.displayMetrics.heightPixels)).toFloat()
        frameLayout.layoutParams = params3
        frameLayout.setBackgroundResource(R.drawable.block_transparent)
        frameLayout.addView(linearLayout)
        frameLayout.setPadding(0,0,5,5)
        frameLayout.animate()
            .translationX(randX-initX*2)
            .translationY(randY-initY)
            .rotation(180-(Math.random()*360).toFloat())
            .setDuration(1000)
            .setStartDelay(1000)
        return frameLayout
    }
}
