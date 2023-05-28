package com.funnco.crowtest.activity.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.databinding.CustomQuestionNumberBinding

class QuestionNumberAdapter(private val questionsAdapter: QuestionsAdapter, val questionsViewPager: ViewPager2) : Adapter<QuestionNumberAdapter.QuestionNumberViewHolder>() {

    class QuestionNumberViewHolder(itemView: View, val questionsViewPager: ViewPager2) : ViewHolder(itemView){
        lateinit var binding: CustomQuestionNumberBinding

        fun bind(item: QuestionModel, position: Int, isOpened: Boolean){
            binding = CustomQuestionNumberBinding.bind(itemView)
            binding.itemQuestionNumber.text = "${position+1}"

            binding.root.setOnClickListener {
                questionsViewPager.setCurrentItem(position, true)
            }

            if(isOpened){
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.card_back))
                binding.itemQuestionNumber.setTextColor(ContextCompat.getColor(binding.root.context, R.color.solved_question))
                binding.root.strokeWidth = 8
            } else {
                binding.root.strokeWidth = 0
            }

            if(item.isAnswered && !isOpened){
                Log.i(this.javaClass.simpleName,"Changed to answered type at position: $position")
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.solved_question))
                binding.itemQuestionNumber.setTextColor(ContextCompat.getColor(binding.root.context, R.color.unsolved_question))
            }

            if(!item.isAnswered){
                Log.i(this.javaClass.simpleName,"Changed to answered type at position: $position")
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.card_back))
                binding.itemQuestionNumber.setTextColor(ContextCompat.getColor(binding.root.context, R.color.solved_question))
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionNumberViewHolder {
        return QuestionNumberViewHolder(CustomQuestionNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false).root, questionsViewPager)
    }

    override fun onBindViewHolder(holder: QuestionNumberViewHolder, position: Int) {
        holder.bind(questionsAdapter.listOfQuestions[position], position, questionsAdapter.currentSelectedPosition.position == position)
    }

    override fun getItemCount(): Int {
        return questionsAdapter.listOfQuestions.size
    }
}