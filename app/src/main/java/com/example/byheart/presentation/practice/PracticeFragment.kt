package com.example.byheart.presentation.practice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.byheart.R
import com.example.byheart.databinding.FragmentPracticeBinding
import com.example.byheart.presentation.quiz.QuizFragment

class PracticeFragment : Fragment() {

    private var _binding: FragmentPracticeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PracticeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPracticeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PracticeViewModel::class.java)

        binding.startQuizButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(QuizFragment::class.simpleName)
                .add(R.id.container, QuizFragment())
                .commit()
        }

        binding.scoreTextView.text = viewModel.getScore(requireContext()).toString()

        parentFragmentManager.addOnBackStackChangedListener {
            binding.scoreTextView.text = viewModel.getScore(requireContext()).toString()
        }
    }
}