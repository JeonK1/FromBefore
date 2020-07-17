package com.example.idealmood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frombefore.MessageData
import com.example.frombefore.R

class MessageCardAdapter(val items:ArrayList<MessageData>)
    : RecyclerView.Adapter<MessageCardAdapter.MessageCardViewHolder>(){

    interface OnItemClickListener{
        fun OnItemClick(holder: MessageCardViewHolder, view: View, data:MessageData, position:Int)
    }

    var itemClickListener : OnItemClickListener?=null

    inner class MessageCardViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var ddayView: TextView = itemView.findViewById(R.id.message_card_dday)
        var textView:TextView = itemView.findViewById(R.id.message_card_text)
        init{
            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(this, it, items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageCardViewHolder {
        val v:View = LayoutInflater.from(parent.context).inflate(R.layout.message_card_item, parent, false)
        return MessageCardViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MessageCardViewHolder, position: Int) {
        holder.ddayView.text = "D-" + items[position].dday
        holder.textView.text = items[position].text
    }


}