package com.funnco.crowtest.activity.test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.question_models.BaseQuestion
import com.funnco.crowtest.databinding.CustomQuestionNumberBinding

class QuestionNumberAdapter(private val questionsAdapter: QuestionsAdapter, val questionsViewPager: ViewPager2) : Adapter<QuestionNumberAdapter.QuestionNumberViewHolder>() {

    class QuestionNumberViewHolder(itemView: View, val questionsViewPager: ViewPager2) : ViewHolder(itemView){
        lateinit var binding: CustomQuestionNumberBinding

        fun bind(item: BaseQuestion, position: Int, isOpened: Boolean){
            binding = CustomQuestionNumberBinding.bind(itemView)
            binding.itemQuestionNumber.text = "${position+1}"

            binding.root.setOnClickListener {
                questionsViewPager.setCurrentItem(position, true)
            }

            if(isOpened){
                binding.root.strokeWidth = 8
            } else {
                binding.root.strokeWidth = 0
            }

            if(item.isAnswered && !isOpened){
                binding.root.setCardBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.solved_question))
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