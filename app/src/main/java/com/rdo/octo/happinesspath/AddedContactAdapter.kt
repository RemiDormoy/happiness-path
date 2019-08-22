package com.rdo.octo.happinesspath

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_contact.view.*
import kotlinx.android.synthetic.main.cell_contact_added.view.*

class AddedContactAdapter(private val makeAppearance: () -> Unit, private val makeDisappear: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val list: MutableList<Contact> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.cell_contact_added,
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
        holder.itemView.imageAdded.setImageResource(contact.picture)
    }

    fun add(contact: Contact) {
        list.add(contact)
        notifyDataSetChanged()
        if (list.size == 1) {
            makeAppearance()
        }
    }

    fun remove(contact: Contact) {
        list.remove(contact)
        notifyDataSetChanged()
        if (list.isEmpty()) {
            makeDisappear()
        }
    }

}
