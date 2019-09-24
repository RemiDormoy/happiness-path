package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_wallet.view.*

class WalletsAdapter(private val clickListener: () -> Unit, private val lockListener: () -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = listOf(
        Wallet(R.drawable.ic_transfert, "Transferts"),
        Wallet(R.drawable.ic_lock_black_24dp, "Mes comptes"),
        Wallet(R.drawable.ic_lock_black_24dp, "Dashboards"),
        Wallet(R.drawable.ic_lock_black_24dp, "Travel to London"),
        Wallet(R.drawable.ic_lock_black_24dp, "Playstation"),
        Wallet(R.drawable.ic_lock_black_24dp, "Cinema savings")
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
            holder.itemView.lottieNotificationLock.visibility = GONE
        } else {
            holder.itemView.walletContainer.setOnClickListener {  lockListener() }
            holder.itemView.lottieNotificationLock.visibility = VISIBLE
        }
    }

}

data class Wallet(
    val icon: Int,
    val name: String
)
