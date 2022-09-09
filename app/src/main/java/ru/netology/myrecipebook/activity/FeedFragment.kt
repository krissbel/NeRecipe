package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.adapter.RecipeAdapter
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.databinding.FragmentFeedBinding

class FeedFragment : Fragment() {

    private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        setFragmentResultListener(
            requestKey = FilterFragment.FILTER_KEY
        ) { requestKey, bundle ->
            if (requestKey != FilterFragment.FILTER_KEY) return@setFragmentResultListener
            val categories = bundle.getParcelableArrayList<ListCategory>(
                FilterFragment.FILTER_KEY
            ) ?: return@setFragmentResultListener
            viewModel.showRecipesByCategories(categories)
        }
        viewModel.navigateToRecipeScreenEvent.observe(this) { initialContent ->
            val direction =
                FeedFragmentDirections.actionFeedFragmentToRecipeFragment(initialContent)
            findNavController().navigate(direction)

        }



        viewModel.navigateFilterEvent.observe(this) {
            val direction =
                FeedFragmentDirections.actionFeedFragmentToFilterFragment()
            findNavController().navigate(direction)

        }
        viewModel.navigateToRecipeDetails.observe(this) {
            val direction = FeedFragmentDirections.actionFeedFragmentToRecipeDetailsFragment()
            findNavController().navigate(direction)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFeedBinding.inflate(layoutInflater, container, false).also { binding ->
        val adapter = RecipeAdapter(viewModel)

        fun showRecipe() {
            viewModel.data.observe(viewLifecycleOwner) { recipe ->
                adapter.submitList(recipe)
            }
        }

        if (viewModel.filterIsActive) {
            binding.showAllRecipes.isVisible = true
        }


        binding.recipeRecyclerView.adapter = adapter


        viewModel.data.observe(viewLifecycleOwner) { recipes ->
            adapter.submitList(recipes)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddRecipeClicked()
        }

        binding.isFavorite.setOnClickListener {
            viewModel.showFavorite()
            showRecipe()
            binding.showAllRecipes.isVisible = true
            binding.fab.isVisible = false
        }

        binding.filter.setOnClickListener {
            viewModel.navigateFilterEvent.call()
            binding.showAllRecipes.isVisible = true
        }



        binding.showAllRecipes.setOnClickListener {
            viewModel.showAllRecipes()
            showRecipe()
            binding.showAllRecipes.isVisible = false
            binding.fab.isVisible = true
        }



        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(quary: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(searchText: String): Boolean {
                if (searchText.isNotBlank()) {
                    viewModel.onSearchClicked(searchText)
                    showRecipe()
                }
                if (searchText.isEmpty() && !viewModel.filterIsActive) {
                    viewModel.showAllRecipes()
                    showRecipe()

                }
                return false
            }

        })

    }.root

}