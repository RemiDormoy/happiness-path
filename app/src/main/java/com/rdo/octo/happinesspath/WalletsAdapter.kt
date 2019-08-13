package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_wallet.view.*

class WalletsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = listOf(
        Wallet("€ 328", "Birthday Tom"),
        Wallet("€ 145", "NYC"),
        Wallet("€ 1200", "New Car"),
        Wallet("€ 320", "Travel to London"),
        Wallet("€ 411", "Playstation"),
        Wallet("€ 90", "Cinema savings")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_wallet,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val colorRes = when (position % 3) {
            0 -> R.color.alizouzPurple
            1 -> R.color.alizouzBlue
            else -> R.color.alizouzBlueLight
        }
        val color = ContextCompat.getColor(holder.itemView.context, colorRes)
        holder.itemView.walletAmountTextView.text = list[position].amount
        holder.itemView.walletNameTextView.text = list[position].name
        holder.itemView.walletContainer.setBackgroundColor(color)
    }

}

data class Wallet(
    val amount: String,
    val name: String
)
