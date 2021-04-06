package com.example.pin_it

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pin_it.Adapters.NotasAdapter
import com.example.pin_it.Entities.Notas
import com.example.pin_it.ViewModel.NotasViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

var PARAM_ID = "id"
var PARAM1_TITLE = "title"
var PARAM2_CONTENT = "content"

class MainActivity : AppCompatActivity(), CellClickListener {
    private lateinit var notasViewModel: NotasViewModel
    private val newWordActivityRequestCode = 1
    private val UpdateNoteActivityRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = NotasAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //view model
        notasViewModel = ViewModelProvider(this).get(NotasViewModel::class.java)
        notasViewModel.allNotes.observe(this, { notes ->
            notes?.let { adapter.setNotes(it) }
        })

        //Fab
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNotas::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null && data.action == "REMOVE") {
            var id = Integer.parseInt(data?.getStringExtra(PARAM_ID))
            notasViewModel.deleteById(id)
            Toast.makeText(this, "Nota eliminada.", Toast.LENGTH_SHORT).show()
            return
        }


        if(requestCode ==  newWordActivityRequestCode) {
            if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
                var titulo = data?.getStringExtra(AddNotas.EXTRA_REPLY_TITLE).toString()
                var content = data?.getStringExtra(AddNotas.EXTRA_REPLY_CONTENT).toString()
                var note = Notas(title = titulo, content = content)
                notasViewModel.insert(note)
                Toast.makeText(this, "Nota guardada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Campo(s) Vazio(s): não inserido.",
                    Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == UpdateNoteActivityRequestCode) {
            if (requestCode == UpdateNoteActivityRequestCode && resultCode == Activity.RESULT_OK) {
                var titulo = data?.getStringExtra(PARAM1_TITLE).toString()
                var content = data?.getStringExtra(PARAM2_CONTENT).toString()
                var id = Integer.parseInt(data?.getStringExtra(PARAM_ID))
                notasViewModel.updateById(titulo, content, id)
                Toast.makeText(applicationContext, "Nota alterada.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "Campo(s) Vazio(s): não alterado.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCellClickListener(data: Notas) {
        val id = data.id.toString()
        val titulo = data.title
        val conteudo = data.content
        val intent = Intent(this, EditNotas::class.java).apply {
            putExtra(PARAM_ID, id)
            putExtra(PARAM1_TITLE, titulo)
            putExtra(PARAM2_CONTENT, conteudo)
        }
        startActivityForResult(intent, UpdateNoteActivityRequestCode)
    }
}