package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.SimpleItemAnimator
import com.funnco.crowtest.R
import com.funnco.crowtest.activity.test.question.accordance_adapters.AccordanceFirstListAdapter
import com.funnco.crowtest.activity.test.question.accordance_adapters.AccordanceSecondListAdapter
import com.funnco.crowtest.activity.test.question.accordance_adapters.ResizeableViewHolder
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.databinding.FragmentAccordanceQuestionBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.streams.toList


class AccordanceQuestionFragment(private val question: QuestionModel, val pos: Int) :
    Fragment() {

    private var totalEntries = 0;

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

        binding.fragmentQuestionTxtTask.text = question.description

        // Split lists
        val firstListOfAnswers =
            question.body.arrayAnswer!!.stream().map { answer -> answer.content }.toList()
        val secondListOfAnswers =
            question.body.arrayAnswer!!.stream().map { answer -> answer.answer as String }.toList()

        totalEntries = secondListOfAnswers.size

        (binding.recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        binding.recyclerView.adapter =
            AccordanceFirstListAdapter(firstListOfAnswers)
        binding.recyclerView2.adapter =
            AccordanceSecondListAdapter(secondListOfAnswers)
        syncRecyclers()
        attachDragAndDropToSecondRecycler()

        return binding.root
    }

    // drag and drop for second class
    private fun attachDragAndDropToSecondRecycler() {

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
//                        remeasureAllViewsInRecyclers()
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

    private fun remeasureAllViewsInRecyclers() {

            for (i in 0 until totalEntries) {

                var viewHolder1 = binding.recyclerView.findViewHolderForAdapterPosition(i) as ResizeableViewHolder?
                var viewHolder2 = binding.recyclerView2.findViewHolderForAdapterPosition(i) as ResizeableViewHolder?

                if(viewHolder1 == null || viewHolder2 == null){
                    continue
                }

                val height1 = viewHolder1.getHeight()
                val height2 = viewHolder2.getHeight()



                if (height1 < height2) {
                    viewHolder1.resize(height2)
                    Log.i("remeasureAllViewsInRecyclers", "fired change of first list with difference: ${height1-height2}")
                } else {
                    viewHolder2.resize(height1)
                    Log.i("remeasureAllViewsInRecyclers", "fired change of second list with difference: ${height1-height2}")
                }
        }
    }
}