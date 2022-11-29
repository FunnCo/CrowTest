package com.funnco.crowtest.activity.main.ui.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test_details.TestDetailsActivity
import com.funnco.crowtest.activity.test_result.TestResultActivity
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.databinding.ItemFinishedTestBinding
import com.squareup.picasso.Picasso

class FinishedTestsAdapter(val listOfItems: List<TestModel>) :
    Adapter<FinishedTestsAdapter.FinishedTestViewHolder>() {
    class FinishedTestViewHolder(itemView: View) : ViewHolder(itemView) {
        private lateinit var binding: ItemFinishedTestBinding

        fun bind(item: TestModel) {
            binding = ItemFinishedTestBinding.bind(itemView)

            binding.txtItemTestHeading.text = item.heading
            binding.itemTestDeadline.text = "Дата прохождения: ${item.solveDate}"
            binding.itemTestSoveTime.text = "Время прохождения: ${item.timeUsedToSolve} мин"
            binding.txtItemTestMark.text = item.mark

            binding.root.setOnClickListener {
                binding.root.context.startActivity(
                    Intent(
                        binding.root.context,
                        TestResultActivity::class.java
                    )
                )
            }


            binding.materialCardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    when(item.mark!!.toInt()){
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