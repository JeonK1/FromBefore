package com.example.frombefore

import android.graphics.Rect
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.idealmood.MessageCardAdapter
import kotlinx.android.synthetic.main.content_gif_test.*

class GifTestActivity : AppCompatActivity() {

    class MessageRecyclerItemDecorator(private val horizontalSpacing: Int,
                                       private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = horizontalSpacing
            outRect.right = horizontalSpacing

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = verticalSpacing
            }

            outRect.bottom = verticalSpacing
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gif_test)

        val rabbit = findViewById<ImageView>(R.id.gif_image)
//        val mailTextList = findViewById<TextView>(R.id.mail_text_list);
        val gifImage = GlideDrawableImageViewTarget(rabbit)
        Glide.with(this).load(R.drawable.socium1).into(gifImage)
        rabbit.isVisible = true

        rabbit.setOnClickListener{
            rabbit.isVisible = false
//            mailTextList.text = "D-44 참... 힘들었었지... 시발..."
            messageScrollView.isVisible = true
        }

        val array:ArrayList<MessageData> = ArrayList<MessageData>()
        array.add(MessageData(10, "10일 남았다 뿌슝 빠슝"))
        array.add(MessageData(20, "20일 남았다 뿌슝 빠슝"))
        array.add(MessageData(30, "30일 남았다 뿌슝 빠슝"))

        messageCardRecyclerView.isActivated = false
        messageCardRecyclerView?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter:MessageCardAdapter = MessageCardAdapter(array)
        messageCardRecyclerView?.adapter = adapter
        messageCardRecyclerView?.addItemDecoration(MessageRecyclerItemDecorator(30, 15))
    }
}