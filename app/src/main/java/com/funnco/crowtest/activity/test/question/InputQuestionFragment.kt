package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.AnswerModel
import com.funnco.crowtest.common.model.question_models.InputQuestion
import com.funnco.crowtest.common.model.question_models.OneAnswerQuestion
import com.funnco.crowtest.databinding.FragmentInputQuestionBinding


class InputQuestionFragment(private val question: InputQuestion, val pos: Int) : Fragment() {

    private lateinit var binding: FragmentInputQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputQuestionBinding.bind(
            inflater.inflate(
                R.layout.fragment_input_question,
                container,
                false
            )
        )


        binding.fragmentQuestionTxtTask.text = question.task
        if(question.isAnswered) {
            binding.fragmentQuestionEtxtAnswer.setText(question.answer!!.content)
        }
        binding.fragmentQuestionEtxtAnswer.doOnTextChanged { text, start, before, count ->
            if(text.isNullOrBlank()) {
                CurrentTest.getInstanceOfTest().answerAtQuestion(null, pos)
            } else {
                CurrentTest.getInstanceOfTest().answerAtQuestion(AnswerModel(text.toString()), pos)
            }
        }

        return binding.root
    }


}