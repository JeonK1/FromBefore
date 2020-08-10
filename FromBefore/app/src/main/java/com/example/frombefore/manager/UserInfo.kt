package com.example.frombefore.manager

import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.io.*
import java.lang.StringBuilder

class UserInfo {
    companion object {
        // 키 종류
        val keys =
            mutableListOf<String>(
                "finalMessage",
                "year",
                "month",
                "dayOfMonth",
                "d_day",
                "subject",
                "dayArray",
                "attendArray"
            )

        val calendarKeys = arrayOf<String>("year", "month", "dayOfMonth")

        // 출석 상태
        val ATTEND_NO_NEED = -2; // -2 : 출석할 필요 없음
        val ATTEND_NOT_DONE_NO_MSG = -1; // -1 : 출석 아직 안함(자기반성 메시지 아직 안보냄)
        val ATTEND_NOT_DONE_YES_MSG = 0; // 0 : 출석 아직 안함
        val ATTEND_DONE = 1; // 1 : 출석 완료

        // 항목 종류 및 키값
        val subjects: Map<String, String> = mapOf<String, String>(
            "대학 입시" to "college",
            "자격증 시험" to "license",
            "국가 고시" to "country",
            "취업 준비" to "employ",
            "일상 공부" to "study"
        )
        val subjectTitles = subjects.keys.toTypedArray()

        // userInfo getter/setter
        private val userInfo:JSONObject = makeInitialUserInfo()

        private fun makeInitialUserInfo():JSONObject {
            var ret:JSONObject = JSONObject()

            // add initial or essential data
            ret = FileManager.readFile()

            return ret
        }

        fun get(key: String):Any? {
            if (userInfo.has(key)) {
                return userInfo[key]
            }

            return null
        }

        fun set(key: String, value:Any) {
            var map: HashMap<String, Any> = HashMap<String, Any>()

            if (key != null)
                map[key] = value

            set(map)
        }

        fun set(map: HashMap<String, Any>) {
            for ((key, value) in map) {
                userInfo.put(key, value)
            }

            FileManager.saveFile(userInfo)
        }

        fun calendar():JSONObject {
            var json:JSONObject = JSONObject()

            for (key in calendarKeys) {
                json.put(key, userInfo.getInt(key))
            }

            return json
        }

        fun calendarStr():String {
            var str:String = ""

            for ((idx, key) in calendarKeys.iterator().withIndex()) {
                str += userInfo.getString(key)

                if (idx != calendarKeys.size - 1) {
                    str += "-"
                }
            }

            return str
        }

        fun has(key: String):Boolean {
            return userInfo.has(key)
        }

    }
}