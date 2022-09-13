package ru.netology.myrecipebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.databinding.RecipeListItemBinding

class RecipeAdapter(
    private val interactionListener: RecipeInteractionListener
) : ListAdapter<Recipe, RecipeAdapter.ViewHolder>(DiffCallback) {


    inner class ViewHolder(
        private val binding: RecipeListItemBinding,
        listener: RecipeInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe


        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }

            }
        }

        init {
            binding.root.setOnClickListener {
                listener.openRecipe(recipe)
            }
            binding.options.setOnClickListener {
                listener.onEditClicked(recipe)
            }
            binding.isFavorite.setOnClickListener {
                listener.onFavoriteClicked(recipe)
            }

        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                authorName.text = recipe.author
                category.text = recipe.category
                textRecipe.text = recipe.nameRecipe
                isFavorite.isChecked = recipe.isFavorite
                options.setOnClickListener { popupMenu.show() }
            }
        }

    }

    private object DiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem


        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemBinding.inflate(inflater, parent, false)

        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)

    }

}

