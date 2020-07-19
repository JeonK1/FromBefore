package com.example.frombefore

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.attendbox_dialog.view.*
import kotlinx.android.synthetic.main.item_schedule.*
import kotlinx.android.synthetic.main.msgbox_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class CalendarRecyclerViewAdapter(val mainActivity: CalendarActivity) : RecyclerView.Adapter<ViewHolderHelper>() {

    lateinit var msgList:ArrayList<Int> // 내게 온 메일 있는지 확인
    lateinit var checkList:ArrayList<Int>  // 출석체크 확인
    val baseCalendar = BaseCalendar()
    var todayDay=0
    init {
        baseCalendar.initBaseCalendar {
        }
        //test case(Todo: msgList를 서버든 로컬이든 어디든 받아와서 여기서 초기화하기)
        checkList = ArrayList() // 출석체크 확인
        todayDay = 17 // 현재 날짜
        for( i in 1..todayDay){
            if(i==4)
                checkList.add(0)
            else
                checkList.add(1)
        }
        msgList= ArrayList() // 내게 온 메일 있는지 확인
        msgList.add(5)
        msgList.add(19)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        view.layoutParams.width = parent.width/7
        if(baseCalendar.prevMonthTailOffset>4)
            view.layoutParams.height = parent.height/6
        else
            view.layoutParams.height = parent.height/5
        return ViewHolderHelper(view)
    }

    override fun getItemCount(): Int {
        if(baseCalendar.prevMonthTailOffset>4){
            return 7 * 6; // 1일의 시작이 금 or 토 이면 6주 표시
        }
        else{
            return 7 * 5;
        }
    }

    override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#383838"))
        val curDay = baseCalendar.data[position]
        holder.itemView.findViewById<TextView>(R.id.tv_date).text = curDay.toString()
        val firstSatDay = 1+(6-baseCalendar.prevMonthTailOffset)
        val firstSunDay = 1+(7-baseCalendar.prevMonthTailOffset)
        if(curDay<todayDay){
            //어제까지의 출석체크 현황 넣어주기
            if(checkList[curDay-1]==1){
                holder.itemView.findViewById<ImageView>(R.id.image_check).setImageResource(R.drawable.image_success)
                holder.itemView.findViewById<ImageView>(R.id.image_check).visibility = View.VISIBLE
            }
            else{
                //holder.itemView.findViewById<ImageView>(R.id.image_check).setImageResource(R.drawable.image_fail)
                //holder.itemView.findViewById<ImageView>(R.id.image_check).visibility = View.VISIBLE
            }
        }
        if(curDay==todayDay){
            //오늘일때 UI 까맣게 해주는거
            holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#ffffff"))
            holder.itemView.findViewById<ImageView>(R.id.image_check).visibility = View.INVISIBLE
            holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#ffffff"))
            holder.itemView.findViewById<LinearLayout>(R.id.item_layout).setBackgroundResource(R.drawable.calender_today)
            holder.itemView.findViewById<LinearLayout>(R.id.item_layout).setOnClickListener {
                //TODO: 원래는 출석체크 관련 창 떠야하는데.. 디자인 바귈거같아서 그냥 dialog관련만 밑에 해놨으니 알아서 바꾸셈 ㅎㅎ
                val mDialogView = LayoutInflater.from(holder.containerView.context).inflate(R.layout.attendbox_dialog, null)
                val mBuilder = holder.containerView.context?.let {
                    AlertDialog.Builder(it)
                        .setView(mDialogView)
                }
                val mAlertDialog = mBuilder!!.show()
                mAlertDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                val accday = 11 // 누적학습일
                mDialogView.tv_accday.setText(accday.toString())
                val dday = 97 //목표 일수
                mDialogView.tv_attendday.setText(dday.toString())
                mDialogView.attendBtn.setOnClickListener {
                    //TODO : 출석 버튼 눌렀을때 이후 작업
                    //TODO : 목표일까지 며칠남았는지, 누적학습일 계산해서 textView에 넣어줘야하는거 해야함
                    mAlertDialog.dismiss()
                }
            }
        }
        if(msgList.contains(baseCalendar.data[position])){
//            holder.itemView.findViewById<ImageView>(R.id.image_mail_to_me).visibility = View.VISIBLE
//            holder.itemView.findViewById<ImageButton>(R.id.btn_read_msg).visibility= View.VISIBLE
//            holder.itemView.findViewById<ImageButton>(R.id.btn_read_msg).setOnClickListener {
//                // 해당 날짜의 읽는 버튼을 눌렀을 때
//                //TODO : 서버에서 해당 일자의 내용 가져와서 dialog로 띄워주기
//                val mDialogView = LayoutInflater.from(holder.containerView.context).inflate(R.layout.msgbox_dialog, null)
//                val mBuilder = holder.containerView.context?.let { it1 ->
//                    AlertDialog.Builder(it1)
//                        .setView(mDialogView)
//                }
//                val mAlertDialog = mBuilder!!.show()
//                val msgDate = "2020.07.01"
//                val msgTo = "D-50의 나에게"
//                val msgFrom = "D-100의 내가"
//                val msgContext = "안녕 나 자신아\n용케도 여기까지 왔구나\n지금쯤 너는 많이 지쳐있겠지..?\n어쩌면 이미 중간에 여러 번\n실패했었을지도 몰라\n사실 난 지금 배가고파."
//                mDialogView.msgDateTextView.setText(msgDate)
//                mDialogView.msgToTextView.setText(msgTo)
//                mDialogView.msgFromTextView.setText(msgFrom)
//                mDialogView.msgContextTextView.setText(msgContext)
//            }
        }
        if(position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate){
            //이번달이 아닌건 글자색 배경색과 동일시시켜서 눈에서 없애버림
            holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#ffffff"))
            holder.itemView.findViewById<ImageView>(R.id.image_check).visibility = View.INVISIBLE
//            holder.itemView.findViewById<ImageView>(R.id.image_mail_to_me).visibility = View.INVISIBLE
        }
    }
}