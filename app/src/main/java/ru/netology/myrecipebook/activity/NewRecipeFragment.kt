package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Spinner
import androidx.core.view.get
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.components.Recipe
import ru.netology.myrecipebook.databinding.FragmentNewRecipeBinding

class NewRecipeFragment : Fragment() {

    private val args by navArgs<NewRecipeFragmentArgs>()
    private val viewModel: RecipeViewModel by activityViewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToStepScreenEvent.observe(this) { initialStep ->
            val direction =
                NewRecipeFragmentDirections.actionToStepFragment(initialStep)
            findNavController().navigate(direction)
            viewModel.onSaveStepClicked(initialStep)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentNewRecipeBinding.inflate(layoutInflater, container, false).also { binding ->

        binding.authorName.requestFocus()
        binding.authorName.setText(args.initialContent?.author)
        binding.recipeName.setText(args.initialContent?.nameRecipe)
        if (binding.category.selectedItem.toString() != args.initialContent?.category) {
            binding.category.setSelection(
                resources.getStringArray(R.array.Category).indexOf(args.initialContent?.category)
            )
        }

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



        if (!text.isNullOrBlank()) {
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
