package com.example.pin_it.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pin_it.CellClickListener
import com.example.pin_it.R
import com.example.pin_it.Adapters.NotasAdapter.NoteViewHolder
import com.example.pin_it.Entities.Notas

class NotasAdapter internal constructor(context: Context, private val cellClickListener: CellClickListener) : RecyclerView.Adapter<NotasAdapter.NoteViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<Notas>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return NoteViewHolder(itemView)
    }

    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteItemViewTitle: TextView = itemView.findViewById(R.id.ViewTitle);
        val noteItemViewContent: TextView = itemView.findViewById(R.id.textViewContent);

    }
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val current = notes[position]

        holder.noteItemViewTitle.text = current.title
        holder.noteItemViewContent.text = current.content

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(current)
        }

    }
    internal fun setNotes(notes: List<Notas>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun getItemCount() = notes.size
}