package com.rdo.octo.mobtrends

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_contact.view.*

class ContactAdapter(private val clickHandler: (Contact, Float) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list = listOf(
        Contact(R.drawable.rafaelle, "Raf"),
        Contact(R.drawable.alizee, "Alizouz"),
        Contact(R.drawable.brm, "Brandone"),
        Contact(R.drawable.bej, "Bénoit"),
        Contact(R.drawable.juliette, "Juju"),
        Contact(R.drawable.bme, "Benoit"),
        Contact(R.drawable.marie, "Maribibi"),
        Contact(R.drawable.megane, "Megazouz"),
        Contact(R.drawable.danyboy, "Danyboy"),
        Contact(R.drawable.cyril, "Cyril")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_contact,
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
        val contact = list[position]
        holder.itemView.contactNameTextView.text = contact.name
        holder.itemView.contactImageView.setImageResource(contact.picture)
        holder.itemView.contactContainer.setOnClickListener {
            val newRes = if (contact.isChecked) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val typeface =  holder.itemView.resources.getFont(R.font.dmsans)
                    holder.itemView.contactNameTextView.typeface = typeface
                }
                holder.itemView.contactNameTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.black))
                R.drawable.ic_plus
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val typeface =  holder.itemView.resources.getFont(R.font.dmsansbold)
                    holder.itemView.contactNameTextView.typeface = typeface
                }
                holder.itemView.contactNameTextView.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimaryDark))
                R.drawable.ic_moins
            }
            holder.itemView.plusLessButton.animate().scaleY(0f).setDuration(250).scaleX(0f).withEndAction {
                holder.itemView.plusLessButton.setImageResource(newRes)
                holder.itemView.plusLessButton.animate().setDuration(250).scaleY(01f).scaleX(1f).start()
            }.start()
            contact.isChecked = contact.isChecked.not()
            clickHandler(contact, holder.itemView.y)
        }
    }

}

data class Contact(
    @DrawableRes val picture: Int,
    val name: String,
    var isChecked: Boolean = false
)
