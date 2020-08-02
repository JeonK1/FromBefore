package com.example.frombefore.message

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.frombefore.R
import kotlinx.android.synthetic.main.fragment_message.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MessageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MessageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_send_msg.setOnClickListener {
            //메시지 전송 버튼
            val secondIntent = Intent(activity, MsgSendToMeActivity::class.java)
            startActivity(secondIntent)
        }

        /* 나에게 온 편지가 있을 때*/
        // TODO
        if(1==1){
            btn_receive_letter.setImageResource(R.drawable.letter_exist)
            btn_receive_letter.setOnClickListener {
                //나에게 온 편지가 있는 경우 버튼 클릭 했을 때
                //TODO : 서버에서 해당 편지 내용 가져와서 읽기
                val intent = Intent(activity, ReceiveMessageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
