package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recyclerView) }
    private val adapter: RecyclerViewAdapter by lazy { RecyclerViewAdapter(cardViewModel) }
    private val cardViewModel: CardViewModel by lazy {
        val deckDatabase = DeckDatabase.getDatabase(this)
        val deckRepository = DeckRepository(deckDatabase.deckDao())
        CardViewModel(deckRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val etCardName: EditText = findViewById(R.id.etCardName)
        val btnLoadCard: Button = findViewById(R.id.btnLoadCard)

        btnLoadCard.setOnClickListener {
            val cardName = etCardName.text.toString().trim()

            // Si el texto esta vacio limpiar el viewmodel entero
            if (cardName.isNotEmpty()) {
                cardViewModel.loadCard(cardName)
                cardViewModel.cardFound.observe(this) { cardFound ->
                    if (!cardFound)
                        Toast.makeText(this, "Card not found!", Toast.LENGTH_SHORT).show()
                }
                cardViewModel.cardList.observe(this) { card ->
                    adapter.submitList(card)
                }
            } else
                Toast.makeText(this, "Please enter a card name", Toast.LENGTH_SHORT).show()
        }
    }
}