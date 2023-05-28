package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test.question.accordance_adapters.AccordanceSecondListAdapter
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.databinding.FragmentOrderQuestionBinding
import kotlin.streams.toList


class OrderQuestionFragment(private val question: QuestionModel, val pos: Int) :
    Fragment() {

    private var totalEntries = 0;

    private lateinit var binding: FragmentOrderQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderQuestionBinding.bind(
            inflater.inflate(
                R.layout.fragment_order_question,
                container,
                false
            )
        )

        binding.fragmentQuestionTxtTask.text = question.description

        // Split lists
        val listOfAnswers =
            question.body.arrayAnswer!!.stream().map { answer -> answer.answer as String }.toList()

        totalEntries = listOfAnswers.size

        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.recyclerView.adapter =
            AccordanceSecondListAdapter(listOfAnswers)
        attachDragAndDropToRecycler()

        return binding.root
    }

    // drag and drop for second class
    private fun attachDragAndDropToRecycler() {

        val touchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.END or ItemTouchHelper.START,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition

                // Swapping answers
                val tempAnswer = question.body.arrayAnswer!![startPosition].answer as String
                question.body.arrayAnswer!![startPosition].answer =
                    question.body.arrayAnswer!![endPosition].answer as String
                question.body.arrayAnswer!![endPosition].answer = tempAnswer

                recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
                return false
            }


            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                when (actionState) {
                    ItemTouchHelper.ACTION_STATE_IDLE -> {
                        question.isAnswered = true
                        CurrentTest.getInstanceOfTest().answerAtQuestion(question.body, pos)
                    }
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

        }

        ItemTouchHelper(touchCallback).attachToRecyclerView(binding.recyclerView)
    }
}