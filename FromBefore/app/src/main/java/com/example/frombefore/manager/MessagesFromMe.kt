package com.example.frombefore.manager

import org.json.JSONArray
import org.json.JSONObject

class MessagesFromMe {
    companion object {
        private val messagesFromMe: JSONArray = makeInitialUserInfo()

        private fun makeInitialUserInfo(): JSONArray {
            var data: String = FileManager.readFile(FileManager.MESSAGES_FROM_ME)

            // add initial or essential data
            return if (data.isNotEmpty()) {
                JSONArray(data)
            } else {
                JSONArray()
            }
        }

        fun all(): JSONArray {
            return messagesFromMe
        }

        fun add(message: JSONObject) {
            messagesFromMe.put(message)
            FileManager.saveFile(messagesFromMe, FileManager.MESSAGES_FROM_ME)
        }

        fun get(dday: Int): JSONObject {
            for (i in 0 until messagesFromMe.length()) {
                val msg = messagesFromMe.getJSONObject(i)
                if (dday == msg.get("dday")) {
                    return msg
                }
            }
            return JSONObject()
        }
    }
}