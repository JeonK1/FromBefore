package com.example.frombefore

import android.app.AlertDialog
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_schedule.*
import kotlinx.android.synthetic.main.msgbox_dialog.view.*
import java.util.*
import kotlin.collections.ArrayList

class CalendarRecyclerViewAdapter(val mainActivity: CalendarActivity) : RecyclerView.Adapter<ViewHolderHelper>() {

    lateinit var msgList:ArrayList<Int>
    val baseCalendar = BaseCalendar()

    init {
        baseCalendar.initBaseCalendar {
        }

        //test case(Todo: msgList를 서버든 로컬이든 어디든 받아와서 여기서 초기화하기)
        msgList= ArrayList()
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
        holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#000000"))
        if(position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate){
            //글자색 배경색과 동일시시켜서 눈에서 없애버림
            holder.itemView.findViewById<TextView>(R.id.tv_date).setTextColor(Color.parseColor("#ffffff"))
        }
        holder.itemView.findViewById<TextView>(R.id.tv_date).text = baseCalendar.data[position].toString()
        if(msgList.contains(baseCalendar.data[position])){
            holder.itemView.findViewById<ImageButton>(R.id.btn_read_msg).visibility= View.VISIBLE
            holder.itemView.findViewById<ImageButton>(R.id.btn_read_msg).setOnClickListener {
                // 해당 날짜의 읽는 버튼을 눌렀을 때
                //TODO : 서버에서 해당 일자의 내용 가져와서 dialog로 띄워주기
                val mDialogView = LayoutInflater.from(holder.containerView.context).inflate(R.layout.msgbox_dialog, null)
                val mBuilder = holder.containerView.context?.let { it1 ->
                    AlertDialog.Builder(it1)
                        .setView(mDialogView)
                }
                val mAlertDialog = mBuilder!!.show()
                val msgDate = "2020.07.01"
                val msgTo = "D-50의 나에게"
                val msgFrom = "D-100의 내가"
                val msgContext = "안녕 나 자신아\n용케도 여기까지 왔구나\n지금쯤 너는 많이 지쳐있겠지..?\n어쩌면 이미 중간에 여러 번\n실패했었을지도 몰라\n사실 난 지금 배가고파."
                mDialogView.msgDateTextView.setText(msgDate)
                mDialogView.msgToTextView.setText(msgTo)
                mDialogView.msgFromTextView.setText(msgFrom)
                mDialogView.msgContextTextView.setText(msgContext)
            }
        }
    }
}