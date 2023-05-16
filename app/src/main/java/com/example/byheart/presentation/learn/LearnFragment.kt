package com.example.byheart.presentation.learn

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.byheart.R
import com.example.byheart.databinding.FragmentLearnBinding
import com.example.byheart.presentation.definition.DefinitionFragment
import com.example.byheart.utils.Utils

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.definitionButton.setOnClickListener {
            hideKeyboard()
            val word = binding.wordEditText.text.toString()
            if (word.trim().isNotEmpty()) {
                showDefinitionFragment(word)
            } else {
                Toast.makeText(requireContext(), "Word is not valid!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.randomButton.setOnClickListener {
            hideKeyboard()
            showDefinitionFragment(Utils.getRandomEnglishWordFromJson(requireContext()))
        }
    }

    private fun showDefinitionFragment(word: String) {
        parentFragmentManager.beginTransaction()
            .addToBackStack(DefinitionFragment::class.simpleName)
            .add(R.id.container, DefinitionFragment.newInstance(word))
            .commit()
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = requireActivity().currentFocus
        currentFocus?.let {
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}