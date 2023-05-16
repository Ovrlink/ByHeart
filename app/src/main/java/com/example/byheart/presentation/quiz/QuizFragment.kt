package com.example.byheart.presentation.quiz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.byheart.R
import com.example.byheart.databinding.FragmentQuizBinding
import com.example.byheart.utils.Utils
import java.util.Random

class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var correctOption = -1
    private var allowChoice = true

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)

        val listOfOptionLayouts = listOf(
            binding.wordOptionOneLayout,
            binding.wordOptionTwoLayout,
            binding.wordOptionThreeLayout,
            binding.wordOptionFourLayout,
            binding.wordOptionFiveLayout
        )

        val viewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        viewModel.generateQuiz(requireContext())

        viewModel.correctWordLiveData().observe(viewLifecycleOwner) { correctWord ->
            binding.questionTextView.text =
                correctWord.meanings.first().definitions.first().definition
            correctOption = Random(System.currentTimeMillis()).nextInt(5) + 1
            getTextViewByOption(correctOption).text = correctWord.word
            fillOtherOptions()
            listOfOptionLayouts.forEach {
                it.background =
                    ResourcesCompat.getDrawable(resources, R.drawable.option_background, null)
            }
            binding.nextButton.visibility = View.INVISIBLE
            allowChoice = true
        }

        viewModel.errorLiveData().observe(viewLifecycleOwner) { isError ->
            binding.errorLayoutTextView.visibility = if (isError) View.VISIBLE else View.GONE
            binding.content.visibility = if (!isError) View.VISIBLE else View.GONE
        }

        viewModel.progressLiveData().observe(viewLifecycleOwner) { visibility ->
            binding.progressBar.visibility = visibility
        }

        listOfOptionLayouts.forEachIndexed { index, view ->
            view.setOnClickListener {
                if (allowChoice) {
                    onOptionClick(view, index + 1)
                    if (!allowChoice)
                        viewModel.addOneScore(requireContext())
                }
            }
        }

        binding.nextButton.setOnClickListener {
            viewModel.generateQuiz(requireContext())
        }

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun onOptionClick(view: View, option: Int) {
        if (option == correctOption) {
            allowChoice = false
            binding.nextButton.visibility = View.VISIBLE
            view.background = ResourcesCompat.getDrawable(
                resources, R.drawable.option_correct_background, null
            )
        } else {
            view.background = ResourcesCompat.getDrawable(
                resources, R.drawable.option_wrong_background, null
            )
        }
    }

    private fun getTextViewByOption(option: Int): TextView {
        return when (option) {
            1 -> binding.wordOptionOneTextView
            2 -> binding.wordOptionTwoTextView
            3 -> binding.wordOptionThreeTextView
            4 -> binding.wordOptionFourTextView
            else -> binding.wordOptionFiveTextView
        }
    }

    private fun fillOtherOptions() {
        for (i in 0..5) {
            if (i != correctOption) {
                getTextViewByOption(i).text = Utils.getRandomEnglishWordFromJson(requireContext())
            }
        }
    }

}