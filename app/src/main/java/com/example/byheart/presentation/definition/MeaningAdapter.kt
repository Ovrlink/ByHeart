package com.example.byheart.presentation.definition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.byheart.R
import com.example.byheart.data.model.Meaning


class MeaningAdapter(private val meanings: List<Meaning>) : RecyclerView.Adapter<MeaningAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val partOfSpeechTextView: TextView
        val definitionTextView: TextView
        val exampleTextView: TextView
        val synonymsTextView: TextView
        val antonymsTextView: TextView
        val synonymsLayout: LinearLayout
        val antonymsLayout: LinearLayout

        init {
            partOfSpeechTextView = view.findViewById(R.id.partOfSpeechTextView)
            definitionTextView = view.findViewById(R.id.definitionTextView)
            exampleTextView = view.findViewById(R.id.exampleTextView)
            synonymsTextView = view.findViewById(R.id.synonymsTextView)
            antonymsTextView = view.findViewById(R.id.antonymsTextView)
            synonymsLayout = view.findViewById(R.id.synonymsLayout)
            antonymsLayout = view.findViewById(R.id.antonymsLayout)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.meaning_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val meaning = meanings[position]
        val definition = meaning.definitions.first()

        viewHolder.partOfSpeechTextView.text = meaning.partOfSpeech
        viewHolder.definitionTextView.text = definition.definition

        if (definition.example != null) {
            viewHolder.exampleTextView.text = definition.example
        } else {
            viewHolder.exampleTextView.visibility = View.GONE
        }

        if (meaning.synonyms.isNotEmpty()) {
            viewHolder.synonymsTextView.text = meaning.synonyms.concat()
        } else {
            viewHolder.synonymsLayout.visibility = View.GONE
        }

        if (meaning.antonyms.isNotEmpty()) {
            viewHolder.antonymsTextView.text = meaning.antonyms.concat()
        } else {
            viewHolder.antonymsLayout.visibility = View.GONE
        }

    }

    override fun getItemCount() = meanings.size

    fun List<String>.concat(): String = joinToString(", ")
}