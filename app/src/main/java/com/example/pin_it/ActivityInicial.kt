package com.example.pin_it

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class ActivityInicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagina_inicial)


        val button2 = findViewById<Button>(R.id.btn_notas)
        button2.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val button = findViewById<Button>(R.id.btn_inicial)
        button.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}