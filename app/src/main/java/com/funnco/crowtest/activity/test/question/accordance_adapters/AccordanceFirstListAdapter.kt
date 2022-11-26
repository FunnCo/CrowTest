package com.funnco.crowtest.activity.test.question.accordance_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.databinding.ItemAccrodanceTaskBinding

class AccordanceFirstListAdapter(val listOfItems: List<AnswerModel>): Adapter<AccordanceFirstListAdapter.AccordanceFirstListItemViewHolder>() {
    class AccordanceFirstListItemViewHolder(itemView: View) : ViewHolder(itemView){
        lateinit var binding: ItemAccrodanceTaskBinding

        fun bind(item: AnswerModel){
            binding = ItemAccrodanceTaskBinding.bind(itemView)

            binding.itemTxtAccordanceAnswerContent.text = item.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccordanceFirstListItemViewHolder {
        return AccordanceFirstListItemViewHolder(ItemAccrodanceTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: AccordanceFirstListItemViewHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}