package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


// Probar con Interfaz de submitList
class RecyclerViewAdapter(
    private val cardViewModel: CardViewModel
) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
        var cardList = mutableListOf<CardData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCardName: TextView = itemView.findViewById(R.id.tvCardName)
        val ivCardImage: ImageView = itemView.findViewById(R.id.ivCardImage)
        val ivCardBackImage: ImageView = itemView.findViewById(R.id.ivCardBackImage)
        val ibAddButton: ImageView = itemView.findViewById(R.id.ibAddButton)
        val tvCardQuantity: TextView = itemView.findViewById(R.id.etCardQuantity)
        val ibRemoveButton: ImageView = itemView.findViewById(R.id.ibRemoveButton)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_elements, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val card = cardList[position]
        val imgToLoad = cardViewModel.getCardImage(card)
        val backImgToLoad = cardViewModel.getCardBackImage(card)

        holder.tvCardName.text = card.name

        Glide.with(holder.itemView.context).load(imgToLoad).into(holder.ivCardImage)
        holder.ivCardBackImage.visibility =
            if (cardViewModel.hasCardBackImage(card)) View.VISIBLE else View.GONE
        Glide.with(holder.itemView.context)
            .load(backImgToLoad)
            .into(holder.ivCardBackImage)

        setListeners(holder, position)

        cardViewModel.cardList.observe(holder.itemView.context as LifecycleOwner) { quantity ->
            holder.tvCardQuantity.text = cardViewModel.getCardQuantity(position).toString()
        }
    }

    override fun getItemCount(): Int = cardList.size

    fun submitList(newList: List<CardData>) {
        cardList = newList.toMutableList()
        notifyDataSetChanged()
    }

    private fun setListeners(holder: ViewHolder, position: Int){
        holder.ibAddButton.setOnClickListener {
            cardViewModel.addCard(position)
        }
        holder.ibRemoveButton.setOnClickListener {
            cardViewModel.removeCard(position)
        }
    }
}