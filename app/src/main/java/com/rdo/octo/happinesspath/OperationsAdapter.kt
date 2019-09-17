package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_complete_profile.view.*
import kotlinx.android.synthetic.main.cell_end_of_operations.view.*
import kotlinx.android.synthetic.main.cell_operation.view.*

class OperationsAdapter(private val completeProfileClick: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list1 = listOf(
        Operation("+200,00€", "Birthday Tom"),
        Operation("-40,00€", "NYC"),
        Operation("-56,75€", "New Car"),
        Operation("+123,90€", "Travel to London"),
        Operation("-89,00€", "Playstation"),
        Operation("+3500,90€", "Salary"),
        Operation("-30,87€", "Cinema savings"),
        Operation("+200,00€", "Birthday Tom"),
        Operation("-40,00€", "NYC"),
        Operation("-56,75€", "New Car"),
        Operation("+123,90€", "Travel to London"),
        Operation("-89,00€", "Playstation"),
        Operation("+3500,90€", "Salary"),
        Operation("-30,87€", "Cinema savings")
    )

    private val notifsViews = mutableListOf<View>()

    var list = list1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val res = when (viewType) {
            0 -> R.layout.cell_end_of_operations
            1 -> R.layout.cell_complete_profile
            else -> R.layout.cell_operation
        }
        val view = LayoutInflater.from(parent.context).inflate(
            res,
            parent,
            false
        )
        return object : RecyclerView.ViewHolder(
            view
        ) {}
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position < list.size && position != 4 && position != 7 && position != 12) {
            val colorRes = when (list[position].amount.first()) {
                '-' -> android.R.color.darker_gray
                else -> R.color.alizouzGreen
            }
            val imageRes = when (list[position].amount.first()) {
                '-' -> R.drawable.ic_up_arrow
                else -> R.drawable.ic_arrow_down
            }
            val backgroundColor = when (list[position].amount.first()) {
                '-' -> R.color.alizouzBlackWithAlpha01
                else -> R.color.alizouzGreenWithAlpha01
            }
            val color = ContextCompat.getColor(holder.itemView.context, colorRes)
            holder.itemView.operationNameTextView.text = list[position].name
            holder.itemView.operationAmountTextView.text = list[position].amount
            holder.itemView.operationAmountTextView.setTextColor(color)
            holder.itemView.imageView7.setImageResource(imageRes)
            holder.itemView.imageView7.setBackgroundColor(
                holder.itemView.resources.getColor(
                    backgroundColor,
                    null
                )
            )
            holder.itemView.setOnClickListener {

            }
        } else if (position < list.size) {
            holder.itemView.setOnClickListener {
                completeProfileClick()
            }
            notifsViews.add(holder.itemView.lottieNotification)
        } else {
            holder.itemView.lottienEndView.visibility = GONE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == list.size) {
            0
        } else if (position == 4 || position == 7 || position == 12) {
            1
        } else {
            2
        }
    }

    fun filter(i: Int) {
        this.list = when (i) {
            0 -> list1
            1 -> list1.filter { it.amount.startsWith("-") }
            else -> list1.filter { it.amount.startsWith("+") }
        }
        notifyDataSetChanged()
    }

    fun showNotifs(showNotifs: Boolean) {
        notifsViews.forEach {
            try {
                it.visibility = if (showNotifs) VISIBLE else GONE
            } catch (e: Throwable) {
                // Do nothing
            }
        }
    }

}

data class Operation(
    val amount: String,
    val name: String
)
