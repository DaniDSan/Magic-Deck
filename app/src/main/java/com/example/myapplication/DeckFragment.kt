package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class DeckFragment : Fragment(R.layout.fragment_decks) {
    val adapter: RecyclerViewDeckAdapter by lazy { RecyclerViewDeckAdapter() }
    val deckDao: DeckDao by lazy { DeckDatabase.getDatabase(requireContext()).deckDao() }
    val deckRepository: DeckRepository by lazy { DeckRepository(deckDao) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            adapter.deckList = deckRepository.getAllCards()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDeck)
        recyclerView.adapter = adapter
    }
}