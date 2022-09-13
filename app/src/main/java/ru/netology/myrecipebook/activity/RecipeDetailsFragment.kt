package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.adapter.StepAdapter
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.databinding.FragmentRecipeDetailsBinding
import ru.netology.myrecipebook.databinding.StepListItemBinding

class RecipeDetailsFragment : Fragment() {

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
                requestKey = NewRecipeFragment.REQUEST_KEY
            ) { requestKey, bundle ->
                if (requestKey != NewRecipeFragment.REQUEST_KEY) return@setFragmentResultListener
                val newContentRecipe = bundle.getString(NewRecipeFragment.RESULT_KEY_AUTHOR)
                    ?: return@setFragmentResultListener
                val category = bundle.getString(NewRecipeFragment.RESULT_KEY_CATEGORY)
                    ?: return@setFragmentResultListener
                val recipeName = bundle.getString(NewRecipeFragment.RESULT_KEY_TEXT)
                    ?: return@setFragmentResultListener

                viewModel.onSaveClicked(newContentRecipe, recipeName, category)
            }
            viewModel.navigateToRecipeScreenEvent.observe(viewLifecycleOwner) {
                val direction =
                    RecipeDetailsFragmentDirections.actionRecipeDetailsFragmentToRecipeFragment(
                        recipe
                    )
                findNavController().navigate(direction)
            }


            recipe?.let {
                binding.options.setOnClickListener { popupMenu.show() }
            }
            val adapter = StepAdapter(viewModel)
            binding.listSteps.adapter = adapter


            fun showSteps() {
                viewModel.stepData.observe(viewLifecycleOwner) { steps ->
                    adapter.submitList(steps)

                }
            }

            viewModel.showStepByRecipeId(recipe?.id)
            showSteps()


        }.root


    private fun FragmentRecipeDetailsBinding.render(recipe: Recipe) {
        recipe.let {
            authorName.text = recipe.author
            category.text = recipe.category
            textRecipe.text = recipe.nameRecipe
        }

    }
}