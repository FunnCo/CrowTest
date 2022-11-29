package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test.question.accordance_adapters.AccordanceFirstListAdapter
import com.funnco.crowtest.activity.test.question.accordance_adapters.AccordanceSecondListAdapter
import com.funnco.crowtest.common.model.question_models.AccordanceQuestion
import com.funnco.crowtest.databinding.FragmentAccordanceQuestionBinding
import java.util.Collections


class AccordanceQuestionFragment(private val question: AccordanceQuestion, val pos: Int) :
    Fragment() {

    private lateinit var binding: FragmentAccordanceQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccordanceQuestionBinding.bind(
            inflater.inflate(
                R.layout.fragment_accordance_question,
                container,
                false
            )
        )

        binding.fragmentQuestionTxtTask.text = question.task

        binding.recyclerView.adapter = AccordanceFirstListAdapter(question.firstListOfAnswers)
        binding.recyclerView2.adapter = AccordanceSecondListAdapter(question.secondListOfAnswers, binding.recyclerView)

        syncRecyclers()
        attachDragAndDropToSecondRecycler()

        return binding.root
    }

    // drag and drop for second class
    private fun attachDragAndDropToSecondRecycler() {

        val touchCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.END or ItemTouchHelper.START, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition

                Collections.swap(question.secondListOfAnswers, startPosition, endPosition)
                recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
                return false
            }


            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                when(actionState){
                    ItemTouchHelper.ACTION_STATE_IDLE -> {
                        binding.recyclerView2.adapter = AccordanceSecondListAdapter(question.secondListOfAnswers, binding.recyclerView)
                        question.isAnswered = true
                        CurrentTest.getInstanceOfTest().answerAtQuestion(question.secondListOfAnswers, pos)
                    }
                }
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

        }

        ItemTouchHelper(touchCallback).attachToRecyclerView(binding.recyclerView2)

    }

    // magic recycler sync
    private fun syncRecyclers() {

        val listOfListeners = mutableListOf<RecyclerView.OnScrollListener>()

        listOfListeners.add(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.recyclerView2.removeOnScrollListener(listOfListeners[1])
                binding.recyclerView2.scrollBy(dx, dy)
                binding.recyclerView2.addOnScrollListener(listOfListeners[1])
            }
        })

        listOfListeners.add(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                binding.recyclerView.removeOnScrollListener(listOfListeners[0])
                binding.recyclerView.scrollBy(dx, dy)
                binding.recyclerView.addOnScrollListener(listOfListeners[0])
            }
        })
        binding.recyclerView.addOnScrollListener(listOfListeners[0])
        binding.recyclerView2.addOnScrollListener(listOfListeners[1])
    }
}