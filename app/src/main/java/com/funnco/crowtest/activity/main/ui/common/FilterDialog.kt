package com.funnco.crowtest.activity.main.ui.common

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.funnco.crowtest.common.model.response.ResponseSolvedTestInfo
import com.funnco.crowtest.common.model.response.TestInfoModel
import com.funnco.crowtest.common.model.response.UserInfoModel
import com.funnco.crowtest.databinding.FragmentTestFilterDialogBinding
import com.funnco.crowtest.repository.Repository
import kotlin.streams.toList

class FilterDialog(
    val listToFilter: List<TestInfoModel>,
    val marks: List<ResponseSolvedTestInfo>? = null,
    val callback: (info : List<TestInfoModel>, marks: List<ResponseSolvedTestInfo>?) -> Unit
) : AppCompatDialogFragment() {
    lateinit var binding: FragmentTestFilterDialogBinding
    lateinit var dialog: AlertDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(activity)

        binding = FragmentTestFilterDialogBinding.inflate(layoutInflater)

        dialogBuilder.setView(binding.root)

        dialog = dialogBuilder.create()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        var listOfSubjects =
            listToFilter.stream().map { entry -> entry.subjectName!! }.toList().distinct()
        var listOfAuthorIds =
            listToFilter.stream().map { entry -> entry.authorId!! }.toList().distinct()
        var listOfMarks = marks
            ?.stream()
            ?.map { entry -> entry.mark }
            ?.toList()
            ?.sortedDescending()
            ?.distinct()
            ?.stream()
            ?.map { it -> it.toString() }
            ?.toList()

        if (marks == null) {
            binding.textView18.visibility = View.GONE
            binding.holderFilterMark.visibility = View.GONE
        } else {
            binding.atxtFilterMark.setSimpleItems(listOfMarks!!.toTypedArray())
        }

        var listOfAuthors = emptyList<UserInfoModel>()
        Repository.getManyAuthors(listOfAuthorIds) {
            listOfAuthors = it
            binding.atxtFilterAuthor.setSimpleItems(it.stream().map { entry -> "${entry.firstname} ${entry.lastname.substring(0,1)}.${if (entry.patronymic != null) entry.patronymic.substring(0,1) + "." else ""}"}.toList().toTypedArray())
        }

        binding.atxtFilterSubject.setSimpleItems(listOfSubjects.toTypedArray())

        binding.btnFilterApply.setOnClickListener {
            var filteredInfoList = listToFilter.toList()

            if(binding.atxtFilterSubject.text.trim().isNotBlank()) {
                filteredInfoList = filteredInfoList.filter { it.subjectName == binding.atxtFilterSubject.text.trim().toString() }
            }

            if(binding.atxtFilterAuthor.text.trim().isNotBlank()) {
                filteredInfoList = filteredInfoList.filter {
                    it.authorId == (listOfAuthors.find { author ->
                        "${author.firstname} ${author.lastname.substring(0,1)}.${author.patronymic.substring(0,1)}." == binding.atxtFilterAuthor.text.trim().toString()
                    }?.userId ?: false)
                }
            }

            if(binding.atxtFilterMark.text.trim().isNotBlank()){
                filteredInfoList = filteredInfoList.filter { testModel ->
                    val correspondingMark = marks!!.find { it.testId == testModel.testId }
                    correspondingMark?.mark == binding.atxtFilterMark.text.toString().toInt()
                }
            }

            var filteredMarksList : List<ResponseSolvedTestInfo>? = null
            if(marks != null) {
                filteredMarksList = marks.filter {mark ->
                    filteredInfoList.any { it.testId == mark.testId }
                }
            }

            callback(filteredInfoList, filteredMarksList)
            dialog.cancel()
        }

        return binding.root
    }

}