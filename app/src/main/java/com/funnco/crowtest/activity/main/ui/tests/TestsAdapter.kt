package com.funnco.crowtest.activity.main.ui.tests

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.funnco.crowtest.activity.test_details.TestDetailsActivity
import com.funnco.crowtest.common.model.response.TestInfoModel
import com.funnco.crowtest.databinding.ItemAvailableTestBinding

class TestsAdapter(var listOfItems: List<TestInfoModel>) : Adapter<TestsAdapter.AvailableTestViewHolder>() {

    init {
        listOfItems = listOfItems.reversed()
    }

    class AvailableTestViewHolder(itemView: View) : ViewHolder(itemView){

        lateinit var binding: ItemAvailableTestBinding

        fun bind(item: TestInfoModel){
            binding = ItemAvailableTestBinding.bind(itemView)

            binding.txtItemTestHeading.text = item.title
            binding.itemTestDeadline.text =  "Крайний срок сдачи: ${item.getPrettyDeadLineDate()}"
            binding.itemTestSoveTime.text = "Время прохождения: ${item.getPrettyTime()}"

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, TestDetailsActivity::class.java)
                intent.putExtra("test_id", item.testId)
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