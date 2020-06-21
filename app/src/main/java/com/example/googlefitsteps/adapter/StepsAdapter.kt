package com.example.googlefitsteps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.googlefitsteps.BR
import com.example.googlefitsteps.adapter.StepsAdapter.ViewHolder
import com.example.googlefitsteps.model.Step
import com.example.googlefitsteps.viewmodel.StepsViewModel

class StepsAdapter(@param:LayoutRes private val layoutId: Int,
                   private val viewModel: StepsViewModel) : RecyclerView.Adapter<ViewHolder>() {

    var steps: List<Step>? = null
        set(value) {
            field = value
        }

    override fun getItemCount(): Int {
        return steps?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater,
                viewType,
                parent,
                false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewModel, position)
    }

    override fun getItemViewType(position: Int): Int {
        return layoutId
    }

    inner class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: StepsViewModel, position: Int?) {
            binding.setVariable(BR.viewModel, viewModel)
            binding.setVariable(BR.position, position)
            binding.executePendingBindings()
        }
    }
}