package com.funnco.crowtest.activity.test.question.accordance_adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.databinding.ItemAccrodanceTaskBinding

class AccordanceSecondListAdapter(
    val listOfItems: List<AnswerModel>,
    val secondRecycler: RecyclerView
) : RecyclerView.Adapter<AccordanceSecondListAdapter.AccordanceSecondListItemViewHolder>() {
    class AccordanceSecondListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var binding: ItemAccrodanceTaskBinding

        fun bind(item: AnswerModel, heightToAdjust: Int) {
            binding = ItemAccrodanceTaskBinding.bind(itemView)
            binding.itemTxtAccordanceAnswerContent.text = item.content


            binding.root.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED)

            val extraPadding = heightToAdjust - binding.root.measuredHeight

            Log.i(this.javaClass.simpleName, "first height: $heightToAdjust, itemView height: ${binding.root.measuredHeight}, difference: $extraPadding")
            if(extraPadding > 0) {
                val layoutParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.setMargins(
                    layoutParams.leftMargin,
                    layoutParams.topMargin,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin + extraPadding
                )
                binding.root.requestLayout()
            }
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
            listOfItems[position],
            secondRecycler.findViewHolderForAdapterPosition(position)!!.itemView.height
        )
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}