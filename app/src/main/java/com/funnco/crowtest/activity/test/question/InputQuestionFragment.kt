package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.databinding.FragmentInputQuestionBinding


class InputQuestionFragment(private val question: QuestionModel, val pos: Int) : Fragment() {

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


        binding.fragmentQuestionTxtTask.text = question.description
        if(question.isAnswered) {
            binding.fragmentQuestionEtxtAnswer.setText(question.body.singleAnswer?.answer as String? ?: "")
        }
        binding.fragmentQuestionEtxtAnswer.doOnTextChanged { text, start, before, count ->
            question.body.singleAnswer!!.answer = text.toString()
            CurrentTest.getInstanceOfTest().answerAtQuestion(question.body, pos)
        }

        return binding.root
    }


}