package ru.netology.myrecipebook.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.myrecipebook.RecipeViewModel
import ru.netology.myrecipebook.databinding.FragmentNewRecipeBinding
import ru.netology.myrecipebook.databinding.FragmentNewStepBinding

class NewStepFragment:Fragment() {

   private val args by navArgs<NewStepFragmentArgs>()
  // private val viewModel by viewModels<RecipeViewModel>(ownerProducer = ::requireParentFragment)


   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ) = FragmentNewStepBinding.inflate(layoutInflater, container, false).also { binding ->


      binding.edit.requestFocus()
    //  binding.edit.setText(args.initialStep)

      binding.ok.setOnClickListener {
         onOkButtonClicked(binding)
      }
   }.root

   private fun onOkButtonClicked(binding: FragmentNewStepBinding) {

      val text = binding.edit.text.toString()

      if (!text.isNullOrBlank()) {
         val resultBundleStep = Bundle(1)
         resultBundleStep.putString(RESULT_KEY_STEP, text)
         setFragmentResult(REQUEST_KEY_STEP, resultBundleStep)
      }

      findNavController().popBackStack()

   }

   companion object {
      const val RESULT_KEY_STEP = "resultKeyStep"
      const val REQUEST_KEY_STEP = "requestKeyStep"


   }
}