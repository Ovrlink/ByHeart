package com.example.byheart.presentation.definition

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.byheart.data.model.Meaning
import com.example.byheart.databinding.FragmentDefinitionBinding

class DefinitionFragment : Fragment() {

    companion object {
        private const val WORD_KEY = "WORD_KEY"

        fun newInstance(word: String): DefinitionFragment =
            DefinitionFragment().apply { arguments = bundleOf(WORD_KEY to word) }
    }

    private var _binding: FragmentDefinitionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDefinitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this).get(DefinitionViewModel::class.java)

        val listOfMeanings = mutableListOf<Meaning>()

        val adapter = MeaningAdapter(listOfMeanings)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.getDefinition(requireArguments().getString(WORD_KEY, ""))

        viewModel.errorLiveData().observe(viewLifecycleOwner) { isError ->
            if (isError) {
                binding.errorLayoutTextView.visibility = View.VISIBLE
                binding.scrollView.visibility = View.GONE
                binding.titleWordTextView.visibility = View.GONE
                binding.transcriptionTextView.visibility = View.GONE
            } else {
                binding.errorLayoutTextView.visibility = View.GONE
                binding.scrollView.visibility = View.VISIBLE
                binding.titleWordTextView.visibility = View.VISIBLE
                binding.transcriptionTextView.visibility = View.VISIBLE
            }
        }

        viewModel.progressLiveData().observe(viewLifecycleOwner) { visibility ->
            binding.progressBar.visibility = visibility
        }

        viewModel.wordLiveData().observe(viewLifecycleOwner) { word ->
            binding.titleWordTextView.text = word.word
            binding.transcriptionTextView.text = word.phonetics.last().text
            listOfMeanings.clear()
            listOfMeanings.addAll(word.meanings)
            adapter.notifyDataSetChanged()
        }

        binding.nextWordButton.setOnClickListener {
            viewModel.defineRandomWord(requireContext())
        }
        binding.backButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

}