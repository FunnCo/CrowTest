package com.funnco.crowtest.activity.test.question.accordance_adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.funnco.crowtest.databinding.ItemAccrodanceTaskBinding

class AccordanceSecondListAdapter(
    val listOfItems: List<String>,
) : RecyclerView.Adapter<AccordanceSecondListAdapter.AccordanceSecondListItemViewHolder>() {

    init{
        setHasStableIds(true)
    }

    class AccordanceSecondListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        ResizeableViewHolder {
        lateinit var binding: ItemAccrodanceTaskBinding
        var basicMargin: Int = 0

        override fun resize(heightToAdjust: Int) {
            val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
            if(basicMargin == 0){
                basicMargin = layoutParams.bottomMargin
            }
            layoutParams.setMargins(
                layoutParams.leftMargin,
                layoutParams.topMargin,
                layoutParams.rightMargin,
                basicMargin + binding.root.height - heightToAdjust
            )
            binding.root.requestLayout()
        }

        override fun getHeight(): Int {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
            return binding.root.measuredHeight
        }

        fun bind(item: String) {
            binding = ItemAccrodanceTaskBinding.bind(itemView)
            binding.itemTxtAccordanceAnswerContent.text = item
            binding.itemOmgAccordanceDrag.visibility = View.VISIBLE
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccordanceSecondListItemViewHolder {
        return AccordanceSecondListItemViewHolder(
            ItemAccrodanceTaskBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ).root
        )

    }

    override fun onBindViewHolder(holder: AccordanceSecondListItemViewHolder, position: Int) {
        holder.bind(
            listOfItems[position]
        )
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}