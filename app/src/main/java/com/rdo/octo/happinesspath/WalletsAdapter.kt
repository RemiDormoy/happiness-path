package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_wallet.view.*

class WalletsAdapter(private val clickListener: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = listOf(
        Wallet(R.drawable.ic_transfert, "Transferts"),
        Wallet(R.drawable.ic_mes_comptes, "Mes comptes"),
        Wallet(R.drawable.ic_dashboard, "Dashboards"),
        Wallet(R.drawable.ic_mes_comptes, "Travel to London"),
        Wallet(R.drawable.ic_mes_comptes, "Playstation"),
        Wallet(R.drawable.ic_mes_comptes, "Cinema savings")
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
        holder.itemView.walletAmountTextView.setImageResource(list[position].icon)
        holder.itemView.walletNameTextView.text = list[position].name
        holder.itemView.walletContainer.setBackgroundColor(color)
        if (position == 0) {
            holder.itemView.walletContainer.setOnClickListener {  clickListener() }
        } else {
            holder.itemView.walletContainer.setOnClickListener {  }
        }
    }

}

data class Wallet(
    val icon: Int,
    val name: String
)
