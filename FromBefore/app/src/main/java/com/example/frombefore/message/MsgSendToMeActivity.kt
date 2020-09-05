package com.example.frombefore.message

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.example.frombefore.R
import com.example.frombefore.manager.MessagesFromMe
import com.example.frombefore.manager.ProgressDialogManager
import com.example.frombefore.manager.UserInfo
import kotlinx.android.synthetic.main.activity_msg_send_to_me.*
import kotlinx.android.synthetic.main.numberpick_dialog.*
import kotlinx.android.synthetic.main.numberpick_dialog.view.*
import org.json.JSONObject

class MsgSendToMeActivity : AppCompatActivity() {

    var hintMessage=""
    // 100 = init d-day value
    var selectedDay = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_msg_send_to_me)
        hintInit()
        buttonInit()
//        spinnerInit()
        ddaySelectBoxInit()
        msgBoxInit()
//        MessageGetterTask(this).execute()
    }

    private fun ddaySelectBoxInit() {
        selectDdayTextView.setOnClickListener {
            val mDialog = LayoutInflater.from(this).inflate(R.layout.numberpick_dialog, null)
            val mBuilder = this?.let{
                AlertDialog.Builder(it)
                    .setView(mDialog)
                    .setNegativeButton("취소"){   dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("확인"){   dialog, which ->
                        selectedDay = (mDialog.numberPicker.value.toString()+ // 선택된 dday 날짜
                                mDialog.numberPicker2.value.toString()+
                                mDialog.numberPicker3.value.toString()).toInt()
                        selectDdayTextView.text = "D - " + selectedDay.toString()
                    }
            }
            val mAlertDialog = mBuilder!!.show()
            mAlertDialog.numberPicker.minValue=0
            mAlertDialog.numberPicker.maxValue=9
            mAlertDialog.numberPicker2.minValue=0
            mAlertDialog.numberPicker2.maxValue=9
            mAlertDialog.numberPicker3.minValue=0
            mAlertDialog.numberPicker3.maxValue=9
            mAlertDialog.numberPicker.value=1
            mAlertDialog.numberPicker2.value=0
            mAlertDialog.numberPicker3.value=0

            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.weight = 10f
            params.gravity = Gravity.CENTER
            mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).layoutParams = params
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE).layoutParams = params
        }
        selectDdayTextView.text = "D - " + selectedDay
    }

    private fun hintInit() {
        hintMessage = msgEditText.hint.toString()
    }

    private fun buttonInit() {
        btn_send_msg_server.setOnClickListener {
            //Todo:send to server
            var message:JSONObject = JSONObject()

            message.put("dday", selectedDay)
            message.put("text", msgEditText.text)


            val messageDdayArray = UserInfo.messageDdayArray
            messageDdayArray.put(selectedDay)
            UserInfo.set("messageDdayArray", messageDdayArray)

            MessagesFromMe.add(message)

            finish()
        }
        btn_back.setOnClickListener {
            finish()
        }
    }

    private fun msgBoxInit() {
        msgEditText.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val maxWordCnt=100; // 최대입력수
                val input= msgEditText.text
                msgWordCnt.setText(input.length.toString() + "/" + maxWordCnt)
            }

        })
    }
//    private fun spinnerInit() {
//        val ddayList = arrayOf("D-100", "D-50", "D-40", "D-10", "D-1")
//        val spinnerAdapter = ArrayAdapter(this,
//            R.layout.spinner_item, ddayList)
//        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item)
//        ddaySpinner.adapter = spinnerAdapter
//        ddaySpinner.setSelection(0)
//        ddaySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                msgEditText.hint = ddayList[position].substring(2)+hintMessage
//            }
//        }
//    }
}
