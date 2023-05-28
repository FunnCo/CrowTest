package com.funnco.crowtest.activity.test


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.funnco.crowtest.activity.test.question.*
import com.funnco.crowtest.common.model.common.QuestionModel

class QuestionsAdapter(
    val listOfQuestions: List<QuestionModel>,
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
        return when(listOfQuestions[position].typeId){
            // это id с сервака
            "ea0d604c-9ca3-4d0c-be97-2a9e4095f240" -> {
                OneCorrectQuestionFragment(listOfQuestions[position], position)
            }
            "3e357bd3-cc70-4690-abcf-3ae9d8512cb8" -> {
                MultipleCorrectQuestionFragment(listOfQuestions[position], position)
            }
            "36c190d8-9bc9-49ef-8d92-7575789c4646" -> {
                InputQuestionFragment(listOfQuestions[position], position)
            }
            "cf897012-9353-4c9f-917a-21ce3aa99dd1" -> {
                OrderQuestionFragment(listOfQuestions[position], position)
            }
            else -> AccordanceQuestionFragment(listOfQuestions[position], position)
        }
    }
}

// one answer 73c0a763-f19d-4695-8765-901956ef0582
// multiple c41fb035-2304-4f35-b7e6-9aa587b63166
// text 425b1e48-7560-46e8-9c31-920e1aec5136
// accordance 43c5b0e1-d25c-4cfd-bcdd-2e26772f85ef

