package com.funnco.crowtest.activity.main.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test_result.TestResultActivity
import com.funnco.crowtest.common.model.response.ResponseSolvedTestInfo
import com.funnco.crowtest.databinding.ItemFinishedTestBinding

class FinishedTestsAdapter(var listOfItems: List<ResponseSolvedTestInfo>) :
    Adapter<FinishedTestsAdapter.FinishedTestViewHolder>() {

    init {
        listOfItems = listOfItems.reversed()
    }

    class FinishedTestViewHolder(itemView: View) : ViewHolder(itemView) {
        private lateinit var binding: ItemFinishedTestBinding

        fun bind(item: ResponseSolvedTestInfo) {
            binding = ItemFinishedTestBinding.bind(itemView)

            binding.txtItemTestHeading.text = item.title
            binding.itemTestDeadline.text = "Дата прохождения: ${item.getPrettySolveDate()}"
            binding.itemTestSoveTime.text = "Время прохождения: ${item.getPrettyTimeSolving()}"
            binding.txtItemTestMark.text = item.mark.toString()


            binding.root.setOnClickListener {

                val intent = Intent(
                    binding.root.context,
                    TestResultActivity::class.java
                )
                intent.putExtra("test_id", item.testId)
                binding.root.context.startActivity(intent)
            }


            binding.materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    when (item.mark!!.toInt()) {
                        5 -> R.color.excellent_mark
                        4 -> R.color.good_mark
                        3 -> R.color.satisfying_mark
                        else -> R.color.bad_mark
                    }
                )
            )

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishedTestViewHolder {
        return FinishedTestViewHolder(
            ItemFinishedTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).root
        )
    }

    override fun onBindViewHolder(holder: FinishedTestViewHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}