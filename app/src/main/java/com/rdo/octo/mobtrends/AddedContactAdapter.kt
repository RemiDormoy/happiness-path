package com.rdo.octo.mobtrends

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cell_contact_added.view.*

class AddedContactAdapter(
    private val makeAppearance: () -> Unit,
    private val makeDisappear: () -> Unit
) :
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
        if (position == list.size - 1) {
            holder.itemView.imageAddedContainer.scaleX = 0f
            holder.itemView.imageAddedContainer.scaleY = 0f
            holder.itemView.imageAdded.setImageResource(contact.picture)
            holder.itemView.animate().scaleX(1f).scaleY(1f).start()
        } else {
            holder.itemView.imageAdded.setImageResource(contact.picture)
        }
    }

    fun add(contact: Contact) {
        list.add(contact)
        //notifyItemRangeChanged(list.size - 1, 1) fuck rémi trouve un truc ici wesh
        notifyDataSetChanged()
        if (list.size == 1) {
            makeAppearance()
        }
    }

    fun remove(contact: Contact) {
        val index = list.indexOf(contact)
        list.remove(contact)
        notifyItemRemoved(index)
        if (list.isEmpty()) {
            makeDisappear()
        }
    }

}
