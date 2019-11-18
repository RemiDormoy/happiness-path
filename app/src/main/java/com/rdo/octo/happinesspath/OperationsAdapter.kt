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

class OperationsAdapter(
    private val completeProfileClick: () -> Unit,
    private val humaneClick: () -> Unit
) :
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
                else -> R.color.octoBlue
            }
            val imageRes = when (list[position].amount.first()) {
                '-' -> R.drawable.ic_up_arrow
                else -> R.drawable.ic_arrow_down
            }
            val backgroundColor = when (list[position].amount.first()) {
                '-' -> R.color.alizouzBlackWithAlpha01
                else -> R.color.octoBlueWithAlpha01
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
            holder.itemView.lottieNotification.visibility = GONE
            when (position) {
                4 -> {
                    notifsViews.add(holder.itemView.lottieNotification)
                    holder.itemView.setStyle("Complétez votre profil !", R.drawable.ic_gift)
                }
                7 -> holder.itemView.setStyle("Découvrez notre coach financier", R.drawable.ic_favorite_black_24dp)
                12 -> holder.itemView.setStyle("Ajustez votre budget mensuel", R.drawable.ic_assignment_turned_in)

            }
        } else {
            holder.itemView.lottienEndView.visibility = GONE
            notifsViews.add(holder.itemView.lottieNotification2)
            holder.itemView.setOnClickListener {
                humaneClick()
            }
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

private fun View.setStyle(message: String, icon: Int) {
    completeProfileImage.setImageResource(icon)
    completeProfileText.text = message
}

data class Operation(
    val amount: String,
    val name: String
)
