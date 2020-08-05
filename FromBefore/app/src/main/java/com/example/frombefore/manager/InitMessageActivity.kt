package com.example.frombefore.manager

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.frombefore.R
import kotlinx.android.synthetic.main.activity_init_message.*
import kotlinx.android.synthetic.main.activity_init_message.btn_back
import org.json.JSONArray
import org.json.JSONObject
import java.util.Calendar

class InitMessageActivity : AppCompatActivity() {
    lateinit var internalMap: Map<String, String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init_message)
        val tmp = desc.text
        desc.text = intent.extras?.get("d_day").toString() + "일" + tmp
        btn_back.setOnClickListener {
            finish()
        }
        nextBtn.setOnClickListener {
            if (finalMessageEditText.text.toString() == "") {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("목표를 이룬 나에게 메세지를 작성 해 주세요.")
                builder.setPositiveButton(
                    "OK",
                    DialogInterface.OnClickListener { dialogInterface, id -> }) //아무것도 안하는데 OK만들어주고 싶어서 넣음
                builder.create()
                builder.show()
            } else {
                // 이 액티비티가 받은 인텐트의 내용을 또 보내줘야함
                val received = intent
                val i = Intent(this, MainLayoutActivity::class.java)
                val cal = Calendar.getInstance()

                // 받은 내용으로 Calendar 초기화
                cal.set(Calendar.YEAR, received.extras!!.getInt("year"))
                cal.set(Calendar.MONTH, received.extras!!.getInt("month"))
                cal.set(Calendar.DAY_OF_MONTH, received.extras!!.getInt("dayOfMonth"))

                // 기존 백스택 모두 날려버리기
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                //내부 저장소에 저장하기
                val values = HashMap<String, String>()
                values["finalMessage"] =finalMessageEditText.text.toString()
                values["year"] = received.extras?.getInt("year").toString()
                values["month"] = (received.extras?.getInt("month")?.plus(1)).toString()
                values["dayOfMonth"] = received.extras?.getInt("dayOfMonth").toString()
                values["d_day"] = received.extras?.getInt("d_day").toString()
                values["subject"] = received.extras?.getString("subject")!!

                val ui = UserInfo(this)
                for (i in 0 until UserInfo.keys.size-2) { // 다른방식저장(dayArray, attendArray) 제외
                    val key = UserInfo.keys[i]
                    ui.writeFile(key, values[key]!!)
                }

                //매주 무슨 요일에 공부할지 정보 저장
                //Array<Int>  ->   String   -> JsonArray  이후 JsonArray  ->  String  ->  Array<Int> 가 복잡해서 일단 함수오버로딩 만듬
                ui.writeFile("dayArray", JSONArray(received.extras?.getIntegerArrayList("dayArray")))

                //출석 배열 저장
                ui.writeFile("attendArray", JSONArray(received.extras?.getIntegerArrayList("attendArray")))

                startActivity(i)
            }
        }
        msgBoxInit() // 글쓴 개수 써주는거 init
    }

    private fun msgBoxInit() {
        finalMessageEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val maxWordCnt = 100; // 최대입력수
                val input = finalMessageEditText.text
                finalMessageWordCnt.setText(input.length.toString() + "/" + maxWordCnt)
            }

        })
    }


}