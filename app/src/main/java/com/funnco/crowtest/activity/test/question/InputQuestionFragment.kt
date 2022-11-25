package com.funnco.crowtest.activity.test.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.funnco.crowtest.R
import com.funnco.crowtest.common.model.question_models.InputQuestion
import com.funnco.crowtest.common.model.question_models.OneAnswerQuestion
import com.funnco.crowtest.databinding.FragmentInputQuestionBinding


class InputQuestionFragment(private val question: InputQuestion) : Fragment() {

    private lateinit var binding: FragmentInputQuestionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInputQuestionBinding.bind(inflater.inflate(R.layout.fragment_input_question, container, false))

        binding.fragmentQuestionTxtTask.text = question.task

        return binding.root
    }


}