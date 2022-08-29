package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.databinding.FragmentNewRecipeBinding

class NewRecipeOrEditFragment : Fragment() {

    private val args by navArgs<NewRecipeOrEditFragmentArgs>()
    private val viewModel:RecipeViewModel by activityViewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToStepScreenEvent.observe(this) { initialStep->
            val direction =
                NewRecipeOrEditFragmentDirections.actionToStepFragment(initialStep)
            findNavController().navigate(direction)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNewRecipeBinding.inflate(layoutInflater, container, false).also { binding ->


        binding.authorName.requestFocus()

        binding.authorName.text.toString()
        binding.recipeName.setText(args.initialContent)


        binding.ok.setOnClickListener {
            onOkButtonClicked(binding)
        }
        binding.newStep.setOnClickListener {
            viewModel.onAddStepClicked()
        }
    }.root



    private fun onOkButtonClicked(binding: FragmentNewRecipeBinding) {
        val text = binding.recipeName.text.toString()
        val category = binding.category.selectedItem.toString()
        val authorName = binding.authorName.text.toString()


        if (!text.isNullOrBlank() && !category.isNullOrBlank()) {
            val resultBundleRecipe = Bundle(1)
            resultBundleRecipe.putString(RESULT_KEY_TEXT, text)
            resultBundleRecipe.putString(RESULT_KEY_CATEGORY, category)
            resultBundleRecipe.putString(RESULT_KEY_AUTHOR, authorName)
            setFragmentResult(REQUEST_KEY, resultBundleRecipe)
        }

        findNavController().navigateUp()
    }


    companion object {
        const val RESULT_KEY_TEXT = "recipeNew"
        const val RESULT_KEY_CATEGORY = "category"
        const val RESULT_KEY_AUTHOR = "author"
        const val REQUEST_KEY = "requestKey"
    }
}