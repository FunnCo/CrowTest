package com.funnco.crowtest.activity.main.ui.tests

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test_details.TestDetailsActivity
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.databinding.ItemAvailableTestBinding
import com.squareup.picasso.Picasso

class TestsAdapter(val listOfItems: List<TestModel>) : Adapter<TestsAdapter.AvailableTestViewHolder>() {
    class AvailableTestViewHolder(itemView: View) : ViewHolder(itemView){

        lateinit var binding: ItemAvailableTestBinding

        fun bind(item: TestModel){
            binding = ItemAvailableTestBinding.bind(itemView)

            binding.txtItemTestHeading.text = item.heading
            binding.itemTestDeadline.text =  "Крайний срок сдачи: ${item.deadLineDate}"
            binding.itemTestSoveTime.text = "Время прохождения: ${item.timeForSolving} мин"

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, TestDetailsActivity::class.java)
                intent.putExtra("test_id", item.id)
                binding.root.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvailableTestViewHolder {
        return AvailableTestViewHolder(ItemAvailableTestBinding.inflate(LayoutInflater.from(parent.context), parent, false).root)
    }

    override fun onBindViewHolder(holder: AvailableTestViewHolder, position: Int) {
        holder.bind(listOfItems[position])
    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }
}