package ru.netology.myrecipebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.components.Step
import ru.netology.myrecipebook.databinding.StepListItemBinding

class StepAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<Step, StepAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(
        private val binding: StepListItemBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var step: Step


        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveStepClicked(step)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditStepClicked(step)
                            true
                        }
                        else -> false
                    }
                }

            }
        }

        init {

            binding.options.setOnClickListener {
                listener.onEditStepClicked(step)
            }

        }

        fun bind(step: Step) {
            this.step = step

            with(binding) {
                textStep.text = step.stepText
                options.setOnClickListener { popupMenu.show() }
            }
        }

    }

    private object DiffCallback : DiffUtil.ItemCallback<Step>() {
        override fun areContentsTheSame(oldItem: Step, newItem: Step) =
            oldItem == newItem


        override fun areItemsTheSame(oldItem: Step, newItem: Step) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = StepListItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = getItem(position)
        holder.bind(step)

    }

}


