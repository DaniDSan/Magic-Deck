package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CardSearchFragment : Fragment(R.layout.fragment_card_search) {
    private val adapter: RecyclerViewAdapter by lazy { RecyclerViewAdapter(cardViewModel) }
    private val cardViewModel: CardViewModel by lazy {
        val deckDatabase = DeckDatabase.getDatabase(requireContext())
        val deckRepository = DeckRepository(deckDatabase.deckDao())
        CardViewModel(deckRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val etCardName: EditText = view.findViewById(R.id.etCardName)
        val btnLoadCard: Button = view.findViewById(R.id.btnLoadCard)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnLoadCard.setOnClickListener {
            val cardName = etCardName.text.toString().trim()

            // Si el texto esta vacio limpiar el viewmodel entero
            if (cardName.isNotEmpty()) {
                cardViewModel.loadCard(cardName)
                cardViewModel.cardFound.observe(viewLifecycleOwner) { cardFound ->
                    if (!cardFound)
                        Toast.makeText(requireContext(), "Card not found!", Toast.LENGTH_SHORT)
                            .show()
                }
                cardViewModel.cardList.observe(viewLifecycleOwner) { card ->
                    adapter.submitList(card)
                }
            } else
                Toast.makeText(requireContext(), "Please enter a card name", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}