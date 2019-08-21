package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_contact.view.*

class ContactAdapter(private val clickHandler: (Contact, Float) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            clickHandler(contact, holder.itemView.y)
        }
    }

}

data class Contact(
    @DrawableRes val picture: Int,
    val name: String
)