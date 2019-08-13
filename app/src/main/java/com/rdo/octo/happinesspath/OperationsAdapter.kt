package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_operation.view.*
import kotlinx.android.synthetic.main.cell_wallet.view.*

class OperationsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = listOf(
        Operation("+200,00€", "Birthday Tom"),
        Operation("-40,00€", "NYC"),
        Operation("-56,75€", "New Car"),
        Operation("+123,90€", "Travel to London"),
        Operation("-89,00€", "Playstation"),
        Operation("+3500,90€", "Salary"),
        Operation("-30,87€", "Cinema savings")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_operation,
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
        val colorRes = when (list[position].amount.first()) {
            '-' -> android.R.color.darker_gray
            else -> R.color.alizouzGreen
        }
        val imageRes = when (list[position].amount.first()) {
            '-' -> R.drawable.ic_arrow_downward_black_24dp
            else -> R.drawable.ic_arrow_upward_black_24dp
        }
        val color = ContextCompat.getColor(holder.itemView.context, colorRes)
        holder.itemView.operationNameTextView.text = list[position].name
        holder.itemView.operationAmountTextView.text = list[position].amount
        holder.itemView.operationAmountTextView.setTextColor(color)
        holder.itemView.imageView7.setImageResource(imageRes)
    }

}

data class Operation(
    val amount: String,
    val name: String
)
