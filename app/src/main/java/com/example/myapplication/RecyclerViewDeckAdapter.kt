package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerViewDeckAdapter() :
    RecyclerView.Adapter<RecyclerViewDeckAdapter.ViewHolder>() {
    var deckList = mutableListOf<Deck>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDeckName: TextView = itemView.findViewById(R.id.tvDeckName)
        val ivCardImage: ImageView = itemView.findViewById(R.id.ivCardImage)
        val tvCardQuantity: TextView = itemView.findViewById(R.id.tvCardQuantity)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_decks_elements, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDeckName.text = deckList.get(position).name
        Glide.with(holder.itemView.context).load(deckList.get(position).imageUri)
            .into(holder.ivCardImage)
        holder.tvCardQuantity.text = deckList.get(position).quantity.toString()
    }

    override fun getItemCount(): Int = deckList.size
}
