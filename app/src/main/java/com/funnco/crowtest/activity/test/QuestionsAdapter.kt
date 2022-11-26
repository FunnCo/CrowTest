package com.funnco.crowtest.activity.test


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.funnco.crowtest.activity.test.question.*
import com.funnco.crowtest.common.model.question_models.*

class QuestionsAdapter(
    val listOfQuestions: List<BaseQuestion>,
    fragmentManager: FragmentManager,
    private val recyclerOfNumbers: RecyclerView,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    val currentSelectedPosition = SelectedPosition(object : SelectedPosition.ChangeListener {
        override fun onChange(pos: Int) {
            recyclerOfNumbers.scrollToPosition(pos)
            recyclerOfNumbers.adapter?.notifyDataSetChanged()
        }
    })

    override fun getItemCount(): Int {
        return listOfQuestions.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (listOfQuestions[position].type) {
            "one_answer" -> {
                OneCorrectQuestionFragment(listOfQuestions[position] as OneAnswerQuestion, position)
            }
            "multiple_answer" -> {
                MultipleCorrectQuestionFragment(listOfQuestions[position] as MultipleAnswerQuestion, position)
            }
            "input_answer" -> {
                InputQuestionFragment(listOfQuestions[position] as InputQuestion, position)
            }
            else -> {
                AccordanceQuestionFragment(listOfQuestions[position] as AccordanceQuestion, position)
            }
        }
    }


}
