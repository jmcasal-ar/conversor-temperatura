package com.example.conversordetemperatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_resultado.*

class ResultadoActivity : AppCompatActivity() {
    private lateinit var txtResultado: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultado)




        setupUI()
    }

    private fun setupUI() {
        txtResultado = findViewById(R.id.txtResultado)

        val bundle = intent.extras
        val resultado = bundle?.getDouble("RESULTADO") ?: 0
        txtResultado.text = resultado.toString()
    }
}