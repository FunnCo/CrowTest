package com.funnco.crowtest.activity.test.question.accordance_adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.databinding.ItemAccrodanceTaskBinding

class AccordanceFirstListAdapter(
    val listOfItems: List<String>,
) : Adapter<AccordanceFirstListAdapter.AccordanceFirstListItemViewHolder>() {

    init{
        setHasStableIds(true)
    }

    class AccordanceFirstListItemViewHolder(itemView: View) : ViewHolder(itemView), ResizeableViewHolder {
        lateinit var binding: ItemAccrodanceTaskBinding
        var basicMargin: Int = 0

        override fun getHeight(): Int {
            binding.root.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED)
            return binding.root.height
        }

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
        fun bind(item: String) {
            binding = ItemAccrodanceTaskBinding.bind(itemView)

            binding.itemTxtAccordanceAnswerContent.text = item

            binding.root.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccordanceFirstListItemViewHolder {
        return AccordanceFirstListItemViewHolder(
            ItemAccrodanceTaskBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            ).root
        )
    }

    override fun onBindViewHolder(holder: AccordanceFirstListItemViewHolder, position: Int) {
        Log.i("ComplexTagToFind", "Bound item")
        holder.bind(
            listOfItems[position]
        )
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    fun adjustItemHeight(position: Int) {
        Log.i("ComplexTagToFind", "Changed sizes")
        notifyItemChanged(position)
    }

    fun adjustAllItemsHeight() {
        Log.i("ComplexTagToFind", "Changed sizes")
        notifyItemRangeChanged(0, listOfItems.size)
    }
}