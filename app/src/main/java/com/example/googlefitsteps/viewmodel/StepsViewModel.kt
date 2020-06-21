package com.example.googlefitsteps.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.googlefitsteps.R
import com.example.googlefitsteps.adapter.StepsAdapter
import com.example.googlefitsteps.model.Step
import com.example.googlefitsteps.model.Steps

class StepsViewModel : ViewModel() {

    private var steps: Steps? = Steps()
    var adapter: StepsAdapter? = StepsAdapter(R.layout.view_step, this)

    fun fetchSteps(context: Context) {
        steps?.fetchSteps(context)
    }

    val stepList: MutableLiveData<List<Step>>
        get() = steps!!.list

    val ascSort: MutableLiveData<Boolean>
        get() = steps!!.ascSort

    fun setStepsInAdapter(steps: List<Step>?) {
        adapter?.steps = steps
        adapter?.notifyDataSetChanged()
    }

    fun getStepAt(index: Int?): Step? {
        return steps?.list?.value!![index!!]
    }

    fun reverseStepList() {
        steps?.reverseList()
    }
}