package ru.netology.myrecipebook.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.databinding.FragmentRecipeDetailsBinding

class RecipeDetailsFragment:Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentRecipeDetailsBinding.inflate(layoutInflater, container, false)
        .also { binding ->

            val recipe = viewModel.currentRecipe.value

            val popupMenu by lazy {
                PopupMenu(layoutInflater.context, binding.options).apply {
                    inflate(R.menu.options_recipe)
                    setOnMenuItemClickListener { menuItem ->
                        when (menuItem.itemId) {
                            R.id.remove -> {
                                if (recipe != null) {
                                    viewModel.onRemoveClicked(recipe)
                                }
                                true
                            }
                            R.id.edit -> {
                                if (recipe != null) {
                                    viewModel.onEditClicked(recipe)
                                }
                                true
                            }
                            else -> false
                        }

                    }
                }
            }
            binding.options.setOnClickListener { popupMenu.show() }

            viewModel.data.observe(viewLifecycleOwner) { recipes ->
                val changedRecipes = recipes.filter { it.id == recipe?.id }
                if (changedRecipes.isNotEmpty()) {
                    binding.render(changedRecipes.first())

                } else {
                    findNavController().navigateUp()
                }
            }

            setFragmentResultListener(
                requestKey = NewRecipeOrEditFragment.REQUEST_KEY
            ) { requestKey, bundle ->
                if (requestKey != NewRecipeOrEditFragment.REQUEST_KEY) return@setFragmentResultListener
                val newPostContent = bundle.getString(NewRecipeOrEditFragment.RESULT_KEY_TEXT)
                    ?: return@setFragmentResultListener
                viewModel.onSaveClicked(newPostContent, NewRecipeOrEditFragment.RESULT_KEY_AUTHOR, NewRecipeOrEditFragment.RESULT_KEY_CATEGORY)
            }


            recipe?.let {
                binding.options.setOnClickListener { popupMenu.show() }
            }



//            viewModel.navigateToPostContentScreenEvent.observe(viewLifecycleOwner) { initialContent ->
//                val direction =
//                    PostContentDetailsFragmentDirections.toPostContentFragment(
//                        initialContent
//                    )
//                findNavController().navigate(direction)
//            }
        }.root


    private fun FragmentRecipeDetailsBinding.render(recipe: Recipe) {
        recipe.let {
            authorName.text = recipe?.author
            category.text = recipe?.category
            textRecipe.text = recipe?.nameRecipe
        }

    }
}