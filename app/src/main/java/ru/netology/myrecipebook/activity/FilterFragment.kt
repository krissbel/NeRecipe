package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.myrecipebook.R
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.components.ListCategory
import ru.netology.myrecipebook.databinding.FragmentFilterBinding

class FilterFragment : Fragment() {

    private val filterViewModel: RecipeViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFilterBinding.inflate(layoutInflater, container, false).also { binding ->
        with(binding) {
            europeanCheck.text = getText(R.string.European)
            asianCheck.text = getText(R.string.Asian)
            panasianCheck.text = getText(R.string.PanAsian)
            easternCheck.text = getText(R.string.Eastern)
            americanCheck.text = getText(R.string.American)
            russianCheck.text = getText(R.string.Russian)
            mediterraneanCheck.text = getText(R.string.Mediterranean)

            binding.ok.setOnClickListener {
                onOkClicked(binding)
            }
        }
    }.root

    private fun onOkClicked(binding: FragmentFilterBinding) {

        val categoryList = arrayListOf<ListCategory>()
        var checkedCount = 7

        if (binding.europeanCheck.isChecked) {
            categoryList.add(ListCategory.European)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.asianCheck.isChecked) {
            categoryList.add(ListCategory.Asian)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.panasianCheck.isChecked) {
            categoryList.add(ListCategory.PanAsian)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.easternCheck.isChecked) {
            categoryList.add(ListCategory.Eastern)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.americanCheck.isChecked) {
            categoryList.add(ListCategory.American)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.russianCheck.isChecked) {
            categoryList.add(ListCategory.Russian)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }
        if (binding.mediterraneanCheck.isChecked) {
            categoryList.add(ListCategory.Mediterranean)
            filterViewModel.filterIsActive = true
            checkedCount + 1
        } else {
            --checkedCount
        }

        if (checkedCount != 0) {
            filterViewModel.filterIsActive = true
            filterViewModel.showRecipesByCategories(categoryList)
            val resultBundle = Bundle(1)
            resultBundle.putParcelableArrayList(FILTER_KEY, categoryList)
            setFragmentResult(FILTER_KEY, resultBundle)

            findNavController().popBackStack()
        } else findNavController().popBackStack()
    }

    companion object {
        const val FILTER_KEY = "filterKey"
    }
}





